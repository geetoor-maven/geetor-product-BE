package com.product.common.constant;

public enum StatusEnum {

    ACTIVE("ACT", "Active"),
    INACTIVE("OFF", "Inactive");

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
