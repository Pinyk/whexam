package com.exam.demo.service.impl;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.toolkit.StringUtils;
import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import com.exam.demo.entity.*;
import com.exam.demo.mapper.*;
import com.exam.demo.otherEntity.*;
import com.exam.demo.params.*;
import com.exam.demo.results.vo.TestpaperVo;
import com.exam.demo.service.*;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.PropertyMatches;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.sql.Timestamp;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.*;

@Service
public class ExamServiceImpl implements ExamService {

    @Autowired
    private ExamMapper examMapper;

    @Autowired
    private ScoreMapper scoreMapper;

    @Autowired
    private ScoreDataMapper scoreDataMapper;

    @Autowired
    private TestPaperService testPaperService;

    @Autowired
    private ExamJudgeService examJudgeService;

    @Autowired
    private ExamSelectService examSelectService;

    @Autowired
    private ExamSubjectService examSubjectService;

    @Autowired
    private ExamFillBlankService examFillBlankService;

    @Autowired
    private DepartmentMapper departmentMapper;

    @Autowired
    private SubjectMapper subjectMapper;

    @Autowired
    private TestPaperMapper testPaperMapper;

    @Autowired
    private UserMapper userMapper;

    @Autowired
    private ExamFillBlankMapper examFillBlankMapper;

    @Autowired
    private ExamSelectMapper examSelectMapper;

    @Autowired
    private ExamJudgeMapper examJudgeMapper;

    @Autowired
    private ExamSubjectMapper examSubjectMapper;

    @Autowired
    private ExamMaterialService examMaterialService;

    @Autowired
    private ExamMaterialMapper examMaterialMapper;

    /**
     * 对选择题内容进行处理，并写入selectQuestion
     * @param examSelects
     */
    public List<Object> change(List<ExamSelect> examSelects) {
        List<Object> selectionQuestions = new ArrayList<>();
        for(Object object : examSelects) {
            ExamSelect examSelect = (ExamSelect) object;
            SelectQuestionVo selectQuestionVo = new SelectQuestionVo();
            selectQuestionVo.setContext(examSelect.getContext());
            selectQuestionVo.setSelections(Arrays.asList(examSelect.getSelection().split("；")));
            selectQuestionVo.setScore(examSelect.getScore());
            selectQuestionVo.setImgUrl(examSelect.getImgUrl());
            selectionQuestions.add(selectQuestionVo);
        }
        return selectionQuestions;
    }

    /**
     * 查找每张试卷对应的所有试题
     * @param testPaperId
     * @return
     */
    @Override
    public Map<String, List<Object>> findByTestPaperId(Integer testPaperId) {
        Map<String, List<Object>> map = new HashMap<>();
        map.put("examJudge", examMapper.findExamJudgeByTestPaperId(testPaperId));
        map.put("singleSelections", change(examMapper.findSingleSelectionByTestPaperId(testPaperId)));
        map.put("multipleSelections", change(examMapper.findMultipleSelectionByTestPaperId(testPaperId)));
        map.put("examSubject", examMapper.findExamSubjectByTestPaperId(testPaperId));
        map.put("examFillBlank", examMapper.findExamFillBlankByTestPaperId(testPaperId));

        return null;
    }

