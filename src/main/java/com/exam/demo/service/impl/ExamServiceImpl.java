package com.exam.demo.service.impl;

import com.alibaba.fastjson.JSONObject;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.toolkit.StringUtils;
import com.exam.demo.entity.*;
import com.exam.demo.mapper.*;
import com.exam.demo.otherEntity.SelectQuestionVo;
import com.exam.demo.otherEntity.UserAnswer;
import com.exam.demo.params.*;
import com.exam.demo.results.vo.TestpaperVo;
import com.exam.demo.service.*;
import org.springframework.beans.BeanUtils;
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

    /**
     * 提交试卷，将用户作答保留到数据库，并为客观题目评分
     * @return
     */
    @Override
    public Integer submitTest(Integer testPaperId, Integer userId, UserAnswer userAnswer) {
        //该试卷是否可以重复作答
        String isRepeat = examMapper.findExamIsRepeatByTestPaperId(testPaperId).getRepeat();

        List<JudgeAnswer> examJudges = userAnswer.getExamJudge();
        List<SelectionAnswer> examSelects1 = userAnswer.getSingleSelections();
        List<SelectionAnswer> examSelects2 = userAnswer.getMultipleSelections();
        List<SubjectAnswer> examSubjects = userAnswer.getExamSubject();
        List<FillBlankAnswer> examFillBlanks = userAnswer.getExamFillBlank();
        List<MaterialAnswer> examMaterials = userAnswer.getExamMaterial();
        // 遍历每道题目用户的作答情况，同时对客观题目，找出标准答案（类型，题号 找到标准答案），与用户答案对比，给分，保存到数据
        // 将用户考试记录插入数据库

        double scorenum = 0.0;

        if (!examJudges.isEmpty() || examJudges.size() != 0){
            // 判断题
            for(JudgeAnswer examJudge : examJudges) {
                String answer = examJudgeService.findById(examJudge.getId()).getAnswer() == 0 ? "false": "true";
                Scoredata scoreData = new Scoredata();
                scoreData.setTestpaperId(testPaperId);
                scoreData.setUserId(userId);
                scoreData.setType(1);
                scoreData.setProblemId(examJudge.getId());
                //false存0，true存1
                scoreData.setAnswer(examJudge.getUserAnswer());

                if (examJudge.getUserAnswer().equals(answer)){
                    double score = examJudgeService.findById(examJudge.getId()).getScore();
                    scoreData.setScore(score);
                    scorenum+=score;
                }else{
                    scoreData.setScore(0);
                }
                //可重复考试且已经提交过
//                int size = scoreMapper.findIsSubmitByUserIdAndTestPaperId(userId,testPaperId).size();
                if (isRepeat.equals("true") && scoreMapper.findIsSubmitByUserIdAndTestPaperId(userId,testPaperId).size() >= 1){
                    scoreDataMapper.updateScoreDataByUserIdAndTestPaperId(scoreData);
                }else{
                    scoreDataMapper.insertScoreData(scoreData);
                }

            }
        }
        if (!examSelects1.isEmpty() || examSelects1.size() != 0){
            // 单选
            for(SelectionAnswer examSelect : examSelects1) {

                String answer = examSelectService.findById(examSelect.getId()).getAnswer();

                Scoredata scoreData = new Scoredata();
                scoreData.setTestpaperId(testPaperId);
                scoreData.setUserId(userId);
                scoreData.setType(2);
                scoreData.setProblemId(examSelect.getId());
                scoreData.setAnswer(examSelect.getUserAnswer());

                if(examSelect.getUserAnswer().equals(answer)) {
                    double score = examSelectService.findById(examSelect.getId()).getScore();
                    scoreData.setScore(score);
                    scorenum+=score;
                } else {
                    scoreData.setScore(0);
                }
                //可重复考试且已经提交过
                if (isRepeat.equals("true") && scoreMapper.findIsSubmitByUserIdAndTestPaperId(userId,testPaperId).size() >= 1){
                    scoreDataMapper.updateScoreDataByUserIdAndTestPaperId(scoreData);
                }else{
                    scoreDataMapper.insertScoreData(scoreData);
                }

            }
        }

        if (!examSelects2.isEmpty() || examSelects2.size() != 0){
            // 多选
            for(SelectionAnswer examSelect : examSelects2) {

                String answer = examSelectService.findById(examSelect.getId()).getAnswer();

                Scoredata scoreData = new Scoredata();
                scoreData.setTestpaperId(testPaperId);
                scoreData.setUserId(userId);
                scoreData.setType(2);
                scoreData.setProblemId(examSelect.getId());
                scoreData.setAnswer(examSelect.getUserAnswer());

                if(examSelect.getUserAnswer().equals(answer)) {
                    double score = examSelectService.findById(examSelect.getId()).getScore();
                    scoreData.setScore(score);
                    scorenum+=score;
                } else {
                    scoreData.setScore(0);
                }
                //可重复考试且已经提交过
                if (isRepeat.equals("true") && scoreMapper.findIsSubmitByUserIdAndTestPaperId(userId,testPaperId).size() >= 1){
                    scoreDataMapper.updateScoreDataByUserIdAndTestPaperId(scoreData);
                }else {
                    scoreDataMapper.insertScoreData(scoreData);
                }
            }
        }

        if (!examSubjects.isEmpty() || examSubjects.size() != 0){
            // 主观题:直接给满分
            for(SubjectAnswer examSubject : examSubjects) {
                Scoredata scoreData = new Scoredata();
                scoreData.setTestpaperId(testPaperId);
                scoreData.setUserId(userId);
                scoreData.setType(3);
                scoreData.setProblemId(examSubject.getId());
                scoreData.setAnswer(examSubject.getUserAnswer());

                double score = examSubjectService.findById(examSubject.getId()).getScore();
                scoreData.setScore(score);
                scorenum+=score;
                //可重复考试且已经提交过
                if (isRepeat.equals("true") && scoreMapper.findIsSubmitByUserIdAndTestPaperId(userId,testPaperId).size() >= 1){
                    scoreDataMapper.updateScoreDataByUserIdAndTestPaperId(scoreData);
                }else{
                    scoreDataMapper.insertScoreData(scoreData);
                }
            }
        }

        if (!examFillBlanks.isEmpty() || examFillBlanks.size() != 0){
            // 填空题
            for(FillBlankAnswer examFillBlank : examFillBlanks) {
                String answer = examFillBlankMapper.selectById(examFillBlank.getId()).getAnswer();
                Scoredata scoreData = new Scoredata();
                scoreData.setTestpaperId(testPaperId);
                scoreData.setUserId(userId);
                scoreData.setType(4);
                scoreData.setProblemId(examFillBlank.getId());
                scoreData.setAnswer(examFillBlank.getUserAnswer());
                if(examFillBlank.getUserAnswer().equals(answer)) {
                    double score = examFillBlankService.findById(examFillBlank.getId()).getScore();
                    scoreData.setScore(score);
                    scorenum+=score;
                } else {
                    scoreData.setScore(0);
                }
                //可重复考试且已经提交过
                if (isRepeat.equals("true") && scoreMapper.findIsSubmitByUserIdAndTestPaperId(userId,testPaperId).size() >= 1){
                    scoreDataMapper.updateScoreDataByUserIdAndTestPaperId(scoreData);
                }else {
                    scoreDataMapper.insertScoreData(scoreData);
                }
            }
        }

        if (!examMaterials.isEmpty() || examMaterials.size() != 0){
            //材料题
            for (MaterialAnswer examMaterial: examMaterials){
                //材料题中的单选题题目
                List<SelectionAnswer> materialSingleSelects = examMaterial.getQuestion().getSingleSelections();
                //材料题中的多选题目
                List<SelectionAnswer> materialMultipleSelects = examMaterial.getQuestion().getMultipleSelections();
                //材料题中的填空题目
                List<FillBlankAnswer> materialFillBlanks = examMaterial.getQuestion().getExamFillBlank();
                //材料题中的判断题
                List<JudgeAnswer> materialJudges = examMaterial.getQuestion().getExamJudge();
                //材料题中的主观题
                List<SubjectAnswer> materialSubjects = examMaterial.getQuestion().getExamSubject();

                //单选题
                if (!materialSingleSelects.isEmpty() || materialSingleSelects.size() != 0){
                    for(SelectionAnswer examSelect : materialSingleSelects) {
                        //正确答案
                        String answer = examSelectService.findById(examSelect.getId()).getAnswer();
                        Scoredata scoreData = new Scoredata();
                        scoreData.setTestpaperId(testPaperId);
                        scoreData.setUserId(userId);
                        scoreData.setType(2);
                        scoreData.setProblemId(examSelect.getId());
                        scoreData.setAnswer(examSelect.getUserAnswer());
                        if(examSelect.getUserAnswer().equals(answer)) {
                            double score = examSelectService.findById(examSelect.getId()).getScore();
                            scoreData.setScore(score);
                            scorenum+=score;
                        } else {
                            scoreData.setScore(0);
                        }
                        //可重复考试且已经提交过
                        if (isRepeat.equals("true") && scoreMapper.findIsSubmitByUserIdAndTestPaperId(userId,testPaperId).size() >= 1){
                            scoreDataMapper.updateScoreDataByUserIdAndTestPaperId(scoreData);
                        }else{
                            scoreDataMapper.insertScoreData(scoreData);
                        }
                    }
                }

                //多选题目
                if (!materialMultipleSelects.isEmpty() || materialMultipleSelects.size() != 0){
                    for(SelectionAnswer examSelect : materialMultipleSelects) {
                        String answer = examSelectService.findById(examSelect.getId()).getAnswer();

                        Scoredata scoreData = new Scoredata();
                        scoreData.setTestpaperId(testPaperId);
                        scoreData.setUserId(userId);
                        scoreData.setType(2);
                        scoreData.setProblemId(examSelect.getId());
                        scoreData.setAnswer(examSelect.getUserAnswer());
                        if(examSelect.getUserAnswer().equals(answer)) {
                            double score = examSelectService.findById(examSelect.getId()).getScore();
                            scoreData.setScore(score);
                            scorenum+=score;
                        } else {
                            scoreData.setScore(0);
                        }
                        //可重复考试且已经提交过
                        if (isRepeat.equals("true") && scoreMapper.findIsSubmitByUserIdAndTestPaperId(userId,testPaperId).size() >= 1){
                            scoreDataMapper.updateScoreDataByUserIdAndTestPaperId(scoreData);
                        }else {
                            scoreDataMapper.insertScoreData(scoreData);
                        }
                    }
                }

                //判断题
                if (!materialJudges.isEmpty() || materialJudges.size() != 0){

                    for (JudgeAnswer examJudge : materialJudges){
                        //false存0，true存1
                        String answer = examJudgeService.findById(examJudge.getId()).getAnswer() == 0 ? "false": "true";
                        Scoredata scoreData = new Scoredata();
                        scoreData.setTestpaperId(testPaperId);
                        scoreData.setUserId(userId);
                        scoreData.setType(1);
                        scoreData.setProblemId(examJudge.getId());
                        //在scoreData表中判断题答案存为false
                        scoreData.setAnswer(examJudge.getUserAnswer());

                        if (examJudge.getUserAnswer().equals(answer)){
                            double score = examJudgeService.findById(examJudge.getId()).getScore();
                            scoreData.setScore(score);
                            scorenum+=score;
                        }else{
                            scoreData.setScore(0);
                        }
                        //可重复考试且已经提交过
                        if (isRepeat.equals("true") && scoreMapper.findIsSubmitByUserIdAndTestPaperId(userId,testPaperId).size() >= 1){
                            scoreDataMapper.updateScoreDataByUserIdAndTestPaperId(scoreData);
                        }else {
                            scoreDataMapper.insertScoreData(scoreData);
                        }
                    }

                }

                // 主观题：直接给满分
                if (!materialSubjects.isEmpty() || materialSubjects.size() != 0){
                    for(SubjectAnswer examSubject : materialSubjects) {
                        Scoredata scoreData = new Scoredata();
                        scoreData.setTestpaperId(testPaperId);
                        scoreData.setUserId(userId);
                        scoreData.setType(3);
                        scoreData.setProblemId(examSubject.getId());
                        scoreData.setAnswer(examSubject.getUserAnswer());
                        double score1 = examSubjectService.findById(examSubject.getId()).getScore();
                        scoreData.setScore(score1);
                        scorenum+=score1;
                        //可重复考试且已经提交过
                        if (isRepeat.equals("true") && scoreMapper.findIsSubmitByUserIdAndTestPaperId(userId,testPaperId).size() >= 1){
                            scoreDataMapper.updateScoreDataByUserIdAndTestPaperId(scoreData);
                        }else {
                            scoreDataMapper.insertScoreData(scoreData);
                        }

                    }
                }

                // 填空题
                if (!materialFillBlanks.isEmpty() || materialFillBlanks.size() != 0){
                    for(FillBlankAnswer examFillBlank : materialFillBlanks) {
                        String answer = examFillBlankMapper.selectById(examFillBlank.getId()).getAnswer();
                        Scoredata scoreData = new Scoredata();
                        scoreData.setTestpaperId(testPaperId);
                        scoreData.setUserId(userId);
                        scoreData.setType(4);
                        scoreData.setProblemId(examFillBlank.getId());
                        scoreData.setAnswer(examFillBlank.getUserAnswer());
                        if(examFillBlank.getUserAnswer().equals(answer)) {
                            double score = examFillBlankService.findById(examFillBlank.getId()).getScore();
                            scoreData.setScore(score);
                            scorenum+=score;
                        } else {
                            scoreData.setScore(0);
                        }
                        //可重复考试且已经提交过
                        if (isRepeat.equals("true") && scoreMapper.findIsSubmitByUserIdAndTestPaperId(userId,testPaperId).size() >= 1){
                            scoreDataMapper.updateScoreDataByUserIdAndTestPaperId(scoreData);
                        }else {
                            scoreDataMapper.insertScoreData(scoreData);
                        }
                    }
                }


            }

        }
        Score score = new Score();
        score.setTestpaperId(testPaperId);
        score.setUserId(userId);
        score.setScorenum(scorenum);
        score.setStatus("已批阅");
        //可重复考试且已经提交过
        if (isRepeat.equals("true") && scoreMapper.findIsSubmitByUserIdAndTestPaperId(userId,testPaperId).size() >= 1){
            scoreMapper.updateScoreByUserIdAndTestPaperId(score);
        }else{
            scoreMapper.insert(score);
        }
        return 1;
    }

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
     * 组建试卷
     */
    @Override
    public Map<String, Object> componentTestPaper(JSONObject jsonObject) {

        Testpaper testpaper = new Testpaper();
        //查询testpaper表
        testpaper.setSubjectId(jsonObject.getInteger("subjectId"));
        testpaper.setName(jsonObject.getString("name"));
        testpaper.setTotalscore(jsonObject.getDouble("totalScore"));
        testpaper.setPassscore(jsonObject.getDouble("passScore"));
        System.out.println(jsonObject.getString("startTime"));

        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        try {
            testpaper.setStartTime(simpleDateFormat.parse(jsonObject.getString("startTime")));
            testpaper.setDeadTime(simpleDateFormat.parse(jsonObject.getString("endTime")));
        } catch (ParseException e) {
            e.printStackTrace();
        }
        testpaper.setTime(jsonObject.getInteger("totalTime"));
        testpaper.setUserId(jsonObject.getInteger("userId"));

        Object[] departmentIds = jsonObject.getJSONArray("departmentId").toArray();
        StringBuffer stringBuffer = new StringBuffer();
        for (int i = 0; i < departmentIds.length; i++) {
            if (i < departmentIds.length - 1) {
                stringBuffer.append(departmentIds[i].toString()).append(" ");
            } else {
                stringBuffer.append(departmentIds[i].toString());
            }
        }
        testpaper.setDepartmentId(stringBuffer.toString());
        testpaper.setRepeat(jsonObject.getBoolean("repeat").toString());
        //testpaper.setExtra(jsonObject.getString("extra"));
        testPaperMapper.insert(testpaper);

        //插入exam表
        insertIntoExam("singleSelections", jsonObject, 1, testpaper);
        insertIntoExam("multiSelections", jsonObject, 1, testpaper);
        insertIntoExam("fb", jsonObject, 2, testpaper);
        insertIntoExam("judge", jsonObject, 3, testpaper);
        insertIntoExam("sub", jsonObject, 4, testpaper);
        insertIntoExam("material", jsonObject, 5, testpaper);

        JSONObject jsonObject1 = new JSONObject();
        jsonObject1.put("newRecordId", testpaper.getId());
        return jsonObject1;

    }
    //插入exam表
    private void insertIntoExam(String problemType, JSONObject jsonObject, Integer type, Testpaper testpaper) {
        for (Object problemId : jsonObject.getJSONArray(problemType)) {
            Exam exam = new Exam();
            exam.setTestpaperId(testpaper.getId());
            exam.setType(type);
            exam.setProblemId((Integer) problemId);
            if (problemType.equals("singleSelections") || problemType.equals("multiSelections")) {
                exam.setScore(examSelectMapper.selectById((Integer) problemId).getScore());
            } else if (problemType.equals("fb")) {
                exam.setScore(examFillBlankMapper.selectById((Integer) problemId).getScore());
            } else if (problemType.equals("judge")) {
                exam.setScore(examJudgeMapper.selectById((Integer) problemId).getScore());
            } else if (problemType.equals("sub")) {
                exam.setScore(examSubjectMapper.selectById((Integer) problemId).getScore());
            } else if (problemType.equals("material")) {
                exam.setScore(examMaterialService.getMaterialTotalScore((Integer) problemId));
            }
            examMapper.insert(exam);
        }
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
        for (Testpaper testpaper : testpapers) {
            TestpaperVo testpaperVo = new TestpaperVo();
            BeanUtils.copyProperties(testpaper, testpaperVo);
            testpaperVo.setDepartment(departmentName);
            testpaperVo.setSubject(subject);
            testpaperVos.add(testpaperVo);
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
        //Map<String, List<Object>>
        List<Map<String, Object>> result = testPaperService.findTestPaperById(testPaperId);
        if (result == null){
            return null;
        }
        Map<String,Object> map = new HashMap<>();
        map.put("examJudgeAnswers", scoreDataMapper.findExamJudgeAnswerByTidAndUid(testPaperId, userId));
        map.put("singleSelectionAnswers", scoreDataMapper.findSingleSelectionAnswerByTidAndUid(testPaperId, userId));
        map.put("multipleSelectionAnswers", scoreDataMapper.findMultipleSelectionAnswerByTidAndUid(testPaperId, userId));
        map.put("examSubjectAnswers", scoreDataMapper.findExamSubjectAnswerByTidAndUid(testPaperId, userId));
        map.put("examFillBlankAnswers", scoreDataMapper.findExamFillBlankAnswerByTidAndUid(testPaperId, userId));

        result.add(map);

        return result;
    }
}
