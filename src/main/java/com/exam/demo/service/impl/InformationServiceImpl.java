package com.exam.demo.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.exam.demo.entity.Information;
import com.exam.demo.entity.User;
import com.exam.demo.mapper.*;
import com.exam.demo.results.vo.*;
import com.exam.demo.service.InformationService;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.LinkedHashMap;
import java.util.LinkedList;
import java.util.List;

/**
 * @Author: wxn
 * @Date: 2022/3/13
 */
@Service
public class InformationServiceImpl implements InformationService {
    @Autowired
    private InformationMapper informationMapper;

    @Autowired
    private UserMapper userMapper;

    @Autowired
    private StudyMapper studyMapper;

    @Autowired
    private SubjectMapper subjectMapper;

    @Autowired
    private SubjectTypeMapper subjectTypeMapper;

    @Autowired
    private DepartmentMapper departmentMapper;

    @Override
    public Integer addNewStudyRecord(Integer userId, Integer dataId, double studyTime) {

        //判断是否存在该条学习记录
        LambdaQueryWrapper<Information> lambdaQueryWrapper = Wrappers.lambdaQuery(Information.class);
        lambdaQueryWrapper.eq(Information::getUserId, userId).eq(Information::getDataId, dataId);
        Information information = informationMapper.selectOne(lambdaQueryWrapper);
        Information newInformationRecord = new Information();
        //查询该用户并修改学习时长
        LambdaQueryWrapper<User> userLambdaQueryWrapper = Wrappers.lambdaQuery(User.class);
        userLambdaQueryWrapper.eq(User::getId,userId);
        User user = userMapper.selectOne(userLambdaQueryWrapper);
        User newUserRecord = new User();
        if (information != null) { //存在  更新study_time逻辑
            newInformationRecord.setStudyTime(information.getStudyTime() + studyTime);
            newUserRecord.setTime(user.getTime()+studyTime);
            informationMapper.update(newInformationRecord, lambdaQueryWrapper);
            userMapper.update(newUserRecord,userLambdaQueryWrapper);
        } else { //不存在 插入逻辑
            newInformationRecord.setUserId(userId);
            newInformationRecord.setDataId(dataId);
            newInformationRecord.setStudyTime(studyTime);
            informationMapper.insert(newInformationRecord);
        }
        return 1;
    }

    @Override
    public PageVo<LinkedHashMap<String, Object>> search(String nums, String username, Integer departmentId, Integer currentPage, Integer pageSize) {
        Page<User> userPage = new Page<>(currentPage, pageSize);
        LambdaQueryWrapper<User> userLambdaQueryWrapper = Wrappers.lambdaQuery(User.class);
        userLambdaQueryWrapper.select(User::getName,User::getNums,User::getDepartmentId,User::getIdentity,User::getTime);
        if (nums != null && !nums.isEmpty()) {
            userLambdaQueryWrapper.like(User::getNums, nums);
        }
        if (username != null && !username.isEmpty()) {
            userLambdaQueryWrapper.like(User::getName,username);
        }
        if (departmentId != null) {
            userLambdaQueryWrapper.eq(User::getDepartmentId, departmentId);
        }
        Page<User> page = userMapper.selectPage(userPage, userLambdaQueryWrapper);
        LinkedList<LinkedHashMap<String, Object>> linkedHashMaps = new LinkedList<>();
        if (!page.getRecords().isEmpty()) {
            for (User record : page.getRecords()) {
                LinkedHashMap<String, Object> map = new LinkedHashMap<>();
                map.put("userName", record.getName());
                map.put("department", departmentMapper.selectById(record.getDepartmentId()).getName());
                map.put("nums", record.getNums());
                map.put("identity", record.getIdentity());
                double time = record.getTime();
                String totalTime = null;
                if (time != 0) {
                    int xS = (int) (time / 60);
                    double fZ = time % 60;
                    if (xS != 0 && fZ != 0) {
                        totalTime = xS + "小时" + fZ + "分钟";
                    } else if (xS != 0 && fZ == 0) {
                        totalTime = xS + "小时";
                    } else {
                        totalTime = fZ + "分钟";
                    }
                }
                map.put("totalTime", totalTime);
                linkedHashMaps.push(map);
            }
        }
        return PageVo.<LinkedHashMap<String, Object>>builder()
                .page(currentPage)
                .size(pageSize)
                .total(page.getTotal())
                .values(linkedHashMaps)
                .build();
    }

    private InformationVo copy(Information information) {
        InformationVo informationVo = new InformationVo();
        BeanUtils.copyProperties(information, informationVo);
        int userId = informationVo.getUserId();
        if (userId != 0) {
            informationVo.setIdentity(userMapper.selectById(userId).getIdentity());
            String department = departmentMapper.selectById(userMapper.selectById(userId).getDepartmentId()).getName();
            if (department != null) {
                informationVo.setDepartment(department);
            }
            double totalTime = userMapper.selectById(userId).getTime();
            String totalTime1 = null;
            if (totalTime != 0) {
                double xS = totalTime / 60;
                double fZ = totalTime % 60;
                if (xS != 0 && fZ != 0) {
                    totalTime1 = xS + "小时" + fZ + "分钟";
                } else if (xS != 0 && fZ == 0) {
                    totalTime1 = xS + "小时";
                } else {
                    totalTime1 = fZ + "分钟";
                }
            }
            informationVo.setTotalTime(totalTime1);
        }
        return informationVo;
    }

