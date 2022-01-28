package com.exam.demo.entity;

import lombok.Data;

import java.util.Date;

@Data
public class Paper {
    private int id;

    private String title;

    private String context;

    private String type;

    private  int userId;

    private Date date;
}
