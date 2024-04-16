package com.product.employeemanagement.service;

import com.product.common.constant.ReturnCode;
import com.product.common.dto.BaseResponse;
import com.product.common.dto.CustomPageAble;
import com.product.common.dto.Meta;
import com.product.common.exception.ItemAlreadyExistException;
import com.product.common.constant.StatusEnum;
import com.product.common.exception.ResourceNotFoundException;
import com.product.employeemanagement.dto.*;
import com.product.employeemanagement.model.Employee;
import com.product.employeemanagement.repository.EmployeeRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.criteria.Predicate;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.Optional;

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

    @Transactional(readOnly = true)
    public Object listEmployee(RequestListEmployeeDTO dto){

        Specification<Employee> specification = (root, query, criteriaBuilder) -> {
            List<Predicate> predicates = new ArrayList<>();
            if (Objects.nonNull(dto.getNip()) && !dto.getNip().equalsIgnoreCase("")){

                Optional<Employee> theEmployee = employeeRepository.findByNip(dto.getNip());

                if (theEmployee.isEmpty()){
                    throw new ResourceNotFoundException("Employee with nip " + dto.getNip() + " not found");
                }

                predicates.add(criteriaBuilder.equal(root.get("nip"), theEmployee.get().getNip()));

            }

            query.orderBy(criteriaBuilder.asc(root.get("createdDate")));
            return query.where(predicates.toArray(new javax.persistence.criteria.Predicate[]{})).getRestriction();
        };

        Pageable pageable = PageRequest.of(dto.getPage(), dto.getSize());
        CustomPageAble<Employee> customPage = new CustomPageAble<>(employeeRepository.findAll(specification, pageable));

        BaseResponse<CustomPageAble<Employee>> baseResponse = new BaseResponse<>(customPage, new Meta(ReturnCode.SUCCESSFULLY_FIND_EMPLOYEE.getStatusCode(), ReturnCode.SUCCESSFULLY_FIND_EMPLOYEE.getMessage(), ""));
        return baseResponse.getCustomizeResponse("employee_list");
    }

    @Transactional
    public Object updateEmployee(RequestUpdateEmployeeDTO dto){

        Optional<Employee> theEmployee = employeeRepository.findByNip(dto.getNip());
        if (theEmployee.isEmpty()){
            throw new ResourceNotFoundException("Employee with nip " + dto.getNip() + " not found");
        }

        theEmployee.get().setEmail(dto.getEmail() != null ? dto.getEmail() : theEmployee.get().getEmail());
        theEmployee.get().setFullName(dto.getName() != null ? dto.getName() : theEmployee.get().getFullName());
        theEmployee.get().setPhoneNumber(dto.getNumberPhone() != null ? dto.getNumberPhone() : theEmployee.get().getPhoneNumber());
        theEmployee.get().setAddress(dto.getAddress() != null ? dto.getAddress() : theEmployee.get().getAddress());

        employeeRepository.save(theEmployee.get());

        BaseResponse<Employee> baseResponse = new BaseResponse<>(theEmployee.get(), new Meta(ReturnCode.SUCCESSFULLY_UPDATE.getStatusCode(), ReturnCode.SUCCESSFULLY_UPDATE.getMessage(),""));
        return baseResponse.getCustomizeResponse("update_employee");
    }

    @Transactional(readOnly = true)
    public Object readEmployee(String nip){

        Optional<Employee> theEmployee = employeeRepository.findByNip(nip);
        if (theEmployee.isEmpty()){
            throw new ResourceNotFoundException("Employee with nip " + nip + " not found");
        }

        BaseResponse<Employee> baseResponse = new BaseResponse<>(theEmployee.get(), new Meta(ReturnCode.SUCCESSFULLY_FIND_EMPLOYEE.getStatusCode(), ReturnCode.SUCCESSFULLY_FIND_EMPLOYEE.getMessage(),""));
        return baseResponse.getCustomizeResponse("employee");
    }


    @Transactional
    public Object deleteEmployee(RequestDeleteEmployeeDTO dto){

        Optional<Employee> theEmployee = employeeRepository.findByNip(dto.getNip());
        if (theEmployee.isEmpty()){
            throw new ResourceNotFoundException("Employee with nip " + dto.getNip() + " not found");
        }

        employeeRepository.delete(theEmployee.get());

        ResponseDTO responseDTO = new ResponseDTO();
        responseDTO.setStatus(ReturnCode.SUCCESSFULLY_DELETE.getStatusCode());
        responseDTO.setMessage(ReturnCode.SUCCESSFULLY_DELETE.getMessage());

        BaseResponse<ResponseDTO> baseResponse = new BaseResponse<>(responseDTO, new Meta(ReturnCode.SUCCESSFULLY_DELETE.getStatusCode(), ReturnCode.SUCCESSFULLY_DELETE.getMessage(),""));
        return baseResponse.getCustomizeResponse("employee");
    }


}
