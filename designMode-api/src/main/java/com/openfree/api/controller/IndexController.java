package com.openfree.api.controller;

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
}
