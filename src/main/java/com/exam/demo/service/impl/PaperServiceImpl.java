package com.exam.demo.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.exam.demo.entity.Paper;
import com.exam.demo.mapper.DatatypeMapper;
import com.exam.demo.mapper.PaperMapper;
import com.exam.demo.service.PaperService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * @Author: csx
 * @Date: 2022/2/28
 */
@Service
public class PaperServiceImpl implements PaperService {
    @Autowired
    private PaperMapper paperMapper;

    @Override
    public int add(Paper paper) {
        return paperMapper.insert(paper);
    }

    @Override
    public int delet(int id) {
        return paperMapper.deleteById(id);
    }

    @Override
    public List<Paper> findAll() {
        return paperMapper.selectList(new LambdaQueryWrapper<>());

    }
}
