package com.exam.demo.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.exam.demo.entity.ExamSelect;
import com.exam.demo.mapper.ExamSelectMapper;
import com.exam.demo.service.ExamSelectService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

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
    public ExamSelect findById(Integer id) {
        return examSelectMapper.selectById(id);
    }

    @Override
    public List<ExamSelect> search(ExamSelect selectSearch) {
        QueryWrapper<ExamSelect> wrapperSelect = new QueryWrapper<>();
        wrapperSelect.like("context", selectSearch.getContext()).eq("difficulty", selectSearch.getDifficulty());
        return examSelectMapper.selectList(wrapperSelect);
    }

    @Override
    public void saveExamSelect(ExamSelect examSelect) {
        examSelectMapper.insert(examSelect);
    }

    @Override
    public void updateExamSelect(ExamSelect examSelect) {
        examSelectMapper.updateById(examSelect);
    }

    @Override
    public void deleteExamSelect(Integer id) {
        examSelectMapper.deleteById(id);
    }
}
