package com.ubuuy.springserver.config.application_beans;


import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.maxmind.geoip2.DatabaseReader;
import com.ubuuy.springserver.utils.json.JsonUtil;
import com.ubuuy.springserver.utils.json.JsonUtilImpl;
import com.ubuuy.springserver.utils.logger.ServerLogger;
import com.ubuuy.springserver.utils.logger.ServerLoggerImpl;
import com.ubuuy.springserver.utils.mapper.Mapper;
import com.ubuuy.springserver.utils.mapper.MapperImpl;
import com.ubuuy.springserver.utils.response_builder.ResponseBuilder;
import com.ubuuy.springserver.utils.response_builder.ResponseBuilderImpl;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.data.repository.query.SecurityEvaluationContextExtension;
import ua_parser.Parser;

import javax.servlet.http.HttpServletRequest;
import java.io.File;
import java.io.IOException;


@Configuration
public class ApplicationBeanConfiguration {

    private final HttpServletRequest httpServletRequest;

    public ApplicationBeanConfiguration(HttpServletRequest httpServletRequest) {
        this.httpServletRequest = httpServletRequest;
    }

    @Bean
    public SecurityEvaluationContextExtension securityEvaluationContextExtension() {
        return new SecurityEvaluationContextExtension();
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
    public ResponseBuilder responseBuilder() {
        return new ResponseBuilderImpl(httpServletRequest);
    }

    @Bean
    public ServerLogger appLogger() {
        return new ServerLoggerImpl();
    }

    @Bean
    public JsonUtil jsonUtil() {
        return new JsonUtilImpl();
    }

    @Bean
    public Parser parser() {
        return new Parser();
    }

    @Bean
    public DatabaseReader databaseReader() throws IOException {
        File database = new File("src/main/resources/GeoLite2-City_20220118/GeoLite2-City.mmdb");
        return new DatabaseReader.Builder(database).build();
    }


}
