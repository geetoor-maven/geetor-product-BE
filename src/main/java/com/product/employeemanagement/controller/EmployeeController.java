package com.product.employeemanagement.controller;

import com.product.common.constant.BasePath;
import com.product.employeemanagement.dto.RequestCreateEmployeeDTO;
import com.product.employeemanagement.service.EmployeeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.Valid;

@RestController
public class EmployeeController {

    @Autowired
    private EmployeeService employeeService;



    @PostMapping(
            path = BasePath.BASE_PATH_EMPLOYEE + "/create",
            consumes = MediaType.APPLICATION_JSON_VALUE,
            produces = MediaType.APPLICATION_JSON_VALUE
    )
    public ResponseEntity<Object> createEmployee(@Valid @RequestBody RequestCreateEmployeeDTO dto){
        return new ResponseEntity<>(employeeService.createEmployee(dto), HttpStatus.OK);
    }
}
