package com.product.common.constant;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public enum ReturnCode {
    SUCCESSFULLY_FIND_EMPLOYEE(200, "{0} Berhasil menemukan employee"),
    SUCCESSFULLY_LOGIN(200, "Login Berhasil"),
    FAILED_SERVER_INTERNAL_SERVER_ERROR(500, "Internal server error"),
    FAILED_BAD_REQUEST(400, "Request Tidak Sesuai"),
    SUCCESSFULLY_DELETE(207, "{0} Berhasil Delete"),
    SUCCESSFULLY_UPDATE(206, "{0} Berhasil Update"),
    SUCCESSFULLY_REGISTER(201, "Register Berhasil"),
    FAILED_NOT_FOUND(404, "{0} Data tidak ditemukan"),
    FAILED_DATA_ALREADY_EXISTS(409, "Data duplicate");

    private final int statusCode;
    private final String message;
}
