package com.aviobrief.springserver.config;


import com.aviobrief.springserver.services.servicesImpl.SpringUserService;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.web.csrf.CookieCsrfTokenRepository;

@Configuration
@EnableWebSecurity
public class SpringSecurityConfig extends WebSecurityConfigurerAdapter {

    private final SpringUserService springUserService;

    public SpringSecurityConfig(SpringUserService springUserService) {
        this.springUserService = springUserService;
    }

    @Override
    protected void configure(HttpSecurity http) throws Exception {

        CookieCsrfTokenRepository cookieCsrfTokenRepository = new CookieCsrfTokenRepository();
        cookieCsrfTokenRepository.setCookieHttpOnly(false);

        http
                .csrf()
                .csrfTokenRepository(cookieCsrfTokenRepository)
                .and()
                .authorizeRequests()
                .antMatchers("/js/**", "/css/**", "/img/**").permitAll()
                .antMatchers("/", "/users/login", "/users/register").permitAll()
                .antMatchers("/**").permitAll() // todo - ApplicationSecurityConfiguration - set .authenticated()
                .and()
                .formLogin();

    }
}
