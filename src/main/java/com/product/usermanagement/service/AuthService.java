package com.product.usermanagement.service;

import com.product.common.constant.ReturnCode;
import com.product.common.constant.StatusEnum;
import com.product.common.dto.BaseResponse;
import com.product.common.dto.Meta;
import com.product.common.exception.ItemAlreadyExistException;
import com.product.usermanagement.dto.RequestCreateUserDTO;
import com.product.usermanagement.model.User;
import com.product.usermanagement.repository.UserRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;
import java.util.UUID;

@Slf4j
@Service
public class AuthService {

    @Autowired
    private UserRepository registrationRepository;

    @Autowired
    private PasswordEncoder passwordEncoder;

    @Transactional
    public Object createUser(RequestCreateUserDTO dto){

        Optional<User> findUser = registrationRepository.findByEmail(dto.getEmail());

        // validasi ketika user sudah daftar sebelumnya
        if (findUser.isPresent()){
            throw new ItemAlreadyExistException("User al ready created with email : " + dto.getEmail());
        }

        User registration = new User();
        registration.setId(UUID.randomUUID());
        registration.setFullName(dto.getName());
        registration.setEmail(dto.getEmail());
        registration.setPassword(passwordEncoder.encode(dto.getPassword()));
        registration.setPhoneNumber(dto.getNumberPhone());
        registration.setStatus(StatusEnum.ACTIVE.getCode());

        registrationRepository.save(registration);

        BaseResponse<User> baseResponse = new BaseResponse<>(registration, new Meta(ReturnCode.SUCCESSFULLY_REGISTER.getStatusCode(), ReturnCode.SUCCESSFULLY_REGISTER.getMessage(), ""));
        return baseResponse.getCustomizeResponse("createUser");
    }

    //public Object loginUser()
}
