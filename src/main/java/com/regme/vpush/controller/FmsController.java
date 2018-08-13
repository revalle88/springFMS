package com.regme.vpush.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ResponseBody;

/**
 * Created by admin on 13.08.2018.
 */
@Controller
public class FmsController {
    @GetMapping(value = "/test")
    @ResponseBody
    public String test(){

        return "OK";
    }
}
