package com.exam.demo.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.exam.demo.entity.Datatype;
import com.exam.demo.entity.Department;
import com.exam.demo.mapper.DatatypeMapper;
import com.exam.demo.mapper.DepartmentMapper;
import com.exam.demo.service.DataTypeService;
import com.exam.demo.service.DepartmentService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class DepartmentServiceImpl implements DepartmentService {
    @Autowired
    private DepartmentMapper departmentMapper;

    @Override
    public Department findById(int id) {
        LambdaQueryWrapper<Datatype> queryWrapper=new LambdaQueryWrapper<>();
        queryWrapper.eq(Datatype::getId,id);
        return departmentMapper.selectById(id);
    }
}
