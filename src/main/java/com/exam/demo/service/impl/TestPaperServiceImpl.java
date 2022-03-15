package com.exam.demo.service.impl;

import com.alibaba.fastjson.JSONObject;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.toolkit.StringUtils;
import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;

import com.exam.demo.entity.*;

import com.exam.demo.mapper.*;
import com.exam.demo.otherEntity.RtTestpaper;
import com.exam.demo.otherEntity.SelectQuestionVo;
import com.exam.demo.results.vo.PageVo;
import com.exam.demo.service.ExamMaterialService;
import com.exam.demo.service.SubjectService;
import com.exam.demo.service.TestPaperService;
import com.exam.demo.results.vo.TestpaperVo;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.sql.Timestamp;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.*;


@Service
public class TestPaperServiceImpl implements TestPaperService {

    @Autowired
    private TestPaperMapper testPaperMapper;

    @Autowired
    private UserMapper userMapper;

    @Autowired
    private DepartmentMapper departmentMapper;

    @Autowired
    private SubjectMapper subjectMapper;

    @Autowired
    private ScoreMapper scoreMapper;

    @Autowired
    private ExamMapper examMapper;

    @Autowired
    private SubjectService subjectService;

    @Autowired
    private ExamSelectMapper examSelectMapper;

    @Autowired
    private ExamSubjectMapper examSubjectMapper;

    @Autowired
    private ExamFillBlankMapper examFillBlankMapper;

    @Autowired
    private ExamJudgeMapper examJudgeMapper;

    @Autowired
    private ExamMaterialService examMaterialService;


    /**
     *查询进行中考试试卷的卷头
     * @param userId
     * @return
     */
    @Override
    public List<Map<String, Object>> findCurrentTestPaperHead(Integer userId) {
        List<Map<String,Object>> testpapers = new ArrayList<>();

        //当前正在进行的考试
        QueryWrapper<Testpaper> QueryWrapper = new QueryWrapper<>();

        QueryWrapper.lambda()
                .ge(Testpaper::getDeadTime,new Timestamp(new Date().getTime()))
                .le(Testpaper::getStartTime,new Timestamp(new Date().getTime()));
        List<Testpaper> testpapers1 = testPaperMapper.selectList(QueryWrapper);

        for (Testpaper testpaper: testpapers1){
            Map<String,Object> map = new LinkedHashMap<>();
            map.put("id",testpaper.getId());
            map.put("subjectName",subjectMapper.selectById(testpaper.getSubjectId()).getName());
            map.put("name",testpaper.getName());
            map.put("totalscore",testpaper.getTotalscore());
            map.put("passscore",testpaper.getPassscore());
            map.put("startTime",testpaper.getStartTime());
            map.put("deadTime",testpaper.getDeadTime());
            map.put("time",testpaper.getTime());
            map.put("repeat",testpaper.getRepeat());
            List<UserTestPaperScore> list1 = scoreMapper.findByUserId(userId);
            List<UserTestPaperScore> list2 = scoreMapper.findByTestPaperId(testpaper.getId());
            list1.retainAll(list2);
            if (list1.isEmpty()){
                map.put("state","未完成");
                map.put("score",null);
            }else {
                map.put("state","已完成");
                map.put("score",list1.get(0).getScorenum());
            }
            testpapers.add(map);

        }
        return testpapers;

    }

    /**
     * 查询历史考试试卷的卷头
     * @param userId
     * @return
     */
    @Override
    public List<Map<String, Object>> findHistorialTestPaperHead(Integer userId) {
        List<Map<String,Object>> testpapers = new ArrayList<>();

        QueryWrapper<Testpaper> queryWrapper = new QueryWrapper<>();
        queryWrapper.lt("dead_time", new Timestamp(new Date().getTime()));

        for (Testpaper testpaper: testPaperMapper.selectList(queryWrapper)){
            Map<String,Object> map = new LinkedHashMap<>();
            map.put("id",testpaper.getId());
            map.put("subjectName",subjectMapper.selectById(testpaper.getSubjectId()).getName());
            map.put("name",testpaper.getName());
            map.put("totalscore",testpaper.getTotalscore());
            map.put("passscore",testpaper.getPassscore());
            map.put("startTime",testpaper.getStartTime());
            map.put("deadTime",testpaper.getDeadTime());
            map.put("time",testpaper.getTime());
            map.put("repeat",testpaper.getRepeat());
            List<UserTestPaperScore> list1 = scoreMapper.findByUserId(userId);
            List<UserTestPaperScore> list2 = scoreMapper.findByTestPaperId(testpaper.getId());
            list1.retainAll(list2);
            if (list1.isEmpty()){
                map.put("state","未完成");
                map.put("score",null);
            }else {
                map.put("state","已完成");
                map.put("score",list1.get(0).getScorenum());
            }
            testpapers.add(map);

        }
        return testpapers;

    }
    /**
     * 对选择题内容进行处理，并写入selectQuestion
     * @param examSelects
     */

