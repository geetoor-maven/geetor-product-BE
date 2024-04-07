package com.product.usermanagement.service;

import com.product.common.constant.ReturnCode;
import com.product.common.constant.StatusEnum;
import com.product.common.dto.BaseResponse;
import com.product.common.dto.Meta;
import com.product.common.exception.ItemAlreadyExistException;
import com.product.usermanagement.dto.RequestCreateUserDTO;
import com.product.usermanagement.model.Registration;
import com.product.usermanagement.repository.RegistrationRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.UUID;

@Slf4j
@Service
public class AuthService {

    @Autowired
    private RegistrationRepository registrationRepository;

    @Autowired
    private PasswordEncoder passwordEncoder;

    @Transactional
    public Object createUser(RequestCreateUserDTO dto){

        Registration findUser = registrationRepository.findByEmail(dto.getEmail());

        // validasi ketika user sudah daftar sebelumnya
        if (findUser != null){
            throw new ItemAlreadyExistException("User al ready created with email : " + dto.getEmail());
        }

        Registration registration = new Registration();
        registration.setId(UUID.randomUUID());
        registration.setFullName(dto.getName());
        registration.setEmail(dto.getEmail());
        registration.setPassword(passwordEncoder.encode(dto.getPassword()));
        registration.setPhoneNumber(dto.getNumberPhone());
        registration.setStatus(StatusEnum.ACTIVE.getCode());

        registrationRepository.save(registration);

        BaseResponse<Registration> baseResponse = new BaseResponse<>(registration, new Meta(ReturnCode.SUCCESSFULLY_REGISTER.getStatusCode(), ReturnCode.SUCCESSFULLY_REGISTER.getMessage(), ""));
        return baseResponse.getCustomizeResponse("createUser");
    }
}
