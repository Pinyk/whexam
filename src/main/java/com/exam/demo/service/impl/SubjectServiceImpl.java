package com.exam.demo.service.impl;
/**
 * @Author: csx
 * @Date: 2022/2/28
 */


import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.exam.demo.entity.Datatype;
import com.exam.demo.entity.Subject;
import com.exam.demo.mapper.DepartmentMapper;
import com.exam.demo.mapper.SubjectMapper;
import com.exam.demo.service.SubjectService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;


@Service
public class SubjectServiceImpl implements SubjectService {
    @Autowired
    private SubjectMapper subjectMapper;

    @Override
    public Subject findById(int id) {
        LambdaQueryWrapper<Datatype> queryWrapper=new LambdaQueryWrapper<>();
        queryWrapper.eq(Datatype::getId,id);
        return subjectMapper.selectById(id);
    }

    /**
     * 返回所有学科
     *
     * @return
     */
    @Override
    public List<Subject> findAll() {
        return subjectMapper.selectList(new LambdaQueryWrapper<>());
    }
}
