package com.exam.demo.otherEntity;

import io.swagger.annotations.ApiModel;
import lombok.Data;


@Data
@ApiModel(value = "userAnswer")
public class UserSelectionAnswer {

    private int id;

    private String context;

    private String selections;

    private String answer;

    private double score;

    private String userAnswer;

    private double userScore;

    private String imgUrl;

}
