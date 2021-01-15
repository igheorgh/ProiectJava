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
public class UserInformationDto {
    @NotNull
    private long id;
    @NotBlank
    private String username;
    @NotBlank
    @Size(min=8)
    private String password;
    @Email
    @NotBlank
    private String email;
    @NotBlank
    private String firstname;
    @NotBlank
    private String lastname;
    @Min(18)
    private Integer age;
}
