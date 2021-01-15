package com.java.proiect.mapper;


import com.java.proiect.dto.UserLoginDto;
import com.java.proiect.entity.User;
import org.mapstruct.Mapper;
@Mapper
public interface UserLoginMapper extends EntityMapper<UserLoginDto, User>{
}
