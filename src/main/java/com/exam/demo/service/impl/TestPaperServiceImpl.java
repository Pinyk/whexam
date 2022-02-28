package com.exam.demo.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.toolkit.StringUtils;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.exam.demo.entity.Department;
import com.exam.demo.entity.Subject;
import com.exam.demo.entity.Testpaper;
import com.exam.demo.mapper.DepartmentMapper;
import com.exam.demo.mapper.SubjectMapper;
import com.exam.demo.mapper.TestPaperMapper;
import com.exam.demo.mapper.UserMapper;
import com.exam.demo.otherEntity.RtTestpaper;
import com.exam.demo.service.TestPaperService;
import com.exam.demo.utils.TestpaperVo;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.sql.Timestamp;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.LinkedList;
import java.util.List;

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
        rtTestpaper.setShuffle(testpaper.getShuffle());
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
                .ge("dead_time", new Timestamp(new Date().getTime()))
                .le("start_time", new Timestamp(new Date().getTime()));

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
        queryWrapper.lt("dead_time", new Timestamp(new Date().getTime()));

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

    /**
     * 查询所有正在考试
     * @return
     */
    @Override
    public List<TestpaperVo> findAllCurrentExam() {
        LambdaQueryWrapper<Testpaper> wrapper = new LambdaQueryWrapper<>();
        wrapper.last("where (SELECT current_timestamp()) BETWEEN testpaper.start_time AND testpaper.dead_time");
        List<Testpaper> testpaperList = testPaperMapper.selectList(wrapper);
        //将查询对象转为交互返回对象
        LinkedList<TestpaperVo> testpaperVos = new LinkedList<>();
        //遍历查询结果，并将符合条件的存入交互对象中
        for (Testpaper testpaper : testpaperList) {
            testpaperVos.add(copyTestpaperBean(new TestpaperVo(), testpaper));
        }
        return testpaperVos;
    }

    /**
     * 组合查询正在考试,并进行分页查询
     * @param testPaperId
     * @param testPaperName
     * @param departmentName
     * @param subject
     * @return
     */
    @Override
    public List<TestpaperVo> findCurrentExam(Integer testPaperId, String testPaperName, String departmentName,
                                             String subject, Integer currentPage, Integer pageSize) {
        Page<Testpaper> page = new Page<>(currentPage, pageSize);
        LambdaQueryWrapper<Testpaper> queryWrapper = new LambdaQueryWrapper<>();
        if (testPaperId != null) {
            queryWrapper.eq(Testpaper::getId, testPaperId);
        }
        if (!StringUtils.isBlank(testPaperName)) {
            queryWrapper.like(Testpaper::getName, testPaperName);
        }
        if (!StringUtils.isBlank(departmentName)) {
            //根据部门名称查询id
            LambdaQueryWrapper<Department> deptWrapper = new LambdaQueryWrapper<>();
            deptWrapper.eq(Department::getName, departmentName);
            Department department = departmentMapper.selectOne(deptWrapper);
            queryWrapper.eq(Testpaper::getDepartmentId, department.getId());
        }
        if (!StringUtils.isBlank(subject)) {
            //根据学科名称查询id
            LambdaQueryWrapper<Subject> subjectWrapper = new LambdaQueryWrapper<>();
            subjectWrapper.eq(Subject::getName, subject);
            Subject sub = subjectMapper.selectOne(subjectWrapper);
            queryWrapper.eq(Testpaper::getSubjectId, sub.getId());
        }
        queryWrapper.last("where (SELECT current_timestamp()) BETWEEN testpaper.start_time AND testpaper.dead_time");
        Page<Testpaper> testpaperPage = testPaperMapper.selectPage(page, queryWrapper);
        //将查询对象转为交互返回对象
        LinkedList<TestpaperVo> testpaperVos = new LinkedList<>();
        //遍历查询结果，并将符合条件的存入交互对象中
        for (Testpaper testpaper : testpaperPage.getRecords()) {
            testpaperVos.add(copyTestpaperBean(new TestpaperVo(), testpaper));
        }
        return testpaperVos;
    }

    @Override
    public List<TestpaperVo> findCurrentExamByPage(Integer currentPage, Integer pageSize) {
        Page<Testpaper> page = new Page<>(currentPage, pageSize);
        LambdaQueryWrapper<Testpaper> queryWrapper = new LambdaQueryWrapper<>();
        queryWrapper.last("where (SELECT current_timestamp()) BETWEEN testpaper.start_time AND testpaper.dead_time");
        Page<Testpaper> testpaperPage = testPaperMapper.selectPage(page, queryWrapper);
        LinkedList<TestpaperVo> testpaperVos = new LinkedList<>();
        for (Testpaper record : testpaperPage.getRecords()) {
            testpaperVos.add(copyTestpaperBean(new TestpaperVo(), record));
        }
        return testpaperVos;
    }

    @Override
    public List<TestpaperVo> findAllHistoricalExam() {
        LambdaQueryWrapper<Testpaper> wrapper = new LambdaQueryWrapper<>();
        wrapper.last("where (SELECT current_timestamp()) > testpaper.dead_time");
        List<Testpaper> testpaperList = testPaperMapper.selectList(wrapper);
        //将查询对象转为交互返回对象
        LinkedList<TestpaperVo> testpaperVos = new LinkedList<>();
        //遍历查询结果，并将符合条件的存入交互对象中
        for (Testpaper testpaper : testpaperList) {
            testpaperVos.add(copyTestpaperBean(new TestpaperVo(), testpaper));
        }
        return testpaperVos;
    }

    @Override
    public List<TestpaperVo> findHistoricalExam(Integer testPaperId, String testPaperName, String departmentName, String subject) {
        return null;
    }

    @Override
    public List<TestpaperVo> findAllFutureExam() {
        LambdaQueryWrapper<Testpaper> wrapper = new LambdaQueryWrapper<>();
        wrapper.last("where testpaper.start_time > (SELECT current_timestamp())");
        List<Testpaper> testpaperList = testPaperMapper.selectList(wrapper);
        //将查询对象转为交互返回对象
        LinkedList<TestpaperVo> testpaperVos = new LinkedList<>();
        //遍历查询结果，并将符合条件的存入交互对象中
        for (Testpaper testpaper : testpaperList) {
            testpaperVos.add(copyTestpaperBean(new TestpaperVo(), testpaper));
        }
        return testpaperVos;
    }

    @Override
    public List<TestpaperVo> findFutureExam(Integer testPaperId, String testPaperName, String departmentName, String subject) {
        return null;
    }

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
        testpaperVo.setDepartment(departmentMapper.selectById(testpaper.getDepartmentId()).getName());
        testpaperVo.setSubject(subjectMapper.selectById(testpaper.getSubjectId()).getName());
        //testpaperVo.setUserName(userMapper.selectById(testpaper.getUserId()).getName());
        return testpaperVo;

    }
}
