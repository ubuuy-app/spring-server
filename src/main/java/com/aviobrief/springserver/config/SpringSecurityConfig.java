package com.aviobrief.springserver.config;


import com.aviobrief.springserver.services.servicesImpl.SpringUserService;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.csrf.CookieCsrfTokenRepository;

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
        cookieCsrfTokenRepository.setCookieHttpOnly(false);

        http
                .csrf()
                .csrfTokenRepository(cookieCsrfTokenRepository)
                .and()
                .authorizeRequests()
                .antMatchers("/js/**", "/css/**", "/img/**").permitAll()
                .antMatchers("/", "/users/login", "/users/register").permitAll()
                .antMatchers("/**").authenticated() // todo - ApplicationSecurityConfiguration - set .authenticated()
                .and()
                .formLogin()
                .defaultSuccessUrl("/users", true)
        ;

    }

    @Override
    protected void configure(AuthenticationManagerBuilder auth) throws Exception {
        auth
                .userDetailsService(springUserService)
//                .passwordEncoder(passwordEncoder)
        ;
    }
}
