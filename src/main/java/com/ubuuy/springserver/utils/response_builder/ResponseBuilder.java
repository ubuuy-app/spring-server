package com.ubuuy.springserver.utils.response_builder;

import com.ubuuy.springserver.utils.response_builder.responses.ErrorResponseObject;
import com.ubuuy.springserver.utils.response_builder.responses.OkResponse;
import com.ubuuy.springserver.utils.response_builder.responses.SingleError;

import javax.servlet.http.HttpServletRequest;

public interface ResponseBuilder {
    OkResponse ok(boolean ok);

    SingleError buildSingleError();

    ErrorResponseObject buildErrorObject();

    ErrorResponseObject buildErrorObject(boolean autoGetPath);

    String getRequestPath(HttpServletRequest httpServletRequest);

    public static enum Type{
        AUTH("Authentication processing."),
        REGISTER("Register request");

        public final String detailedType;

        private Type(String detailedType){
            this.detailedType = detailedType;
        }
    }
}
