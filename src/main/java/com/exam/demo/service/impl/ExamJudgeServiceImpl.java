package com.exam.demo.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.toolkit.StringUtils;
import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.exam.demo.Utils.FileCommit;
import com.exam.demo.entity.ExamJudge;
import com.exam.demo.entity.Subject;
import com.exam.demo.mapper.ExamJudgeMapper;
import com.exam.demo.mapper.SubjectMapper;
import com.exam.demo.params.submit.JudgeSubmitParam;
import com.exam.demo.results.vo.ExamJudgeVo;
import com.exam.demo.results.vo.PageVo;
import com.exam.demo.service.ExamJudgeService;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import javax.jws.Oneway;
import java.io.IOException;
import java.util.*;

@Service
public class ExamJudgeServiceImpl implements ExamJudgeService {

    @Autowired
    private ExamJudgeMapper examJudgeMapper;

    @Autowired
    private SubjectMapper subjectMapper;

    @Autowired
    private FileCommit fileCommit;

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
    public List<ExamJudge> findBySubjectId(Integer subjectId) {
        QueryWrapper<ExamJudge> queryWrapper = new QueryWrapper<>();
        queryWrapper.eq("subject_id",subjectId);
        return examJudgeMapper.selectList(queryWrapper);
    }
//=====================================================组合查询===========================================================
    @Override
    public PageVo<ExamJudgeVo> search(Integer current, Integer pageSize, Integer id, String context,String subject, Integer materialQuestion) {
        Page<ExamJudge> page = new Page(current, pageSize);
        LambdaQueryWrapper<ExamJudge> queryWrapper = Wrappers.lambdaQuery(ExamJudge.class);

        queryWrapper.eq(ExamJudge::getMaterialQuestion, materialQuestion);
        if (id != null) {
            queryWrapper.eq(ExamJudge::getId, id);
        }
        if (!StringUtils.isBlank(context)) {
            queryWrapper.like(ExamJudge::getContext, context);
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
                queryWrapper.in(ExamJudge::getSubjectId, subjectIds);
            }
        }
        Page<ExamJudge> selectPage = examJudgeMapper.selectPage(page, queryWrapper);
        LinkedList<ExamJudgeVo> examJudgeVos = new LinkedList<>();
        if (selectPage.getRecords() != null) {
            for (ExamJudge record : selectPage.getRecords()) {
                ExamJudgeVo examJudgeVo = copy(new ExamJudgeVo(), record);
                examJudgeVos.add(examJudgeVo);
            }
        }
        return PageVo.<ExamJudgeVo>builder()
                .values(examJudgeVos)
                .size(pageSize)
                .page(current)
                .total(selectPage.getTotal())
                .build();
    }

    private ExamJudgeVo copy(ExamJudgeVo examJudgeVo, ExamJudge examJudge) {
        BeanUtils.copyProperties(examJudge,examJudgeVo);
        examJudgeVo.setSubject(subjectMapper.selectById(examJudge.getSubjectId()).getName());
        if (examJudge.getAnswer() == 0) {
            examJudgeVo.setAnswer("错误");
        } else {
            examJudgeVo.setAnswer("正确");
        }
        return examJudgeVo;
    }
//===============================================新增填空题===============================================================
    @Override
    public Map<String,Object> saveExamJudge(String context, Integer subjectId, Integer answer, Double score, MultipartFile image, boolean isMaterialProblem) {

        ExamJudge examJudge = new ExamJudge();

        if (StringUtils.isNotBlank(context)) {
            examJudge.setContext(context);
        }
        if (subjectId != null) {
            examJudge.setSubjectId(subjectId);
        }
        if (answer != null) {
            examJudge.setAnswer(answer);
        }
        if (score != null) {
            examJudge.setScore(score);
        }
        if (image != null) {
            //调用COS服务
            try {
                fileCommit.fileCommit(image);
                //写入图片url
                String downLoadUrl = fileCommit.downLoad(image);
                String url = downLoadUrl.split("\\?sign=")[0];
                examJudge.setImgUrl(url);
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        examJudge.setDifficulty(1);
        if (!isMaterialProblem) {
            examJudge.setMaterialQuestion(0);
        } else {
            examJudge.setMaterialQuestion(1);
        }
        examJudgeMapper.insert(examJudge);
        HashMap<String, Object> map = new LinkedHashMap<>();
        map.put("newRecordId", examJudge.getId());
        return map;
    }
//======================================================================================================================
    @Override
    public Integer updateExamJudge(ExamJudge examJudge) {
        return examJudgeMapper.updateById(examJudge);
    }

    @Override
    public Integer deleteExamJudge(Integer id) {
        LambdaQueryWrapper<ExamJudge> lambdaQueryWrapper = new LambdaQueryWrapper<>();
        lambdaQueryWrapper.eq(ExamJudge::getMaterialQuestion, 0).eq(ExamJudge::getId, id);
        return examJudgeMapper.delete(lambdaQueryWrapper);
    }
}
