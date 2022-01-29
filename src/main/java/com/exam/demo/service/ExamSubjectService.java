package com.exam.demo.service;

import com.exam.demo.entity.ExamSubject;
import org.springframework.stereotype.Service;

import java.util.List;

public interface ExamSubjectService {

    List<ExamSubject> findAll();

    ExamSubject findById(Integer id);

    List<ExamSubject> search(ExamSubject examSubject);

    Integer saveExamSubject(ExamSubject examSubject);

    Integer updateExamSubject(ExamSubject examSubject);

    Integer deleteExamSubject(Integer id);
}
