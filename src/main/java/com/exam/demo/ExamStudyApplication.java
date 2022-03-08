package com.exam.demo;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication

public class ExamStudyApplication {

    public static void main(String[] args) {
//revert
        System.out.println(1);
        SpringApplication.run(ExamStudyApplication.class, args);
    }




}
