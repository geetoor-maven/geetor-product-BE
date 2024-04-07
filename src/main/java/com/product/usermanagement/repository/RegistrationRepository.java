package com.product.usermanagement.repository;

import com.product.usermanagement.model.Registration;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.UUID;

@Repository
public interface RegistrationRepository extends JpaRepository<Registration, UUID> {
    Registration findByEmail(String email);
}