    public List<Object> change(List<ExamSelect> examSelects) {

//    public List<Object> change(List<Object> examSelects) {

        List<Object> selectionQuestions = new ArrayList<>();
        for(Object object : examSelects) {
            ExamSelect examSelect = (ExamSelect) object;
            SelectQuestionVo selectQuestionVo = new SelectQuestionVo();
            selectQuestionVo.setId(examSelect.getId());
            selectQuestionVo.setContext(examSelect.getContext());
            selectQuestionVo.setSelections(Arrays.asList(examSelect.getSelection().split(";")));
            selectQuestionVo.setAnswer(examSelect.getAnswer());

            selectQuestionVo.setSubject(subjectService.findById(examSelect.getSubjectId()).getName());
//            selectQuestionVo.setSubject(examSelect.getSubjectId().toString());
            selectQuestionVo.setDifficulty(examSelect.getDifficulty());
            selectQuestionVo.setScore(examSelect.getScore());
            selectQuestionVo.setType(examSelect.getType());
            selectQuestionVo.setImgUrl(examSelect.getImgUrl());
            selectQuestionVo.setMaterial_question(examSelect.getMaterialQuestion());

            selectQuestionVo.setScore(examSelect.getScore());
           // selectQuestionVo.setImgUrl(examSelect.getImgUrl());
            selectionQuestions.add(selectQuestionVo);
        }
        return selectionQuestions;
    }