    @Override
    public Map<String, Object> submitTest(JSONObject jsonObject) {
        Double totalScore = 0.0;

        LambdaQueryWrapper<Score> lambdaQueryWrapper = Wrappers.lambdaQuery(Score.class);
        lambdaQueryWrapper
                .eq(Score::getTestpaperId,jsonObject.getInteger("testPaperId"))
                .eq(Score::getUserId,jsonObject.getInteger("userId"));
        Score score = scoreMapper.selectOne(lambdaQueryWrapper);
        //score为null说明执行插入逻辑
        if (score == null) {
            //选择题
            //单选题
            JSONArray singleSelections = jsonObject.getJSONArray("singleSelections");
            if (!singleSelections.isEmpty()) {
                for (Object singleSelection : singleSelections) {
                    ExamSelect examSelect = examSelectMapper.selectById(((JSONObject) singleSelection).getInteger("id"));
                    Scoredata scoredata = new Scoredata();
                    if (examSelect.getAnswer().equals(((JSONObject) singleSelection).getString("userAnswer"))) {
                        totalScore += examSelect.getScore();
                        scoredata.setScore(examSelect.getScore());
                    } else {
                        scoredata.setScore(0.0);
                    }
                    scoredata.setTestpaperId(jsonObject.getInteger("testPaperId"));
                    scoredata.setUserId(jsonObject.getInteger("userId"));
                    scoredata.setType(1);
                    scoredata.setProblemId(((JSONObject) singleSelection).getInteger("id"));
                    scoredata.setAnswer(((JSONObject) singleSelection).getString("userAnswer"));
                    scoreDataMapper.insert(scoredata);
                }
            }
            //多选题
            JSONArray multipleSelections = jsonObject.getJSONArray("multipleSelections");
            if (!multipleSelections.isEmpty()) {
                for (Object multipleSelection : multipleSelections) {
                    ExamSelect examSelect = examSelectMapper.selectById(((JSONObject) multipleSelection).getInteger("id"));
                    Scoredata scoredata = new Scoredata();
                    String userAnswer = ((JSONObject) multipleSelection).getString("userAnswer");
                    if (!userAnswer.isEmpty()) {
                        char[] chars = userAnswer.toCharArray();
                        Arrays.sort(chars);
                        if (new String(chars).equals(examSelect.getAnswer())) {
                            totalScore += examSelect.getScore();
                            scoredata.setScore(examSelect.getScore());
                        } else {
                            scoredata.setScore(0.0);
                        }
                        scoredata.setTestpaperId(jsonObject.getInteger("testPaperId"));
                        scoredata.setUserId(jsonObject.getInteger("userId"));
                        scoredata.setType(1);
                        scoredata.setProblemId(((JSONObject) multipleSelection).getInteger("id"));
                        scoredata.setAnswer(((JSONObject) multipleSelection).getString("userAnswer"));
                        scoreDataMapper.insert(scoredata);
                    }
                }
            }
            //填空题
            //未做多空和空串
            JSONArray examFillBlanks = jsonObject.getJSONArray("examFillBlank");
            if (!examFillBlanks.isEmpty()) {
                for (Object examFillBlank : examFillBlanks) {
                    ExamFillBlank fillBlank = examFillBlankMapper.selectById(((JSONObject) examFillBlank).getInteger("id"));
                    Scoredata scoredata = new Scoredata();
                    String userAnswer = ((JSONObject) examFillBlank).getString("userAnswer");
                    if (userAnswer.equals(fillBlank.getAnswer())) {
                        totalScore += fillBlank.getScore();
                        scoredata.setScore(fillBlank.getScore());
                    } else {
                        scoredata.setScore(0.0);
                    }
                    scoredata.setTestpaperId(jsonObject.getInteger("testPaperId"));
                    scoredata.setUserId(jsonObject.getInteger("userId"));
                    scoredata.setType(2);
                    scoredata.setProblemId(((JSONObject) examFillBlank).getInteger("id"));
                    scoredata.setAnswer(((JSONObject) examFillBlank).getString("userAnswer"));
                    scoreDataMapper.insert(scoredata);
                }
            }
            //判断题
            //前端传0 1
            JSONArray examJudges = jsonObject.getJSONArray("examJudge");
            if (!examJudges.isEmpty()) {
                for (Object examJudge : examJudges) {
                    ExamJudge judge = examJudgeMapper.selectById(((JSONObject) examJudge).getInteger("id"));
                    Scoredata scoredata = new Scoredata();
                    Integer userAnswer = ((JSONObject) examJudge).getInteger("userAnswer");
                    if (userAnswer.equals(judge.getAnswer())) {
                        totalScore += judge.getScore();
                        scoredata.setScore(judge.getScore());
                    } else {
                        scoredata.setScore(0.0);
                    }
                    scoredata.setTestpaperId(jsonObject.getInteger("testPaperId"));
                    scoredata.setUserId(jsonObject.getInteger("userId"));
                    scoredata.setType(3);
                    scoredata.setProblemId(((JSONObject) examJudge).getInteger("id"));
                    scoredata.setAnswer(((JSONObject) examJudge).getString("userAnswer"));
                    scoreDataMapper.insert(scoredata);
                }
            }
            //主观题
            JSONArray examSubjects = jsonObject.getJSONArray("examSubject");
            if (!examSubjects.isEmpty()) {
                for (Object examSubject : examSubjects) {
                    ExamSubject subject = examSubjectMapper.selectById(((JSONObject) examSubject).getInteger("id"));
                    Scoredata scoredata = new Scoredata();
                    totalScore += subject.getScore();
                    scoredata.setScore(subject.getScore());
                    scoredata.setTestpaperId(jsonObject.getInteger("testPaperId"));
                    scoredata.setUserId(jsonObject.getInteger("userId"));
                    scoredata.setType(4);
                    scoredata.setProblemId(((JSONObject) examSubject).getInteger("id"));
                    scoredata.setAnswer(((JSONObject) examSubject).getString("userAnswer"));
                    scoreDataMapper.insert(scoredata);
                }
            }
            //材料题
            JSONArray examMaterials = jsonObject.getJSONArray("examMaterial");
            if (!examMaterials.isEmpty()) {
                for (Object examMaterial : examMaterials) {
                    ExamMaterial material = examMaterialMapper.selectById(((JSONObject) examMaterial).getInteger("id"));
                    Scoredata scoredata = new Scoredata();
                    Double materialTotalScore = examMaterialService.getMaterialTotalScore(material.getId());
                    scoredata.setScore(materialTotalScore);
                    totalScore += materialTotalScore;
                    scoredata.setTestpaperId(jsonObject.getInteger("testPaperId"));
                    scoredata.setUserId(jsonObject.getInteger("userId"));
                    scoredata.setType(5);
                    scoredata.setProblemId(((JSONObject) examMaterial).getInteger("id"));
                    scoredata.setAnswer(((JSONObject) examMaterial).getString("userAnswer"));
                    scoreDataMapper.insert(scoredata);
                }
            }
            Score scoreRecord = new Score();
            scoreRecord.setTestpaperId(jsonObject.getInteger("testPaperId"));
            scoreRecord.setUserId(jsonObject.getInteger("userId"));
            scoreRecord.setScorenum(totalScore);
            scoreRecord.setStatus("已批阅");
            scoreMapper.insert(scoreRecord);
        } else { //执行更新逻辑
            //选择题
            //单选题
            JSONArray singleSelections = jsonObject.getJSONArray("singleSelections");
            if (!singleSelections.isEmpty()) {
                for (Object singleSelection : singleSelections) {
                    ExamSelect examSelect = examSelectMapper.selectById(((JSONObject) singleSelection).getInteger("id"));
                    Scoredata scoredata = new Scoredata();
                    if (examSelect.getAnswer().equals(((JSONObject) singleSelection).getString("userAnswer"))) {
                        totalScore += examSelect.getScore();
                        scoredata.setScore(examSelect.getScore());
                    } else {
                        scoredata.setScore(0.0);
                    }
                    scoredata.setAnswer(((JSONObject) singleSelection).getString("userAnswer"));
                    LambdaQueryWrapper<Scoredata> queryWrapper = new LambdaQueryWrapper<>();
                    queryWrapper.eq(Scoredata::getTestpaperId,jsonObject.getInteger("testPaperId"))
                            .eq(Scoredata::getUserId, jsonObject.getInteger("userId"))
                            .eq(Scoredata::getProblemId,((JSONObject) singleSelection).getInteger("id"));
                    scoreDataMapper.update(scoredata,queryWrapper);
                }
            }
            //多选题
            JSONArray multipleSelections = jsonObject.getJSONArray("multipleSelections");
            if (!multipleSelections.isEmpty()) {
                for (Object multipleSelection : multipleSelections) {
                    ExamSelect examSelect = examSelectMapper.selectById(((JSONObject) multipleSelection).getInteger("id"));
                    Scoredata scoredata = new Scoredata();
                    String userAnswer = ((JSONObject) multipleSelection).getString("userAnswer");
                    if (!userAnswer.isEmpty()) {
                        char[] chars = userAnswer.toCharArray();
                        Arrays.sort(chars);
                        if (new String(chars).equals(examSelect.getAnswer())) {
                            totalScore += examSelect.getScore();
                            scoredata.setScore(examSelect.getScore());
                            scoredata.setAnswer(userAnswer);
                        } else {
                            scoredata.setScore(0.0);
                            scoredata.setAnswer(userAnswer);
                        }
                        LambdaQueryWrapper<Scoredata> queryWrapper = new LambdaQueryWrapper<>();
                        queryWrapper.eq(Scoredata::getTestpaperId,jsonObject.getInteger("testPaperId"))
                                .eq(Scoredata::getUserId, jsonObject.getInteger("userId"))
                                .eq(Scoredata::getProblemId,((JSONObject) multipleSelection).getInteger("id"));
                        scoreDataMapper.update(scoredata,queryWrapper);
                    }
                }
            }
            //填空题
            //未做多空和空串
            JSONArray examFillBlanks = jsonObject.getJSONArray("examFillBlank");
            if (!examFillBlanks.isEmpty()) {
                for (Object examFillBlank : examFillBlanks) {
                    ExamFillBlank fillBlank = examFillBlankMapper.selectById(((JSONObject) examFillBlank).getInteger("id"));
                    Scoredata scoredata = new Scoredata();
                    String userAnswer = ((JSONObject) examFillBlank).getString("userAnswer");
                    if (userAnswer.equals(fillBlank.getAnswer())) {
                        totalScore += fillBlank.getScore();
                        scoredata.setScore(fillBlank.getScore());
                        scoredata.setAnswer(userAnswer);
                    } else {
                        scoredata.setScore(0.0);
                        scoredata.setAnswer(userAnswer);
                    }
                    LambdaQueryWrapper<Scoredata> queryWrapper = new LambdaQueryWrapper<>();
                    queryWrapper.eq(Scoredata::getTestpaperId,jsonObject.getInteger("testPaperId"))
                            .eq(Scoredata::getUserId, jsonObject.getInteger("userId"))
                            .eq(Scoredata::getProblemId,((JSONObject) examFillBlank).getInteger("id"));
                    scoreDataMapper.update(scoredata,queryWrapper);
                }
            }
            //判断题
            //前端传0 1
            JSONArray examJudges = jsonObject.getJSONArray("examJudge");
            if (!examJudges.isEmpty()) {
                for (Object examJudge : examJudges) {
                    ExamJudge judge = examJudgeMapper.selectById(((JSONObject) examJudge).getInteger("id"));
                    Scoredata scoredata = new Scoredata();
                    Integer userAnswer = ((JSONObject) examJudge).getInteger("userAnswer");
                    if (userAnswer.equals(judge.getAnswer())) {
                        totalScore += judge.getScore();
                        scoredata.setScore(judge.getScore());
                        scoredata.setAnswer(userAnswer.toString());
                    } else {
                        scoredata.setScore(0.0);
                        scoredata.setAnswer(userAnswer.toString());
                    }
                    LambdaQueryWrapper<Scoredata> queryWrapper = new LambdaQueryWrapper<>();
                    queryWrapper.eq(Scoredata::getTestpaperId,jsonObject.getInteger("testPaperId"))
                            .eq(Scoredata::getUserId, jsonObject.getInteger("userId"))
                            .eq(Scoredata::getProblemId,((JSONObject) examJudge).getInteger("id"));
                    scoreDataMapper.update(scoredata,queryWrapper);
                }
            }
            //主观题
            JSONArray examSubjects = jsonObject.getJSONArray("examSubject");
            if (!examSubjects.isEmpty()) {
                for (Object examSubject : examSubjects) {
                    ExamSubject subject = examSubjectMapper.selectById(((JSONObject) examSubject).getInteger("id"));
                    Scoredata scoredata = new Scoredata();
                    totalScore += subject.getScore();
                    scoredata.setScore(subject.getScore());
                    LambdaQueryWrapper<Scoredata> queryWrapper = new LambdaQueryWrapper<>();
                    queryWrapper.eq(Scoredata::getTestpaperId,jsonObject.getInteger("testPaperId"))
                            .eq(Scoredata::getUserId, jsonObject.getInteger("userId"))
                            .eq(Scoredata::getProblemId,((JSONObject) examSubject).getInteger("id"));
                    scoreDataMapper.update(scoredata,queryWrapper);
                }
            }
            //材料题
            JSONArray examMaterials = jsonObject.getJSONArray("examMaterial");
            if (!examMaterials.isEmpty()) {
                for (Object examMaterial : examMaterials) {
                    ExamMaterial material = examMaterialMapper.selectById(((JSONObject) examMaterial).getInteger("id"));
                    Scoredata scoredata = new Scoredata();
                    Double materialTotalScore = examMaterialService.getMaterialTotalScore(material.getId());
                    scoredata.setScore(materialTotalScore);
                    totalScore += materialTotalScore;
                    LambdaQueryWrapper<Scoredata> queryWrapper = new LambdaQueryWrapper<>();
                    queryWrapper.eq(Scoredata::getTestpaperId,jsonObject.getInteger("testPaperId"))
                            .eq(Scoredata::getUserId, jsonObject.getInteger("userId"))
                            .eq(Scoredata::getProblemId,((JSONObject) examMaterial).getInteger("id"));
                    scoreDataMapper.update(scoredata,queryWrapper);
                }
            }
            LambdaQueryWrapper<Score> queryWrapper = new LambdaQueryWrapper<>();
            queryWrapper.eq(Score::getId, score.getId());
            Score score1 = new Score();
            score1.setScorenum(totalScore);
            scoreMapper.update(score1,queryWrapper);
        }
        JSONObject jsonObject1 = new JSONObject(new LinkedHashMap<>());
        jsonObject1.put("totalScore", totalScore);
        return jsonObject1;
    }

