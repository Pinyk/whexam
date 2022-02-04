package com.exam.demo.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.exam.demo.entity.TestPaper;
import com.exam.demo.mapper.TestPaperMapper;
import com.exam.demo.service.TestPaperService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

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
    public List<TestPaper> findAll() {
        return testPaperMapper.selectList(new LambdaQueryWrapper<>());
    }

    /**
     * 查询正在进行的考试
     * @return
     */
    @Override
    public List<TestPaper> findTesting() {
        QueryWrapper<TestPaper> queryWrapper = new QueryWrapper<>();
        queryWrapper
                .gt("startTime", new Date())
                .lt("deadTime", new Date());
        return testPaperMapper.selectList(queryWrapper);
    }

    /**
     * 查询历史考试
     * @return
     */
    @Override
    public List<TestPaper> findTested() {
        QueryWrapper<TestPaper> queryWrapper = new QueryWrapper<>();
        queryWrapper.gt("deadTime",new Date());
        return testPaperMapper.selectList(queryWrapper);
    }

    /**
     * 增加试卷头信息
     * @param testPaper
     */
    @Override
    public Integer addTestPaper(TestPaper testPaper) {
        return testPaperMapper.insert(testPaper);
    }
}
