package com.exam.demo.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.exam.demo.entity.RoleMessage;
import com.exam.demo.entity.User;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * @Author: gaoyk
 * @Date: 2022/1/20 20:20
 * 用户持久层
 */
@Repository
public interface UserMapper extends BaseMapper<User> {
}
