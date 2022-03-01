package com.exam.demo.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.core.toolkit.StringUtils;
import com.exam.demo.entity.ExamSelect;
import com.exam.demo.entity.Subject;
import com.exam.demo.mapper.ExamSelectMapper;
import com.exam.demo.mapper.SubjectMapper;
import com.exam.demo.otherEntity.SelectQuestionVo;
import com.exam.demo.service.ExamSelectService;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.*;

@Service
public class ExamSelectServiceImpl implements ExamSelectService {

    @Autowired
    private ExamSelectMapper examSelectMapper;

    @Autowired
    SubjectMapper subjectMapper;

    /**
     * 对查询的 ExamSelect 进行处理后，将内容复制到 SelectQuestion 中
     * @param examSelect
     * @return
     */
    public SelectQuestionVo change(ExamSelect examSelect) {
        SelectQuestionVo selectQuestionVo = new SelectQuestionVo();
        selectQuestionVo.setId(examSelect.getId());
        selectQuestionVo.setContext(examSelect.getContext());
        selectQuestionVo.setSelections(Arrays.asList(examSelect.getSelection().split("；")));
        selectQuestionVo.setAnswer(examSelect.getAnswer());
        selectQuestionVo.setSubject(subjectMapper.selectById(examSelect.getSubjectId()).getName());
        selectQuestionVo.setDifficulty(examSelect.getDifficulty());
        selectQuestionVo.setScore(examSelect.getScore());
        selectQuestionVo.setType(examSelect.getType());
        selectQuestionVo.setImgUrl(examSelect.getImgUrl());
        return selectQuestionVo;
    }

    @Override
    public List<SelectQuestionVo> findAll() {
        List<SelectQuestionVo> selectQuestionVoList = new ArrayList<>();

        List<ExamSelect> examSelectList = examSelectMapper.selectList(new LambdaQueryWrapper<>());
        for(ExamSelect examSelect : examSelectList) {
            SelectQuestionVo selectQuestionVo = change(examSelect);
            selectQuestionVoList.add(selectQuestionVo);
        }
        return selectQuestionVoList;
    }

    @Override
    public List<SelectQuestionVo> findSingleOrMultipleSelection(int type) {
        List<SelectQuestionVo> selectQuestionVoList = new ArrayList<>();

        QueryWrapper<ExamSelect> queryWrapper = new QueryWrapper<>();
        queryWrapper.eq("type", type);
        List<ExamSelect> examSelectList = examSelectMapper.selectList(queryWrapper);
        for(ExamSelect examSelect : examSelectList) {
            SelectQuestionVo selectQuestionVo = change(examSelect);
            selectQuestionVoList.add(selectQuestionVo);
        }
        return selectQuestionVoList;
    }

    @Override
    public List<SelectQuestionVo> findPage(int current, int pageSize, int type) {
        List<SelectQuestionVo> selectQuestionVos = new ArrayList<>();

        QueryWrapper<ExamSelect> queryWrapper = new QueryWrapper<>();
        queryWrapper.eq("type", type);

        Page<ExamSelect> page = new Page<>(current, pageSize);
        Page<ExamSelect> examSelectPage = examSelectMapper.selectPage(page, queryWrapper);
        List<ExamSelect> examSelectList = examSelectPage.getRecords();
        for(ExamSelect examSelect : examSelectList) {
            SelectQuestionVo selectQuestionVo = change(examSelect);
            selectQuestionVos.add(selectQuestionVo);
        }
        return selectQuestionVos;
    }

    @Override
    public SelectQuestionVo findById(Integer id) {
        ExamSelect examSelect = examSelectMapper.selectById(id);
        SelectQuestionVo selectQuestionVo = change(examSelect);
        return selectQuestionVo;
    }

    @Override
    public List<ExamSelect> findBySubjectId(Integer subjectId, Integer type) {
        QueryWrapper<ExamSelect> queryWrapper = new QueryWrapper<>();
        queryWrapper.eq("subject_id",subjectId);
        queryWrapper.eq("type",type);
        return examSelectMapper.selectList(queryWrapper);
    }

    @Override
    public List<SelectQuestionVo> search(Integer id, String name, String subject, Integer currentPage, Integer pageSize, Integer type) {
        List<SelectQuestionVo> selectQuestionVos = new LinkedList<>();
        Page<ExamSelect> page = new Page<>(currentPage, pageSize);
        LambdaQueryWrapper<ExamSelect> wrapperSelect = new LambdaQueryWrapper();

        if (id != null) {
            wrapperSelect.eq(ExamSelect::getId, id);
        }
        if (!StringUtils.isBlank(name)) {
            wrapperSelect.like(ExamSelect::getContext, name);
        }
        if (!StringUtils.isBlank(subject)) {
            LambdaQueryWrapper<Subject> subjectwrapper = new LambdaQueryWrapper<>();
            subjectwrapper.like(Subject::getName,name);
            Subject sub = subjectMapper.selectOne(subjectwrapper);
            if (sub != null) {
                wrapperSelect.like(ExamSelect::getSubjectId, sub.getId());
            }
        }

        Page<ExamSelect> examSelectPage = examSelectMapper.selectPage(page, wrapperSelect);
        List<ExamSelect> examSelectList = examSelectPage.getRecords();
        for(ExamSelect examSelect : examSelectList) {
            SelectQuestionVo selectQuestionVo = change(examSelect);
            selectQuestionVos.add(selectQuestionVo);
        }
        return selectQuestionVos;
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
