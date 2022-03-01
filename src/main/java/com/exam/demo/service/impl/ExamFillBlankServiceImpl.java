package com.exam.demo.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.exam.demo.entity.ExamFillBlank;
import com.exam.demo.mapper.ExamFillBlankMapper;
import com.exam.demo.otherEntity.SelectQuestionVo;
import com.exam.demo.service.ExamFillBlankService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import java.util.List;
@Service
public class ExamFillBlankServiceImpl implements ExamFillBlankService {

    @Autowired
    ExamFillBlankMapper examFillBlankMapper;
    /**
     * 查询所有填空题
     */
    @Override
    public List<ExamFillBlank> findAll() {
        return examFillBlankMapper.selectList(new LambdaQueryWrapper<>());
    }
    /**
     * 分页查询所有填空题
     */
    @Override
    public List<ExamFillBlank> findPage(int current, int pageSize) {
        Page<ExamFillBlank> page = new Page<>(current, pageSize);
        Page<ExamFillBlank> examFillBlankPage = examFillBlankMapper.selectPage(page, new LambdaQueryWrapper<>());
        return examFillBlankPage.getRecords();
    }
    /**
     * 根据题目ID查询填空题
     */
    @Override
    public ExamFillBlank findById(Integer id) {
        return examFillBlankMapper.selectById(id);
    }
    /**
     * 根据科目ID查询填空题
     */
    @Override
    public List<ExamFillBlank> findByBlankId(Integer subjectId) {
        QueryWrapper<ExamFillBlank> queryWrapper = new QueryWrapper<>();
        queryWrapper.eq("subject_id",subjectId);
        return examFillBlankMapper.selectList(queryWrapper);
    }
    /**
     * 根据条件查询填空题
     */
    @Override
    public List<ExamFillBlank> search(Integer current, Integer pageSize, SelectQuestionVo queryQuestion) {
        Page<ExamFillBlank> page = new Page<>(current, pageSize);
        QueryWrapper<ExamFillBlank> wrapperSubject = new QueryWrapper<>();
        String context = queryQuestion.getContext();
        if(!StringUtils.isEmpty(context)) {
            wrapperSubject.like("context", context);
        }
        if(queryQuestion.getDifficulty() != null) {
            wrapperSubject.eq("difficulty", queryQuestion.getDifficulty());
        }
        Page<ExamFillBlank> examFillBlankPage = examFillBlankMapper.selectPage(page, wrapperSubject);
        return examFillBlankPage.getRecords();
    }
    /**
     * 向题库添加填空题
     */
    @Override
    public Integer saveExamFillBlank(ExamFillBlank examFillBlank) {
        return examFillBlankMapper.insert(examFillBlank);
    }
    /**
     * 修改题库的填空题
     */
    @Override
    public Integer updateExamFillBlank(ExamFillBlank examFillBlank) {
        return examFillBlankMapper.updateById(examFillBlank);
    }
    /**
     * 删除题库中的填空题
     */
    @Override
    public Integer deleteExamFillBlank(Integer id) {
        return examFillBlankMapper.deleteById(id);
    }
}