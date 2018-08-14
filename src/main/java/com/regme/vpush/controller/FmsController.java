package com.regme.vpush.controller;

import com.regme.vpush.domain.Department;
import com.regme.vpush.repository.DepartmentRepository;
import com.regme.vpush.service.FmsParser;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

/**
 * Created by admin on 13.08.2018.
 */
@Controller
public class FmsController {
    @Autowired
    private FmsParser fmsParser;

    @Autowired
    DepartmentRepository departmentRepository;


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

    @PostMapping(value = "/getdep")
    public @ResponseBody Department getDepartment(@RequestParam("code") String code){
        Department department = departmentRepository.findByCode(code);
        return department;
    }




}