    /**
     * 提交试卷，将用户作答保留到数据库，并为客观题目评分
     * @return
     */
    //@Override
//    public Integer submitTest1(Integer testPaperId, Integer userId) {
//        //该试卷是否可以重复作答
//        String isRepeat = examMapper.findExamIsRepeatByTestPaperId(testPaperId).getRepeat();
//
//        List<JudgeAnswer> examJudges = userAnswer.getExamJudge();
//        List<SelectionAnswer> examSelects1 = userAnswer.getSingleSelections();
//        List<SelectionAnswer> examSelects2 = userAnswer.getMultipleSelections();
//        List<SubjectAnswer> examSubjects = userAnswer.getExamSubject();
//        List<FillBlankAnswer> examFillBlanks = userAnswer.getExamFillBlank();
//        List<MaterialAnswer> examMaterials = userAnswer.getExamMaterial();
//        // 遍历每道题目用户的作答情况，同时对客观题目，找出标准答案（类型，题号 找到标准答案），与用户答案对比，给分，保存到数据
//        // 将用户考试记录插入数据库
//
//        double scorenum = 0.0;
//
//        if (!examJudges.isEmpty() || examJudges.size() != 0){
//            // 判断题
//            for(JudgeAnswer examJudge : examJudges) {
//                String answer = examJudgeService.findById(examJudge.getId()).getAnswer() == 0 ? "false": "true";
//                Scoredata scoreData = new Scoredata();
//                scoreData.setTestpaperId(testPaperId);
//                scoreData.setUserId(userId);
//                scoreData.setType(1);
//                scoreData.setProblemId(examJudge.getId());
//                //false存0，true存1
//                scoreData.setAnswer(examJudge.getUserAnswer());
//
//                if (examJudge.getUserAnswer().equals(answer)){
//                    double score = examJudgeService.findById(examJudge.getId()).getScore();
//                    scoreData.setScore(score);
//                    scorenum+=score;
//                }else{
//                    scoreData.setScore(0);
//                }
//                //可重复考试且已经提交过
////                int size = scoreMapper.findIsSubmitByUserIdAndTestPaperId(userId,testPaperId).size();
//                if (isRepeat.equals("true") && scoreMapper.findIsSubmitByUserIdAndTestPaperId(userId,testPaperId).size() >= 1){
//                    scoreDataMapper.updateScoreDataByUserIdAndTestPaperId(scoreData);
//                }else{
//                    scoreDataMapper.insertScoreData(scoreData);
//                }
//
//            }
//        }
//        if (!examSelects1.isEmpty() || examSelects1.size() != 0){
//            // 单选
//            for(SelectionAnswer examSelect : examSelects1) {
//
//                String answer = examSelectService.findById(examSelect.getId()).getAnswer();
//
//                Scoredata scoreData = new Scoredata();
//                scoreData.setTestpaperId(testPaperId);
//                scoreData.setUserId(userId);
//                scoreData.setType(2);
//                scoreData.setProblemId(examSelect.getId());
//                scoreData.setAnswer(examSelect.getUserAnswer());
//
//                if(examSelect.getUserAnswer().equals(answer)) {
//                    double score = examSelectService.findById(examSelect.getId()).getScore();
//                    scoreData.setScore(score);
//                    scorenum+=score;
//                } else {
//                    scoreData.setScore(0);
//                }
//                //可重复考试且已经提交过
//                if (isRepeat.equals("true") && scoreMapper.findIsSubmitByUserIdAndTestPaperId(userId,testPaperId).size() >= 1){
//                    scoreDataMapper.updateScoreDataByUserIdAndTestPaperId(scoreData);
//                }else{
//                    scoreDataMapper.insertScoreData(scoreData);
//                }
//
//            }
//        }
//
//        if (!examSelects2.isEmpty() || examSelects2.size() != 0){
//            // 多选
//            for(SelectionAnswer examSelect : examSelects2) {
//
//                String answer = examSelectService.findById(examSelect.getId()).getAnswer();
//
//                Scoredata scoreData = new Scoredata();
//                scoreData.setTestpaperId(testPaperId);
//                scoreData.setUserId(userId);
//                scoreData.setType(2);
//                scoreData.setProblemId(examSelect.getId());
//                scoreData.setAnswer(examSelect.getUserAnswer());
//
//                if(examSelect.getUserAnswer().equals(answer)) {
//                    double score = examSelectService.findById(examSelect.getId()).getScore();
//                    scoreData.setScore(score);
//                    scorenum+=score;
//                } else {
//                    scoreData.setScore(0);
//                }
//                //可重复考试且已经提交过
//                if (isRepeat.equals("true") && scoreMapper.findIsSubmitByUserIdAndTestPaperId(userId,testPaperId).size() >= 1){
//                    scoreDataMapper.updateScoreDataByUserIdAndTestPaperId(scoreData);
//                }else {
//                    scoreDataMapper.insertScoreData(scoreData);
//                }
//            }
//        }
//
//        if (!examSubjects.isEmpty() || examSubjects.size() != 0){
//            // 主观题:直接给满分
//            for(SubjectAnswer examSubject : examSubjects) {
//                Scoredata scoreData = new Scoredata();
//                scoreData.setTestpaperId(testPaperId);
//                scoreData.setUserId(userId);
//                scoreData.setType(3);
//                scoreData.setProblemId(examSubject.getId());
//                scoreData.setAnswer(examSubject.getUserAnswer());
//
//                double score = examSubjectService.findById(examSubject.getId()).getScore();
//                scoreData.setScore(score);
//                scorenum+=score;
//                //可重复考试且已经提交过
//                if (isRepeat.equals("true") && scoreMapper.findIsSubmitByUserIdAndTestPaperId(userId,testPaperId).size() >= 1){
//                    scoreDataMapper.updateScoreDataByUserIdAndTestPaperId(scoreData);
//                }else{
//                    scoreDataMapper.insertScoreData(scoreData);
//                }
//            }
//        }
//        //相似度匹配   给一个空字符串
//        if (!examFillBlanks.isEmpty() || examFillBlanks.size() != 0){
//            // 填空题
//            for(FillBlankAnswer examFillBlank : examFillBlanks) {
//                String answer = examFillBlankMapper.selectById(examFillBlank.getId()).getAnswer();
//                Scoredata scoreData = new Scoredata();
//                scoreData.setTestpaperId(testPaperId);
//                scoreData.setUserId(userId);
//                scoreData.setType(4);
//                scoreData.setProblemId(examFillBlank.getId());
//                scoreData.setAnswer(examFillBlank.getUserAnswer());
//                if(examFillBlank.getUserAnswer().equals(answer)) {
//                    double score = examFillBlankService.findById(examFillBlank.getId()).getScore();
//                    scoreData.setScore(score);
//                    scorenum+=score;
//                } else {
//                    scoreData.setScore(0);
//                }
//                //可重复考试且已经提交过
//                if (isRepeat.equals("true") && scoreMapper.findIsSubmitByUserIdAndTestPaperId(userId,testPaperId).size() >= 1){
//                    scoreDataMapper.updateScoreDataByUserIdAndTestPaperId(scoreData);
//                }else {
//                    scoreDataMapper.insertScoreData(scoreData);
//                }
//            }
//        }
//
//        if (!examMaterials.isEmpty() || examMaterials.size() != 0){
//            //材料题
//            for (MaterialAnswer examMaterial: examMaterials){
//                //材料题中的单选题题目
//                List<SelectionAnswer> materialSingleSelects = examMaterial.getQuestion().getSingleSelections();
//                //材料题中的多选题目
//                List<SelectionAnswer> materialMultipleSelects = examMaterial.getQuestion().getMultipleSelections();
//                //材料题中的填空题目
//                List<FillBlankAnswer> materialFillBlanks = examMaterial.getQuestion().getExamFillBlank();
//                //材料题中的判断题
//                List<JudgeAnswer> materialJudges = examMaterial.getQuestion().getExamJudge();
//                //材料题中的主观题
//                List<SubjectAnswer> materialSubjects = examMaterial.getQuestion().getExamSubject();
//
//                //单选题
//                if (!materialSingleSelects.isEmpty() || materialSingleSelects.size() != 0){
//                    for(SelectionAnswer examSelect : materialSingleSelects) {
//                        //正确答案
//                        String answer = examSelectService.findById(examSelect.getId()).getAnswer();
//                        Scoredata scoreData = new Scoredata();
//                        scoreData.setTestpaperId(testPaperId);
//                        scoreData.setUserId(userId);
//                        scoreData.setType(2);
//                        scoreData.setProblemId(examSelect.getId());
//                        scoreData.setAnswer(examSelect.getUserAnswer());
//                        if(examSelect.getUserAnswer().equals(answer)) {
//                            double score = examSelectService.findById(examSelect.getId()).getScore();
//                            scoreData.setScore(score);
//                            scorenum+=score;
//                        } else {
//                            scoreData.setScore(0);
//                        }
//                        //可重复考试且已经提交过
//                        if (isRepeat.equals("true") && scoreMapper.findIsSubmitByUserIdAndTestPaperId(userId,testPaperId).size() >= 1){
//                            scoreDataMapper.updateScoreDataByUserIdAndTestPaperId(scoreData);
//                        }else{
//                            scoreDataMapper.insertScoreData(scoreData);
//                        }
//                    }
//                }
//
//                //多选题目
//                if (!materialMultipleSelects.isEmpty() || materialMultipleSelects.size() != 0){
//                    for(SelectionAnswer examSelect : materialMultipleSelects) {
//                        String answer = examSelectService.findById(examSelect.getId()).getAnswer();
//
//                        Scoredata scoreData = new Scoredata();
//                        scoreData.setTestpaperId(testPaperId);
//                        scoreData.setUserId(userId);
//                        scoreData.setType(2);
//                        scoreData.setProblemId(examSelect.getId());
//                        scoreData.setAnswer(examSelect.getUserAnswer());
//                        if(examSelect.getUserAnswer().equals(answer)) {
//                            double score = examSelectService.findById(examSelect.getId()).getScore();
//                            scoreData.setScore(score);
//                            scorenum+=score;
//                        } else {
//                            scoreData.setScore(0);
//                        }
//                        //可重复考试且已经提交过
//                        if (isRepeat.equals("true") && scoreMapper.findIsSubmitByUserIdAndTestPaperId(userId,testPaperId).size() >= 1){
//                            scoreDataMapper.updateScoreDataByUserIdAndTestPaperId(scoreData);
//                        }else {
//                            scoreDataMapper.insertScoreData(scoreData);
//                        }
//                    }
//                }
//
//                //判断题
//                if (!materialJudges.isEmpty() || materialJudges.size() != 0){
//
//                    for (JudgeAnswer examJudge : materialJudges){
//                        //false存0，true存1
//                        String answer = examJudgeService.findById(examJudge.getId()).getAnswer() == 0 ? "false": "true";
//                        Scoredata scoreData = new Scoredata();
//                        scoreData.setTestpaperId(testPaperId);
//                        scoreData.setUserId(userId);
//                        scoreData.setType(1);
//                        scoreData.setProblemId(examJudge.getId());
//                        //在scoreData表中判断题答案存为false
//                        scoreData.setAnswer(examJudge.getUserAnswer());
//
//                        if (examJudge.getUserAnswer().equals(answer)){
//                            double score = examJudgeService.findById(examJudge.getId()).getScore();
//                            scoreData.setScore(score);
//                            scorenum+=score;
//                        }else{
//                            scoreData.setScore(0);
//                        }
//                        //可重复考试且已经提交过
//                        if (isRepeat.equals("true") && scoreMapper.findIsSubmitByUserIdAndTestPaperId(userId,testPaperId).size() >= 1){
//                            scoreDataMapper.updateScoreDataByUserIdAndTestPaperId(scoreData);
//                        }else {
//                            scoreDataMapper.insertScoreData(scoreData);
//                        }
//                    }
//
//                }
//
//                // 主观题：直接给满分
//                if (!materialSubjects.isEmpty() || materialSubjects.size() != 0){
//                    for(SubjectAnswer examSubject : materialSubjects) {
//                        Scoredata scoreData = new Scoredata();
//                        scoreData.setTestpaperId(testPaperId);
//                        scoreData.setUserId(userId);
//                        scoreData.setType(3);
//                        scoreData.setProblemId(examSubject.getId());
//                        scoreData.setAnswer(examSubject.getUserAnswer());
//                        double score1 = examSubjectService.findById(examSubject.getId()).getScore();
//                        scoreData.setScore(score1);
//                        scorenum+=score1;
//                        //可重复考试且已经提交过
//                        if (isRepeat.equals("true") && scoreMapper.findIsSubmitByUserIdAndTestPaperId(userId,testPaperId).size() >= 1){
//                            scoreDataMapper.updateScoreDataByUserIdAndTestPaperId(scoreData);
//                        }else {
//                            scoreDataMapper.insertScoreData(scoreData);
//                        }
//
//                    }
//                }
//
//                // 填空题
//                if (!materialFillBlanks.isEmpty() || materialFillBlanks.size() != 0){
//                    for(FillBlankAnswer examFillBlank : materialFillBlanks) {
//                        String answer = examFillBlankMapper.selectById(examFillBlank.getId()).getAnswer();
//                        Scoredata scoreData = new Scoredata();
//                        scoreData.setTestpaperId(testPaperId);
//                        scoreData.setUserId(userId);
//                        scoreData.setType(4);
//                        scoreData.setProblemId(examFillBlank.getId());
//                        scoreData.setAnswer(examFillBlank.getUserAnswer());
//                        if(examFillBlank.getUserAnswer().equals(answer)) {
//                            double score = examFillBlankService.findById(examFillBlank.getId()).getScore();
//                            scoreData.setScore(score);
//                            scorenum+=score;
//                        } else {
//                            scoreData.setScore(0);
//                        }
//                        //可重复考试且已经提交过
//                        if (isRepeat.equals("true") && scoreMapper.findIsSubmitByUserIdAndTestPaperId(userId,testPaperId).size() >= 1){
//                            scoreDataMapper.updateScoreDataByUserIdAndTestPaperId(scoreData);
//                        }else {
//                            scoreDataMapper.insertScoreData(scoreData);
//                        }
//                    }
//                }
//
//
//            }
//
//        }
//        Score score = new Score();
//        score.setTestpaperId(testPaperId);
//        score.setUserId(userId);
//        score.setScorenum(scorenum);
//        score.setStatus("已批阅");
//        //可重复考试且已经提交过
//        if (isRepeat.equals("true") && scoreMapper.findIsSubmitByUserIdAndTestPaperId(userId,testPaperId).size() >= 1){
//            scoreMapper.updateScoreByUserIdAndTestPaperId(score);
//        }else{
//            scoreMapper.insert(score);
//        }
//        return 1;
//    }

