package com.island.aadhar.exeption;

import com.island.aadhar.util.enums.IDError;
import lombok.AllArgsConstructor;
import lombok.Data;
import org.springframework.http.HttpStatus;

@Data
@AllArgsConstructor
public class ApplicationException extends Exception{
    Integer errorCode;
    String message;
    HttpStatus httpStatus;

    public ApplicationException (IDError idError) {
        super();
        this.errorCode = idError.getErrorCode();
        this.httpStatus = idError.getHttpStatus();
        this.message = idError.getErrorMessage();
    }
}
