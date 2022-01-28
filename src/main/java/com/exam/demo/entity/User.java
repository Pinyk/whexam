package com.exam.demo.entity;

import lombok.Data;

@Data
public class User {
    private int id;

    private String name;

    private String gender;

    private String openid;

    private int roleId;

    private String image;

    private String wxname;

    private String tele;

    private String email;

    private double time;

    private int departmentId;

    private int positionId;

    private int password;
}
