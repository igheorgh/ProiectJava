package com.java.proiect.entity;


import javax.validation.constraints.*;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class User {
    @NotNull
    private long id;
    @NotEmpty
    private String username;
    @NotEmpty
    @Size(min=8)
    private String password;
    @Email
    @NotEmpty
    private String email;
    @NotBlank
    private String firstname;
    @NotEmpty
    private String lastname;
    @Min(18)
    private Integer age;
}
