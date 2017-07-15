package com.openfree.service.util;

import com.openfree.domain.model.user.UserInfo;
import com.openfree.enums.ErrorCodeEnum;
import com.openfree.exception.ApiException;
import org.apache.shiro.crypto.hash.Md5Hash;
import org.springframework.stereotype.Component;

import java.util.UUID;

/**
 * Created by luokai on 17-7-15.
 */
@Component("passwordHelper")
public class PasswordHelper {



    public void genUserInfoPassword(UserInfo userInfo){
        String upassword = userInfo.getUpassword();
        String salt = UUID.randomUUID().toString().replaceAll("-", "");
        userInfo.setSalt(salt);
        userInfo.setUpassword(new Md5Hash(upassword, userInfo.getChaos() + salt).toHex());
    }

    public UserInfo verifyUserInfoPassword(String password, UserInfo userInfo) throws ApiException {
        if (userInfo == null){
            throw new ApiException(ErrorCodeEnum.USERNAME_PASSWORD_ERROR);
        }
        String password1 = new Md5Hash(password, userInfo.getChaos() + userInfo.getSalt()).toHex();
        if (password1.equals(userInfo.getUpassword())){
            return  userInfo;
        }else {
            throw new ApiException(ErrorCodeEnum.USERNAME_PASSWORD_ERROR);
        }
    }
}
