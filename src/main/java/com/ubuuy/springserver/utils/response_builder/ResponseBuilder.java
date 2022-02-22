package com.ubuuy.springserver.utils.response_builder;

import com.ubuuy.springserver.models.responses.api_responses.GenericOkResponse;
import com.ubuuy.springserver.utils.response_builder.responses.ErrorResponseObject;
import com.ubuuy.springserver.utils.response_builder.responses.SingleError;

import javax.servlet.http.HttpServletRequest;

public interface ResponseBuilder {
    GenericOkResponse ok(boolean ok);

    SingleError buildSingleError();

    ErrorResponseObject buildErrorObject();

    ErrorResponseObject buildErrorObject(boolean autoGetPath);

    String getRequestPath(HttpServletRequest httpServletRequest);

    enum Type {
        AUTH("Authentication processing."),
        REGISTER("Register request."),
        PRODUCTS("Product(s) request."),
        PURCHASES("Purchase(s) request.");


        public final String detailedType;

        Type(String detailedType) {
            this.detailedType = detailedType;
        }
    }
}
