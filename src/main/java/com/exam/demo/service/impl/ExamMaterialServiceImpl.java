package com.exam.demo.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.toolkit.StringUtils;
import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.exam.demo.entity.*;
import com.exam.demo.mapper.*;
import com.exam.demo.otherEntity.SelectQuestionVo;
import com.exam.demo.results.vo.*;
import com.exam.demo.service.*;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.LinkedList;
import java.util.List;

@Service
public class ExamMaterialServiceImpl implements ExamMaterialService {

    @Autowired
    ExamMaterialMapper materialMapper;
    @Autowired
    MaterialProblemMapper materialProblemMapper;
    @Autowired
    ExamSelectService examSelectService;
    @Autowired
    ExamJudgeService examJudgeService;
    @Autowired
    ExamSubjectService examSubjectService;
    @Autowired
    ExamFillBlankService examFillBlankService;
    @Autowired
    SubjectMapper subjectMapper;
    @Autowired
    ExamJudgeMapper examJudgeMapper;
    @Autowired
    ExamSelectMapper examSelectMapper;
    @Autowired
    ExamFillBlankMapper examFillBlankMapper;
    @Autowired
    ExamSubjectMapper examSubjectMapper;

    /**
     * 根据材料题id查询并分页
     * @param id 材料题Id
     * @param context 材料题题目内容
     * @return
     */
    @Override
    public ExamMaterialVo findMaterialProblemByIdAndContext(Integer id, String context) {

        ExamMaterialVo examMaterialVo = new ExamMaterialVo();

        //先查exam_material表
        LambdaQueryWrapper<ExamMaterial> queryWrapper = Wrappers.lambdaQuery(ExamMaterial.class);
        queryWrapper.eq((id != null), ExamMaterial::getId, id);
        queryWrapper.like(StringUtils.isNotBlank(context), ExamMaterial::getContext, context);
        ExamMaterial examMaterial = materialMapper.selectOne(queryWrapper);
        //对返回对象进行判断
        if (examMaterial != null) {
            BeanUtils.copyProperties(examMaterial,examMaterialVo);
            examMaterialVo.setSubject(subjectMapper.selectById(examMaterial.getSubjectId()).getName());
            //再使用material的Id查询中间表
            List<MaterialProblem> materialProblemList = materialProblemMapper.selectList(new LambdaQueryWrapper<MaterialProblem>()
                    .eq(MaterialProblem::getMaterialId, examMaterial.getId()));
            if (!materialProblemList.isEmpty()) {
                ProblemsVo problemsVo = new ProblemsVo();
                LinkedList<ExamSelectVo> selectProblems = new LinkedList<>();
                LinkedList<ExamFillBlankVo> fillBlankProblems = new LinkedList<>();
                LinkedList<ExamJudgeVo> judgeProblems = new LinkedList<>();
                LinkedList<ExamSubjectVo> subjectProblems = new LinkedList<>();

                for (MaterialProblem materialProblem : materialProblemList) {
                    //获取问题类型
                    //1：选择题 暂时还没想好需不需要分单选多选，边写边看,个人感觉不用分
                    //2：填空题
                    //3：判断题
                    //4：问答题
                    Integer problemType = materialProblem.getProblemType();
                    if (problemType == 1) {
                        ExamSelect examSelect = examSelectService.findByIdAndMaterialQuestion(materialProblem.getProblemId(), 1);
                        if (examSelect != null) {
                            ExamSelectVo examSelectVo = new ExamSelectVo();
                            BeanUtils.copyProperties(examSelect, examSelectVo);
                            examSelectVo.setSubject(subjectMapper.selectById(examSelect.getSubjectId()).getName());
                            selectProblems.add(examSelectVo);
                            problemsVo.setSelectProblem(selectProblems);
                        }
                    } else if (problemType == 2) {
                        ExamFillBlank examFillBlank = examFillBlankMapper.findByIdAndMaterialQuestion(materialProblem.getProblemId());
                        if (examFillBlank != null) {
                            ExamFillBlankVo examFillBlankVo = new ExamFillBlankVo();
                            BeanUtils.copyProperties(examFillBlank,examFillBlankVo);
                            examFillBlankVo.setSubject(subjectMapper.selectById(examFillBlank.getSubjectId()).getName());
                            fillBlankProblems.add(examFillBlankVo);
                            problemsVo.setFillBlankProblem(fillBlankProblems);
                        }
                    } else if (problemType == 3) {
                        ExamJudge examJudge = examJudgeMapper.findByIdAndMaterialQuestion(materialProblem.getProblemId());
                        ExamJudgeVo examJudgeVo = new ExamJudgeVo();
                        if (examJudge != null) {
                            BeanUtils.copyProperties(examJudge, examJudgeVo);
                            examJudgeVo.setSubject(subjectMapper.selectById(examJudge.getSubjectId()).getName());
                            judgeProblems.add(examJudgeVo);
                            problemsVo.setJudgeProblem(judgeProblems);
                        }
                    } else if (problemType == 4) {
                        ExamSubject examSubject = examSubjectMapper.findByIdAndMaterialQuestion(materialProblem.getProblemId());
                        if (examSubject != null) {
                            ExamSubjectVo examSubjectVo = new ExamSubjectVo();
                            BeanUtils.copyProperties(examSubject,examSubjectVo);
                            examSubjectVo.setSubject(subjectMapper.selectById(examSubject.getSubjectId()).getName());
                            subjectProblems.add(examSubjectVo);
                            problemsVo.setSubjectProblem(subjectProblems);
                        }
                    }
                }
                examMaterialVo.setProblems(problemsVo);
            }
        }
        return examMaterialVo;
    }


}
