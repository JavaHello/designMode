package com.openfree.enums;

/**
 * 错误代码枚举
 * code :  长度为6位
 * 1... 权限校验相关
 * 4... 参数校验相关
 * Created by luokai on 17-7-13.
 */
public enum ErrorCodeEnum {


    SUCCESS(100000,"成功"),

    SYSTEM_ERROR(400000,"系统异常"),
    USERNAME_PASSWORD_ERROR(100001, "用户名或密码错误"),
    PARAMETER_IS_NULL(400001, "参数不能为空"),
    TOKEN_INVALID(400002, "无效的token"),
    PARAMETER_ERROR(400005, "参数错误"),

    VERIFICATION_SIGN_FAILURE(400003, "验证签名失败"),
    USER_EXIST(500001, "用户名已存在"),
    EMAIL_EXIST(500002, "邮箱已注册");

    private ErrorCodeEnum(Integer code, String message) {
        this.code = code;
        this.message = message;
    }

    public int getCode() {
        return code;
    }

    public String getMessage() {
        return message;
    }

    private int code;
    private String message;
}
