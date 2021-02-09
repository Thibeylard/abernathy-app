package com.mediscreen.abernathyapp.app.filters;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.node.ObjectNode;
import com.google.common.io.CharStreams;
import com.netflix.zuul.ZuulFilter;
import com.netflix.zuul.context.RequestContext;
import com.netflix.zuul.exception.ZuulException;
import org.slf4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cloud.netflix.ribbon.apache.RibbonApacheHttpResponse;
import org.springframework.stereotype.Component;

import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.nio.charset.StandardCharsets;
import java.util.HashMap;

import static com.mediscreen.abernathyapp.app.constants.ApiExposedOperations.GET_ALL;
import static com.mediscreen.abernathyapp.app.constants.ApiExposedOperations.GET_SINGLE;
import static org.springframework.cloud.netflix.zuul.filters.support.FilterConstants.*;

@Component
public class HalLinksRefactoringFilter extends ZuulFilter {

    private final Logger logger;
    private final ObjectMapper objectMapper;
    private String serviceID;
    private String itemURI;
    private String collectionURI;
    private String serviceRequestedURI;

    @Autowired
    public HalLinksRefactoringFilter(Logger logger, ObjectMapper objectMapper) {
        this.logger = logger;
        this.objectMapper = objectMapper;
    }

    /**
     * to classify a filter by type. Standard types in Zuul are "pre" for pre-routing filtering,
     * "route" for routing to an origin, "post" for post-routing filters, "error" for error handling.
     * We also support a "static" type for static responses see  StaticResponseFilter.
     * Any filterType made be created or added and run by calling FilterProcessor.runFilters(type)
     *
     * @return A String representing that type
     */
    @Override
    public String filterType() {
        return POST_TYPE;
    }

    /**
     * filterOrder() must also be defined for a filter. Filters may have the same  filterOrder if precedence is not
     * important for a filter. filterOrders do not need to be sequential.
     *
     * @return the int order of a filter
     */
    @Override
    public int filterOrder() {
        return SEND_RESPONSE_FILTER_ORDER - 1;
    }

    /**
     * a "true" return from this method means that the run() method should be invoked
     *
     * @return true if the run() method should be invoked. false will not invoke the run() method
     */
    @Override
    public boolean shouldFilter() {
        RequestContext context = RequestContext.getCurrentContext();
        this.serviceID = context.get(SERVICE_ID_KEY).toString();
        int statusCode = context.getResponseStatusCode();
        return serviceID != null
                && (statusCode == 200 || statusCode == 201);
    }

    /**
     * if shouldFilter() is true, this method will be invoked. this method is the core method of a ZuulFilter
     *
     * @return Some arbitrary artifact may be returned. Current implementation ignores it.
     * @throws ZuulException if an error occurs during execution.
     */
    @Override
    public Object run() throws ZuulException {
        RequestContext context = RequestContext.getCurrentContext();
        this.setServiceRequestedURI();
        this.buildResourceURIs();

        try (final InputStream in = context.getResponseDataStream()) {
            if (in == null) {
                //TODO add log
                return null;
            }
            String responseData = CharStreams.toString(new InputStreamReader(in, StandardCharsets.UTF_8));
            logger.debug("BEFORE PARSING : " + responseData);
            JsonNode tree = objectMapper.readTree(responseData);

            if (isCollectionResource(tree)) {
                responseData = collectionResourceParsing(responseData);
            } else if (isItemResource(tree)) {
                responseData = itemResourceParsing(responseData);
            } else {
                //TODO throw exception
            }

            // Ensure that CharacterEncoding is UTF-8
            context.getResponse().setCharacterEncoding(StandardCharsets.UTF_8.toString());

            context.setResponseBody(responseData);
        } catch (IOException e) {
            e.printStackTrace();
        }

        return null;
    }

    private boolean isCollectionResource(JsonNode tree) {
        return tree.get("_embedded") != null
                && tree.get("page") != null
                && tree.get("_links") != null;
    }

    private boolean isItemResource(JsonNode tree) {
        return tree.get("_embedded") == null
                && tree.get("page") == null
                && tree.get("_links") != null;
    }

    private String collectionResourceParsing(String responseData) throws JsonProcessingException {

        JsonNode embeddedJson = objectMapper.readTree(responseData).get("_embedded");
        String embeddedRefactored = itemResourceParsing(embeddedJson.toString());
        embeddedJson = objectMapper.readTree(embeddedRefactored);

        JsonNode linksJson = objectMapper.readTree(responseData).get("_links");
        String links = linksJson.toString().replace(serviceRequestedURI, collectionURI);
        ObjectNode linksJsonObject = objectMapper.convertValue(objectMapper.readTree(links), ObjectNode.class);
        linksJsonObject.remove("profile");

        ObjectNode refactoredResponse = this.objectMapper.createObjectNode();
        refactoredResponse.set("_embedded", embeddedJson);
        refactoredResponse.set("_links", linksJsonObject);
        refactoredResponse.set("page", objectMapper.readTree(responseData).get("page"));

        return refactoredResponse.toPrettyString();
    }

    private String itemResourceParsing(String responseData) {
        // An additional modification is needed here to transform path parameter as URL parameter
        return responseData.replace(this.serviceRequestedURI + "/", this.itemURI + "?id=");
    }

    private void setServiceRequestedURI() {
        RibbonApacheHttpResponse ribbonResponse = (RibbonApacheHttpResponse) RequestContext.getCurrentContext().get("ribbonResponse");

        // Remove all service URI then add back service name to have clean URI, avoiding complex parsing of path or URL parameters
        this.serviceRequestedURI = ribbonResponse.getRequestedURI().toString().replaceAll("/" + serviceID + ".*$", "");
        this.serviceRequestedURI = this.serviceRequestedURI + "/" + serviceID;
    }

    private void buildResourceURIs() {
        HashMap<String, String> zuulHeaders = (HashMap<String, String>) RequestContext.getCurrentContext().get("zuulRequestHeaders");

        StringBuilder stringBuilder = new StringBuilder();
        stringBuilder.append(zuulHeaders.get("x-forwarded-proto")).append("://"); // ex: http://
        stringBuilder.append(zuulHeaders.get("x-forwarded-host")); // ex: localhost:8080
        stringBuilder.append("/").append(serviceID); // ex: /patient

        this.collectionURI = stringBuilder.toString() + GET_ALL.getBaseUri();
        this.itemURI = stringBuilder.toString() + GET_SINGLE.getBaseUri();
    }
}
