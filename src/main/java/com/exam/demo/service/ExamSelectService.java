package com.exam.demo.service;

import com.exam.demo.entity.ExamSelect;

import java.util.List;

public interface ExamSelectService {

    List<ExamSelect> findAll();

    ExamSelect findById(Integer id);

    List<ExamSelect> search(ExamSelect selectSearch);

    void saveExamSelect(ExamSelect examSelect);

    void updateExamSelect(ExamSelect examSelect);

    void deleteExamSelect(Integer id);
}
