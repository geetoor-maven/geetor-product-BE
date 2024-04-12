package com.product.employeemanagement.service;

import com.product.common.constant.ReturnCode;
import com.product.common.dto.BaseResponse;
import com.product.common.dto.Meta;
import com.product.common.exception.ItemAlreadyExistException;
import com.product.common.constant.StatusEnum;
import com.product.employeemanagement.dto.RequestCreateEmployeeDTO;
import com.product.employeemanagement.model.Employee;
import com.product.employeemanagement.repository.EmployeeRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Slf4j
@Service
public class EmployeeService {

    @Autowired
    private EmployeeRepository employeeRepository;

    @Transactional
    public Object createEmployee(RequestCreateEmployeeDTO dto){

        // validasi jika nip sudah ada di dalam database
        if (employeeRepository.existsByNip(dto.getNip())) {
            throw new ItemAlreadyExistException("User al ready created with nip : " + dto.getNip());
        }

        Employee employee = new Employee();
        employee.setNip(dto.getNip());
        employee.setEmail(dto.getEmail());
        employee.setFullName(dto.getName());
        employee.setPhoneNumber(dto.getNumberPhone());
        employee.setAddress(dto.getAddress());
        employee.setStatus(StatusEnum.ACTIVE.getCode());

        employeeRepository.save(employee);

        BaseResponse<Employee> baseResponse = new BaseResponse<>(employee, new Meta(ReturnCode.SUCCESSFULLY_REGISTER.getStatusCode(), ReturnCode.SUCCESSFULLY_REGISTER.getMessage(), ""));

        return baseResponse.getCustomizeResponse("createEmployee");
    }
}
