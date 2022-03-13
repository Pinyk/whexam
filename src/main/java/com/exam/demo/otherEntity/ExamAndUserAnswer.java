package com.exam.demo.otherEntity;

import io.swagger.annotations.ApiModel;
import lombok.Data;

import java.util.List;

@Data
@ApiModel(value = "examAndUserAnswer")
public class ExamAndUserAnswer {
    private Integer id;

    private String name;

    private double totalScore;

    private double passScore;

    private double time; //考试时间min

    private Integer subject; //id

    private Integer department;

    private String startTime;

    private String endTime;

    private String repeat;  //boolean or String

//    private double userScore;

//    private String isPass; //boolean or String

    private String username;

    private Integer userDepartment;

    private  String userPosition;

    private String userNum;


}
