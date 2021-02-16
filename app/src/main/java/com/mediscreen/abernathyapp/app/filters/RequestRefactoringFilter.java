package com.mediscreen.abernathyapp.app.filters;

import com.fasterxml.jackson.core.JsonFactory;
import com.fasterxml.jackson.core.JsonGenerator;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.netflix.zuul.ZuulFilter;
import com.netflix.zuul.context.RequestContext;
import com.netflix.zuul.exception.ZuulException;
import com.netflix.zuul.http.HttpServletRequestWrapper;
import com.netflix.zuul.http.ServletInputStreamWrapper;
import org.apache.http.entity.ContentType;
import org.slf4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpMethod;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Component;

import javax.servlet.ServletInputStream;
import javax.servlet.ServletRequest;
import javax.servlet.http.HttpServletRequest;
import java.io.IOException;
import java.io.StringWriter;
import java.io.Writer;
import java.nio.charset.StandardCharsets;
import java.util.Arrays;
import java.util.Enumeration;
import java.util.HashMap;
import java.util.Map;

import static com.mediscreen.abernathyapp.app.constants.ApiExposedOperations.*;
import static org.springframework.cloud.netflix.zuul.filters.support.FilterConstants.*;

//TODO divide this class with common RequestFilter and specific Filter for services specific operations

@Component
public class RequestRefactoringFilter extends ZuulFilter {

    private final Logger logger;
    ObjectMapper objectMapper;
    private String serviceId;
    private String requestURI;

    @Autowired
    public RequestRefactoringFilter(Logger logger, ObjectMapper objectMapper) {
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
        return PRE_TYPE;
    }

    /**
     * filterOrder() must also be defined for a filter. Filters may have the same  filterOrder if precedence is not
     * important for a filter. filterOrders do not need to be sequential.
     *
     * @return the int order of a filter
     */
    @Override
    public int filterOrder() {
        return PRE_DECORATION_FILTER_ORDER + 1;
    }

    /**
     * a "true" return from this method means that the run() method should be invoked
     *
     * @return true if the run() method should be invoked. false will not invoke the run() method
     */
    @Override
    public boolean shouldFilter() {
        RequestContext context = RequestContext.getCurrentContext();
        this.requestURI = context.getRequest().getRequestURI();
        this.serviceId = context.get(SERVICE_ID_KEY).toString();
        return isSpringRestService() && requestURI != null;
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
        context.set("isSpringRestService", true);
        HttpServletRequest request = context.getRequest();

        if (requestIsPostOnAddUri()) {
            additionRequestRefactoring();
        } else if (requestIsPutOnUpdateUri()) {
            updateRequestRefactoring();
        } else if (requestIsGetOnGetUri()) {
            getRequestRefactoring();
        } else if (requestIsGetOnListUri()) {
            listRequestRefactoring();
        } else if (requestIsGetOnPatHistoryOfPatientUri()) {
            ofPatientRequestRefactoring();
        } else {
            logger.debug("Unexpected endpoint call under /{}", requestURI);
            context.setResponseStatusCode(HttpStatus.NOT_FOUND.value());
            context.setResponseBody(request.getMethod() + " request on " + requestURI + " is not a valid endpoint.");
            context.setSendZuulResponse(false);
        }

        return null;
    }

    // Boolean methods to identify request characteristics
    // ---------------------------------------------------------------------------------------------------------

    private boolean isSpringRestService() {
        return this.serviceId != null &&
                (this.serviceId.equals("patient") ||
                        this.serviceId.equals("patHistory"));
    }

    private boolean requestIsPostOnAddUri() {
        return this.requestURI.equals("/" + serviceId + ADD.getBaseUri()) &&
                RequestContext.getCurrentContext().getRequest().getMethod().equals(HttpMethod.POST.toString());
    }

    private boolean requestIsPutOnUpdateUri() {
        return this.requestURI.equals("/" + serviceId + UPDATE.getBaseUri()) &&
                RequestContext.getCurrentContext().getRequest().getMethod().equals(HttpMethod.PUT.toString());
    }

    private boolean requestIsGetOnGetUri() {
        return this.requestURI.equals("/" + serviceId + GET_SINGLE.getBaseUri()) &&
                RequestContext.getCurrentContext().getRequest().getMethod().equals(HttpMethod.GET.toString());
    }

    private boolean requestIsGetOnListUri() {
        return this.requestURI.equals("/" + serviceId + GET_ALL.getBaseUri()) &&
                RequestContext.getCurrentContext().getRequest().getMethod().equals(HttpMethod.GET.toString());
    }

    private boolean requestIsGetOnPatHistoryOfPatientUri() {
        return this.requestURI.equals("/patHistory" + GET_OF_PATIENT.getBaseUri()) &&
                RequestContext.getCurrentContext().getRequest().getMethod().equals(HttpMethod.GET.toString());
    }

    // Refactoring methods
    // ---------------------------------------------------------------------------------------------------------

    private void additionRequestRefactoring() {
        RequestContext context = RequestContext.getCurrentContext();
        logger.debug("POST request to /{}/add is about to be refactored", serviceId);
        RequestContext.getCurrentContext().set(REQUEST_URI_KEY, "/" + serviceId);

        refactoringParametersAsJsonBody();
    }

