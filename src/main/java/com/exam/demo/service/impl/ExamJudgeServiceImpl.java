package com.exam.demo.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.exam.demo.entity.ExamJudge;
import com.exam.demo.mapper.ExamJudgeMapper;
import com.exam.demo.service.ExamJudgeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

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
    public ExamJudge findById(Integer id) {
        return examJudgeMapper.selectById(id);
    }

    @Override
    public List<ExamJudge> search(ExamJudge judgeSearch) {
        QueryWrapper<ExamJudge> wrapperJudge = new QueryWrapper<>();
        if(!"".equals(judgeSearch.getContext())) {
            wrapperJudge.like("context", judgeSearch.getContext());
        }
        if(judgeSearch.getDefficulty() != 0) {
            wrapperJudge.eq("defficulty", judgeSearch.getDefficulty());
        }
//        wrapperJudge.like("context", judgeSearch.getContext()).eq("defficulty", judgeSearch.getDefficulty());
        return examJudgeMapper.selectList(wrapperJudge);
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
