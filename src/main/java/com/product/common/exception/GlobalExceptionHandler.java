package com.product.common.exception;

import com.product.common.constant.ReturnCode;
import com.product.common.dto.BaseResponse;
import com.product.common.dto.ErrorObjectDTO;
import com.product.common.dto.Meta;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.method.annotation.MethodArgumentTypeMismatchException;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@ControllerAdvice
public class GlobalExceptionHandler extends ResponseEntityExceptionHandler {

    // response error null pointer
    @ExceptionHandler(NullPointerException.class)
    public ResponseEntity<Object> handleNullException(NullPointerException ex, WebRequest request) {
        ErrorObjectDTO theErrorObject = new ErrorObjectDTO();
        theErrorObject.setStatusCode(HttpStatus.NOT_FOUND.value());
        theErrorObject.setMessage(ex.getMessage());
        theErrorObject.setTimeStamp(new Date());
        BaseResponse<ErrorObjectDTO> baseResponse = new BaseResponse<>(theErrorObject, new Meta(ReturnCode.FAILED_NOT_FOUND.getStatusCode(), ReturnCode.FAILED_NOT_FOUND.getMessage(),""));
        return new ResponseEntity<>(baseResponse.getCustomizeResponse("error"), HttpStatus.NOT_FOUND);
    }

    @ExceptionHandler(MethodArgumentTypeMismatchException.class)
    public ResponseEntity<Object> handleMethodArgumentException(MethodArgumentTypeMismatchException ex, WebRequest request) {
        ErrorObjectDTO errorObject = new ErrorObjectDTO();
        errorObject.setStatusCode(HttpStatus.BAD_REQUEST.value());
        errorObject.setMessage(ex.getMessage());
        errorObject.setTimeStamp(new Date());
        BaseResponse<ErrorObjectDTO> baseResponse = new BaseResponse<>(errorObject, new Meta(ReturnCode.FAILED_BAD_REQUEST.getStatusCode(), ReturnCode.FAILED_BAD_REQUEST.getMessage(),""));
        return new ResponseEntity<>(baseResponse.getCustomizeResponse("error"), HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler(Exception.class)
    public ResponseEntity<Object> handleGeneralException(Exception ex, WebRequest request) {
        ErrorObjectDTO errorObject = new ErrorObjectDTO();
        errorObject.setStatusCode(HttpStatus.INTERNAL_SERVER_ERROR.value());
        errorObject.setMessage(ex.getMessage());
        errorObject.setTimeStamp(new Date());
        BaseResponse<ErrorObjectDTO> baseResponse = new BaseResponse<>(errorObject, new Meta(ReturnCode.FAILED_SERVER_INTERNAL_SERVER_ERROR.getStatusCode(), ex.getMessage(),""));
        return new ResponseEntity<>(baseResponse.getCustomizeResponse("error"), HttpStatus.INTERNAL_SERVER_ERROR);
    }

    @Override
    protected ResponseEntity<Object> handleMethodArgumentNotValid(MethodArgumentNotValidException ex, HttpHeaders headers, HttpStatus status, WebRequest request) {
        Map<String, Object> body = new HashMap<>();
        body.put("timestamp", new Date());
        body.put("statusCode", HttpStatus.BAD_REQUEST.value());


        List<String> errors = ex.getBindingResult()
                .getFieldErrors()
                .stream()
                .map(x -> x.getDefaultMessage())
                .collect(Collectors.toList());

        body.put("messages", errors);

        BaseResponse<Object> baseResponse = new BaseResponse<>(body.get("messages"), new Meta(ReturnCode.FAILED_BAD_REQUEST.getStatusCode(), ex.getMessage(),""));
        return new ResponseEntity<>(baseResponse.getCustomizeResponse("error"), HttpStatus.NOT_FOUND);
    }

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
