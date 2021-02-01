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
import org.springframework.stereotype.Component;

import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.nio.charset.StandardCharsets;
import java.util.Iterator;
import java.util.Map;

import static org.springframework.cloud.netflix.zuul.filters.support.FilterConstants.POST_TYPE;
import static org.springframework.cloud.netflix.zuul.filters.support.FilterConstants.SEND_RESPONSE_FILTER_ORDER;

@Component
public class HalLinksRefactoringFilter extends ZuulFilter {

    private final Logger logger;
    private final ObjectMapper objectMapper;

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
        int statusCode = RequestContext.getCurrentContext().getResponseStatusCode();
        return statusCode == 200 || statusCode == 201;
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
        String requestURI = context.getRequest().getRequestURI();

        try (final InputStream in = context.getResponseDataStream()) {
            if (in == null) {
                //TODO add log
                return null;
            }
            String responseData = CharStreams.toString(new InputStreamReader(in, StandardCharsets.UTF_8));

            if (requestURI.contains("/patient/list")) {
                responseData = collectionResourceParsing(responseData);
            } else if (requestURI.equals("/patient/get") || requestURI.equals("/patient/add") || requestURI.equals("/patient/update")) {
                responseData = itemResourceParsing(responseData, requestURI);
            }

            context.setResponseBody(responseData);
        } catch (IOException e) {
            e.printStackTrace();
        }

        return null;
    }

    private String collectionResourceParsing(String responseData) throws JsonProcessingException {
        ObjectNode jsonLinks = objectMapper.createObjectNode();
        JsonNode tree = objectMapper.readTree(responseData);
        // TODO Check if JsonNode is null or check if error
        String embeddedPatients = itemResourceParsing(tree.get("_embedded").toString(), "/patient/get");
        String links = tree.get("_links").toString().replace("/patient/patient", "/patient/list");

        Iterator<Map.Entry<String, JsonNode>> it = objectMapper.readTree(links).fields();
        while (it.hasNext()) {
            Map.Entry<String, JsonNode> field = it.next();
            jsonLinks.set(field.getKey(), field.getValue());
        }

        ObjectNode refactoredResponse = this.objectMapper.createObjectNode();
        refactoredResponse.set("_embedded", objectMapper.readTree(embeddedPatients));
        refactoredResponse.set("_links", jsonLinks.at("/_links"));
        refactoredResponse.set("page", tree.get("page"));

        return refactoredResponse.toPrettyString();
    }

    private String itemResourceParsing(String responseData, String newRequestUri) {
        return responseData.replace("/patient/patient", newRequestUri);
    }
}