    @Override
    public LinkedList<LinkedHashMap<String, Object>> searchIn(Integer userId) {
        LambdaQueryWrapper<Information> queryWrapper = new LambdaQueryWrapper<>();
        queryWrapper
                .eq(Information::getUserId, userId);
//        LinkedList<Integer> userIds = new LinkedList<>();
//        userIds.add(userId);
//        queryWrapper.in(Information::getUserId, userIds);
        InformationInVo informationInVo = new InformationInVo();
        List<Information> informationList = informationMapper.selectList(queryWrapper);
        LinkedList<LinkedHashMap<String, Object>> linkedHashMaps = new LinkedList<>();
        if (!informationList.isEmpty()) {
            String studyTime;
            for (Information information : informationList) {
                LinkedHashMap<String, Object> map = new LinkedHashMap<>();
                String subject = subjectMapper.selectById(studyMapper.selectById(information.getDataId()).getSubjectid()).getName();
                String time = studyMapper.selectById(information.getDataId()).getTime();
                String name = subjectTypeMapper.selectById(studyMapper.selectById(information.getDataId()).getTypeid()).getName();
                double xS = Double.parseDouble(time);
                double sTime = information.getStudyTime();
                int xSS = (int) (sTime / 60);
                double fZS = sTime % 60;
                if (xSS != 0 && fZS != 0) {
                studyTime = xSS + "小时" + fZS + "分钟";
                } else if (xSS != 0 && fZS == 0) {
                studyTime = xSS + "小时";
                } else {
                studyTime = fZS + "分钟";
                }
                map.put("subject", subject);
                map.put("name", name);
                map.put("beizhu", studyMapper.selectById(information.getDataId()).getBeizhu());
                map.put("studyTime", studyTime);
                int process = (int) (sTime * 100 / xS);
                if (process >= 100){
                process = 100;
                }
                map.put("process", process);
                linkedHashMaps.push(map);
            }
        }
        return linkedHashMaps;
    }
    @Override
    public InformationAllVo getStudyDurationByUserId(Integer userId) {
        LambdaQueryWrapper<Information> lambdaQueryWrapper = new LambdaQueryWrapper<>();
        lambdaQueryWrapper
                .eq(Information::getUserId, userId);
        InformationAllVo informationAllVo = new InformationAllVo();
        User user = userMapper.selectById(userId);
        String totalTime;
        if (user != null) {
            informationAllVo.setUsername(user.getName());
            informationAllVo.setDepartment(departmentMapper.selectById(user.getDepartmentId()).getName());
            informationAllVo.setNums(user.getNums());
            informationAllVo.setIdentity(user.getIdentity());
            double time = user.getTime();
            int xS = (int) (time / 60);
            double fZ = time % 60;
            if (xS != 0 && fZ != 0){
                totalTime = xS+"小时"+fZ+"分钟";
            }else if (xS !=0 && fZ ==0){
                totalTime = xS+"小时";
            }else {
                totalTime = fZ+"分钟";
            }
            informationAllVo.setTotalTime(totalTime);
        }
        List<Information> informationList = informationMapper.selectList(lambdaQueryWrapper);
        if (!informationList.isEmpty()) {
            List<LinkedHashMap<String, Object>> value = new LinkedList<>();
            String studyTime;
            for (Information information : informationList) {
                LinkedHashMap<String, Object> map = new LinkedHashMap<>();
                String subject = subjectMapper.selectById(studyMapper.selectById(information.getDataId()).getSubjectid()).getName();
                String name = subjectTypeMapper.selectById(studyMapper.selectById(information.getDataId()).getTypeid()).getName();
                String time = studyMapper.selectById(information.getDataId()).getTime();
                double xS = Double.parseDouble(time);
                double sTime = information.getStudyTime();
                int xSS = (int) (sTime / 60);
                double fZS = sTime % 60;
                if (xSS != 0 && fZS != 0) {
                    studyTime = xSS + "小时" + fZS + "分钟";
                } else if (xSS != 0 && fZS == 0) {
                    studyTime = xSS + "小时";
                } else {
                    studyTime = fZS + "分钟";
                }
                map.put("subject", subject);
                map.put("name", name);
                map.put("beizhu", studyMapper.selectById(information.getDataId()).getBeizhu());
                map.put("studyTime", studyTime);
                int process = (int) (sTime * 100 / xS);
                if (process >= 100){
                    process = 100;
                }
                map.put("process", process);
                value.add(map);
            }
            informationAllVo.setValue(value);
        }
        return informationAllVo;
    }
}
