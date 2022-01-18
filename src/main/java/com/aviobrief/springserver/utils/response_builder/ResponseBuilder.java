package com.aviobrief.springserver.utils.response_builder;

import com.aviobrief.springserver.utils.response_builder.responses.ErrorResponseObject;
import com.aviobrief.springserver.utils.response_builder.responses.OkResponse;
import com.aviobrief.springserver.utils.response_builder.responses.SingleError;

import javax.servlet.http.HttpServletRequest;


public class ResponseBuilder {

    private final HttpServletRequest httpServletRequest;

    public ResponseBuilder(HttpServletRequest httpServletRequest) {
        this.httpServletRequest = httpServletRequest;
    }

    public OkResponse ok(boolean ok) {
        return new OkResponse().setOk(ok);
    }

    public SingleError buildSingleError(String message){
        return new SingleError().setMessage(message);
    }

    public ErrorResponseObject buildErrorObject(){
        return new ErrorResponseObject();
    }

    public ErrorResponseObject buildErrorObject(boolean autoGetPath) {
        if (autoGetPath) {
            return new ErrorResponseObject().setPath(getRequestPath(httpServletRequest));
        }

        return new ErrorResponseObject();
    }

    public String getRequestPath(HttpServletRequest httpServletRequest){
        StringBuffer requestURL = httpServletRequest.getRequestURL();
        String queryString = httpServletRequest.getQueryString();

        if (queryString == null){
            return requestURL.toString();
        }

        return requestURL.append('?').append(queryString).toString();
    }
}
