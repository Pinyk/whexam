package com.exam.demo.entity;

import lombok.Data;

@Data
public class ExamJudge {

    private int id;

    private String context;

    private int answer;

    private int subjectId;

    private int difficulty;
}
