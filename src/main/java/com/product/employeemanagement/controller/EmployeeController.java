package com.product.employeemanagement.controller;

import com.product.common.constant.BasePath;
import com.product.employeemanagement.dto.RequestCreateEmployeeDTO;
import com.product.employeemanagement.dto.RequestDeleteEmployeeDTO;
import com.product.employeemanagement.dto.RequestListEmployeeDTO;
import com.product.employeemanagement.dto.RequestUpdateEmployeeDTO;
import com.product.employeemanagement.service.EmployeeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

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

    @PostMapping(
            path = BasePath.BASE_PATH_EMPLOYEE,
            consumes = MediaType.APPLICATION_JSON_VALUE,
            produces = MediaType.APPLICATION_JSON_VALUE
    )
    public ResponseEntity<Object> findListEmployee(@Valid @RequestBody RequestListEmployeeDTO dto){
        return new ResponseEntity<>(employeeService.listEmployee(dto), HttpStatus.OK);
    }


    @PutMapping(
            path = BasePath.BASE_PATH_EMPLOYEE + "/edit",
            consumes = MediaType.APPLICATION_JSON_VALUE,
            produces = MediaType.APPLICATION_JSON_VALUE
    )
    public ResponseEntity<Object> updateEmployee(@Valid @RequestBody RequestUpdateEmployeeDTO dto){
        return new ResponseEntity<>(employeeService.updateEmployee(dto), HttpStatus.OK);
    }

    @GetMapping(
            path = BasePath.BASE_PATH_EMPLOYEE,
            produces = MediaType.APPLICATION_JSON_VALUE
    )
    public ResponseEntity<Object> findEmployee(@RequestParam String nip){
        return new ResponseEntity<>(employeeService.readEmployee(nip), HttpStatus.OK);
    }

    @DeleteMapping(
            path = BasePath.BASE_PATH_EMPLOYEE + "/delete",
            consumes = MediaType.APPLICATION_JSON_VALUE,
            produces = MediaType.APPLICATION_JSON_VALUE
    )
    public ResponseEntity<Object> deleteEmployee(@Valid @RequestBody RequestDeleteEmployeeDTO dto){
        return new ResponseEntity<>(employeeService.deleteEmployee(dto), HttpStatus.OK);
    }


}
