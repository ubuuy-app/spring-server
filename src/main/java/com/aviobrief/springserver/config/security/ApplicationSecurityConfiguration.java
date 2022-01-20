package com.aviobrief.springserver.config.security;


import com.aviobrief.springserver.config.security.csrf.CsrfAuthenticationFilter;
import com.aviobrief.springserver.config.security.jwt.JwtAuthenticationEntryPoint;
import com.aviobrief.springserver.config.security.jwt.JwtAuthenticationFilter;
import com.aviobrief.springserver.services.servicesImpl.UserDetailsSpringService;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.BeanIds;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.cors.CorsConfigurationSource;
import org.springframework.web.cors.UrlBasedCorsConfigurationSource;

import java.util.List;

@Configuration
@EnableWebSecurity
@EnableGlobalMethodSecurity(
        securedEnabled = true,
        jsr250Enabled = true,
        prePostEnabled = true
)
public class ApplicationSecurityConfiguration extends WebSecurityConfigurerAdapter {

    private final CsrfAuthenticationFilter csrfAuthenticationFilter;
    private final JwtAuthenticationFilter jwtAuthenticationFilter;
    private final JwtAuthenticationEntryPoint jwtUnauthorizedHandler;
    private final UserDetailsSpringService userDetailsSpringService;
    private final PasswordEncoder passwordEncoder;

    public ApplicationSecurityConfiguration(CsrfAuthenticationFilter csrfAuthenticationFilter, JwtAuthenticationFilter jwtAuthenticationFilter,
                                            UserDetailsSpringService userDetailsSpringService,
                                            PasswordEncoder passwordEncoder,
                                            JwtAuthenticationEntryPoint jwtUnauthorizedHandler) {
        this.csrfAuthenticationFilter = csrfAuthenticationFilter;
        this.jwtAuthenticationFilter = jwtAuthenticationFilter;
        this.userDetailsSpringService = userDetailsSpringService;
        this.passwordEncoder = passwordEncoder;
        this.jwtUnauthorizedHandler = jwtUnauthorizedHandler;
    }

    @Override
    protected void configure(HttpSecurity http) throws Exception {

        http
                .cors()
                .and()
                .csrf().disable();

        /* Filters application */
        http.addFilterBefore(jwtAuthenticationFilter, UsernamePasswordAuthenticationFilter.class);
        http.addFilterAfter(csrfAuthenticationFilter, JwtAuthenticationFilter.class);

        http
                .exceptionHandling()
                .authenticationEntryPoint(jwtUnauthorizedHandler)
                .and()
                .sessionManagement()
                .sessionCreationPolicy(SessionCreationPolicy.STATELESS)
                .and()
                .authorizeRequests()
                .antMatchers("/",
                        "/favicon.ico",
                        "/**/*.png",
                        "/**/*.gif",
                        "/**/*.svg",
                        "/**/*.jpg",
                        "/**/*.html",
                        "/**/*.css",
                        "/**/*.js")
                .permitAll()
                .antMatchers("/api/auth/**")
                .permitAll()
                .anyRequest()
                .authenticated()
        ;


    }

    @Override
    protected void configure(AuthenticationManagerBuilder auth) throws Exception {
        auth.userDetailsService(userDetailsSpringService).passwordEncoder(passwordEncoder);
    }

    /*
    Cors config:

    setAllowCredentials(true) is important, otherwise:
        The value of the 'Access-Control-Allow-Origin' header in the response must not be the wildcard '*'
        when the request's credentials mode is 'include'.

    setAllowedHeaders() is important! Without it, OPTIONS preflight request will fail with 403 Invalid CORS request
    */
    @Bean
    public CorsConfigurationSource corsConfigurationSource() {
        /* Build and set up configuration */
        CorsConfiguration configuration = new CorsConfiguration();
        configuration.setAllowedOrigins(List.of("http://localhost:3000"));
        configuration.setAllowedMethods(List.of("GET", "POST"));
        configuration.setAllowCredentials(true);
        configuration.setAllowedHeaders(List.of("Authorization", "Cache-Control", "Content-Type"));

        /* Build and return configurationSource that uses the above configuration. */
        UrlBasedCorsConfigurationSource source = new UrlBasedCorsConfigurationSource();
        source.registerCorsConfiguration("/**", configuration);
        return source;
    }

    /* Bean to export default authenticationManagerBean (returns WebSecurityConfigurerAdapter), injected in AuthController */
    @Bean(BeanIds.AUTHENTICATION_MANAGER)
    @Override
    public AuthenticationManager authenticationManagerBean() throws Exception {
        return super.authenticationManagerBean();
    }

}
