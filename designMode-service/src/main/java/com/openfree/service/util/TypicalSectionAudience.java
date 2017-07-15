package com.openfree.service.util;

import com.openfree.domain.Examined;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import org.aspectj.lang.annotation.Pointcut;
import org.springframework.stereotype.Component;

import java.util.Date;

/**
 * dao层切面类
 * Created by luokai on 17-7-16.
 */
@Aspect
@Component
public class TypicalSectionAudience {
    @Pointcut(value = "execution(* com.openfree.domain.mapper.*.*(..))")
    public void performance(){

    }

    @Before(value = "execution(* com.openfree.domain.mapper..*(..)) && args(examined)")
    public void insertBefore(Examined examined){
        if (examined.getCreateTime() == null){
            examined.setCreateTime(new Date());
        }
        if (examined.getVersion() == null){
            examined.setVersion(1);
        }
    }
}
