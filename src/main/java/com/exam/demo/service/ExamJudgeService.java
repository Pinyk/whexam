package com.exam.demo.service;

import com.exam.demo.entity.ExamJudge;

import java.util.List;

public interface ExamJudgeService {

    List<ExamJudge> findAll();

    ExamJudge findById(Integer id);

    List<ExamJudge> search(ExamJudge judgeSearch);

    void saveExamJudge(ExamJudge examJudge);

    void updateExamJudge(ExamJudge examJudge);

    void deleteExamJudge(Integer id);
}
