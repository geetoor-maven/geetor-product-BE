package com.product.usermanagement.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.databind.PropertyNamingStrategies;
import com.fasterxml.jackson.databind.annotation.JsonNaming;
import com.product.common.BaseEntity;
import lombok.Data;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;
import java.util.UUID;

@JsonNaming(PropertyNamingStrategies.SnakeCaseStrategy.class)
@Entity
@Table(name = "registration", schema = "users")
@Data
public class Registration extends BaseEntity {

    @Id
    @Column(name = "id")
    private UUID id;

    @Column(name = "full_name")
    private String fullName;

    @Column(name = "email")
    private String email;

    @Column(name = "phone_number")
    private String phoneNumber;

    @JsonIgnore
    @Column(name = "password")
    private String password;

    @Column(name = "status")
    private String status;

}
