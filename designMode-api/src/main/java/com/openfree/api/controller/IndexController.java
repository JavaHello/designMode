package com.openfree.api.controller;

import com.openfree.api.util.WebSocketPushUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

/**
 * Created by luokai on 17-7-12.
 */
@Controller
public class IndexController {
    @RequestMapping("/")
    @ResponseBody
    public String index(){
        return "Hello World!";
    }

    @RequestMapping("/push")
    @ResponseBody
    public String push(){
        WebSocketPushUtil.pushAll("hello world!");
        return "SUCCESS";
    }
}
