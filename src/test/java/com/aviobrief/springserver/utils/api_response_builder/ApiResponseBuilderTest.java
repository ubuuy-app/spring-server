package com.aviobrief.springserver.utils.api_response_builder;

import com.aviobrief.springserver.config.date_time.ApplicationDateTimeConfiguration;
import com.aviobrief.springserver.utils.api_response_builder.response_models.ApiOkBooleanResponse;
import com.aviobrief.springserver.utils.api_response_builder.response_models.error_models.ApiErrorObjectResponse;
import com.aviobrief.springserver.utils.api_response_builder.response_models.error_models.ApiSingleError;
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
class ApiResponseBuilderTest {

    @Mock
    private HttpServletRequest httpServletRequest;

    private ApiResponseBuilder apiResponseBuilder;

    @BeforeEach
    private void initApiResponseBuilder() {
        apiResponseBuilder = new ApiResponseBuilder(httpServletRequest);
    }

    @Test
    void ok_method_returns_true(){
        ApiOkBooleanResponse ok = apiResponseBuilder.ok(true);
        assertThat(ok).hasFieldOrPropertyWithValue("ok", true);
    }

    @Test
    void ok_method_returns_false(){
        ApiOkBooleanResponse ok = apiResponseBuilder.ok(false);
        assertThat(ok).hasFieldOrPropertyWithValue("ok", false);
    }

    @Test
    void buildSingleError_works_ok(){
        ApiSingleError apiSingleError = apiResponseBuilder.buildSingleError("test message");
        assertThat(apiSingleError).hasAllNullFieldsOrPropertiesExcept("message");
        assertThat(apiSingleError).hasFieldOrPropertyWithValue("message", "test message");
    }

    @Test
    void buildErrorObject_works_ok(){
        ApplicationDateTimeConfiguration.setApplicationTimeZoneDefault();
        ApiErrorObjectResponse apiErrorObjectResponse = apiResponseBuilder.buildErrorObject();

        String resultTimeStampNoMillis = apiErrorObjectResponse.getTimestamp().split("\\.")[0];
        String timeStampNowNoMillis = ZonedDateTime.now().toString().split("\\.")[0];

        assertThat(apiErrorObjectResponse).isInstanceOf(ApiErrorObjectResponse.class);
        assertThat(apiErrorObjectResponse).hasAllNullFieldsOrPropertiesExcept("timestamp", "errors");
        assertThat(resultTimeStampNoMillis).isEqualTo(timeStampNowNoMillis);
        assertThat(apiErrorObjectResponse.getErrors()).size().isEqualTo(0);
    }

    @Test
    void buildErrorObject_works_ok_with_autoGetPah_true_and_with_query_string(){
        ApplicationDateTimeConfiguration.setApplicationTimeZoneDefault();
        when(httpServletRequest.getRequestURL()).thenReturn(new StringBuffer("http://fake-url"));
        when(httpServletRequest.getQueryString()).thenReturn("fake-qs");

        ApiErrorObjectResponse apiErrorObjectResponse = apiResponseBuilder.buildErrorObject(true);
        assertThat(apiErrorObjectResponse.getPath()).isEqualTo("http://fake-url?fake-qs");

    }

    @Test
    void buildErrorObject_works_ok_with_autoGetPah_true_and_no_query_string(){
        ApplicationDateTimeConfiguration.setApplicationTimeZoneDefault();
        when(httpServletRequest.getRequestURL()).thenReturn(new StringBuffer("http://fake-url"));
        when(httpServletRequest.getQueryString()).thenReturn(null);

        ApiErrorObjectResponse apiErrorObjectResponse = apiResponseBuilder.buildErrorObject(true);
        assertThat(apiErrorObjectResponse.getPath()).isEqualTo("http://fake-url");
    }

    @Test
    void buildErrorObject_works_ok_with_autoGetPah_false(){
        ApplicationDateTimeConfiguration.setApplicationTimeZoneDefault();

        ApiErrorObjectResponse apiErrorObjectResponse = apiResponseBuilder.buildErrorObject(false);
        assertThat(apiErrorObjectResponse).hasFieldOrPropertyWithValue("path", null);
    }


}