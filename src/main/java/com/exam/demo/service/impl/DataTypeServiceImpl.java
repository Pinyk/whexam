package com.exam.demo.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.exam.demo.entity.Datatype;
import com.exam.demo.entity.Study;
import com.exam.demo.mapper.DatatypeMapper;
import com.exam.demo.service.DataTypeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
@Service
public class DataTypeServiceImpl implements DataTypeService {

    /**
     * @Author: csx
     * @Date: 2022/2/28
     */
    @Autowired
    private DatatypeMapper datatypeMapper;

    @Override
    public Datatype findById(int id) {
        LambdaQueryWrapper<Datatype> queryWrapper=new LambdaQueryWrapper<>();
        queryWrapper.eq(Datatype::getId,id);
        return datatypeMapper.selectById(id);
    }

    @Override
    public int add(Datatype datatype) {
        return datatypeMapper.insert(datatype);
    }

    @Override
    public int deleteById(int id) {
        return datatypeMapper.deleteById(id);
    }
}
