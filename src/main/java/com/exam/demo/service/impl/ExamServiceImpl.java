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
import info.debatty.java.stringsimilarity.Jaccard;
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
        double totalScore = 0.0;

        LambdaQueryWrapper<Score> lambdaQueryWrapper = Wrappers.lambdaQuery(Score.class);
        lambdaQueryWrapper
                .eq(Score::getTestpaperId,jsonObject.getInteger("testPaperId"))
                .eq(Score::getUserId,jsonObject.getInteger("userId"));
        Score score = scoreMapper.selectOne(lambdaQueryWrapper);
        //score为null说明执行插入逻辑
        Integer testPaperId = jsonObject.getInteger("testPaperId");
        Integer userId = jsonObject.getInteger("userId");
        if (score == null) {
            //选择题
            //单选题
            totalScore = submitTestOfSingleSelections(jsonObject, totalScore, true, false, testPaperId, userId);
            //多选题
            totalScore = submitTestOfMultipleSelections(jsonObject,totalScore,true,false, testPaperId, userId);
            //填空题
            //未做多空和空串
            totalScore = submitTestOfExamFillBlanks(jsonObject,totalScore,true,false, testPaperId, userId);
            //判断题
            //前端传0 1
            totalScore = submitTestOfExamJudges(jsonObject,totalScore,true,false, testPaperId, userId);
            //主观题
            totalScore = submitTestOfExamSubjects(jsonObject,totalScore,true,false, testPaperId, userId);
            //材料题
            totalScore = submitTestOfExamMaterials(jsonObject,totalScore,true,false, testPaperId, userId);
            System.out.println(totalScore);
            Score scoreRecord = new Score();
            scoreRecord.setTestpaperId(jsonObject.getInteger("testPaperId"));
            scoreRecord.setUserId(jsonObject.getInteger("userId"));
            scoreRecord.setScorenum(totalScore);
            scoreRecord.setStatus("已批阅");
            scoreMapper.insert(scoreRecord);
        } else { //执行更新逻辑
            //选择题
            //单选题
            totalScore = submitTestOfSingleSelections(jsonObject,totalScore,false,true, testPaperId, userId);
            //多选题
            totalScore = submitTestOfMultipleSelections(jsonObject,totalScore,false,true, testPaperId, userId);
            //填空题
            //未做多空和空串
            totalScore = submitTestOfExamFillBlanks(jsonObject,totalScore,false,true, testPaperId, userId);
            //判断题
            //前端传0 1
            totalScore = submitTestOfExamJudges(jsonObject,totalScore,false,true, testPaperId, userId);
            //主观题
            totalScore = submitTestOfExamSubjects(jsonObject,totalScore,false,true, testPaperId, userId);
            //材料题
            totalScore = submitTestOfExamMaterials(jsonObject,totalScore,false,true, testPaperId, userId);

            LambdaQueryWrapper<Score> queryWrapper = new LambdaQueryWrapper<>();
            queryWrapper.eq(Score::getId, score.getId());
            Score score1 = new Score();
            score1.setScorenum(totalScore);
            scoreMapper.update(score1,queryWrapper);
        }
        System.out.println(totalScore);
        JSONObject jsonObject1 = new JSONObject(new LinkedHashMap<>());
        jsonObject1.put("totalScore", totalScore);
        return jsonObject1;
    }

    private double submitTestOfSingleSelections(JSONObject jsonObject, double totalScore, boolean isInsert, boolean isUpdate, Integer testPaperId, Integer userId) {
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
                if (isInsert) {
                    scoredata.setTestpaperId(testPaperId);
                    scoredata.setUserId(userId);
                    scoredata.setType(1);
                    scoredata.setProblemId(((JSONObject) singleSelection).getInteger("id"));
                    scoredata.setAnswer(((JSONObject) singleSelection).getString("userAnswer"));
                    scoreDataMapper.insert(scoredata);
                }
                if (isUpdate) {
                    scoredata.setAnswer(((JSONObject) singleSelection).getString("userAnswer"));
                    LambdaQueryWrapper<Scoredata> queryWrapper = new LambdaQueryWrapper<>();
                    queryWrapper.eq(Scoredata::getTestpaperId,testPaperId)
                            .eq(Scoredata::getUserId, userId)
                            .eq(Scoredata::getProblemId,((JSONObject) singleSelection).getInteger("id"));
                    scoreDataMapper.update(scoredata,queryWrapper);
                }
            }
        }
        return totalScore;
    }

    private double submitTestOfMultipleSelections(JSONObject jsonObject, double totalScore, boolean isInsert, boolean isUpdate, Integer testPaperId, Integer userId) {
        JSONArray multipleSelections = jsonObject.getJSONArray("multipleSelections");
        if (!multipleSelections.isEmpty()) {
            for (Object multipleSelection : multipleSelections) {
                ExamSelect examSelect = examSelectMapper.selectById(((JSONObject) multipleSelection).getInteger("id"));
                Scoredata scoredata = new Scoredata();
                String userAnswer = ((JSONObject) multipleSelection).getString("userAnswer");
                if (!userAnswer.isEmpty()) {
                    //处理用户的多选题答案
                    char[] chars = userAnswer.toCharArray();
                    Arrays.sort(chars);
                    //处理题库中的多选题答案
                    String[] split = examSelect.getAnswer().split(",");
                    StringBuffer stringBuffer = new StringBuffer();
                    for (String s : split) {
                        stringBuffer.append(s);
                    }
                    if (new String(chars).equals(stringBuffer.toString())) {
                        totalScore += examSelect.getScore();
                        scoredata.setScore(examSelect.getScore());
                    } else {
                        scoredata.setScore(0.0);
                    }
                    scoredata.setAnswer(userAnswer);
                    if (isInsert) {
                        scoredata.setTestpaperId(testPaperId);
                        scoredata.setUserId(userId);
                        scoredata.setType(1);
                        scoredata.setProblemId(((JSONObject) multipleSelection).getInteger("id"));
                        scoreDataMapper.insert(scoredata);
                    }
                    if (isUpdate) {
                        LambdaQueryWrapper<Scoredata> queryWrapper = new LambdaQueryWrapper<>();
                        queryWrapper.eq(Scoredata::getTestpaperId,testPaperId)
                                .eq(Scoredata::getUserId, userId)
                                .eq(Scoredata::getProblemId,((JSONObject) multipleSelection).getInteger("id"));
                        scoreDataMapper.update(scoredata,queryWrapper);
                    }
                }
            }
        }
        return totalScore;
    }

    private double submitTestOfExamFillBlanks(JSONObject jsonObject, double totalScore, boolean isInsert, boolean isUpdate, Integer testPaperId, Integer userId) {
        JSONArray examFillBlanks = jsonObject.getJSONArray("examFillBlank");
        if (!examFillBlanks.isEmpty()) {
            for (Object examFillBlank : examFillBlanks) {
                ExamFillBlank fillBlank = examFillBlankMapper.selectById(((JSONObject) examFillBlank).getInteger("id"));
                Scoredata scoredata = new Scoredata();
                String userAnswer = ((JSONObject) examFillBlank).getString("userAnswer");
                //判断时候多空
                if (userAnswer.contains(";")) {

                } else {

                }
                if (userAnswer.equals(fillBlank.getAnswer())) {
                    totalScore += fillBlank.getScore();
                    scoredata.setScore(fillBlank.getScore());
                } else {
                    scoredata.setScore(0.0);
                }
                if (isInsert) {
                    scoredata.setTestpaperId(testPaperId);
                    scoredata.setUserId(userId);
                    scoredata.setType(2);
                    scoredata.setProblemId(((JSONObject) examFillBlank).getInteger("id"));
                    scoredata.setAnswer(userAnswer);
                    scoreDataMapper.insert(scoredata);
                }
                if (isUpdate) {
                    LambdaQueryWrapper<Scoredata> queryWrapper = new LambdaQueryWrapper<>();
                    queryWrapper.eq(Scoredata::getTestpaperId,testPaperId)
                            .eq(Scoredata::getUserId, userId)
                            .eq(Scoredata::getProblemId,((JSONObject) examFillBlank).getInteger("id"));
                    scoreDataMapper.update(scoredata,queryWrapper);
                }
            }
        }
        return totalScore;
    }

    private double submitTestOfExamJudges(JSONObject jsonObject, double totalScore, boolean isInsert, boolean isUpdate, Integer testPaperId, Integer userId) {
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
                scoredata.setAnswer(userAnswer.toString());
                if (isInsert) {
                    scoredata.setTestpaperId(testPaperId);
                    scoredata.setUserId(userId);
                    scoredata.setType(3);
                    scoredata.setProblemId(((JSONObject) examJudge).getInteger("id"));
                    scoreDataMapper.insert(scoredata);
                }
                if (isUpdate) {
                    LambdaQueryWrapper<Scoredata> queryWrapper = new LambdaQueryWrapper<>();
                    queryWrapper.eq(Scoredata::getTestpaperId,testPaperId)
                            .eq(Scoredata::getUserId, userId)
                            .eq(Scoredata::getProblemId,((JSONObject) examJudge).getInteger("id"));
                    scoreDataMapper.update(scoredata,queryWrapper);
                }
            }
        }
        return totalScore;
    }

    private double submitTestOfExamSubjects(JSONObject jsonObject, double totalScore, boolean isInsert, boolean isUpdate, Integer testPaperId, Integer userId) {
        JSONArray examSubjects = jsonObject.getJSONArray("examSubject");
        if (!examSubjects.isEmpty()) {
            for (Object examSubject : examSubjects) {
                ExamSubject subject = examSubjectMapper.selectById(((JSONObject) examSubject).getInteger("id"));

                String answer = examSubjectMapper.selectById(((JSONObject) examSubject).getInteger("id")).getAnswer();
                String userAnswer = ((JSONObject) examSubject).getString("userAnswer");
                //相似度匹配算法
                Jaccard jaccard = new Jaccard(1);
                double similarity = jaccard.similarity(answer, userAnswer);
                double getScore = 0.0;
                if (similarity >= 0.7) {
                    getScore = subject.getScore();
                } else if (similarity >=0.5) {
                    getScore = subject.getScore()*0.6;
                } else if (similarity >= 0.4) {
                    getScore = subject.getScore()*0.3;
                } else if (similarity >= 0.2) {
                    getScore = subject.getScore()*0.1;
                } else {
                    getScore = 0.0;
                }
                Scoredata scoredata = new Scoredata();
                totalScore += getScore;
                scoredata.setScore(getScore);
                if (isInsert) {
                    scoredata.setTestpaperId(testPaperId);
                    scoredata.setUserId(userId);
                    scoredata.setType(4);
                    scoredata.setProblemId(((JSONObject) examSubject).getInteger("id"));
                    scoredata.setAnswer(((JSONObject) examSubject).getString("userAnswer"));
                    scoreDataMapper.insert(scoredata);
                }
                if (isUpdate) {
                    LambdaQueryWrapper<Scoredata> queryWrapper = new LambdaQueryWrapper<>();
                    queryWrapper.eq(Scoredata::getTestpaperId,testPaperId)
                            .eq(Scoredata::getUserId, userId)
                            .eq(Scoredata::getProblemId,((JSONObject) examSubject).getInteger("id"));
                    scoreDataMapper.update(scoredata,queryWrapper);
                }
            }
        }
        return totalScore;
    }

    private double submitTestOfExamMaterials(JSONObject jsonObject, double totalScore, boolean isInsert, boolean isUpdate, Integer testPaperId, Integer userId) {
        JSONArray examMaterials = jsonObject.getJSONArray("examMaterial");
        if (!examMaterials.isEmpty()) {
            for (Object examMaterial : examMaterials) {
                double materialScore = 0.0;
                //单选题
                materialScore = submitTestOfSingleSelections((JSONObject) examMaterial, materialScore, isInsert, isUpdate, testPaperId, userId);
                //多选题
                materialScore = submitTestOfMultipleSelections((JSONObject) examMaterial, materialScore, isInsert, isUpdate, testPaperId, userId);
                //判断题
                materialScore = submitTestOfExamJudges((JSONObject) examMaterial, materialScore, isInsert, isUpdate, testPaperId, userId);
                //主观题
                materialScore = submitTestOfExamSubjects((JSONObject) examMaterial, materialScore, isInsert, isUpdate, testPaperId, userId);
                //填空题
                materialScore = submitTestOfExamFillBlanks((JSONObject) examMaterial, materialScore, isInsert, isUpdate, testPaperId, userId);

                Scoredata scoredata = new Scoredata();
                scoredata.setScore(materialScore);
                totalScore += materialScore;
                if (isInsert) {
                    scoredata.setTestpaperId(testPaperId);
                    scoredata.setUserId(userId);
                    scoredata.setType(5);
                    scoredata.setProblemId(((JSONObject) examMaterial).getInteger("id"));
                    scoreDataMapper.insert(scoredata);
                }
                if (isUpdate) {
                    LambdaQueryWrapper<Scoredata> queryWrapper = new LambdaQueryWrapper<>();
                    queryWrapper.eq(Scoredata::getTestpaperId,testPaperId)
                            .eq(Scoredata::getUserId, userId)
                            .eq(Scoredata::getProblemId,((JSONObject) examMaterial).getInteger("id"));
                    scoreDataMapper.update(scoredata,queryWrapper);
                }
            }
        }
        return totalScore;
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

        String department = examAndUserAnswer.getDepartment();
        LinkedList<String> list = new LinkedList<>();
        if (department.contains(" ")) {

            String[] deptIds = department.split(" ");
            for (String deptId : deptIds) {
                list.add(departmentMapper.selectById(deptId).getName());
            }
        }
        map.put("department",list);
        map.put("startTime",examAndUserAnswer.getStartTime());
        map.put("endTime",examAndUserAnswer.getEndTime());
        map.put("repeat",examAndUserAnswer.getRepeat());
        double userscore = scoreMapper.findByTestPaperIdAndUserId(testPaperId,userId).get(0).getScorenum();

        map.put("userScore",userscore);
        String ispass = userscore >= passscore ? "true":"false";
        map.put("isPass",ispass);
        map.put("username",userMapper.selectById(userId).getName());
        map.put("userDepartment",departmentMapper.selectById(examAndUserAnswer.getUserDepartment()).getName());
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
