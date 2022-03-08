package com.exam.demo.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.toolkit.StringUtils;
import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.exam.demo.entity.Department;
import com.exam.demo.entity.User;
import com.exam.demo.mapper.DepartmentMapper;
import com.exam.demo.results.vo.PageVo;
import com.exam.demo.results.vo.PowerVo;
import com.exam.demo.entity.Role;
import com.exam.demo.mapper.RoleMapper;
import com.exam.demo.mapper.UserMapper;
import com.exam.demo.service.RoleService;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.LinkedList;
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
    @Autowired
    private DepartmentMapper departmentMapper;

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
     * @param
     * @return
     */
    @Override
    public PageVo<PowerVo> findRole(String nums, String name, String department,Integer currentPage,Integer pageSize) {
        Page<User> page = new Page<>(currentPage, pageSize);
        LambdaQueryWrapper<User> wrapperSelect = Wrappers.lambdaQuery(User.class);
        if(!name.isEmpty()){
            wrapperSelect.eq(User::getName,name);
        }
        if(!nums.isEmpty()){
            wrapperSelect.eq(User::getNums,nums);
        }
            if (!StringUtils.isBlank(department)){
            LambdaQueryWrapper<Department> departmentwrapper = Wrappers.lambdaQuery(Department.class);
            departmentwrapper.select(Department::getId).like(Department::getName,department);
            List<Department> departmentlist = departmentMapper.selectList(departmentwrapper);
            if(!departmentlist.isEmpty()){
                LinkedList<Integer> departmentids = new LinkedList<>();
                for(Department department1:departmentlist){
                    departmentids.add(department1.getId());
                }
                wrapperSelect.in(User::getDepartmentId,departmentids);
            }
        }
        LinkedList<PowerVo> userPowerVos = new LinkedList<>();
        Page<User> userSelectPage = userMapper.selectPage(page, wrapperSelect);
        List<User> userSelectList = userSelectPage.getRecords();
        for(User u : userSelectList) {
            PowerVo powerVo = copy(u);
           userPowerVos.add(powerVo);
        }
        return PageVo.<PowerVo>builder()
                .values(userPowerVos)
                .page(currentPage)
                .size(pageSize)
                .total(userSelectPage.getTotal())
                .build();
    }
    private PowerVo copy(User u){
        PowerVo powerVo = new  PowerVo();
        BeanUtils.copyProperties(u, powerVo);
        powerVo.setDepartment(departmentMapper.selectById(u.getDepartmentId()).getName());
        if(u.getRoleId()!=null)
        powerVo.setManager(u.getRoleId());
        return  powerVo;
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

