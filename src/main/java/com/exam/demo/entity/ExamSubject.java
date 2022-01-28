package com.exam.demo.entity;

import lombok.Data;

@Data
public class ExamSubject {
    private int id;

    private String context;

    private String answer;

    private int subjectId;

    private int difficulty;
}
