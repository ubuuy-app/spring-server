package com.ubuuy.springserver.config.security;


import com.ubuuy.springserver.config.security.csrf_token.CsrfAuthenticationFilter;
import com.ubuuy.springserver.config.security.jwt.JwtAuthenticationEntryPoint;
import com.ubuuy.springserver.config.security.jwt.JwtAuthenticationFilter;
import com.ubuuy.springserver.services.servicesImpl.UserDetailsService;
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
    private final UserDetailsService userDetailsService;
    private final PasswordEncoder passwordEncoder;

    public ApplicationSecurityConfiguration(CsrfAuthenticationFilter csrfAuthenticationFilter, JwtAuthenticationFilter jwtAuthenticationFilter,
                                            UserDetailsService userDetailsService,
                                            PasswordEncoder passwordEncoder,
                                            JwtAuthenticationEntryPoint jwtUnauthorizedHandler) {
        this.csrfAuthenticationFilter = csrfAuthenticationFilter;
        this.jwtAuthenticationFilter = jwtAuthenticationFilter;
        this.userDetailsService = userDetailsService;
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
//        http.addFilterAfter(csrfAuthenticationFilter, JwtAuthenticationFilter.class);

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
                .antMatchers("/api/auth/**", "/.well-known/first-party-set", "/first-party-cookie")
                .permitAll()
                .anyRequest()
                .authenticated()
                .and()
                .logout().disable();
        ;


    }

    @Override
    protected void configure(AuthenticationManagerBuilder auth) throws Exception {
        auth.userDetailsService(userDetailsService).passwordEncoder(passwordEncoder);
    }


    /* Bean to export default authenticationManagerBean (returns WebSecurityConfigurerAdapter), injected in AuthController */
    @Bean(BeanIds.AUTHENTICATION_MANAGER)
    @Override
    public AuthenticationManager authenticationManagerBean() throws Exception {
        return super.authenticationManagerBean();
    }

}
