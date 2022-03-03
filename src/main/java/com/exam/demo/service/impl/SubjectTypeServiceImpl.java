package com.exam.demo.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;

import com.exam.demo.entity.SubjectType;
import com.exam.demo.mapper.SubjectTypeMapper;
import com.exam.demo.service.SubjectTypeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class SubjectTypeServiceImpl implements SubjectTypeService {
    @Autowired
    private SubjectTypeMapper subjectTypeMapper;

    @Override
    public SubjectType findById(int id) {
        LambdaQueryWrapper<SubjectType> queryWrapper=new LambdaQueryWrapper<>();
        queryWrapper.eq(SubjectType::getId,id);
        return subjectTypeMapper.selectById(id);
    }

    @Override
    public List<SubjectType> findAll() {
        return subjectTypeMapper.selectList(new LambdaQueryWrapper<>());
    }

    @Override
    public int add(SubjectType subjectType) {
        return subjectTypeMapper.insert(subjectType);
    }

    @Override
    public int deletById(int id) {

        return subjectTypeMapper.deleteById(id);
    }

}
