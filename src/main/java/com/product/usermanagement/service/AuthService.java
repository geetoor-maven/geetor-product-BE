package com.product.usermanagement.service;

import com.product.common.constant.ReturnCode;
import com.product.common.constant.StatusEnum;
import com.product.common.dto.BaseResponse;
import com.product.common.dto.Meta;
import com.product.common.exception.ItemAlreadyExistException;
import com.product.common.exception.ResourceNotFoundException;
import com.product.usermanagement.dto.RequestCreateUserDTO;
import com.product.usermanagement.dto.RequestLoginDTO;
import com.product.usermanagement.dto.ResponseUserLoginDTO;
import com.product.usermanagement.model.User;
import com.product.usermanagement.repository.UserRepository;
import com.product.usermanagement.security.JWTTokenUtil;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.DisabledException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.userdetails.UserDetails;
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
    private AuthenticationManager authenticationManager;
    @Autowired
    private PasswordEncoder passwordEncoder;
    @Autowired
    private CustomUserDetailService customUserDetailsServices;
    @Autowired
    private JWTTokenUtil jwtTokenUtil;

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

    @Transactional
    public Object loginUser(RequestLoginDTO dto){

        Optional<User> theUser = registrationRepository.findByEmail(dto.getEmail());

        if (theUser.isEmpty()){
            throw new ResourceNotFoundException("User not found");
        }

        if (!passwordEncoder.matches(dto.getPassword(), theUser.get().getPassword())){
            throw new ResourceNotFoundException("Password incorrect");
        }

        try {
            authenticationUser(dto.getEmail(), dto.getPassword());
        } catch (Exception ex) {
            log.info("ex{}", ex.getMessage());
        }

        // generate token
        final UserDetails details = customUserDetailsServices.loadUserByUsername(theUser.get().getEmail());
        final String token = jwtTokenUtil.generateToken(details);

        ResponseUserLoginDTO responseUserLoginDTO = new ResponseUserLoginDTO();
        responseUserLoginDTO.setEmail(theUser.get().getEmail());
        responseUserLoginDTO.setJwtToken(token);

        BaseResponse<ResponseUserLoginDTO> baseResponse = new BaseResponse<>(responseUserLoginDTO, new Meta(ReturnCode.SUCCESSFULLY_LOGIN.getStatusCode(), ReturnCode.SUCCESSFULLY_LOGIN.getMessage(), ""));

        return baseResponse.getCustomizeResponse("user");
    }

    private void authenticationUser(String email, String pwd) throws Exception {
        try {
            authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(email, pwd));
        } catch (DisabledException ex) {
            throw new Exception("User Disable");
        } catch (BadCredentialsException ex) {
            throw new Exception("Bad Credeantials");
        }
    }
}
