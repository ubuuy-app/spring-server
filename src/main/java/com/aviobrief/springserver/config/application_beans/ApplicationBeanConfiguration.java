package com.aviobrief.springserver.config.application_beans;


import com.aviobrief.springserver.utils.mapper.Mapper;
import com.aviobrief.springserver.utils.mapper.MapperImpl;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;


@Configuration
public class ApplicationBeanConfiguration {

    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }

    @Bean
    public Mapper mapper() {
        return new MapperImpl();
    }


}