    private void updateRequestRefactoring() {
        logger.debug("PUT request to /{}/update is about to be refactored", serviceId);

        RequestContext context = RequestContext.getCurrentContext();


        //TODO add pretty error object model

        String id = context.getRequest().getParameter("id");
        if (id == null || id.isBlank()) {
            context.setResponseStatusCode(HttpStatus.BAD_REQUEST.value());
            context.setResponseBody("Missing id property to identify and update " + serviceId.substring(0, 1).toUpperCase() + serviceId.substring(1));
            context.setSendZuulResponse(false);
        } else {
            context.set(REQUEST_URI_KEY, "/" + serviceId + "/" + id);

            refactoringParametersAsJsonBody();
        }

    }

    private void getRequestRefactoring() {
        logger.debug("GET request to /{}/get is about to be refactored", serviceId);
        RequestContext context = RequestContext.getCurrentContext();
        String id = context.getRequest().getParameter("id");

        //TODO add pretty error object model

        // Due to spring data rest path variable /collectionPath/itemID, an id value starting by a "/" would be valid ! condition below aims to prevent that case.
        if (id == null || id.isBlank() || id.charAt(0) == '/') {
            context.setResponseStatusCode(HttpStatus.BAD_REQUEST.value());
            context.setResponseBody("Missing or invalid id parameter to get any " + serviceId.substring(0, 1).toUpperCase() + serviceId.substring(1));
            context.setSendZuulResponse(false);
        } else {
            context.set(REQUEST_URI_KEY, "/" + serviceId + "/" + id);
        }
    }

    private void listRequestRefactoring() {
        logger.debug("GET request to /{}/list is about to be refactored", serviceId);
        RequestContext context = RequestContext.getCurrentContext();
        context.set(REQUEST_URI_KEY, "/" + serviceId);
    }

    private void ofPatientRequestRefactoring() {
        logger.debug("GET request to /{}/ofPatient is about to be refactored", serviceId);
        RequestContext context = RequestContext.getCurrentContext();
        String patientId = context.getRequest().getParameter("patientId");

        //TODO add pretty error object model

        if (patientId == null || patientId.isBlank()) {
            context.setResponseStatusCode(HttpStatus.BAD_REQUEST.value());
            context.setResponseBody("Missing patientId parameter to get any " + serviceId.substring(0, 1).toUpperCase() + serviceId.substring(1));
            context.setSendZuulResponse(false);
        } else {
            context.set(REQUEST_URI_KEY, "/" + serviceId + "/search/withPatientId");
        }
    }

    private void refactoringParametersAsJsonBody() {
        RequestContext context = RequestContext.getCurrentContext();
        try {
            Writer bodyWriter = new StringWriter();
            JsonGenerator generator = new JsonFactory().createGenerator(bodyWriter);

            generator.writeStartObject();
            context.getRequest().getParameterMap().forEach((k, v) ->
            {
                try {
                    generator.writeStringField(k, Arrays.stream(v).findFirst().get());
                } catch (IOException e) {
                    //TODO Handle error
                }
            });
            generator.writeEndObject();
            generator.close();

            byte[] body = bodyWriter.toString().getBytes(StandardCharsets.UTF_8);

            //TODO replace HttpServletRequestWrapper with custom builder

            context.setRequest(new HttpServletRequestWrapper(context.getRequest()) {

                @Override
                public ServletInputStream getInputStream() {
                    return new ServletInputStreamWrapper(body);
                }

                /**
                 * The default behavior of this method is to return getRequestURI() on the
                 * wrapped request object.
                 */
                @Override
                public String getRequestURI() {
                    return "/patient";
                }

                /**
                 * This method is safe to use multiple times.
                 * Changing the returned map or the array of any of the map's values will not
                 * interfere with this class operation.
                 *
                 * @return The cloned parameters map.
                 */
                @Override
                public HashMap<String, String[]> getParameters() {
                    super.getParameters().clear();
                    return super.getParameters();
                }

                /**
                 * This method is safe to execute multiple times.
                 *
                 * @param name
                 * @see ServletRequest#getParameter(String)
                 */
                @Override
                public String getParameter(String name) {
                    return null;
                }

                /**
                 * This method is safe.
                 *
                 * @see {@link #getParameters()}
                 * @see ServletRequest#getParameterMap()
                 */
                @Override
                public Map getParameterMap() {
                    super.getParameterMap().clear();
                    return super.getParameterMap();
                }

                /**
                 * This method is safe to execute multiple times.
                 *
                 * @see ServletRequest#getParameterNames()
                 */
                @Override
                public Enumeration getParameterNames() {
                    return new Enumeration() {
                        @Override
                        public boolean hasMoreElements() {
                            return false;
                        }

                        @Override
                        public Object nextElement() {
                            return null;
                        }
                    };
                }

                /**
                 * This method is safe to execute multiple times.
                 * Changing the returned array will not interfere with this class operation.
                 *
                 * @param name
                 * @see ServletRequest#getParameterValues(String)
                 */
                @Override
                public String[] getParameterValues(String name) {
                    return null;
                }

                /**
                 * The default behavior of this method is to return getContentLength() on
                 * the wrapped request object.
                 */
                @Override
                public int getContentLength() {
                    return body.length;
                }

                /**
                 * The default behavior of this method is to return getContentLengthLong()
                 * on the wrapped request object.
                 *
                 * @since Servlet 3.1
                 */
                @Override
                public long getContentLengthLong() {
                    return body.length;
                }


            });

            context.addZuulRequestHeader("Content-Type", ContentType.APPLICATION_JSON.toString());

        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
