package com.exam.demo.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.toolkit.StringUtils;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.exam.demo.entity.ExamSubject;
import com.exam.demo.mapper.SubjectMapper;
import com.exam.demo.params.SelectParam;
import com.exam.demo.mapper.ExamSubjectMapper;
import com.exam.demo.results.vo.ExamJudgeVo;
import com.exam.demo.results.vo.ExamSubjectVo;
import com.exam.demo.results.vo.PageVo;
import com.exam.demo.service.ExamSubjectService;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.LinkedList;
import java.util.List;

@Service
public class ExamSubjectServiceImpl implements ExamSubjectService {

    @Autowired
    private ExamSubjectMapper examSubjectMapper;

    @Autowired
    SubjectMapper subjectMapper;

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
    public List<ExamSubject> findBySubjectId(Integer subjectId) {
        QueryWrapper<ExamSubject> queryWrapper = new QueryWrapper<>();
        queryWrapper.eq("subject_id",subjectId);
        return examSubjectMapper.selectList(queryWrapper);
    }

    @Override
    public PageVo<ExamSubjectVo> search(Integer current, Integer pageSize, Integer id, String context) {
        Page<ExamSubject> page = new Page<>(current, pageSize);
        LambdaQueryWrapper<ExamSubject> queryWrapper = new LambdaQueryWrapper<>();

        if (id != null) {
            queryWrapper.eq(ExamSubject::getId,id);
        }
        if (!StringUtils.isBlank(context)) {
            queryWrapper.like(ExamSubject::getContext, context);
        }
        Page<ExamSubject> examSubjectPage = examSubjectMapper.selectPage(page, queryWrapper);
        LinkedList<ExamSubjectVo> examSubjectVos = new LinkedList<>();
        for (ExamSubject record : examSubjectPage.getRecords()) {
            ExamSubjectVo subjectVo = copy(new ExamSubjectVo(), record);
            examSubjectVos.add(subjectVo);
        }
        return PageVo.<ExamSubjectVo>builder()
                .values(examSubjectVos)
                .size(pageSize)
                .page(current)
                .total(examSubjectPage.getTotal())
                .build();
    }

    private ExamSubjectVo copy(ExamSubjectVo examSubjectVo, ExamSubject examSubject) {
        BeanUtils.copyProperties(examSubject,examSubjectVo);
        examSubjectVo.setSubject(subjectMapper.selectById(examSubject.getSubjectId()).getName());
        return examSubjectVo;
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
