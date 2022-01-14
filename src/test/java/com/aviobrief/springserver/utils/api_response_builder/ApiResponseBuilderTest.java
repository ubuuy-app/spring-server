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
        assertThat(resultTimeStampNoMillis).isEqualTo(timeStampNowNoMillis);
        assertThat(apiErrorObjectResponse.getErrors()).size().isEqualTo(0);
    }


}