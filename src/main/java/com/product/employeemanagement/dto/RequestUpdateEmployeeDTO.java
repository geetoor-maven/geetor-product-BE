package com.product.employeemanagement.dto;

import com.fasterxml.jackson.databind.PropertyNamingStrategies;
import com.fasterxml.jackson.databind.annotation.JsonNaming;
import lombok.Data;

import javax.validation.constraints.NotBlank;

@JsonNaming(PropertyNamingStrategies.SnakeCaseStrategy.class)
@Data
public class RequestUpdateEmployeeDTO {
    @NotBlank(message = "Nip should not be empty")
    private String nip;

    private String email;

    private String name;

    private String numberPhone;

    private String address;
}
