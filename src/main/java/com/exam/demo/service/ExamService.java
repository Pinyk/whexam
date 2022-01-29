package com.exam.demo.service;

import com.exam.demo.entity.Exam;

import java.util.List;

public interface ExamService {

    List<Exam> findByTestPaperId(Integer testPaperId);

    Integer addProblem(Exam exam);

    Integer deleteProblem(Integer id);
}
