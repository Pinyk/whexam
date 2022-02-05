package com.exam.demo.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.exam.demo.entity.Role;
import com.exam.demo.entity.User;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * @Author: gaoyk
 * @Date: 2022/1/19 13:38
 */
@Repository
public interface RoleMapper extends BaseMapper<Role> {


//    public List<User> findUserGtAge(int age);
}
