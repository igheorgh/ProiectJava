package com.java.proiect;

import com.java.proiect.dto.UserInformationDto;
import org.apache.commons.lang3.RandomStringUtils;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.junit.jupiter.MockitoExtension;

import javax.validation.ConstraintViolation;
import javax.validation.Validation;
import javax.validation.Validator;
import java.util.Set;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;

@ExtendWith(MockitoExtension.class)
public class UserDtoT {

    private Validator validator;
    private UserInformationDto request;

    @BeforeEach
    void setUp() {
        validator = Validation.buildDefaultValidatorFactory().getValidator();
        request = UserInformationDto.builder()
                .id(1L)
                .username(RandomStringUtils.randomAlphabetic(30))
                .email("test@yahoo.com")
                .lastname(RandomStringUtils.randomAlphabetic(30))
                .firstname(RandomStringUtils.randomAlphabetic(30))
                .age(20)
                .build();
    }

    @Test
    void test_request_whenIsValid() {

        // act
        Set<ConstraintViolation<UserInformationDto>> constraintViolations = validator.validate(request);

        // Assert
        assertThat(constraintViolations.isEmpty());
    }

    @Test
    void test_request_when_id_is_invalid() {

        //Arrange
        request.setEmail("");
         request.setPassword("");

        // Act
        Set<ConstraintViolation<UserInformationDto>> constraintViolations = validator.validate(request);

        // Assert
        assertThat(constraintViolations.size()).isEqualTo(3);
    }
}
