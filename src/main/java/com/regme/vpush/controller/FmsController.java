package com.regme.vpush.controller;

import com.regme.vpush.service.FmsParser;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

/**
 * Created by admin on 13.08.2018.
 */
@Controller
public class FmsController {
    @Autowired
    private FmsParser fmsParser;

    @GetMapping(value = "/test")
    @ResponseBody
    public String test(){

        return "OK";
    }

    @RequestMapping("/parse")
    @ResponseBody
    public String parseFms(){
        //TODO
        fmsParser.load();
        fmsParser.unzip();
        fmsParser.parse();
        return "OK";
        // return "OK";
    }



}
