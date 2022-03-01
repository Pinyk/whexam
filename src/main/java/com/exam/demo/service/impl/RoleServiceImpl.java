package com.exam.demo.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.exam.demo.entity.Power;
import com.exam.demo.entity.Role;
import com.exam.demo.mapper.RoleMapper;
import com.exam.demo.mapper.UserMapper;
import com.exam.demo.service.RoleService;
import io.swagger.annotations.ApiOperation;
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
    private UserMapper userMapper;
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

    /**
     * 通过部分条件查找角色
     * @param nums
     * @param name
     * @param department
     * @param rolename
     * @return
     */
    @Override
    public List<Power> findRole(String nums, String name, String department, String rolename) {
        Integer temp1;
        Integer temp2;
        temp1=userMapper.findbydepartment(department);
        temp2=roleMapper.findByRoleName(name);
        if(temp1==null&&department.length()!=0)
            temp1=-2;
        if(temp2==null&&rolename.length()!=0)
            temp2=-2;
        if(name.length()==0)
            name=null;
        if(nums.length()==0)
            nums=null;
        if(department.length()==0||department==null||temp1==null){
            temp1=-1;
        }
        if(rolename.length()==0||rolename==null||temp2==null){
            temp2=-1;
        }

        return roleMapper.findRole(nums,name,temp1,temp2);
    }

    /**
     * 通过rolename查找id
     * @param name
     * @return
     */
    @Override
    public Integer findByRoleName(String name) {
        return roleMapper.findByRoleName(name);
    }
}

