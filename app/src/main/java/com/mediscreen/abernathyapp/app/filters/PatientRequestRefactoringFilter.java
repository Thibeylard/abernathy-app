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

import static org.springframework.cloud.netflix.zuul.filters.support.FilterConstants.*;

@Component
public class PatientRequestRefactoringFilter extends ZuulFilter {

    private final Logger logger;
    ObjectMapper objectMapper;

    @Autowired
    public PatientRequestRefactoringFilter(Logger logger, ObjectMapper objectMapper) {
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
        return RequestContext.getCurrentContext().get(SERVICE_ID_KEY).toString().equals("patient");
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
        HttpServletRequest request = context.getRequest();

        if (request.getRequestURI().equals("/patient/add")
                && request.getMethod().equals(HttpMethod.POST.toString())) {
            additionRequestRefactoring();
        } else if (request.getRequestURI().equals("/patient/update")
                && request.getMethod().equals(HttpMethod.PUT.toString())) {
            updateRequestRefactoring();
        } else if (request.getRequestURI().equals("/patient/get")
                && request.getMethod().equals(HttpMethod.GET.toString())) {
            getRequestRefactoring();
        } else if (request.getRequestURI().equals("/patient/list")
                && request.getMethod().equals(HttpMethod.GET.toString())) {
            listRequestRefactoring();
        } else {
            logger.debug("Unexpected endpoint call under /patient");
            context.setResponseStatusCode(HttpStatus.NOT_FOUND.value());
            context.setResponseBody(context.getRequest().getMethod() + " request on " + context.getRequest().getRequestURI() + " is not a valid endpoint.");
            context.setSendZuulResponse(false);
        }

        return null;
    }

    private void additionRequestRefactoring() {
        logger.debug("POST request to /patient/add is about to be refactored");
        RequestContext.getCurrentContext().set(REQUEST_URI_KEY, "/patient");

        refactoringParametersAsJsonBody();
    }

    private void updateRequestRefactoring() {
        logger.debug("PUT request to /patient/update is about to be refactored");

        RequestContext context = RequestContext.getCurrentContext();


        //TODO add pretty error object model

        String id = context.getRequest().getParameter("id");
        if (id == null || id.isBlank()) {
            context.setResponseStatusCode(HttpStatus.BAD_REQUEST.value());
            context.setResponseBody("Missing id property to identify and update Patient");
            context.setSendZuulResponse(false);
        } else {
            context.set(REQUEST_URI_KEY, "/patient/" + id);

            refactoringParametersAsJsonBody();
        }

    }

    private void getRequestRefactoring() {
        logger.debug("GET request to /patient/get is about to be refactored");
        RequestContext context = RequestContext.getCurrentContext();
        String id = context.getRequest().getParameter("id");

        //TODO add pretty error object model

        if (id == null || id.isBlank()) {
            context.setResponseStatusCode(HttpStatus.BAD_REQUEST.value());
            context.setResponseBody("Missing id parameter to get any Patient");
            context.setSendZuulResponse(false);
        } else {
            context.set(REQUEST_URI_KEY, "/patient/" + id);
        }
    }

    private void listRequestRefactoring() {
        logger.debug("GET request to /patient/list is about to be refactored");
        RequestContext context = RequestContext.getCurrentContext();
        context.set(REQUEST_URI_KEY, "/patient");
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
