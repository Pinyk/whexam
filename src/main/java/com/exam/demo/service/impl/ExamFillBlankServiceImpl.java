package com.exam.demo.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.toolkit.StringUtils;
import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.exam.demo.entity.ExamFillBlank;
import com.exam.demo.entity.Subject;
import com.exam.demo.mapper.ExamFillBlankMapper;
import com.exam.demo.mapper.SubjectMapper;
import com.exam.demo.params.submit.FillBlankSubmitParam;
import com.exam.demo.results.vo.ExamFillBlankVo;
import com.exam.demo.results.vo.PageVo;
import com.exam.demo.service.ExamFillBlankService;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.LinkedList;
import java.util.List;
@Service
public class ExamFillBlankServiceImpl implements ExamFillBlankService {

    @Autowired
    ExamFillBlankMapper examFillBlankMapper;
    @Autowired
    SubjectMapper subjectMapper;
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
    public ExamFillBlankVo findById(Integer id) {
        ExamFillBlank examFillBlank = examFillBlankMapper.selectById(id);
        if (examFillBlank != null) {
            ExamFillBlankVo examFillBlankVo = new ExamFillBlankVo();
            BeanUtils.copyProperties(examFillBlank,examFillBlankVo);
            examFillBlankVo.setSubject(subjectMapper.selectById(examFillBlank.getSubjectId()).getName());
            return examFillBlankVo;
        }
        return null;
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
    public PageVo<ExamFillBlankVo> search(Integer current, Integer pageSize, Integer id, String context, String subject, Integer materialQuestion) {
        Page<ExamFillBlank> page = new Page<>(current, pageSize);
        LambdaQueryWrapper<ExamFillBlank> queryWrapper = new LambdaQueryWrapper<>();

        queryWrapper.eq(ExamFillBlank::getMaterialQuestion, materialQuestion);
        queryWrapper.eq(id != null, ExamFillBlank::getId, id);
        queryWrapper.like(StringUtils.isNotBlank(context), ExamFillBlank::getContext, context);
        if (!StringUtils.isBlank(subject)) {
            LambdaQueryWrapper<Subject> subjectwrapper = Wrappers.lambdaQuery(Subject.class);
            subjectwrapper
                    .select(Subject::getId)
                    .like(Subject::getName,subject);
            List<Subject> subjects = subjectMapper.selectList(subjectwrapper);
            if (!subjects.isEmpty()) {
                LinkedList<Integer> subjectIds = new LinkedList<>();
                for (Subject sub : subjects) {
                    subjectIds.add(sub.getId());
                }
                queryWrapper.in(ExamFillBlank::getSubjectId, subjectIds);
            }
        }
        Page<ExamFillBlank> examFillBlankPage = examFillBlankMapper.selectPage(page, queryWrapper);
        LinkedList<ExamFillBlankVo> examFillBlankVos = new LinkedList<>();
        for (ExamFillBlank record : examFillBlankPage.getRecords()) {
            ExamFillBlankVo examFillBlankVo = copy(new ExamFillBlankVo(), record);
            examFillBlankVos.add(examFillBlankVo);
        }

        return PageVo.<ExamFillBlankVo>builder()
                .values(examFillBlankVos)
                .size(pageSize)
                .page(current)
                .total(examFillBlankPage.getTotal())
                .build();
    }

    private ExamFillBlankVo copy(ExamFillBlankVo examFillBlankVo, ExamFillBlank examFillBlank) {
        BeanUtils.copyProperties(examFillBlank,examFillBlankVo);
        examFillBlankVo.setSubject(subjectMapper.selectById(examFillBlank.getSubjectId()).getName());
        return examFillBlankVo;
    }

    /**
     * 向题库添加填空题
     */
    @Override
    public Integer saveExamFillBlank(FillBlankSubmitParam fillBlankSubmitParam) {

        ExamFillBlank examFillBlank = new ExamFillBlank();

        if (StringUtils.isNotBlank(fillBlankSubmitParam.getContext())) {
            examFillBlank.setContext(fillBlankSubmitParam.getContext());
        }
        if (fillBlankSubmitParam.getSubjectId() != null) {
            examFillBlank.setSubjectId(fillBlankSubmitParam.getSubjectId());
        }
        if (fillBlankSubmitParam.getAnswer() != null) {
            examFillBlank.setAnswer(fillBlankSubmitParam.getAnswer());
        }
        if (fillBlankSubmitParam.getScore() != null) {
            examFillBlank.setScore(fillBlankSubmitParam.getScore());
        }
        if (fillBlankSubmitParam.getPicture() != null) {

        }
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