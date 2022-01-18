package com.aviobrief.springserver.utils.response_builder;

import com.aviobrief.springserver.utils.response_builder.responses.ErrorResponseObject;
import com.aviobrief.springserver.utils.response_builder.responses.OkResponse;
import com.aviobrief.springserver.utils.response_builder.responses.SingleError;

import javax.servlet.http.HttpServletRequest;

public interface ResponseBuilder {
    OkResponse ok(boolean ok);

    SingleError buildSingleError(String target, String message, Object rejectedValue, String reason);

    ErrorResponseObject buildErrorObject();

    ErrorResponseObject buildErrorObject(boolean autoGetPath);

    String getRequestPath(HttpServletRequest httpServletRequest);
}
