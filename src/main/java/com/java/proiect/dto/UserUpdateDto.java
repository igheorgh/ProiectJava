package com.java.proiect.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.*;
@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class UserUpdateDto {
    private String username;
    @Size(min=8)
    private String password;
    @Email
    private String email;
    private String firstname;
    private String lastname;
    @Min(18)
    private Integer age;
}
