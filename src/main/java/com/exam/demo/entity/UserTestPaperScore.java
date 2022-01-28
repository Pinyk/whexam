package com.exam.demo.entity;

import lombok.Data;

import java.util.Date;

@Data
public class UserTestPaperScore {
    private String testPaperName;

    private double totalScore;

    private double passScore;

    private Date startTime;

    private int time;

    private String userName;

    private double scoreNum;
}
