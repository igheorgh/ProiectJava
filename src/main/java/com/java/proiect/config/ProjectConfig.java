package com.java.proiect.config;

import com.java.proiect.dto.UserLoginDto;
import com.java.proiect.mapper.*;
import com.java.proiect.repository.PortofelRepository;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class ProjectConfig {

    @Bean
    public UserMapper userMapper() {
        return new UserMapperImpl();
    }

    @Bean
    public UserUpdateMapper userUpdateMapper() {
        return new UserUpdateMapperImpl();
    }

    @Bean
    public UserLoginMapper userLoginMapper() {return new UserLoginMapperImpl();}

    @Bean
    public PortofelMapper portofelMapper(){return  new PortofelMapperImpl();}

    @Bean
    public ObiectVanzareMapper obiectVanzareMapper(){return  new ObiectVanzareMapperImpl();}

    @Bean
    public LicitatieMapper licitatieMapper(){return  new LicitatieMapperImpl();}

    @Bean
    public ComentariuMapper comentariuMapper(){return new ComentariuMapperImpl();}

    @Bean
    public CalificativMapper calificativMapper(){return new CalificativMapperImpl();}
}

