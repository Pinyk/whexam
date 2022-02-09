package com.exam.demo.service;

import com.exam.demo.entity.User;
import io.swagger.models.auth.In;

import java.util.List;

/**
 * @Author: gaoyk
 * @Date: 2022/2/3 20:21
 * 用户服务层接口
 */
public interface UserService {
    /**
     * 微信小程序登录服务层
     * @param user
     * @return
     */
    public User loginWx(User user);

    /**
     * Web登录服务层
     * @param user
     * @return
     */
    public User loginWeb(User user);

    /**
     * 修改用户信息
     * @param user
     * @return
     */
    public Boolean updateUser(User user);


    /**
     * 返回所有用户
     * @return
     */
    public List<User> findAll();


    /**
     * 根据Id删除用户
     * @return
     */
    public Integer deleteById(Integer id);

    /**
     * 给用户授权
     * @param role_id
     * @param user_id
     * @return
     */
    public Boolean grantRole(Integer role_id, Integer user_id);
}
