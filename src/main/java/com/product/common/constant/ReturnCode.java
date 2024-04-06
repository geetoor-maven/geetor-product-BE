package com.product.common.constant;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public enum ReturnCode {

    FAILED_NOT_FOUND(404, "{0} Data tidak ditemukan");

    private final int statusCode;
    private final String message;
}
