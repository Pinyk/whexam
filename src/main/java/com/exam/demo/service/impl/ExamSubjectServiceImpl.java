package com.exam.demo.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.toolkit.StringUtils;
import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.exam.demo.Utils.FileCommit;
import com.exam.demo.entity.ExamSubject;
import com.exam.demo.entity.Subject;
import com.exam.demo.mapper.SubjectMapper;
import com.exam.demo.mapper.ExamSubjectMapper;
import com.exam.demo.params.submit.SubjectSubmitParam;
import com.exam.demo.results.vo.ExamSubjectVo;
import com.exam.demo.results.vo.PageVo;
import com.exam.demo.service.ExamSubjectService;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.util.LinkedList;
import java.util.List;

@Service
public class ExamSubjectServiceImpl implements ExamSubjectService {

    @Autowired
    private ExamSubjectMapper examSubjectMapper;

    @Autowired
    SubjectMapper subjectMapper;

    @Autowired
    FileCommit fileCommit;

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
//=========================================================组合查询=======================================================
    @Override
    public PageVo<ExamSubjectVo> search(Integer current, Integer pageSize, Integer id, String context, String subject, Integer materialQuestion) {
        Page<ExamSubject> page = new Page<>(current, pageSize);
        LambdaQueryWrapper<ExamSubject> queryWrapper = new LambdaQueryWrapper<>();

        queryWrapper.eq(ExamSubject::getMaterialQuestion, materialQuestion);
        if (id != null) {
            queryWrapper.eq(ExamSubject::getId,id);
        }
        if (!StringUtils.isBlank(context)) {
            queryWrapper.like(ExamSubject::getContext, context);
        }
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
                queryWrapper.in(ExamSubject::getSubjectId, subjectIds);
            }
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
//==================================================新增=================================================================
    @Override
    public Integer saveSubject(SubjectSubmitParam subjectSubmitParam) {

        ExamSubject examSubject = new ExamSubject();

        if (StringUtils.isNotBlank(subjectSubmitParam.getContext())) {
            examSubject.setContext(subjectSubmitParam.getContext());
        }
        if (subjectSubmitParam.getSubjectId() != null) {
            examSubject.setSubjectId(subjectSubmitParam.getSubjectId());
        }
        if (subjectSubmitParam.getAnswer() != null) {
            examSubject.setAnswer(subjectSubmitParam.getAnswer());
        }
        if (subjectSubmitParam.getScore() != null) {
            examSubject.setScore(subjectSubmitParam.getScore());
        }
        if (subjectSubmitParam.getPicture() != null) {
            try {
                fileCommit.fileCommit(subjectSubmitParam.getPicture());
                String downLoadUrl = fileCommit.downLoad(subjectSubmitParam.getPicture());
                String url = downLoadUrl.split("\\?sign=")[0];
                examSubject.setImgUrl(url);
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        examSubject.setDifficulty(1);
        examSubject.setMaterialQuestion(0);
        examSubjectMapper.insert(examSubject);

        //返回插入信息的Id
        return examSubject.getId();
    }
//======================================================================================================================
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
