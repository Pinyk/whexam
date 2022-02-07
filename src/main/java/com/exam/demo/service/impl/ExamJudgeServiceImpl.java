package com.exam.demo.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.exam.demo.entity.ExamJudge;
import com.exam.demo.entity.QueryQuestion;
import com.exam.demo.mapper.ExamJudgeMapper;
import com.exam.demo.service.ExamJudgeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import java.util.List;

@Service
public class ExamJudgeServiceImpl implements ExamJudgeService {

    @Autowired
    private ExamJudgeMapper examJudgeMapper;

    @Override
    public List<ExamJudge> findAll() {
        return examJudgeMapper.selectList(new LambdaQueryWrapper<>());
    }

    @Override
    public List<ExamJudge> findPage(int current, int pageSize) {
        Page<ExamJudge> page = new Page<>(current, pageSize);
        Page<ExamJudge> examJudgePage = examJudgeMapper.selectPage(page, new LambdaQueryWrapper<>());
        return examJudgePage.getRecords();
    }

    @Override
    public ExamJudge findById(Integer id) {
        return examJudgeMapper.selectById(id);
    }

    @Override
    public List<ExamJudge> search(Integer current, Integer pageSize, QueryQuestion queryQuestion) {
        Page<ExamJudge> page = new Page<>(current, pageSize);

        QueryWrapper<ExamJudge> wrapperJudge = new QueryWrapper<>();
        String context = queryQuestion.getContext();
        int difficulty = queryQuestion.getDifficulty();
        if(!StringUtils.isEmpty(context)) {
            wrapperJudge.like("context", context);
        }
        if(!StringUtils.isEmpty(difficulty)) {
            wrapperJudge.eq("defficulty", difficulty);
        }
        Page<ExamJudge> examJudgePage = examJudgeMapper.selectPage(page, wrapperJudge);
        return examJudgePage.getRecords();
    }

    @Override
    public Integer saveExamJudge(ExamJudge examJudge) {
        return examJudgeMapper.insert(examJudge);
    }

    @Override
    public Integer updateExamJudge(ExamJudge examJudge) {
        return examJudgeMapper.updateById(examJudge);
    }

    @Override
    public Integer deleteExamJudge(Integer id) {
        return examJudgeMapper.deleteById(id);
    }
}
