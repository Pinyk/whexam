package com.exam.demo.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.exam.demo.entity.ExamSubject;
import com.exam.demo.entity.QueryQuestion;
import com.exam.demo.mapper.ExamSubjectMapper;
import com.exam.demo.service.ExamSubjectService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

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
    public List<ExamSubject> findPage(int current, int pageSize) {
        Page<ExamSubject> page = new Page<>(current, pageSize);
        Page<ExamSubject> examSubjectPage = examSubjectMapper.selectPage(page, new LambdaQueryWrapper<>());
        return examSubjectPage.getRecords();
    }

    @Override
    public ExamSubject findById(Integer id) {
        return examSubjectMapper.selectById(id);
    }

    @Override
    public List<ExamSubject> search(Integer current, Integer pageSize, QueryQuestion queryQuestion) {
        Page<ExamSubject> page = new Page<>(current, pageSize);

        QueryWrapper<ExamSubject> wrapperSubject = new QueryWrapper<>();
        String context = queryQuestion.getContext();
        int difficulty = queryQuestion.getDifficulty();
        if(!StringUtils.isEmpty(context)) {
            wrapperSubject.like("context", context);
        }
        if(!StringUtils.isEmpty(difficulty)) {
            wrapperSubject.eq("difficulty", difficulty);
        }

        Page<ExamSubject> examSubjectPage = examSubjectMapper.selectPage(page, wrapperSubject);
        return examSubjectPage.getRecords();
    }

    @Override
    public Integer saveExamSubject(ExamSubject examSubject) {
        return examSubjectMapper.insert(examSubject);
    }

    @Override
    public Integer updateExamSubject(ExamSubject examSubject) {
        return examSubjectMapper.updateById(examSubject);
    }

    @Override
    public Integer deleteExamSubject(Integer id) {
        return examSubjectMapper.deleteById(id);
    }
}
