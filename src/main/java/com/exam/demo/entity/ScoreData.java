package com.exam.demo.entity;

import lombok.Data;

@Data
public class ScoreData {
    private int id;

    private int testPaperId;

    private int userId;

    private int type;

    private int problemId;

    private double score;
}
