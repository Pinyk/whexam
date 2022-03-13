package com.exam.demo.service.impl;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
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
import com.google.gson.JsonObject;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;

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
     * @return
     */
    @Override
    public PageVo<ExamMaterialVo> searchMaterial(Integer id, String context, Integer subjectId, Integer currentPage, Integer pageSize) {

        Page<ExamMaterial> page = new Page<>(currentPage, pageSize);
        LambdaQueryWrapper<ExamMaterial> lambdaQueryWrapper = new LambdaQueryWrapper<ExamMaterial>();
        lambdaQueryWrapper.select(ExamMaterial::getId,ExamMaterial::getContext,ExamMaterial::getSubjectId);
        if (id != null) {
            lambdaQueryWrapper.eq(ExamMaterial::getId, id);
        }
        if (StringUtils.isNotBlank(context)) {
            lambdaQueryWrapper.like(ExamMaterial::getContext, context);
        }
        if (subjectId != null) {
            lambdaQueryWrapper.eq(ExamMaterial::getSubjectId, subjectId);
        }
        Page<ExamMaterial> examMaterialPage = materialMapper.selectPage(page, lambdaQueryWrapper);
        List<ExamMaterial> examMaterials = examMaterialPage.getRecords();
        LinkedList<ExamMaterialVo> list = new LinkedList<>();
        if (!examMaterials.isEmpty()) {
            for (ExamMaterial examMaterial : examMaterials) {
                ExamMaterialVo examMaterialVo = new ExamMaterialVo();
                BeanUtils.copyProperties(examMaterial, examMaterialVo);
                examMaterialVo.setSubject(subjectMapper.selectById(examMaterial.getSubjectId()).getName());
                //查找总分
                examMaterialVo.setScore(getMaterialTotalScore(examMaterial.getId()));
                list.add(examMaterialVo);
            }
        }
        return PageVo.<ExamMaterialVo>builder()
                .values(list)
                .total(examMaterialPage.getTotal())
                .page(currentPage)
                .size(pageSize)
                .build();
    }

    /**
     * 根据ID查找材料题总分
     * @return
     */
    public Double getMaterialTotalScore(Integer id) {
        double total = 0.0;
        //在中间表中查出包含题目的Id
        LambdaQueryWrapper<MaterialProblem> lambdaQueryWrapper = new LambdaQueryWrapper<>();
        lambdaQueryWrapper.select(MaterialProblem::getProblemId, MaterialProblem::getProblemType)
                .eq(MaterialProblem::getMaterialId, id);
        List<MaterialProblem> materialProblems = materialProblemMapper.selectList(lambdaQueryWrapper);
        if (!materialProblems.isEmpty()) {
            for (MaterialProblem materialProblem : materialProblems) {
                Integer problemId = materialProblem.getProblemId();
                Integer problemType = materialProblem.getProblemType();
                if (problemType == 1) {
                    LambdaQueryWrapper<ExamSelect> queryWrapper = new LambdaQueryWrapper<>();
                    queryWrapper.select(ExamSelect::getScore).eq(ExamSelect::getId, problemId);
                    ExamSelect examSelect = examSelectMapper.selectOne(queryWrapper);
                    total += examSelect.getScore();
                } else if (problemType == 2) {
                    LambdaQueryWrapper<ExamFillBlank> queryWrapper = new LambdaQueryWrapper<>();
                    queryWrapper.select(ExamFillBlank::getScore).eq(ExamFillBlank::getId, problemId);
                    ExamFillBlank examFillBlank = examFillBlankMapper.selectOne(queryWrapper);
                    total += examFillBlank.getScore();
                } else if (problemType == 3) {
                    LambdaQueryWrapper<ExamJudge> queryWrapper = new LambdaQueryWrapper<>();
                    queryWrapper.select(ExamJudge::getScore).eq(ExamJudge::getId, problemId);
                    ExamJudge examJudge = examJudgeMapper.selectOne(queryWrapper);
                    total += examJudge.getScore();
                } else {
                    LambdaQueryWrapper<ExamSubject> queryWrapper = new LambdaQueryWrapper<>();
                    queryWrapper.select(ExamSubject::getScore).eq(ExamSubject::getId, problemId);
                    ExamSubject examSubject = examSubjectMapper.selectOne(queryWrapper);
                    total += examSubject.getScore();
                }
            }
        }
        return total;
    }

    @Override
    public Map<String, Object> deleteExamMaterial(Integer id) {

        //根据中间表删除各个类型的题
        LambdaQueryWrapper<MaterialProblem> lambdaQueryWrapper = new LambdaQueryWrapper<>();
        lambdaQueryWrapper.eq(MaterialProblem::getMaterialId, id);
        List<MaterialProblem> materialProblems = materialProblemMapper.selectList(lambdaQueryWrapper);
        if (!materialProblems.isEmpty()) {
            for (MaterialProblem materialProblem : materialProblems) {
                Integer problemType = materialProblem.getProblemType();
                if (problemType == 1) {
                    examSelectMapper.deleteById(materialProblem.getProblemId());
                } else if (problemType == 2) {
                    examFillBlankMapper.deleteById(materialProblem.getProblemId());
                } else if (problemType == 3) {
                    examJudgeMapper.deleteById(materialProblem.getProblemId());
                } else {
                    examSubjectMapper.deleteById(materialProblem.getProblemId());
                }
            }
        }
        //删除中间表的记录
        LambdaQueryWrapper<MaterialProblem> queryWrapper = new LambdaQueryWrapper<>();
        queryWrapper.eq(MaterialProblem::getMaterialId, id);
        materialProblemMapper.delete(queryWrapper);

        JSONObject jsonObject = new JSONObject();
        jsonObject.put("deletedRecordTotal", materialMapper.deleteById(id));

        return jsonObject;
    }

    @Override
    public Map<String, Object> previewById(Integer id) {
        JSONObject jsonObject = new JSONObject();
        jsonObject.put("subjectId", id);
        LambdaQueryWrapper<MaterialProblem> lambdaQueryWrapper = new LambdaQueryWrapper<>();
        lambdaQueryWrapper.select(MaterialProblem::getProblemId, MaterialProblem::getProblemType);
        if (id != null) {
            lambdaQueryWrapper.eq(MaterialProblem::getMaterialId, id);
        }
        List<MaterialProblem> materialProblems = materialProblemMapper.selectList(lambdaQueryWrapper);
        LinkedList<JSONObject> examSingleSelects = new LinkedList<>();
        LinkedList<JSONObject> examMultipleSelects = new LinkedList<>();
        LinkedList<JSONObject> examFillBlanks = new LinkedList<>();
        LinkedList<JSONObject> examJudges = new LinkedList<>();
        LinkedList<JSONObject> examSubjects = new LinkedList<>();
        if (!materialProblems.isEmpty()) {
            for (MaterialProblem materialProblem : materialProblems) {
                Integer problemId = materialProblem.getProblemId();
                Integer problemType = materialProblem.getProblemType();

                if (problemType == 1) {
                    LambdaQueryWrapper<ExamSelect> queryWrapper = new LambdaQueryWrapper<>();
                    queryWrapper.select(ExamSelect::getContext,ExamSelect::getSelection,ExamSelect::getScore,
                            ExamSelect::getImgUrl,ExamSelect::getType).eq(ExamSelect::getId,problemId);
                    ExamSelect examSelect = examSelectMapper.selectOne(queryWrapper);
                    JSONObject jsonObject1 = new JSONObject();
                    jsonObject1.put("id", problemId);
                    jsonObject1.put("context", examSelect.getContext());
                    jsonObject1.put("selectionA", examSelect.getSelection().split(";")[0]);
                    jsonObject1.put("selectionB", examSelect.getSelection().split(";")[1]);
                    jsonObject1.put("selectionC", examSelect.getSelection().split(";")[2]);
                    jsonObject1.put("selectionD", examSelect.getSelection().split(";")[3]);
                    jsonObject1.put("score", examSelect.getScore());
                    if (StringUtils.isNotBlank(examSelect.getImgUrl())) {
                        jsonObject1.put("imgUrl", examSelect.getImgUrl());
                    }
                    if (examSelect.getType() == 1) {
                        examSingleSelects.add(jsonObject1);
                    } else {
                        examMultipleSelects.add(jsonObject1);
                    }
                } else if (problemType == 2) {
                    LambdaQueryWrapper<ExamFillBlank> queryWrapper = new LambdaQueryWrapper<>();
                    queryWrapper.select(ExamFillBlank::getContext,ExamFillBlank::getScore,
                            ExamFillBlank::getImgUrl).eq(ExamFillBlank::getId,problemId);
                    ExamFillBlank examFillBlank = examFillBlankMapper.selectOne(queryWrapper);
                    JSONObject jsonObject1 = new JSONObject();
                    jsonObject1.put("id", problemId);
                    jsonObject1.put("context", examFillBlank.getContext());
                    jsonObject1.put("score", examFillBlank.getScore());
                    if (StringUtils.isNotBlank(examFillBlank.getImgUrl())) {
                        jsonObject1.put("imgUrl", examFillBlank.getImgUrl());
                    }
                    examFillBlanks.add(jsonObject1);
                } else if (problemType == 3) {
                    LambdaQueryWrapper<ExamJudge> queryWrapper = new LambdaQueryWrapper<>();
                    queryWrapper.select(ExamJudge::getContext, ExamJudge::getScore, ExamJudge::getImgUrl)
                            .eq(ExamJudge::getId,problemId);
                    ExamJudge examJudge = examJudgeMapper.selectOne(queryWrapper);
                    JSONObject jsonObject1 = new JSONObject();
                    jsonObject1.put("id", problemId);
                    jsonObject1.put("context", examJudge.getContext());
                    jsonObject1.put("score", examJudge.getScore());
                    if (StringUtils.isNotBlank(examJudge.getImgUrl())) {
                        jsonObject1.put("imgUrl", examJudge.getImgUrl());
                    }
                    examJudges.add(jsonObject1);
                } else {
                    LambdaQueryWrapper<ExamSubject> queryWrapper = new LambdaQueryWrapper<>();
                    queryWrapper.select(ExamSubject::getContext,ExamSubject::getScore,ExamSubject::getImgUrl)
                            .eq(ExamSubject::getId, problemId);
                    ExamSubject examSubject = examSubjectMapper.selectOne(queryWrapper);
                    JSONObject jsonObject1 = new JSONObject();
                    jsonObject1.put("id", problemId);
                    jsonObject1.put("context", examSubject.getContext());
                    jsonObject1.put("score", examSubject.getScore());
                    if (StringUtils.isNotBlank(examSubject.getImgUrl())) {
                        jsonObject1.put("imgUrl", examSubject.getImgUrl());
                    }
                    examSubjects.add(jsonObject1);
                }
            }
        }
        jsonObject.put("examSingleSelects", examSingleSelects);
        jsonObject.put("examMultipleSelects", examMultipleSelects);
        jsonObject.put("examFillBlanks", examFillBlanks);
        jsonObject.put("examJudges", examJudges);
        jsonObject.put("examSubjects", examSubjects);

        return jsonObject;
    }

    @Override
    public Map<String, Object> saveExamMaterial(JSONObject jsonObject) {
        //exam_material表
        ExamMaterial examMaterial = new ExamMaterial();
        //material_problem
        MaterialProblem materialProblem = new MaterialProblem();

        if (jsonObject.getInteger("subjectId") != null) {
            examMaterial.setSubjectId(jsonObject.getInteger("subjectId"));
        }

        if (jsonObject.getString("context") != null) {
            examMaterial.setContext(jsonObject.getString("context"));
        }
        materialMapper.insert(examMaterial);

        saveMaterialProblem("singleSelections", 1, jsonObject, materialProblem, examMaterial);
        saveMaterialProblem("multipleSelections", 1, jsonObject, materialProblem, examMaterial);
        saveMaterialProblem("examFillBlanks", 2, jsonObject, materialProblem, examMaterial);
        saveMaterialProblem("examJudges", 3, jsonObject, materialProblem, examMaterial);
        saveMaterialProblem("examSubjects", 4, jsonObject, materialProblem, examMaterial);

        JSONObject object = new JSONObject();
        object.put("newRecordId", examMaterial.getId());
        return object;
    }



    /**
     * 保存material_problem表数据
     */
    private void saveMaterialProblem(String key, Integer problemType, JSONObject jsonObject, MaterialProblem materialProblem,
                                     ExamMaterial examMaterial) {
        if (jsonObject.getJSONArray(key) != null && !jsonObject.getJSONArray(key).isEmpty()) {
            JSONArray jsonArray = jsonObject.getJSONArray(key);
            for (Object o : jsonArray) {
                materialProblem.setMaterialId(examMaterial.getId());
                materialProblem.setProblemId((Integer) o);
                materialProblem.setProblemType(problemType);
                materialProblemMapper.insert(materialProblem);
            }
        }
    }

}
