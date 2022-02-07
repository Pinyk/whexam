package com.exam.demo.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.exam.demo.entity.ExamSelect;
import com.exam.demo.entity.QueryQuestion;
import com.exam.demo.mapper.ExamSelectMapper;
import com.exam.demo.service.ExamSelectService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import java.util.List;

@Service
public class ExamSelectServiceImpl implements ExamSelectService {

    @Autowired
    private ExamSelectMapper examSelectMapper;

    @Override
    public List<ExamSelect> findAll() {
        return examSelectMapper.selectList(new LambdaQueryWrapper<>());
    }

    @Override
    public List<ExamSelect> findPage(int current, int pageSize) {
        Page<ExamSelect> page = new Page<>(current, pageSize);
        Page<ExamSelect> examSelectPage = examSelectMapper.selectPage(page, new LambdaQueryWrapper<>());
        return examSelectPage.getRecords();
    }

    @Override
    public ExamSelect findById(Integer id) {
        return examSelectMapper.selectById(id);
    }

    @Override
    public List<ExamSelect> search(Integer current, Integer pageSize, QueryQuestion queryQuestion) {
        Page<ExamSelect> page = new Page<>(current, pageSize);

        QueryWrapper<ExamSelect> wrapperSelect = new QueryWrapper<>();
        String context = queryQuestion.getContext();
        int difficulty = queryQuestion.getDifficulty();
        if(!StringUtils.isEmpty(context)) {
            wrapperSelect.like("context", context);
        }
        if(!StringUtils.isEmpty(difficulty)) {
            wrapperSelect.eq("difficulty", difficulty);
        }

        Page<ExamSelect> examSelectPage = examSelectMapper.selectPage(page, wrapperSelect);
        return examSelectPage.getRecords();
    }

    @Override
    public Integer saveExamSelect(ExamSelect examSelect) {
        return examSelectMapper.insert(examSelect);
    }

    @Override
    public Integer updateExamSelect(ExamSelect examSelect) {
        return examSelectMapper.updateById(examSelect);
    }

    @Override
    public Integer deleteExamSelect(Integer id) {
        return examSelectMapper.deleteById(id);
    }
}
