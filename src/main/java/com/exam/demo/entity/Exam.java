package com.exam.demo.entity;

import lombok.Data;

@Data
public class Exam {
    private int id;

    private int testPaperId;

    private int type;

    private int problemId;

    private double score;
}
