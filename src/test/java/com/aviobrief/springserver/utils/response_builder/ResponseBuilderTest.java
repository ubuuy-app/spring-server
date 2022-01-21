package com.aviobrief.springserver.utils.response_builder;

import com.aviobrief.springserver.config.date_time.ApplicationDateTimeConfiguration;
import com.aviobrief.springserver.utils.json.JsonUtil;
import com.aviobrief.springserver.utils.json.JsonUtilImpl;
import com.aviobrief.springserver.utils.response_builder.responses.ErrorResponseObject;
import com.aviobrief.springserver.utils.response_builder.responses.OkResponse;
import com.aviobrief.springserver.utils.response_builder.responses.SingleError;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import javax.servlet.http.HttpServletRequest;
import java.time.ZonedDateTime;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class ResponseBuilderTest {

    @Mock
    private HttpServletRequest httpServletRequest;

    private ResponseBuilder responseBuilder;
    private JsonUtil jsonUtil;

    @BeforeEach
    private void initApiResponseBuilder() {
        responseBuilder = new ResponseBuilderImpl(httpServletRequest);
        jsonUtil = new JsonUtilImpl();
    }

    @Test
    void ok_method_returns_true() {
        OkResponse ok = responseBuilder.ok(true);
        assertThat(ok).hasFieldOrPropertyWithValue("ok", true);
    }

    @Test
    void ok_method_returns_false() {
        OkResponse ok = responseBuilder.ok(false);
        assertThat(ok).hasFieldOrPropertyWithValue("ok", false);
    }

    @Test
    void buildSingleError_works_ok() {
        SingleError singleError = responseBuilder
                .buildSingleError()
                .setTarget("test_target")
                .setMessage("test_message")
                .setRejectedValue(jsonUtil.toJson(jsonUtil.pair("test_key", "test_value")))
                .setReason("test_reason");
        assertThat(singleError).hasFieldOrPropertyWithValue("target", "test_target");
        assertThat(singleError).hasFieldOrPropertyWithValue("message", "test_message");
        assertThat(singleError.getRejectedValue()).isEqualTo("{\"test_key\":\"test_value\"}");
        assertThat(singleError).hasFieldOrPropertyWithValue("reason", "test_reason");
    }

    @Test
    void buildErrorObject_works_ok() {
        ApplicationDateTimeConfiguration.setApplicationTimeZoneDefault();
        ErrorResponseObject errorResponseObject = responseBuilder.buildErrorObject();

        String resultTimeStampNoMillis = errorResponseObject.getTimestamp().split("\\.")[0];
        String timeStampNowNoMillis = ZonedDateTime.now().toString().split("\\.")[0];

        assertThat(errorResponseObject).isInstanceOf(ErrorResponseObject.class);
        assertThat(errorResponseObject).hasAllNullFieldsOrPropertiesExcept("timestamp", "errors");
        assertThat(resultTimeStampNoMillis).isEqualTo(timeStampNowNoMillis);
        assertThat(errorResponseObject.getErrors()).size().isEqualTo(0);
    }

    @Test
    void buildErrorObject_works_ok_with_autoGetPah_true_and_with_query_string() {
        ApplicationDateTimeConfiguration.setApplicationTimeZoneDefault();
        when(httpServletRequest.getRequestURL()).thenReturn(new StringBuffer("http://fake-url"));
        when(httpServletRequest.getQueryString()).thenReturn("fake-qs");

        ErrorResponseObject errorResponseObject = responseBuilder.buildErrorObject(true);
        assertThat(errorResponseObject.getPath()).isEqualTo("http://fake-url?fake-qs");

    }

    @Test
    void buildErrorObject_works_ok_with_autoGetPah_true_and_no_query_string() {
        ApplicationDateTimeConfiguration.setApplicationTimeZoneDefault();
        when(httpServletRequest.getRequestURL()).thenReturn(new StringBuffer("http://fake-url"));
        when(httpServletRequest.getQueryString()).thenReturn(null);

        ErrorResponseObject errorResponseObject = responseBuilder.buildErrorObject(true);
        assertThat(errorResponseObject.getPath()).isEqualTo("http://fake-url");
    }

    @Test
    void buildErrorObject_works_ok_with_autoGetPah_false() {
        ApplicationDateTimeConfiguration.setApplicationTimeZoneDefault();

        ErrorResponseObject errorResponseObject = responseBuilder.buildErrorObject(false);
        assertThat(errorResponseObject).hasFieldOrPropertyWithValue("path", null);
    }


}