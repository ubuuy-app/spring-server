package com.aviobrief.springserver.utils.error_response_builder;

import com.aviobrief.springserver.utils.error_response_builder.api_response_error_models.ApiResponseErrorObject;
import com.aviobrief.springserver.utils.error_response_builder.api_response_error_models.ApiSingleResponseError;

import javax.servlet.http.HttpServletRequest;


public class ResponseErrorBuilder {


    public ApiSingleResponseError buildSingleError(String message){
        return new ApiSingleResponseError(message);
    }

    public ApiResponseErrorObject buildErrorObject(){
        return new ApiResponseErrorObject();
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
