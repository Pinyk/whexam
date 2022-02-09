package com.exam.demo.service;

import com.exam.demo.entity.Role;

import java.util.List;

/**
 * @Author: gaoyk
 * @Date: 2022/1/19 13:42
 * 角色service
 */
public interface RoleService {
    /**
     * 查询所有角色
     * @return
     */
    public List<Role> findAll();


    /**
     * 给定名字添加角色
     * @param name
     * @return
     */
    public Integer addRole(String name);

    /**
     * 根据Id查找角色
     * @param id
     * @return
     */
    public Role findById(Integer id);

    /**
     * 根据Id删除角色
     * @param id
     * @return
     */
    public Integer deleteById(Integer id);
}
