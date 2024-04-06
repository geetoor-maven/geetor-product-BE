package com.product.employeemanagement.constant;

public enum StatusEnum {

    ONLINE("ONL", "Online"),
    OFFLINE("OFF", "Offline");

    private String code;
    private String value;

    StatusEnum(String code, String value) {
        this.code = code;
        this.value = value;
    }

    public String getCode() {
        return code;
    }

    public String getValue() {
        return value;
    }
}
