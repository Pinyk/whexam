package com.exam.demo.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.toolkit.StringUtils;
import com.exam.demo.entity.*;
import com.exam.demo.mapper.*;
import com.exam.demo.otherEntity.SelectQuestion;
import com.exam.demo.service.ExamJudgeService;
import com.exam.demo.service.ExamSelectService;
import com.exam.demo.service.ExamService;
import com.exam.demo.service.ExamSubjectService;
import com.exam.demo.utils.TestpaperVo;
import io.swagger.models.auth.In;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

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
    private ExamJudgeService examJudgeService;

    @Autowired
    private ExamSelectService examSelectService;

    @Autowired
    private ExamSubjectService examSubjectService;

    @Autowired
    private DepartmentMapper departmentMapper;

    @Autowired
    private SubjectMapper subjectMapper;

    @Autowired
    TestPaperMapper testPaperMapper;

    /**
     * 查找每张试卷对应的所有试题
     * @param testPaperId
     * @return
     */
    @Override
    public Map<String, List<Object>> findByTestPaperId(Integer testPaperId) {
        Map<String, List<Object>> map = new HashMap<>();
        List<Object> singleSelections = new ArrayList<>();
        for(Object object : examMapper.findSingleSelectionByTestPaperId(testPaperId)) {
            ExamSelect examSelect = (ExamSelect) object;
            SelectQuestion selectQuestion = new SelectQuestion();
            selectQuestion.setContext(examSelect.getContext());
            selectQuestion.setSelections(Arrays.asList(examSelect.getSelection().split("；")));
            selectQuestion.setScore(examSelect.getScore());
            selectQuestion.setImgUrl(examSelect.getImgUrl());
            singleSelections.add(selectQuestion);
        }

        List<Object> multipleSelections = new ArrayList<>();
        for(Object object : examMapper.findMultipleSelectionByTestPaperId(testPaperId)) {
            ExamSelect examSelect = (ExamSelect) object;
            SelectQuestion selectQuestion = new SelectQuestion();
            selectQuestion.setContext(examSelect.getContext());
            selectQuestion.setSelections(Arrays.asList(examSelect.getSelection().split("；")));
            selectQuestion.setScore(examSelect.getScore());
            selectQuestion.setImgUrl(examSelect.getImgUrl());
            multipleSelections.add(selectQuestion);
        }

        map.put("examJudge", examMapper.findExamJudgeByTestPaperId(testPaperId));
        map.put("singleSelection", singleSelections);
        map.put("multipleSelection", multipleSelections);
        map.put("examSubject", examMapper.findExamSubjectByTestPaperId(testPaperId));
        return map;
    }

    /**
     * 提交试卷，将用户作答保留到数据库，并为客观题目评分
     * @return
     */
    @Override
    public Integer submitTest(Integer testPaperId, Integer userId, List<ExamJudge> examJudges, List<ExamSelect> examSelects, List<ExamSubject> examSubjects) {
        // 遍历每道题目用户的作答情况，同时对客观题目，找出标准答案（类型，题号 找到标准答案），与用户答案对比，给分，保存到数据
        // 将用户考试记录插入数据库
        Score score = new Score();
        score.setTestpaperId(testPaperId);
        score.setUserId(userId);
        scoreMapper.insert(score);
        // 判断题
        for(ExamJudge examJudge : examJudges) {
            ExamJudge examJudgeByDB = examJudgeService.findById(examJudge.getId());
            int answer = examJudgeByDB.getAnswer();
            Scoredata scoreData = new Scoredata();
            scoreData.setTestpaperId(testPaperId);
            scoreData.setUserId(userId);
            scoreData.setType(1);
            scoreData.setProblemId(examJudge.getId());
            scoreData.setAnswer(String.valueOf(examJudge.getAnswer()));
            if(answer == examJudge.getAnswer()) {
                scoreData.setScore(examJudgeByDB.getScore());
            } else {
                scoreData.setScore(0);
            }
            scoreDataMapper.insert(scoreData);
        }
        // 选择题
        for(ExamSelect examSelect : examSelects) {
            SelectQuestion examSelectByDB = examSelectService.findById(examSelect.getId());
            String answer = examSelectByDB.getAnswer();
            Scoredata scoreData = new Scoredata();
            scoreData.setTestpaperId(testPaperId);
            scoreData.setUserId(userId);
            scoreData.setType(2);
            scoreData.setProblemId(examSelect.getId());
            scoreData.setAnswer(examSelect.getAnswer());
            if(examSelect.getAnswer().equals(answer)) {
                scoreData.setScore(examSelectByDB.getScore());
            } else {
                scoreData.setScore(0);
            }
            scoreDataMapper.insert(scoreData);
        }
        // 主观题
        for(ExamSubject examSubject : examSubjects) {
            Scoredata scoreData = new Scoredata();
            scoreData.setTestpaperId(testPaperId);
            scoreData.setUserId(userId);
            scoreData.setType(3);
            scoreData.setProblemId(examSubject.getId());
            scoreData.setAnswer(examSubject.getAnswer());
            scoreData.setScore(examSubjectService.findById(examSubject.getId()).getScore());// 暂时为满分，等自批改算法
            scoreDataMapper.insert(scoreData);
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
     * 随机组建试卷
     * 需要：1.科目类型；2.判断、选择、主观各几道；
     * @return
     */
    @Override
    public Integer randomComponentPaper(Integer testPaperId, Integer subjectId, Integer judgeCount, Integer singleCount, Integer multipleCount, Integer subjectCount) {
        int count = 0;
        Random random = new Random();

        List<ExamJudge> examJudges = examJudgeService.findBySubjectId(subjectId);
        List<ExamSelect> singleSelections = examSelectService.findBySubjectId(subjectId, 1);
        List<ExamSelect> multipleSelections = examSelectService.findBySubjectId(subjectId, 2);
        List<ExamSubject> examSubjects = examSubjectService.findBySubjectId(subjectId);

        while(count++ < judgeCount) {
            ExamJudge examJudge = examJudges.get(random.nextInt(examJudges.size()));
            Exam exam = new Exam();
            exam.setTestpaperId(testPaperId);
            exam.setType(1);
            exam.setProblemId(examJudge.getId());
            exam.setScore(examJudge.getScore());
            examMapper.insert(exam);
        }
        count = 0;
        while(count++ < singleCount) {
            ExamSelect examSelect = singleSelections.get(random.nextInt(singleSelections.size()));
            Exam exam = new Exam();
            exam.setTestpaperId(testPaperId);
            exam.setType(1);
            exam.setProblemId(examSelect.getId());
            exam.setScore(examSelect.getScore());
            examMapper.insert(exam);
        }
        count = 0;
        while(count++ < multipleCount) {
            ExamSelect examSelect = multipleSelections.get(random.nextInt(multipleSelections.size()));
            Exam exam = new Exam();
            exam.setTestpaperId(testPaperId);
            exam.setType(1);
            exam.setProblemId(examSelect.getId());
            exam.setScore(examSelect.getScore());
            examMapper.insert(exam);
        }
        count = 0;
        while(count++ < subjectCount) {
            ExamSubject examSubject = examSubjects.get(random.nextInt(examSubjects.size()));
            Exam exam = new Exam();
            exam.setTestpaperId(testPaperId);
            exam.setType(3);
            exam.setProblemId(examSubject.getId());
            exam.setScore(examSubject.getScore());
            examMapper.insert(exam);
        }
        return 1;
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
        return scoreMapper.updateScoreByUserId(sc);
    }

    /**
     * 根据用户ID和试卷ID查询考试明细接口
     * @param testPaperId
     * @param userId
     * @return
     */
    @Override
    public Map<String, List<Object>> findScoreDetailByUIdAndTPId(Integer testPaperId, Integer userId) {
        Map<String, List<Object>> result = findByTestPaperId(testPaperId);

        QueryWrapper<Scoredata> queryWrapper = new QueryWrapper<>();
        queryWrapper.eq("testpaper_id",testPaperId);
        queryWrapper.eq("user_id",userId);
        result.put("userAnswerDetail", Collections.singletonList(scoreDataMapper.selectList(queryWrapper)));
        return result;
    }
}
