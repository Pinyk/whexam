package com.exam.demo.entity;

import lombok.Data;

@Data
public class ExamSelect {

    private int id;

    private String context;

    private String selection;

    private String answer;

    private int subjectId;

    private int difficulty;
}
