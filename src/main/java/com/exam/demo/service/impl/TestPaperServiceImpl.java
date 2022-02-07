package com.exam.demo.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.exam.demo.entity.Testpaper;
import com.exam.demo.mapper.TestPaperMapper;
import com.exam.demo.service.TestPaperService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.sql.Timestamp;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

@Service
public class TestPaperServiceImpl implements TestPaperService {

    @Autowired
    private TestPaperMapper testPaperMapper;

    /**
     * 查询所有试卷信息
     * @return
     */
    @Override
    public List<Testpaper> findAll() {
        return testPaperMapper.selectList(new LambdaQueryWrapper<>());
    }

    /**
     * 查询正在进行的考试
     * @return
     */
    @Override
    public List<Testpaper> findTesting() {
        QueryWrapper<Testpaper> queryWrapper = new QueryWrapper<>();
        queryWrapper
                .ge("dead_time", new Timestamp(new Date().getTime()))
                .le("start_time", new Timestamp(new Date().getTime()));
        return testPaperMapper.selectList(queryWrapper);
    }

    /**
     * 查询历史考试
     * @return
     */
    @Override
    public List<Testpaper> findTested() {
        QueryWrapper<Testpaper> queryWrapper = new QueryWrapper<>();
        queryWrapper.lt("dead_time", new Timestamp(new Date().getTime()));
        return testPaperMapper.selectList(queryWrapper);
    }

    /**
     * 查询尚未开始的考试
     * @return
     */
    @Override
    public List<Testpaper> findNotStartTest() {
        QueryWrapper<Testpaper> queryWrapper = new QueryWrapper<>();
        queryWrapper.gt("start_time", new Timestamp(new Date().getTime()));
        return testPaperMapper.selectList(queryWrapper);
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