    @Override
    public List<Map<String, Object>> findTestPaperById(int testPaperId) {
        List<Map<String,Object>> testpapers = new ArrayList<>();

        Map<String,Object> map = new LinkedHashMap<>();
        map.put("singleSelections", change(examMapper.findSingleSelectionByTestPaperId(testPaperId)));
        map.put("multipleSelections", change(examMapper.findMultipleSelectionByTestPaperId(testPaperId)));
        map.put("examFillBlank", examMapper.findExamFillBlankByTestPaperId(testPaperId));
        map.put("examJudge", examMapper.findExamJudgeByTestPaperId(testPaperId));
        map.put("examSubject", examMapper.findExamSubjectByTestPaperId(testPaperId));
        List<ExamMaterial> examMaterialByTestPaperId = examMapper.findExamMaterialByTestPaperId(testPaperId);
        List<Map<String,Object>> list = new ArrayList<>();
        for (ExamMaterial examMaterial : examMaterialByTestPaperId){
            Map<String,Object> map1 = new LinkedHashMap<>();
            map1.put("id",examMaterial.getId());
            map1.put("context",examMaterial.getContext());
            Map<String,Object> map2 = new HashMap<>();
            map2.put("singleSelections", change(examMapper.findSingleSelectionByExamMaterialId(examMaterial.getId())));
            map2.put("multipleSelections",change(examMapper.findMultipulSelectionByExamMaterialId(examMaterial.getId())));
            map2.put("examFillBlank",examMapper.findExamFillBlankByExamMaterialId(examMaterial.getId()));
            map2.put("examJudge",examMapper.findExamJudgeByExamMaterialId(examMaterial.getId()));
            map2.put("examSubject",examMapper.findExamSubjectByExamMaterialId(examMaterial.getId()));
            map1.put("question",map2);
            list.add(map1);
        }

        map.put("examMaterial",list);
        testpapers.add(map);
        return testpapers;
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
        testpaper.setExtra(jsonObject.getString("extra"));
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

    @Override
    public Map<String, Object> deleteTestPaper(Integer id) {
        //删除试卷题目详情
        LambdaQueryWrapper<Exam> lambdaQueryWrapper = new LambdaQueryWrapper<>();
        //删除试卷
        lambdaQueryWrapper.eq(Exam::getTestpaperId, id);
        examMapper.delete(lambdaQueryWrapper);
        JSONObject jsonObject = new JSONObject();
        jsonObject.put("deletedTestPaperTotal", testPaperMapper.deleteById(id));
        return jsonObject;
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
     * 将 Testpaper 中的内容修改复制到 RtTestpaper 中
     * @param testpaper
     * @return
     */
    public RtTestpaper change(Testpaper testpaper) {
        RtTestpaper rtTestpaper = new RtTestpaper();
        rtTestpaper.setId(testpaper.getId());
        rtTestpaper.setSubjectName(subjectMapper.selectById(testpaper.getSubjectId()).getName());
        rtTestpaper.setName(testpaper.getName());
        rtTestpaper.setTotalscore(testpaper.getTotalscore());
        rtTestpaper.setPassscore(testpaper.getPassscore());
        rtTestpaper.setStartTime(testpaper.getStartTime());
        rtTestpaper.setDeadTime(testpaper.getDeadTime());
        rtTestpaper.setTime(testpaper.getTime());
        rtTestpaper.setUserName(userMapper.selectById(testpaper.getUserId()).getName());
        rtTestpaper.setDepartmentName(departmentMapper.selectById(testpaper.getDepartmentId()).getName());
        return rtTestpaper;
    }

    /**
     * 查询所有试卷信息
     * @return
     */
    @Override
    public List<RtTestpaper> findAll() {
        List<RtTestpaper> rtTestpaperList = new ArrayList<>();
        List<Testpaper> testpaperList = testPaperMapper.selectList(new LambdaQueryWrapper<>());
        if(testpaperList.size() != 0) {
            for (Testpaper testpaper : testpaperList) {
                RtTestpaper rtTestpaper = change(testpaper);
                rtTestpaperList.add(rtTestpaper);
            }
        }
        return rtTestpaperList;
    }

    /**
     * 查询正在进行的考试
     * @return
     */
    @Override
    public List<RtTestpaper> findTesting() {
        List<RtTestpaper> rtTestpaperList = new ArrayList<>();

        QueryWrapper<Testpaper> queryWrapper = new QueryWrapper<>();
        queryWrapper
                .ge("start_time", new Timestamp(new Date().getTime()))
                .le("dead_time", new Timestamp(new Date().getTime()));

        for(Testpaper testpaper : testPaperMapper.selectList(queryWrapper)) {
            RtTestpaper rtTestpaper = change(testpaper);
            rtTestpaperList.add(rtTestpaper);
        }
        return rtTestpaperList;
    }

    /**
     * 查询历史考试
     * @return
     */
    @Override
    public List<RtTestpaper> findTested() {
        List<RtTestpaper> rtTestpaperList = new ArrayList<>();

        QueryWrapper<Testpaper> queryWrapper = new QueryWrapper<>();
        queryWrapper.gt("dead_time", new Timestamp(new Date().getTime()));

        for(Testpaper testpaper : testPaperMapper.selectList(queryWrapper)) {
            RtTestpaper rtTestpaper = change(testpaper);
            rtTestpaperList.add(rtTestpaper);
        }
        return rtTestpaperList;
    }

    /**
     * 查询尚未开始的考试
     * @return
     */
    @Override
    public List<RtTestpaper> findNotStartTest() {
        List<RtTestpaper> rtTestpaperList = new ArrayList<>();

        QueryWrapper<Testpaper> queryWrapper = new QueryWrapper<>();
        queryWrapper.gt("start_time", new Timestamp(new Date().getTime()));
        for(Testpaper testpaper : testPaperMapper.selectList(queryWrapper)) {
            RtTestpaper rtTestpaper = change(testpaper);
            rtTestpaperList.add(rtTestpaper);
        }
        return rtTestpaperList;
    }

    /**
     * 增加试卷头信息
     * @param testPaper
     */
    @Override
    public Integer addTestPaper(Testpaper testPaper) {
        return testPaperMapper.insert(testPaper);
    }
//==============================================正在考试=================================================================
    /**
     * 查询所有正在考试——不支持分页功能
     * @return
     */
    @Override
    public List<TestpaperVo> findAllCurrentExam() {
        return testManageFindAll("(SELECT current_timestamp()) BETWEEN testpaper.start_time AND testpaper.dead_time");
    }

    /**
     * 组合查询并进行分页查询——正在考试
     * @param testPaperId
     * @param testPaperName
     * @param departmentId
     * @param subject
     * @return
     */
    @Override
    public PageVo<TestpaperVo> findCurrentExam(Integer testPaperId, String testPaperName, Integer departmentId,
                                               String subject, Integer currentPage, Integer pageSize) {
        HashMap<String, Object> map = testManageCombinedQuery(testPaperId, testPaperName, departmentId, subject, currentPage, pageSize,
                "(SELECT current_timestamp()) BETWEEN testpaper.start_time AND testpaper.dead_time");
        return PageVo.<TestpaperVo>builder()
                .values((LinkedList<TestpaperVo>)map.get("testpaperVos"))
                .page(currentPage)
                .size(pageSize)
                .total((Long) map.get("total"))
                .build();
    }

    /**
     * 查询所有正在考试——支持分页功能
     * @param currentPage
     * @param pageSize
     * @return
     */
    @Override
    public List<TestpaperVo> findCurrentExamByPage(Integer currentPage, Integer pageSize) {
        return testManageByPage(currentPage, pageSize,
                "(SELECT current_timestamp()) BETWEEN testpaper.start_time AND testpaper.dead_time");
    }
//=====================================================历史考试==========================================================
    /**
     * 查询所有历史考试
     * @return
     */
    @Override
    public List<TestpaperVo> findAllHistoricalExam() {
        return testManageFindAll("(SELECT current_timestamp()) > testpaper.dead_time");
    }

    /**
     * 组合查询并进行分页查询——历史考试
     * @param testPaperId
     * @param testPaperName
     * @param departmentId
     * @param subject
     * @param currentPage
     * @param pageSize
     * @return
     */
    @Override
    public PageVo<TestpaperVo> findHistoricalExam(Integer testPaperId, String testPaperName, Integer departmentId,
                                                String subject, Integer currentPage, Integer pageSize) {
        HashMap<String, Object> map = testManageCombinedQuery(testPaperId, testPaperName, departmentId, subject, currentPage
                , pageSize, "(SELECT current_timestamp()) > testpaper.dead_time");
        return PageVo.<TestpaperVo>builder()
                .values((LinkedList<TestpaperVo>)map.get("testpaperVos"))
                .page(currentPage)
                .size(pageSize)
                .total((Long) map.get("total"))
                .build();
    }

    /**
     * 查询所有历史考试——支持分页功能
     * @param currentPage
     * @param pageSize
     * @return
     */
    @Override
    public List<TestpaperVo> findHistoricalExamByPage(Integer currentPage, Integer pageSize) {
        return testManageByPage(currentPage, pageSize,
                "(SELECT current_timestamp()) > testpaper.dead_time");
    }
//===================================================未来考试============================================================
    /**
     * 查询所有未来考试
     * @return
     */
    @Override
    public List<TestpaperVo> findAllFutureExam() {
        return testManageFindAll("testpaper.start_time > (SELECT current_timestamp())");
    }

    /**
     * 组合查询并进行分页查询——未来考试
     * @param testPaperId
     * @param testPaperName
     * @param departmentId
     * @param subject
     * @param currentPage
     * @param pageSize
     * @return
     */
    @Override
    public PageVo<TestpaperVo> findFutureExam(Integer testPaperId, String testPaperName, Integer departmentId,
                                            String subject, Integer currentPage, Integer pageSize) {
        HashMap<String, Object> map = testManageCombinedQuery(testPaperId, testPaperName, departmentId, subject, currentPage,
                pageSize, "testpaper.start_time > (SELECT current_timestamp())");
        return PageVo.<TestpaperVo>builder()
                .values((LinkedList<TestpaperVo>)map.get("testpaperVos"))
                .page(currentPage)
                .size(pageSize)
                .total((Long) map.get("total"))
                .build();
    }

    /**
     * 查询所有历史考试——支持分页功能
     * @param currentPage
     * @param pageSize
     * @return
     */
    @Override
    public List<TestpaperVo> findFutureExamByPage(Integer currentPage, Integer pageSize) {
        return testManageByPage(currentPage, pageSize,
                "testpaper.start_time > (SELECT current_timestamp())");
    }
//==================================================工具方法=============================================================
    /**
     * 将Testpaper转换为TestpaperVo
     * @param testpaperVo
     * @param testpaper
     * @return
     */
    private TestpaperVo copyTestpaperBean(TestpaperVo testpaperVo, Testpaper testpaper) {

        DateFormat df = new SimpleDateFormat("yyyy-MM-dd hh:mm:ss");

        BeanUtils.copyProperties(testpaper, testpaperVo);
        testpaperVo.setStartTime(df.format(testpaper.getStartTime()));
        testpaperVo.setDeadTime(df.format(testpaper.getDeadTime()));

        String[] s = testpaper.getDepartmentId().split(" ");
        if (s.length != 0) {
            LinkedList<String> list = new LinkedList<>();
            for (String s1 : s) {
                list.add(departmentMapper.selectById(s1).getName());
            }
            testpaperVo.setDepartment(list);
        }
        testpaperVo.setSubject(subjectMapper.selectById(testpaper.getSubjectId()).getName());
        testpaperVo.setUserName(userMapper.selectById(testpaper.getUserId()).getName());
        return testpaperVo;
    }

    /**
     * 通用组合查询方法
     * @param testPaperId
     * @param testPaperName
     * @param departmentId
     * @param subject
     * @param currentPage
     * @param pageSize
     * @param sql
     * @return
     */
    private HashMap<String, Object> testManageCombinedQuery(Integer testPaperId, String testPaperName, Integer departmentId,
                                                     String subject, Integer currentPage, Integer pageSize, String sql) {
        Page<Testpaper> page = new Page<>(currentPage, pageSize);
        LambdaQueryWrapper<Testpaper> queryWrapper = Wrappers.lambdaQuery(Testpaper.class);
        queryWrapper.select(Testpaper::getId,Testpaper::getSubjectId
                ,Testpaper::getName,Testpaper::getTotalscore,Testpaper::getPassscore,Testpaper::getDepartmentId
                ,Testpaper::getStartTime,Testpaper::getDeadTime,Testpaper::getTime, Testpaper::getUserId);

        if (testPaperId == null && StringUtils.isBlank(testPaperName) && departmentId == null
                && StringUtils.isBlank(subject)){
            queryWrapper.last("where " + sql);
        } else {
            if (testPaperId != null) {
                queryWrapper.eq(Testpaper::getId, testPaperId);
            }
            queryWrapper.like(StringUtils.isNotBlank(testPaperName),Testpaper::getName, testPaperName);
            if (departmentId != null) {
                queryWrapper.like(Testpaper::getDepartmentId, departmentId.toString());
            }
            if (!StringUtils.isBlank(subject)) {
                //根据学科名称查询id
                LambdaQueryWrapper<Subject> subjectWrapper = new LambdaQueryWrapper<>();
                subjectWrapper.like(Subject::getName, subject);
                Subject sub = subjectMapper.selectOne(subjectWrapper);
                if (sub != null) {
                    queryWrapper.eq(Testpaper::getSubjectId, sub.getId());
                }
            }
            queryWrapper.last("and " + sql);
        }
        if (departmentId != null) {
            queryWrapper.like(Testpaper::getDepartmentId, departmentId.toString());
        }
        Page<Testpaper> testpaperPage = testPaperMapper.selectPage(page, queryWrapper);
        //将查询对象转为交互返回对象
        LinkedList<TestpaperVo> testpaperVos = new LinkedList<>();
        //遍历查询结果，并将符合条件的存入交互对象中
        if (!testpaperPage.getRecords().isEmpty()) {
            for (Testpaper testpaper : testpaperPage.getRecords()) {
                testpaperVos.add(copyTestpaperBean(new TestpaperVo(), testpaper));
            }
        }
        HashMap<String, Object> map = new HashMap<>();
        map.put("total", testpaperPage.getTotal());
        map.put("testpaperVos", testpaperVos);
        return map;
    }

    /**
     * 考试管理模块的通用分页查询
     * @param currentPage
     * @param pageSize
     * @param lastSql
     * @return
     */
    private List<TestpaperVo> testManageByPage(Integer currentPage, Integer pageSize, String lastSql) {
        Page<Testpaper> page = new Page<>(currentPage, pageSize);
        LambdaQueryWrapper<Testpaper> queryWrapper = new LambdaQueryWrapper<>();
        queryWrapper.last("where " + lastSql);
        Page<Testpaper> testpaperPage = testPaperMapper.selectPage(page, queryWrapper);
        LinkedList<TestpaperVo> testpaperVos = new LinkedList<>();
        for (Testpaper record : testpaperPage.getRecords()) {
            testpaperVos.add(copyTestpaperBean(new TestpaperVo(), record));
        }
        return testpaperVos;
    }

    /**
     * 考试管理模块的通用查询全部方法
     * @param lastSql
     * @return
     */
    private List<TestpaperVo> testManageFindAll(String lastSql) {
        LambdaQueryWrapper<Testpaper> wrapper = new LambdaQueryWrapper<>();
        wrapper.last("where " + lastSql);
        List<Testpaper> testpaperList = testPaperMapper.selectList(wrapper);
        //将查询对象转为交互返回对象
        LinkedList<TestpaperVo> testpaperVos = new LinkedList<>();
        //遍历查询结果，并将符合条件的存入交互对象中
        for (Testpaper testpaper : testpaperList) {
            testpaperVos.add(copyTestpaperBean(new TestpaperVo(), testpaper));
        }
        return testpaperVos;
    }

}
