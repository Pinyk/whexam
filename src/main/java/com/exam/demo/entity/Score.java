package com.exam.demo.entity;

import lombok.Data;

@Data
public class Score {
    private int id;

    private int testPaperId;

    private int userId;

    private double scoreNum;
}
