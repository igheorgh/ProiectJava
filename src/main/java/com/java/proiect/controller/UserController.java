package com.java.proiect.controller;

import com.java.proiect.dto.UserInformationDto;
import com.java.proiect.dto.UserLoginDto;
import com.java.proiect.dto.UserUpdateDto;
import com.java.proiect.entity.User;
import com.java.proiect.mapper.UserMapper;
import com.java.proiect.mapper.UserUpdateMapper;
import com.java.proiect.service.UserService;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;
@Validated
@RestController
@RequestMapping("user")
public class UserController {
    private  UserService userService;
    private  UserMapper mapper ;
    private UserUpdateMapper userUpdateMapper;

    public UserController(UserService userService, UserMapper mapper, UserUpdateMapper userUpdateMapper) {
        this.userService = userService;
        this.mapper = mapper;
        this.userUpdateMapper = userUpdateMapper;
    }

    @PostMapping(path = "/create", consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<User> create(@Valid  @RequestBody UserInformationDto user) {
        User usercreate = userService.create(mapper.toEntity(user));
        return new ResponseEntity<>(usercreate, HttpStatus.CREATED);
    }

    @GetMapping(path = "/{id}", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<UserInformationDto> get(@PathVariable Long id) {
        User user = userService.getbyid(id);
        return new ResponseEntity<>(mapper.toDto(user), HttpStatus.OK);
    }

    @GetMapping
    public ResponseEntity<List<UserInformationDto>> getAll() {
        List<User> userList = userService.getAll();
        return new ResponseEntity<>(mapper.toDtoList(userList), HttpStatus.OK);
    }

    @PutMapping(path = "/{id}")
    public void update(@PathVariable Long id,@Valid  @RequestBody UserUpdateDto userUpdateDto) {
        userService.update(userUpdateDto, id);
    }

    @DeleteMapping(path = "/{id}")
    public void delete(@PathVariable Long id) {
        userService.delete(id);
    }

    @GetMapping(path = "/login", consumes = MediaType.APPLICATION_JSON_VALUE)
    public Long Login(@RequestBody UserLoginDto userLoginDto) {
        User user = userService.Login(userLoginDto);
        return user.getId();
    }


}
