package com.aviobrief.springserver.config;


import com.aviobrief.springserver.utils.mapper.Mapper;
import com.aviobrief.springserver.utils.mapper.MapperImpl;
import org.modelmapper.ModelMapper;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Scope;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;


@Configuration
public class ApplicationBeanConfiguration {


    @Bean
    public ModelMapper modelMapper() {
        return new ModelMapper();
    }

    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }

//    @Bean
//    public Gson gson() {
//        return new GsonBuilder().
//                excludeFieldsWithoutExposeAnnotation().
//                create();
//    }
//
//    @Bean
//    public ValidationUtil validationUtil() {
//        return new ValidationUtilImpl() {
//        };
//    }

    @Bean
    public Mapper mapper() {
        return new MapperImpl();
    }


}
