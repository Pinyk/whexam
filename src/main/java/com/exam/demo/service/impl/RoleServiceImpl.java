package com.exam.demo.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.exam.demo.entity.Role;
import com.exam.demo.mapper.RoleMapper;
import com.exam.demo.service.RoleService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * @Author: gaoyk
 * @Date: 2022/1/19 13:44
 * 角色service实现类
 */
@Service
public class RoleServiceImpl implements RoleService {

    @Autowired
    private RoleMapper roleMapper;

    @Override
    public List<Role> findAll() {
        return roleMapper.selectList(new LambdaQueryWrapper<>());
    }

    @Override
    public Integer addRole(String name) {
        return roleMapper.insert(new Role(name));
    }

    @Override
    public Role findById(Integer id) {
        return roleMapper.selectById(id);
    }
}