    /**
     * 添加试卷试题
     * @param exam
     * @return
     */
    @Override
    public Integer addProblem(Exam exam) {
        return examMapper.insert(exam);
    }



    /**
     * 删除试卷试题
     * @param id
     * @return
     */
    @Override
    public Integer deleteProblem(Integer id) {
        return examMapper.deleteById(id);
    }

    /**
     * 组合查询试卷
     * @Author: LBX
     * @param testPaperId
     * @param testPaperName
     * @param departmentName
     * @param subject
     * @return
     */
    @Override
    public List<TestpaperVo> combinedQueryTestPaper(Integer testPaperId, String testPaperName, String departmentName, String subject) {

        LambdaQueryWrapper<Testpaper> wrapper = new LambdaQueryWrapper<>();

        if (testPaperId != null) {
            wrapper.eq(Testpaper::getId, testPaperId);
        }
        if (!StringUtils.isBlank(testPaperName)) {
            wrapper.eq(Testpaper::getName, testPaperName);
        }
        if (!StringUtils.isBlank(departmentName)) {
            //验证该部门是否存在
            QueryWrapper<Department> depWrapper = new QueryWrapper<>();
            depWrapper.select("id").eq("name", departmentName);
            Department department = departmentMapper.selectOne(depWrapper);
            if (department != null) { //该部门存在
                wrapper.eq(Testpaper::getDepartmentId, department.getId());
            } else {
                return null; //该部门不存在，则直接返回
            }
        }
        if (!StringUtils.isBlank(subject)) {
            QueryWrapper<Subject> subjectWrapper = new QueryWrapper<>();
            subjectWrapper.select("id").eq("name", subject);
            Subject sub = subjectMapper.selectOne(subjectWrapper);
            if (sub != null) {
                wrapper.eq(Testpaper::getSubjectId, sub.getId());
            } else {
                return null;
            }
        }
        List<Testpaper> testpapers = testPaperMapper.selectList(wrapper);
        //将查询对象转为交互返回对象
        LinkedList<TestpaperVo> testpaperVos = new LinkedList<>();
        if (!testpapers.isEmpty()){
            for (Testpaper testpaper : testpapers) {
                TestpaperVo testpaperVo = new TestpaperVo();
                BeanUtils.copyProperties(testpaper, testpaperVo);

                String[] s = testpaper.getDepartmentId().split(" ");
                if (s.length != 0) {
                    LinkedList<String> list = new LinkedList<>();
                    for (String s1 : s) {
                        list.add(departmentMapper.selectById(s1).getName());
                    }
                    testpaperVo.setDepartment(list);
                }
                testpaperVo.setSubject(subject);
                testpaperVos.add(testpaperVo);
            }
        }

        return testpaperVos;
    }

