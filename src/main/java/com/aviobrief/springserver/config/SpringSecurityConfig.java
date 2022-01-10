package com.aviobrief.springserver.config;


import com.aviobrief.springserver.services.servicesImpl.SpringUserService;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.csrf.CookieCsrfTokenRepository;
import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.cors.CorsConfigurationSource;
import org.springframework.web.cors.UrlBasedCorsConfigurationSource;

import java.util.List;

@Configuration
@EnableWebSecurity
public class SpringSecurityConfig extends WebSecurityConfigurerAdapter {

    private final SpringUserService springUserService;
    private final PasswordEncoder passwordEncoder;

    public SpringSecurityConfig(SpringUserService springUserService, PasswordEncoder passwordEncoder) {
        this.springUserService = springUserService;
        this.passwordEncoder = passwordEncoder;
    }

    @Override
    protected void configure(HttpSecurity http) throws Exception {

        CookieCsrfTokenRepository cookieCsrfTokenRepository = new CookieCsrfTokenRepository();
        cookieCsrfTokenRepository.setCookieHttpOnly(true);

        http
                .csrf()
                .csrfTokenRepository(cookieCsrfTokenRepository)
                .and()
                .cors()
//                .configurationSource(request -> new CorsConfiguration().applyPermitDefaultValues())
                .and()
                .authorizeRequests()
//                .antMatchers(HttpMethod.OPTIONS,"/**").permitAll()
//                .antMatchers("/js/**", "/css/**", "/img/**").permitAll()
//                .antMatchers("/", "/users/login", "/users/register").permitAll()
                .antMatchers("/**").authenticated() // todo - ApplicationSecurityConfiguration - set .authenticated()
//                .and()
//                .formLogin()
//                .defaultSuccessUrl("/users", true)
                .and()
                .httpBasic()
                .and()
                .logout()
                .logoutUrl("/auth-logout")
                .invalidateHttpSession(true)
                .deleteCookies("JSESSIONID")
        ;

    }

    @Override
    protected void configure(AuthenticationManagerBuilder auth) throws Exception {
        auth
                .userDetailsService(springUserService)
                .passwordEncoder(passwordEncoder)
        ;
    }

    @Bean
    CorsConfigurationSource corsConfigurationSource() {
        CorsConfiguration configuration = new CorsConfiguration();
        configuration.setAllowedOrigins(List.of("http://localhost:3000"));
        configuration.setAllowedMethods(List.of("GET","POST"));

        // setAllowCredentials(true) is important, otherwise:
        // The value of the 'Access-Control-Allow-Origin' header in the response must not be the wildcard '*'
        // when the request's credentials mode is 'include'.
        configuration.setAllowCredentials(true);
        // setAllowedHeaders is important! Without it, OPTIONS preflight request
        // will fail with 403 Invalid CORS request
        configuration.setAllowedHeaders(List.of("Authorization", "Cache-Control", "Content-Type"));

        UrlBasedCorsConfigurationSource source = new UrlBasedCorsConfigurationSource();
        source.registerCorsConfiguration("/**", configuration);
        return source;
    }
}
