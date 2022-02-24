package com.exam.demo.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.exam.demo.entity.Testpaper;
import com.exam.demo.mapper.DepartmentMapper;
import com.exam.demo.mapper.SubjectMapper;
import com.exam.demo.mapper.TestPaperMapper;
import com.exam.demo.mapper.UserMapper;
import com.exam.demo.otherEntity.RtTestpaper;
import com.exam.demo.service.TestPaperService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.Date;
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

}
