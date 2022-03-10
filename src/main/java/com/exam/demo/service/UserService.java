package com.exam.demo.service;

import com.exam.demo.entity.RoleMessage;
import com.exam.demo.entity.User;
import com.exam.demo.entity.Userwx;
import com.exam.demo.otherEntity.UserPojo;
import com.exam.demo.results.vo.PageVo;
import com.exam.demo.results.vo.UserSelectVo;
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
     * @param userwx
     * @return
     */
    public UserPojo loginWx(Userwx userwx);

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
     * @param user_id
     * @return
     */
    public Boolean grantRole(Integer user_id);

    /**
     * 返回部分信息
     * @return
     */
    public List<UserPojo> findPart();
    /**
     * 根据条件查询
     */
    public PageVo<UserSelectVo> findUser(String name, String nums, String department, String address, Integer currentPage, Integer pageSize, Integer roleid) ;

    /**
     * 根据工号校验
     * @param userwx
     * @return
     */
    public UserPojo check(Userwx userwx);

    /**
     * 增加用户
     * @param user
     * @return
     */
    public String add(User user);
}
