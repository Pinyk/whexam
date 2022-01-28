package com.exam.demo.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.exam.demo.entity.ExamSubject;
import com.exam.demo.mapper.ExamSubjectMapper;
import com.exam.demo.service.ExamSubjectService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ExamSubjectServiceImpl implements ExamSubjectService {

    @Autowired
    private ExamSubjectMapper examSubjectMapper;

    @Override
    public List<ExamSubject> findAll() {
        return examSubjectMapper.selectList(new LambdaQueryWrapper<>());
    }

    @Override
    public ExamSubject findById(Integer id) {
        return examSubjectMapper.selectById(id);
    }

    @Override
    public List<ExamSubject> search(ExamSubject examSubject) {
        QueryWrapper<ExamSubject> wrapperSubject = new QueryWrapper<>();
        wrapperSubject.like("context", examSubject.getContext()).eq("difficulty", examSubject.getDifficulty());
        return examSubjectMapper.selectList(wrapperSubject);
    }

    @Override
    public void saveExamSubject(ExamSubject examSubject) {
        examSubjectMapper.insert(examSubject);
    }

    @Override
    public void updateExamSubject(ExamSubject examSubject) {
        examSubjectMapper.updateById(examSubject);
    }

    @Override
    public void deleteExamSubject(Integer id) {
        examSubjectMapper.deleteById(id);
    }
}
