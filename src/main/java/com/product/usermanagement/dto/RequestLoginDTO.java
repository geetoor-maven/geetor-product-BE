package com.product.usermanagement.dto;

import com.fasterxml.jackson.databind.PropertyNamingStrategies;
import com.fasterxml.jackson.databind.annotation.JsonNaming;
import lombok.Data;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Size;

@JsonNaming(PropertyNamingStrategies.SnakeCaseStrategy.class)
@Data
public class RequestLoginDTO {

    @NotBlank(message = "email should not be empty")
    public String email;

    @NotBlank(message = "Password should not be empty")
    @Size(min = 8, message = "Password should be atleast 8 char")
    public String password;
    
}
