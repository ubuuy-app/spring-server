package com.aviobrief.springserver.utils.api_response_builder;

import com.aviobrief.springserver.utils.api_response_builder.response_models.ApiOkBooleanResponse;
import com.aviobrief.springserver.utils.api_response_builder.response_models.error_models.ApiErrorObjectResponse;
import com.aviobrief.springserver.utils.api_response_builder.response_models.error_models.ApiSingleError;
import org.springframework.beans.factory.annotation.Autowired;

import javax.servlet.http.HttpServletRequest;


public class ApiResponseBuilder {

    @Autowired
    private HttpServletRequest httpServletRequest;

    public ApiResponseBuilder() {
    }

    public ApiOkBooleanResponse ok(boolean ok) {
        return new ApiOkBooleanResponse(ok);
    }

    public ApiSingleError buildSingleError(String message){
        return new ApiSingleError(message);
    }

    public ApiErrorObjectResponse buildErrorObject(){
        return new ApiErrorObjectResponse();
    }

    public ApiErrorObjectResponse buildErrorObject(boolean getPath) {
        if (getPath) {
            return new ApiErrorObjectResponse().setPath(getRequestPath(httpServletRequest));
        }

        return new ApiErrorObjectResponse();
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