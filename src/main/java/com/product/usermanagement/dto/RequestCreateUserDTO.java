package com.product.usermanagement.dto;

import com.fasterxml.jackson.databind.PropertyNamingStrategies;
import com.fasterxml.jackson.databind.annotation.JsonNaming;
import lombok.Data;

import javax.validation.constraints.NotBlank;

@JsonNaming(PropertyNamingStrategies.SnakeCaseStrategy.class)
@Data
public class RequestCreateUserDTO {

    @NotBlank(message = "Name should not be empty")
    private String name;

    @NotBlank(message = "Email should not be empty")
    private String email;

    @NotBlank(message = "Password should not be empty")
    private String password;

    private String numberPhone;

}
