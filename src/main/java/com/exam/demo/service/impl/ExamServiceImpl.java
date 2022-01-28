package com.exam.demo.service.impl;

import com.exam.demo.entity.Exam;
import com.exam.demo.mapper.ExamMapper;
import com.exam.demo.service.ExamService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ExamServiceImpl implements ExamService {

    @Autowired
    private ExamMapper examMapper;

    /**
     * 查找每张试卷对应的所有试题
     * @param testPaperId
     * @return
     */
    @Override
    public List<Exam> findByTestPaperId(Integer testPaperId) {
        return null;
    }

    @Override
    public void addProblem(Exam exam) {
        examMapper.insert(exam);
    }

    @Override
    public void deleteProblem(Integer id) {
        examMapper.deleteById(id);
    }
}