    /**
     * 根据用户ID和试卷ID修改试卷总分
     * @param testPaperId
     * @param userId
     * @return
     */
    @Override
    public Integer updateScoreByUserId(Double score, Integer testPaperId, Integer userId) {
        Score sc = new Score();
        sc.setTestpaperId(testPaperId);
        sc.setUserId(userId);
        sc.setScorenum(score);
        return scoreMapper.updateScoreByUserIdAndTestPaperId(sc);
    }



    /**
     * 根据用户ID和试卷ID查询考试明细接口
     * @param testPaperId
     * @param userId
     * @return
     */
    @Override
    public List<Map<String, Object>> findScoreDetailByUIdAndTPId(Integer testPaperId, Integer userId) {
        List<Map<String,Object>> result = new ArrayList<>();
        Map<String,Object> map = new LinkedHashMap<>();
        map.put("singleSelections", change2(scoreDataMapper.findSingleSelectionAnswerByTidAndUid(testPaperId, userId)));
        map.put("multipleSelections", change2(scoreDataMapper.findMultipleSelectionAnswerByTidAndUid(testPaperId, userId)));
        map.put("examFillBlank", scoreDataMapper.findExamFillBlankAnswerByTidAndUid(testPaperId, userId));
        List<UserOtherAnswer> judgeAnswers = scoreDataMapper.findExamJudgeAnswerByTidAndUid(testPaperId, userId);
        if (!judgeAnswers.isEmpty() || judgeAnswers.size() != 0){
            for(UserOtherAnswer otherAnswer: judgeAnswers){
                if (otherAnswer.getAnswer().equals("0")){
                    otherAnswer.setAnswer("false");
                }else if (otherAnswer.getAnswer().equals("1")){
                    otherAnswer.setAnswer("true");
                }
            }
        }
        map.put("examJudge", judgeAnswers);

        map.put("examSubject", scoreDataMapper.findExamSubjectAnswerByTidAndUid(testPaperId, userId));
        List<ExamMaterial> examMaterials  = examMapper.findExamMaterialByTestPaperId(testPaperId);
        List<Map<String,Object>> list = new ArrayList<>();
        if (examMaterials.size()!=0 || !examMaterials.isEmpty()) {
            for (ExamMaterial examMaterial : examMaterials) {
                Map<String, Object> map1 = new LinkedHashMap<>();
                map1.put("id", examMaterial.getId());
                map1.put("context", examMaterial.getContext());
                Map<String, Object> map2 = new HashMap<>();
                map2.put("singleSelections", change2(scoreDataMapper.findSingleSelectionAnswerByMaterialId(testPaperId, userId, examMaterial.getId())));
                map2.put("multipleSelections", change2(scoreDataMapper.findMutipleSelectionAnswerByMaterialId(testPaperId, userId, examMaterial.getId())));
                map2.put("examFillBlank", scoreDataMapper.findExamFillBlankAnswerByMaterialId(testPaperId, userId, examMaterial.getId()));
                map2.put("examJudge", scoreDataMapper.findExamJudgeAnswerByMaterialId(testPaperId, userId, examMaterial.getId()));
                map2.put("examSubject", scoreDataMapper.findExamSubjectAnswerByMaterialId(testPaperId, userId, examMaterial.getId()));
                map1.put("question", map2);
                list.add(map1);
            }
        }
        map.put("examMaterial",list);
        result.add(map);

        return result;
    }

