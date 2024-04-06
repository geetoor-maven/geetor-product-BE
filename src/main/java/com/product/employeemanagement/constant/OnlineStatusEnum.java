package com.product.employeemanagement.constant;

public enum OnlineStatusEnum {

    ONLINE("ONL", "Online"),
    OFFLINE("OFF", "Offline");

    private String code;
    private String value;

    OnlineStatusEnum(String code, String value) {
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
