package com.product.common.exception;

import com.product.common.constant.ReturnCode;
import com.product.common.dto.BaseResponse;
import com.product.common.dto.ErrorObjectDTO;
import com.product.common.dto.Meta;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

import java.util.Date;

@ControllerAdvice
public class GlobalExceptionHandler extends ResponseEntityExceptionHandler {

    // response error resource not found
    @ExceptionHandler(ResourceNotFoundException.class)
    public ResponseEntity<Object> handleNotFoundException(ResourceNotFoundException e, WebRequest request) {
        ErrorObjectDTO dto = new ErrorObjectDTO();
        dto.setMessage(e.getMessage());
        dto.setStatusCode(HttpStatus.NOT_FOUND.value());
        dto.setTimeStamp(new Date());
        BaseResponse<ErrorObjectDTO> baseResponse = new BaseResponse<>(dto, new Meta(ReturnCode.FAILED_NOT_FOUND.getStatusCode(), e.getMessage(), ""));
        return new ResponseEntity<>(baseResponse.getCustomizeResponse("error"), HttpStatus.NOT_FOUND);
    }

    // response error item already exist
    @ExceptionHandler(ItemAlreadyExistException.class)
    public ResponseEntity<Object> handleItemExistsException(ItemAlreadyExistException ex, WebRequest request) {
        ErrorObjectDTO theErrorObject = new ErrorObjectDTO();
        theErrorObject.setStatusCode(HttpStatus.CONFLICT.value());
        theErrorObject.setMessage(ex.getMessage());
        theErrorObject.setTimeStamp(new Date());
        BaseResponse<ErrorObjectDTO> baseResponse = new BaseResponse<>(theErrorObject, new Meta(ReturnCode.FAILED_DATA_ALREADY_EXISTS.getStatusCode(), ReturnCode.FAILED_DATA_ALREADY_EXISTS.getMessage(),""));
        return new ResponseEntity<>(baseResponse.getCustomizeResponse("error"), HttpStatus.CONFLICT);
    }


}
