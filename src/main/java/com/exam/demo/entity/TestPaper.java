package com.exam.demo.entity;

import lombok.Data;

import java.util.Date;

@Data
public class TestPaper {
    private int id;

    private int subjectId;

    private String name;

    private double totalScore;

    private double passScore;

    private Date startTime;

    private Date deadTime;

    private int time;

    private int userId;

    private int departmentId;

    private int shuffle;
}
