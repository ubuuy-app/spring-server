package com.aviobrief.springserver.config.application_beans;


import com.aviobrief.springserver.utils.api_response_builder.ApiResponseBuilder;
import com.aviobrief.springserver.utils.mapper.Mapper;
import com.aviobrief.springserver.utils.mapper.MapperImpl;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;

import javax.servlet.http.HttpServletRequest;


@Configuration
public class ApplicationBeanConfiguration {

    private final HttpServletRequest httpServletRequest;

    public ApplicationBeanConfiguration(HttpServletRequest httpServletRequest) {
        this.httpServletRequest = httpServletRequest;
    }

    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }

    @Bean
    public Mapper mapper() {
        return new MapperImpl();
    }

    @Bean
    public Gson gson() {
        return new GsonBuilder()
                .excludeFieldsWithoutExposeAnnotation()
                .create();
    }

    @Bean
    public ApiResponseBuilder responseErrorBuilder(){
        return new ApiResponseBuilder(httpServletRequest);
    }


}
