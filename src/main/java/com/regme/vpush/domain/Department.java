package com.regme.vpush.domain;

import javax.persistence.*;

/**
 * Created by admin on 13.08.2018.
 */
@Entity
@Table(name = "department")
public class Department {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)

    private Long id;

    private String name;
    private String code;


    public Department(String name, String code) {
        this.name = name;
        this.code = code;
    }

    public Department() {

    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }
}