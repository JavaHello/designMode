package com.openfree.api.controller;

import com.alibaba.fastjson.JSONObject;
import com.github.pagehelper.util.StringUtil;
import com.openfree.enums.ErrorCodeEnum;
import com.openfree.exception.ApiException;
import com.openfree.service.token.ApiService;
import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.io.IOUtils;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;

import javax.annotation.PostConstruct;
import javax.servlet.http.HttpServletRequest;
import javax.validation.ConstraintViolation;
import javax.validation.Validation;
import javax.validation.Validator;
import javax.validation.ValidatorFactory;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;
import java.util.Set;

/**
 * Created by luokai on 17-7-15.
 */
@Controller
public class BaseController {
    private Logger logger = LoggerFactory.getLogger(getClass());

    private final static String CODE = "code";
    private final static String CODE_MSG = "codeMsg";

    @Autowired
    protected ApiService apiService;

    private Validator validator;

    @PostConstruct
    private void initValidator() {
        ValidatorFactory factory = Validation.buildDefaultValidatorFactory();
        validator = factory.getValidator();
    }

    protected String readRequestParam(HttpServletRequest request) {
        try {
            return IOUtils.toString(request.getInputStream(), "UTF8");
        } catch (IOException e) {
            logger.error("读取请求参数错误:", e);
        }
        return null;
    }

    /**
     * get请求参数封装为map
     * @param request
     * @return map
     */
    protected Map<String, String> toMap(HttpServletRequest request) {
        Map<String, String> map = new HashMap<>();
        Map<String, String[]> parameterMap = request.getParameterMap();
        for (Map.Entry<String, String[]> stringEntry : parameterMap.entrySet()) {
            map.put(stringEntry.getKey(), StringUtils.join(stringEntry.getValue(), ","));
        }
        return map;
    }

    protected final void verifyParamNull(JSONObject json, String... key) throws ApiException {
        verifyParam(json, false, key);
    }

    protected final void verifyParamEmpty(JSONObject json, String... key) throws ApiException {
        verifyParam(json, true, key);
    }

    protected final void verifyParam(JSONObject json, boolean b, String... key) throws ApiException {
        for (String s : key) {
            String value = json.getString(s);
            if (null == value) {
                throw new ApiException(ErrorCodeEnum.PARAMETER_IS_NULL, "缺少参数:" + s);
            }
            if (b) {
                if (StringUtil.isEmpty(value)) {
                    throw new ApiException(ErrorCodeEnum.PARAMETER_IS_NULL, "参数为空:" + s);
                }
            }
        }
    }

    protected final void putSuccessMessage(JSONObject json) {
        putSuccessMessage(json, null);
    }

    protected void putSuccessMessage(JSONObject json, String message) {
        putMessage(json, ErrorCodeEnum.SUCCESS.getCode(), null != message ? message : ErrorCodeEnum.SUCCESS.getMessage());
    }

    protected final void putErrorMessage(JSONObject json) {
        putMessage(json, ErrorCodeEnum.SYSTEM_ERROR.getCode(), ErrorCodeEnum.SYSTEM_ERROR.getMessage());
    }

    protected final void putMessage(JSONObject json, Integer code, String message) {
        json.put(CODE, code);
        json.put(CODE_MSG, message);
    }

    protected final void putMessage(JSONObject json, ErrorCodeEnum errorCodeEnum) {
        putMessage(json, errorCodeEnum.getCode(), errorCodeEnum.getMessage());
    }

    protected final void putMessage(JSONObject json, ErrorCodeEnum errorCodeEnum, String message) {
        putMessage(json, errorCodeEnum.getCode(), message);
    }

    protected final void validateModel(Object object) throws ApiException {
        Set<ConstraintViolation<Object>> validate = validator.validate(object);
        if (CollectionUtils.isNotEmpty(validate)) {
            for (ConstraintViolation<Object> objectConstraintViolation : validate) {
                throw new ApiException(ErrorCodeEnum.PARAMETER_ERROR, objectConstraintViolation.getMessage());
            }
        }
    }

}
