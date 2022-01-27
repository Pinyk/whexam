package com.exam.demo.service;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.exam.demo.entity.Role;
import com.exam.demo.mapper.RoleMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
@Service
public class RoleService {
    @Autowired
    private RoleMapper roleMapper;
    public List<Role> findAll() {
        return roleMapper.selectList(new LambdaQueryWrapper<>());
    }
}