    @Override
    public List<Map<String, Object>> exportUserAnswerByUIdAndTPId(Integer testPaperId, Integer userId) {
        List<Map<String,Object>> data = findScoreDetailByUIdAndTPId(testPaperId,userId);

        List<Map<String,Object>> result = new ArrayList<>();
        Map<String,Object> map = new LinkedHashMap<>();

        List<ExamAndUserAnswer> examAndUserAnswers= examMapper.findUserExamAnswerByUIdAndTPId(testPaperId,userId);
        ExamAndUserAnswer examAndUserAnswer = examAndUserAnswers.get(0);
        map.put("name",examAndUserAnswer.getName());
        map.put("totalScore",examAndUserAnswer.getTotalScore());
        double passscore = examAndUserAnswer.getPassScore();
        map.put("passScore",passscore);
        map.put("time",examAndUserAnswer.getTime());
        map.put("subject",subjectMapper.selectById(examAndUserAnswer.getSubject()).getName());
        map.put("department",departmentMapper.selectById(examAndUserAnswer.getDepartment()).getName());
        map.put("startTime",examAndUserAnswer.getStartTime());
        map.put("endTime",examAndUserAnswer.getEndTime());
        map.put("repeat",examAndUserAnswer.getRepeat());
        double userscore = scoreMapper.findByTestPaperIdAndUserId(testPaperId,userId).get(0).getScorenum();

        map.put("userScore",userscore);
        String ispass = userscore >= passscore ? "true":"false";
        map.put("isPass",ispass);
        map.put("username",userMapper.selectById(userId).getName());
        map.put("userDepartment",departmentMapper.selectById(examAndUserAnswer.getDepartment()).getName());
        map.put("userPosition",userMapper.selectById(userId).getPosition());
        map.put("userNums",userMapper.selectById(userId).getNums());
        map.putAll(data.get(0));
        result.add(map);
//        result.add(data.get(0));
        return result;


    }

    /**
     * 用户导出选择题的格式
     * @param examSelects
     * @return
     */
    public List<Object> change2(List<UserSelectionAnswer> examSelects) {

        List<Object> selectionQuestions = new ArrayList<>();
        for(Object object : examSelects) {
            UserSelectionAnswer examSelect = (UserSelectionAnswer) object;
            UserSelectQuestionVo selectQuestionVo = new UserSelectQuestionVo();
            selectQuestionVo.setId(examSelect.getId());
            selectQuestionVo.setContext(examSelect.getContext());
            selectQuestionVo.setSelections(Arrays.asList(examSelect.getSelections().split(";")));
            selectQuestionVo.setAnswer(examSelect.getAnswer());

            selectQuestionVo.setScore(examSelect.getScore());
            selectQuestionVo.setUserAnswer(examSelect.getUserAnswer());
            selectQuestionVo.setUserScore(examSelect.getUserScore());
            selectQuestionVo.setImgUrl(examSelect.getImgUrl());
            selectionQuestions.add(selectQuestionVo);
        }
        return selectionQuestions;
    }
}
