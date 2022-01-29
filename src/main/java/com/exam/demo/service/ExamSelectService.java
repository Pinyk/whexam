package com.exam.demo.service;

import com.exam.demo.entity.ExamSelect;

import java.util.List;

public interface ExamSelectService {

    List<ExamSelect> findAll();

    ExamSelect findById(Integer id);

    List<ExamSelect> search(ExamSelect selectSearch);

    Integer saveExamSelect(ExamSelect examSelect);

    Integer updateExamSelect(ExamSelect examSelect);

    Integer deleteExamSelect(Integer id);
}
