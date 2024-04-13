package com.product.usermanagement.controller;

import com.product.common.constant.BasePath;
import com.product.usermanagement.dto.RequestCreateUserDTO;
import com.product.usermanagement.dto.RequestLoginDTO;
import com.product.usermanagement.service.AuthService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.Valid;

@RestController
public class AuthController {

    @Autowired
    private AuthService authService;

    @PostMapping(
            path = BasePath.BASE_PATH_USER_REGIST + "/register",
            consumes = MediaType.APPLICATION_JSON_VALUE,
            produces = MediaType.APPLICATION_JSON_VALUE
    )
    public ResponseEntity<Object> registerUser(@Valid @RequestBody RequestCreateUserDTO dto){
        return new ResponseEntity<>(authService.createUser(dto), HttpStatus.OK);
    }

    @PostMapping(
            path = BasePath.BASE_API + "/login",
            consumes = MediaType.APPLICATION_JSON_VALUE,
            produces = MediaType.APPLICATION_JSON_VALUE
    )
    public ResponseEntity<Object> loginUser(@Valid @RequestBody RequestLoginDTO dto) {
        return new ResponseEntity<>(authService.loginUser(dto), HttpStatus.OK);
    }

}
