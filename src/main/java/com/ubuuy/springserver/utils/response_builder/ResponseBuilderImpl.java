package com.ubuuy.springserver.utils.response_builder;

import com.ubuuy.springserver.models.responses.api.GenericOkResponse;
import com.ubuuy.springserver.utils.response_builder.responses.ErrorResponseObject;
import com.ubuuy.springserver.utils.response_builder.responses.SingleError;

import javax.servlet.http.HttpServletRequest;


public class ResponseBuilderImpl implements ResponseBuilder{

    private final HttpServletRequest httpServletRequest;

    public ResponseBuilderImpl(HttpServletRequest httpServletRequest) {
        this.httpServletRequest = httpServletRequest;
    }

    @Override
    public GenericOkResponse ok(boolean ok) {
        return new GenericOkResponse().setOk(ok);
    }

    @Override
    public SingleError buildSingleError(){
        return new SingleError();
    }

    @Override
    public ErrorResponseObject buildErrorObject(){
        return new ErrorResponseObject();
    }

    @Override
    public ErrorResponseObject buildErrorObject(boolean autoGetPath) {
        if (autoGetPath) {
            return new ErrorResponseObject().setPath(getRequestPath(httpServletRequest));
        }

        return new ErrorResponseObject();
    }

    @Override
    public String getRequestPath(HttpServletRequest httpServletRequest){
        StringBuffer requestURL = httpServletRequest.getRequestURL();
        String queryString = httpServletRequest.getQueryString();

        if (queryString == null){
            return requestURL.toString();
        }

        return requestURL.append('?').append(queryString).toString();
    }
}

/* implementing it like this makes using the building process not so explicit */

//    @Override
//    public SingleError buildSingleError(String target, String message, Object rejectedValue, String reason){
//        return new SingleError().setTarget(target).setMessage(message).setRejectedValue(rejectedValue).setReason(reason);
//    }
