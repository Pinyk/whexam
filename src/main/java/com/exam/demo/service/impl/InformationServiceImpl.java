package com.exam.demo.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.toolkit.StringUtils;
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
        if (information != null) { //存在  更新study_time逻辑
            newInformationRecord.setStudyTime(information.getStudyTime() + studyTime);
            informationMapper.update(newInformationRecord, lambdaQueryWrapper);
        } else { //不存在 插入逻辑
            newInformationRecord.setUserId(userId);
            newInformationRecord.setDataId(dataId);
            newInformationRecord.setStudyTime(studyTime);
            informationMapper.insert(newInformationRecord);
        }
        return 1;
    }

    @Override
    public PageVo<InformationVo> search(String nums, String username, Integer departmentId, Integer currentPage, Integer pageSize) {
        Page<Information> page = new Page<>(currentPage, pageSize);
        LambdaQueryWrapper<Information> informationWrapper = new LambdaQueryWrapper<>();
        if (!StringUtils.isBlank(username)) {
            LambdaQueryWrapper<User> userWrapper = Wrappers.lambdaQuery(User.class);
            userWrapper
                    .select(User::getId)
                    .eq(User::getName, username)
                    .eq(User::getNums,nums)
                    .eq(User::getDepartmentId,departmentId);
            List<User> users = userMapper.selectList(userWrapper);
            if (!users.isEmpty()) {
                LinkedList<Integer> userIds = new LinkedList<>();
                for (User user : users) {
                    userIds.add(user.getId());
                }
                informationWrapper.in(Information::getUserId, userIds);
            }
        }
        LinkedList<InformationVo> informationVos = new LinkedList<>();
        Page<Information> informationPage = informationMapper.selectPage(page, informationWrapper);
        List<Information> informationList = informationPage.getRecords();
        if (!informationList.isEmpty()) {
            for (Information info : informationList) {
                InformationVo informationVo1 = copy(info);
                if (username != null){
                    informationVo1.setUsername(username);
                }
                if (nums != null){
                    informationVo1.setNums(nums);
                }
                informationVos.add(informationVo1);
            }
        }
        return PageVo.<InformationVo>builder()
                .page(currentPage)
                .total(informationPage.getTotal())
                .size(pageSize)
                .values(informationVos)
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
    public InformationInVo searchIn(Integer userId) {
        LambdaQueryWrapper<Information> queryWrapper = new LambdaQueryWrapper<>();
        queryWrapper
                .eq(Information::getUserId, userId);
        LinkedList<Integer> userIds = new LinkedList<>();
        userIds.add(userId);
        queryWrapper.in(Information::getUserId, userIds);
        InformationInVo informationInVo = new InformationInVo();
        List<Information> informationList = informationMapper.selectList(queryWrapper);
        if (!informationList.isEmpty()) {
            String studyTime1;
            for (Information information : informationList) {
                String subject = subjectMapper.selectById(studyMapper.selectById(information.getDataId()).getSubjectid()).getName();
                if (subject != null) {
                    informationInVo.setSubject(subject);
                }
                informationInVo.setTime(studyMapper.selectById(information.getDataId()).getTime());
                informationInVo.setType(subjectTypeMapper.selectById
                        (studyMapper.selectById(information.getDataId()).getTypeid()).getName());
                double studyTime = informationMapper.selectById(userId).getStudyTime();
                int xSS = (int) (studyTime / 60);
                double fZS = studyTime % 60;
                if (xSS != 0 && fZS != 0) {
                    studyTime1 = xSS + "小时" + fZS + "分钟";
                } else if (xSS != 0 && fZS == 0) {
                    studyTime1 = xSS + "小时";
                } else {
                    studyTime1 = fZS + "分钟";
                }
                informationInVo.setStudyTime(studyTime1);
            }
        }
        return informationInVo;
    }
    //    @Override
//    public InformationAllVo getStudyDurationByUserId(Integer userId) {
//        LambdaQueryWrapper<Information> lambdaQueryWrapper = new LambdaQueryWrapper<>();
//        lambdaQueryWrapper.eq(Information::getUserId, userId);
//
//        InformationAllVo informationAllVo = new InformationAllVo();
//        User user = userMapper.selectById(userId);
//        if (user != null) {
//            informationAllVo.setUsername(user.getName());
//            informationAllVo.setDepartment(departmentMapper.selectById(user.getDepartmentId()).getName());
//            informationAllVo.setNums(user.getNums());
//            informationAllVo.setIdentity(user.getIdentity());
//        }
//        int totalTime = 0;
//        List<Information> informationList = informationMapper.selectList(lambdaQueryWrapper);
//        if (!informationList.isEmpty()) {
//            List<LinkedHashMap<String, Object>> value = new LinkedList<>();
//            String totalTime1 = null;
//            String studyTime1 = null;
//            for (Information information : informationList) {
//                LinkedHashMap<String, Object> map = new LinkedHashMap<>();
////                map.put("subject", subjectMapper.selectById(information.getSubjectId()).getName());
//                map.put("name", studyMapper.selectById(information.getDataId()).getName());
//                map.put("beizhu", studyMapper.selectById(information.getDataId()).getBeizhu());
//                map.put("time", studyMapper.selectById(information.getDataId()).getTime());
//                int iST = Integer.parseInt(information.getStudyTime());
//                int xSS = iST / 60;
//                int fZS = iST % 60;
//                if (xSS != 0 && fZS != 0){
//                    studyTime1 = xSS+"小时"+fZS+"分钟";
//                }else if (xSS !=0 && fZS ==0){
//                    studyTime1 = xSS+"小时";
//                }else {
//                    studyTime1 = fZS+"分钟";
//                }
//                map.put("studyTime", studyTime1);
//                totalTime += iST;
//                int xS = totalTime / 60;
//                int fZ = totalTime % 60;
//                if (xS != 0 && fZ != 0){
//                    totalTime1 = xS+"小时"+fZ+"分钟";
//                }else if (xS !=0 && fZ ==0){
//                    totalTime1 = xS+"小时";
//                }else {
//                    totalTime1 = fZ+"分钟";
//                }
//                int time = Integer.parseInt(studyMapper.selectById(information.getDataId()).getTime());
////                int time1 = (int) (time * 0.6);
//                int process = iST * 100 / time;
//                if (process >= 100){
//                    process = 100;
//                }
//                map.put("process", process);
//                value.add(map);
//            }
//            informationAllVo.setTotalTime(totalTime1);
//            informationAllVo.setValue(value);
//        }
//
//        return informationAllVo;
//    }
}
