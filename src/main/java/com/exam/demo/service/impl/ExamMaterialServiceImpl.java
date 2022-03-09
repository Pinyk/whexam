package com.exam.demo.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.toolkit.StringUtils;
import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.exam.demo.Utils.FileCommit;
import com.exam.demo.entity.*;
import com.exam.demo.mapper.*;
import com.exam.demo.otherEntity.SelectQuestionVo;
import com.exam.demo.params.submit.MaterialSubmitParam;
import com.exam.demo.params.submit.SelectSubmitParam;
import com.exam.demo.params.submit.materialqueation.MaterialFillBlank;
import com.exam.demo.params.submit.materialqueation.MaterialJudge;
import com.exam.demo.params.submit.materialqueation.MaterialSelection;
import com.exam.demo.params.submit.materialqueation.MaterialSubject;
import com.exam.demo.results.vo.*;
import com.exam.demo.service.*;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.io.IOException;
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
    @Autowired
    FileCommit fileCommit;
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
                            selectProblems.add(examSelectVo);
                            problemsVo.setSelectProblem(selectProblems);
                        }
                    } else if (problemType == 2) {
                        ExamFillBlank examFillBlank = examFillBlankMapper.findByIdAndMaterialQuestion(materialProblem.getProblemId());
                        if (examFillBlank != null) {
                            ExamFillBlankVo examFillBlankVo = new ExamFillBlankVo();
                            BeanUtils.copyProperties(examFillBlank,examFillBlankVo);
                            fillBlankProblems.add(examFillBlankVo);
                            problemsVo.setFillBlankProblem(fillBlankProblems);
                        }
                    } else if (problemType == 3) {
                        ExamJudge examJudge = examJudgeMapper.findByIdAndMaterialQuestion(materialProblem.getProblemId());
                        ExamJudgeVo examJudgeVo = new ExamJudgeVo();
                        if (examJudge != null) {
                            BeanUtils.copyProperties(examJudge, examJudgeVo);
                            judgeProblems.add(examJudgeVo);
                            problemsVo.setJudgeProblem(judgeProblems);
                        }
                    } else if (problemType == 4) {
                        ExamSubject examSubject = examSubjectMapper.findByIdAndMaterialQuestion(materialProblem.getProblemId());
                        if (examSubject != null) {
                            ExamSubjectVo examSubjectVo = new ExamSubjectVo();
                            BeanUtils.copyProperties(examSubject,examSubjectVo);
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


    @Override
    public Integer saveExamMaterial(MaterialSubmitParam materialSubmitParam) {
        //exam_material表
        ExamMaterial examMaterial = new ExamMaterial();
        //material_problem
        MaterialProblem materialProblem = new MaterialProblem();

        //插入material表
        if (StringUtils.isNotBlank(materialSubmitParam.getContext())){
            examMaterial.setContext(materialSubmitParam.getContext());
        }
        if (materialSubmitParam.getSubjectId() != null) {
            examMaterial.setSubjectId(materialSubmitParam.getSubjectId());
        }
        materialMapper.insert(examMaterial);

        //将各个题型插入各个表中
        if (!materialSubmitParam.getSingleSelections().isEmpty()) {
            for (MaterialSelection singleSelection : materialSubmitParam.getSingleSelections()) {
                ExamSelect examSelect = saveSelection(singleSelection, 1);
                saveMaterialProblem(materialProblem, examSelect, examMaterial.getId(),
                        examSelect.getId(), 1);
            }
        }
        if (!materialSubmitParam.getMultipleSelections().isEmpty()) {
            for (MaterialSelection multipleSelection : materialSubmitParam.getMultipleSelections()) {
                ExamSelect examSelect = saveSelection(multipleSelection, 2);
                saveMaterialProblem(materialProblem, examSelect, examMaterial.getId(),
                        examSelect.getId(), 1);
            }
        }
        if (!materialSubmitParam.getExamFillBlanks().isEmpty()) {
            for (MaterialFillBlank materialFillBlank : materialSubmitParam.getExamFillBlanks()) {
                ExamFillBlank examFillBlank = new ExamFillBlank();

                if (StringUtils.isNotBlank(materialFillBlank.getContext())) {
                    examFillBlank.setContext(materialFillBlank.getContext());
                }
                if (materialFillBlank.getAnswer() != null) {
                    examFillBlank.setAnswer(materialFillBlank.getAnswer());
                }
                if (materialFillBlank.getScore() != null) {
                    examFillBlank.setScore(materialFillBlank.getScore());
                }
                if (materialFillBlank.getImg() != null) {
                    try {
                        fileCommit.fileCommit(materialFillBlank.getImg());
                        String downLoadUrl = fileCommit.downLoad(materialFillBlank.getImg());
                        String url = downLoadUrl.split("\\?sign=")[0];
                        examFillBlank.setImgUrl(url);
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                }
                examFillBlank.setMaterialQuestion(1);
                examFillBlankMapper.insert(examFillBlank);
                saveMaterialProblem(materialProblem, examFillBlank, examMaterial.getId(),
                        examFillBlank.getId(), 2);
            }
        }
        if (!materialSubmitParam.getExamJudges().isEmpty()) {

            for (MaterialJudge materialJudge : materialSubmitParam.getExamJudges()) {
                ExamJudge examJudge = new ExamJudge();

                if (StringUtils.isNotBlank(materialJudge.getContext())) {
                    examJudge.setContext(materialJudge.getContext());
                }
                if (materialJudge.getAnswer() != null) {
                    examJudge.setAnswer(materialJudge.getAnswer());
                }
                if (materialJudge.getScore() != null) {
                    examJudge.setScore(materialJudge.getScore());
                }
                if (materialJudge.getImg() != null) {
                    //调用COS服务
                    try {
                        fileCommit.fileCommit(materialJudge.getImg());
                        //写入图片url
                        String downLoadUrl = fileCommit.downLoad(materialJudge.getImg());
                        String url = downLoadUrl.split("\\?sign=")[0];
                        examJudge.setImgUrl(url);
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                }
                examJudge.setDifficulty(1);
                examJudge.setMaterialQuestion(1);
                examJudgeMapper.insert(examJudge);
                saveMaterialProblem(materialProblem, examJudge, examMaterial.getId(),
                        examJudge.getId(), 3);
            }

        }
        if (!materialSubmitParam.getExamSubjects().isEmpty()) {
            for (MaterialSubject materialSubject : materialSubmitParam.getExamSubjects()) {
                ExamSubject examSubject = new ExamSubject();

                if (StringUtils.isNotBlank(materialSubject.getContext())) {
                    examSubject.setContext(materialSubject.getContext());
                }
                if (materialSubject.getAnswer() != null) {
                    examSubject.setAnswer(materialSubject.getAnswer());
                }
                if (materialSubject.getScore() != null) {
                    examSubject.setScore(materialSubject.getScore());
                }
                if (materialSubject.getImg() != null) {
                    try {
                        fileCommit.fileCommit(materialSubject.getImg());
                        String downLoadUrl = fileCommit.downLoad(materialSubject.getImg());
                        String url = downLoadUrl.split("\\?sign=")[0];
                        examSubject.setImgUrl(url);
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                }
                examSubject.setMaterialQuestion(1);
                examSubject.setDifficulty(1);
                examSubjectMapper.insert(examSubject);
                saveMaterialProblem(materialProblem, examSubject, examMaterial.getId(),
                        examSubject.getId(), 4);
            }
        }

        return examMaterial.getId();
    }

    //保存选择题
    private ExamSelect saveSelection(MaterialSelection selectSubmitParam, Integer type) {
        //实例化选择题对象
        ExamSelect examSelect = new ExamSelect();
        //添加题目内容
        if (selectSubmitParam.getContext() != null) {
            examSelect.setContext(selectSubmitParam.getContext());
        }
        //添加题目选项
        if(selectSubmitParam.getSelectionA() != null && selectSubmitParam.getSelectionB() != null
                && selectSubmitParam.getSelectionC() != null && selectSubmitParam.getSelectionD() != null) {
            StringBuffer stringBuffer = new StringBuffer();
            stringBuffer
                    .append(selectSubmitParam.getSelectionA())
                    .append(";")
                    .append(selectSubmitParam.getSelectionB())
                    .append(";")
                    .append(selectSubmitParam.getSelectionC())
                    .append(";")
                    .append(selectSubmitParam.getSelectionD());
            examSelect.setSelection(stringBuffer.toString());
        }

        //添加题目答案
        examSelect.setAnswer(selectSubmitParam.getAnswer());

        //添加题目难度
        examSelect.setDifficulty(1);
        //添加分数
        examSelect.setScore(selectSubmitParam.getScore());
        //添加选择题type
        examSelect.setType(type);
        //添加materialQuestion
        examSelect.setMaterialQuestion(1);
        //添加图片
        if (selectSubmitParam.getImg() != null) {
            //调用COS存储图片
            try {
                fileCommit.fileCommit(selectSubmitParam.getImg());
                //将图片对应的url存入数据库
                String downLoadUrl = fileCommit.downLoad(selectSubmitParam.getImg());
                String url = downLoadUrl.split("\\?sign=")[0];
                examSelect.setImgUrl(url);
            } catch (IOException e) {
                e.printStackTrace();
            }

        }
        examSelectMapper.insert(examSelect);
        return examSelect;
    }

    /**
     * 保存material_problem表数据
     * @param materialId
     * @param problemId
     * @param problemType
     */
    private void saveMaterialProblem(MaterialProblem materialProblem, ExamObject examObject, Integer materialId,
                                        Integer problemId, Integer problemType) {
        materialProblem.setMaterialId(materialId);
        materialProblem.setProblemId(problemId);
        materialProblem.setProblemType(problemType);
        materialProblemMapper.insert(materialProblem);
    }
}
