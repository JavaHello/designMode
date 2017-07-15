package com.openfree.enums;

/**
 * 错误代码枚举
 * code :  长度为6位
 * 1... 权限校验相关
 * 4... 参数校验相关
 * Created by luokai on 17-7-13.
 */
public enum ErrorCodeEnum {


    PARAMETER_IS_NULL(400001, "参数不能为空"),
    TOKEN_INVALID(400002, "无效的token"),
    VERIFICATION_SIGN_FAILURE(400003, "验证签名失败");

    private ErrorCodeEnum(Integer code, String message) {
        this.code = code;
        this.message = message;
    }

    public Integer getCode() {
        return code;
    }

    public String getMessage() {
        return message;
    }

    private Integer code;
    private String message;
}
