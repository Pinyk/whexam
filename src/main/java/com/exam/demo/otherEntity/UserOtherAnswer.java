package com.exam.demo.otherEntity;

import io.swagger.annotations.ApiModel;
import lombok.Data;

@Data
@ApiModel(value = "userOtherAnswer")
public class UserOtherAnswer {

    private int id;

    private String context;

    private String answer;

    private double score;

    private String userAnswer;

    private double userScore;

    private String imgUrl;

}
