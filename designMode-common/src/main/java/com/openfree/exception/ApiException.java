package com.openfree.exception;

import com.openfree.enums.ErrorCodeEnum;

/**
 * Created by luokai on 17-7-13.
 */
public class ApiException extends Exception {

    private Integer code;

    private String message;

    public ApiException(ErrorCodeEnum errorCodeEnum) {
        this(errorCodeEnum.getCode(), errorCodeEnum.getMessage());
    }

    private ApiException(Integer code, String message) {
        super(message);
        this.message = message;
        this.code = code;
    }

    public ApiException(ErrorCodeEnum errorCodeEnum, String message){
        this(errorCodeEnum.getCode(),message);
    }


    public Integer getCode() {
        return code;
    }

    @Override
    public String getMessage() {
        return message;
    }
}
