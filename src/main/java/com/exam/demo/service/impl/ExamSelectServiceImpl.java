package com.exam.demo.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.exam.demo.entity.ExamSelect;
import com.exam.demo.entity.QueryQuestion;
import com.exam.demo.mapper.ExamSelectMapper;
import com.exam.demo.rtEntity.SelectQuestion;
import com.exam.demo.service.ExamSelectService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import java.util.*;

@Service
public class ExamSelectServiceImpl implements ExamSelectService {

    @Autowired
    private ExamSelectMapper examSelectMapper;

    /**
     * 对查询的 ExamSelect 进行处理后，将内容复制到 SelectQuestion 中
     * @param examSelect
     * @return
     */
    public SelectQuestion change(ExamSelect examSelect) {
        SelectQuestion selectQuestion = new SelectQuestion();
        selectQuestion.setId(examSelect.getId());
        selectQuestion.setContext(examSelect.getContext());
        selectQuestion.setSelections(Arrays.asList(examSelect.getSelection().split("；")));
        selectQuestion.setAnswer(examSelect.getAnswer());
        selectQuestion.setSubjectId(examSelect.getSubjectId());
        selectQuestion.setDifficulty(examSelect.getDifficulty());
        return selectQuestion;
    }

    @Override
    public List<SelectQuestion> findAll() {
        List<SelectQuestion> selectQuestionList = new ArrayList<>();

        List<ExamSelect> examSelectList = examSelectMapper.selectList(new LambdaQueryWrapper<>());
        for(ExamSelect examSelect : examSelectList) {
            SelectQuestion selectQuestion = change(examSelect);
            selectQuestionList.add(selectQuestion);
        }
        return selectQuestionList;
    }

    @Override
    public List<SelectQuestion> findPage(int current, int pageSize) {
        List<SelectQuestion> selectQuestions = new ArrayList<>();

        Page<ExamSelect> page = new Page<>(current, pageSize);
        Page<ExamSelect> examSelectPage = examSelectMapper.selectPage(page, new LambdaQueryWrapper<>());
        List<ExamSelect> examSelectList = examSelectPage.getRecords();
        for(ExamSelect examSelect : examSelectList) {
            SelectQuestion selectQuestion = change(examSelect);
            selectQuestions.add(selectQuestion);
        }
        return selectQuestions;
    }

    @Override
    public SelectQuestion findById(Integer id) {
        ExamSelect examSelect = examSelectMapper.selectById(id);
        SelectQuestion selectQuestion = change(examSelect);
        return selectQuestion;
    }

    @Override
    public List<SelectQuestion> search(Integer current, Integer pageSize, QueryQuestion queryQuestion) {
        List<SelectQuestion> selectQuestions = new ArrayList<>();

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
        List<ExamSelect> examSelectList = examSelectPage.getRecords();
        for(ExamSelect examSelect : examSelectList) {
            SelectQuestion selectQuestion = change(examSelect);
            selectQuestions.add(selectQuestion);
        }
        return selectQuestions;
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
