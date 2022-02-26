package com.exam.demo.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.exam.demo.mapper.DepartmentMapper;
import com.exam.demo.mapper.PositionMapper;
import com.exam.demo.mapper.RoleMapper;
import com.exam.demo.otherEntity.UserPojo;
import org.json.JSONObject;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.exam.demo.entity.User;
import com.exam.demo.mapper.UserMapper;
import com.exam.demo.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.URL;
import java.net.URLConnection;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

/**
 * @Author: gaoyk
 * @Date: 2022/2/3 20:23
 */
@Service
public class UserServiceImpl implements UserService {

    @Autowired
    private UserMapper userMapper;

    @Autowired
    private RoleMapper roleMapper;

    @Autowired
    private DepartmentMapper departmentMapper;

    @Autowired
    private PositionMapper positionMapper;


    /**
     * 微信小程序登录服务层
     *
     * @param user
     * @return
     */
    @Override
    public User loginWx(User user) {
        String s = UserServiceImpl.sendGet("https://api.weixin.qq.com/sns/jscode2session?appid=wxfe52cfbbf230d5ee&secret=3b61d56a43ce72243813b1f71b017c90&js_code=" + user.getOpenid() + "&grant_type=authorization_code", "");
        JSONObject json = new JSONObject(s);
        System.err.println(s);
        QueryWrapper<User> wrapper = new QueryWrapper<>();
        wrapper.eq("openid",json.getString("openid"));
//        wrapper.eq("openid","123456");
        User p = userMapper.selectOne(wrapper);
        if(p != null){
            User q = p;
            q.setImage(user.getImage());
            q.setWxname(user.getWxname());
            q.setGender(user.getGender());
            userMapper.updateById(q);
            return q;
        }else{
            User now = new User(user.getGender(), json.getString("openid"), 2, user.getImage(), user.getWxname(), 0, "123456");
//            User now = new User(user.getGender(), "123456", 2, user.getImage(), user.getWxname(), 0, "123456");
            userMapper.insert(now);
            return now;
        }
    }

    /**
     * Web登录服务层
     *
     * @param user
     * @return
     */
    @Override
    public User loginWeb(User user) {
        QueryWrapper<User> wrapper = new QueryWrapper<>();
        wrapper.eq("name",user.getName());
        User p = userMapper.selectOne(wrapper);

        if (p != null){
            if (p.getPassword().equals(user.getPassword())){
                return p;
            }else {
                return null;
            }
        }
        return null;
    }

    /**
     * 修改用户信息
     *
     * @param user
     * @return
     */
    @Override
    public Boolean updateUser(User user) {
        if(userMapper.updateById(user) == 1){
            return true;
        }
        return false;
    }

    /**
     * 返回所有用户
     *
     * @return
     */
    @Override
    public List<User> findAll() {
        return userMapper.selectList(new LambdaQueryWrapper<>());
    }

    /**
     * 根据Id删除用户
     *
     * @param id
     * @return
     */
    @Override
    public Integer deleteById(Integer id) {
        return userMapper.deleteById(id);
    }

    /**
     * 给用户授权
     *
     * @param role_id
     * @param user_id
     * @return
     */
    @Override
    public Boolean grantRole(Integer user_id) {
        User user = userMapper.selectById(user_id);
        if (user.getRoleId() == 1){
            user.setRoleId(2);
        }else{
            user.setRoleId(1);
        }
        if(userMapper.updateById(user) == 1){
            return true;
        }
        return false;
    }

    /**
     * 返回部分信息
     *
     * @return
     */
    @Override
    public List<UserPojo> findPart() {
        List<User> users = userMapper.selectList(new LambdaQueryWrapper<>());
        List<UserPojo> userPojos = new ArrayList<>();
        for (User x : users) {
            UserPojo userPojo = new UserPojo();
            userPojo.setId(x.getId());
            userPojo.setName(x.getName());
            userPojo.setGender(x.getGender());
            userPojo.setRole(roleMapper.selectById(x.getRoleId()).getName());
            userPojo.setDepartment(departmentMapper.selectById(x.getDepartmentId()).getName());
            userPojo.setPosition(positionMapper.selectById(x.getPositionId()).getName());
            userPojo.setAddress(x.getAddress());
            userPojo.setEmail(x.getEmail());
            userPojo.setTele(x.getTele());
            userPojo.setTime(x.getTime());
            userPojo.setWxname(x.getWxname());
            userPojo.setNums(x.getNums());
            userPojos.add(userPojo);
        }
        return userPojos;
    }

    public static String sendGet(String url, String param) {
        String result = "";
        BufferedReader in = null;
        try {
            String urlNameString = url + "?" + param;
            URL realUrl = new URL(urlNameString);

            // 打开和URL之间的连接
            URLConnection connection = realUrl.openConnection();

            // 设置通用的请求属性
            connection.setRequestProperty("accept", "*/*");
            connection.setRequestProperty("connection", "Keep-Alive");
            connection.setRequestProperty("user-agent", "Mozilla/4.0 (compatible; MSIE 6.0; Windows NT 5.1;SV1)");

            // 建立实际的连接
            connection.connect();

            // 获取所有响应头字段
            Map<String, List<String>> map = connection.getHeaderFields();

            // 遍历所有的响应头字段
            for (String key : map.keySet()) {
                System.out.println(key + "--->" + map.get(key));
            }

            // 定义 BufferedReader输入流来读取URL的响应
            in = new BufferedReader(new InputStreamReader(
                    connection.getInputStream()));
            String line;
            while ((line = in.readLine()) != null) {
                result += line;
            }
        } catch (Exception e) {
            System.out.println("发送GET请求出现异常！" + e);
            e.printStackTrace();
        }

        // 使用finally块来关闭输入流
        finally {
            try {
                if (in != null) {
                    in.close();
                }
            } catch (Exception e2) {
                e2.printStackTrace();
            }
        }
        return result;
    }
}
