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

    /**
     * 查询所有角色
     *
     * @return
     */
    @Override
    public List<Role> findAll() {
        return roleMapper.selectList(new LambdaQueryWrapper<>());
    }

    /**
     * 根据添加角色
     *
     * @param name
     * @return
     */
    @Override
    public Integer addRole(String name) {
        return roleMapper.insert(new Role(name));
    }


    /**
     * 根据Id查找角色
     *
     * @param id
     * @return
     */
    @Override
    public Role findById(Integer id) {
        return roleMapper.selectById(id);
    }


    /**
     * 根据Id删除角色
     *
     * @param id
     * @return
     */
    @Override
    public Integer deleteById(Integer id) {
        return roleMapper.deleteById(id);
    }
}
