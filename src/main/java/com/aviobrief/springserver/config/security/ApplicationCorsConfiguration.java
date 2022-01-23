package com.aviobrief.springserver.config.security;

import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.CorsRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

@Configuration
public class ApplicationCorsConfiguration implements WebMvcConfigurer {

    @Override
    public void addCorsMappings(CorsRegistry registry) {
        registry
                .addMapping("/**")
                .allowedOrigins("http://localhost:3000", "https://ubuuy-53992.web.app", "https://ubuuy-53992.firebaseapp.com")
                .allowCredentials(true)
                .allowedHeaders("Authorization", "Cache-Control", "Content-Type","X-CSRF-TOKEN")
                .exposedHeaders("X-CSRF-TOKEN")
                .allowedMethods("OPTIONS", "GET", "POST");
    }
}


    /*
    Cors config:

    setAllowCredentials(true) is important, otherwise:
        The value of the 'Access-Control-Allow-Origin' header in the response must not be the wildcard '*'
        when the request's credentials mode is 'include'.

    setAllowedHeaders() is important! Without it, OPTIONS preflight request will fail with 403 Invalid CORS request
    */
//    @Bean
//    public CorsConfigurationSource corsConfigurationSource() {
//        /* Build and set up configuration */
//        CorsConfiguration configuration = new CorsConfiguration();
//        configuration.setAllowedOrigins(List.of("http://localhost:3000"));
//        configuration.setAllowedMethods(List.of("GET", "POST"));
//        configuration.setAllowCredentials(true);
//        configuration.setAllowedHeaders(List.of("Authorization", "Cache-Control", "Content-Type","X-CSRF-TOKEN"));
//
//        /* Build and return configurationSource that uses the above configuration. */
//        UrlBasedCorsConfigurationSource source = new UrlBasedCorsConfigurationSource();
//        source.registerCorsConfiguration("/**", configuration);
//        return source;
//    }
