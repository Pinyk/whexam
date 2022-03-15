package com.exam.demo.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.toolkit.StringUtils;
import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.exam.demo.entity.Information;
import com.exam.demo.entity.Study;
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
    public PageVo<InformationVo> search(String nums, String username, String department, Integer currentPage, Integer pageSize) {
        Page<Information> page = new Page<>(currentPage, pageSize);
        LambdaQueryWrapper<Information> informationWrapper = new LambdaQueryWrapper<>();
        if (!StringUtils.isBlank(username)){
            LambdaQueryWrapper<User> userWrapper = Wrappers.lambdaQuery(User.class);
            userWrapper
                    .select(User::getId)
                    .like(User::getName,username);
            List<User> users = userMapper.selectList(userWrapper);
            if (!users.isEmpty()){
                LinkedList<Integer> userIds = new LinkedList<>();
                for (User user : users){
//                    System.out.println("=============="+user.getId());
                    userIds.add(user.getId());
                }
                informationWrapper.in(Information::getUserId, userIds);
            }
        }
        LinkedList<InformationVo> informationVos = new LinkedList<>();
        Page<Information> informationPage = informationMapper.selectPage(page,informationWrapper);
        List<Information> informationList = informationPage.getRecords();
        InformationVo informationVo = new InformationVo();
        informationVo.setUsername(username);
        informationVo.setDepartment(department);
        informationVo.setNums(nums);
        int totalTime = 0;
        if (!informationList.isEmpty()){
            for (Information info : informationList){
                LinkedHashMap<String, Object> map = new LinkedHashMap<>();
                map.put("studyTime", info.getStudyTime());
                int iST = Integer.parseInt(info.getStudyTime());
                totalTime += iST;
            }
        }
        for (Information info : informationList){
            InformationVo informationVo1  = copy(informationVo,info);
            int xS = totalTime / 60;
            int fZ = totalTime % 60;
            String totalTime1 = null;
            if (xS != 0 && fZ != 0){
                totalTime1 = xS+"小时"+fZ+"分钟";
            }else if (xS !=0 && fZ ==0){
                totalTime1 = xS+"小时";
            }else {
                totalTime1 = fZ+"分钟";
            }
//            String totalTime1 = String.valueOf(totalTime);
            informationVo1.setTotalTime(totalTime1);
            informationVos.add(informationVo1);
        }
        return PageVo.<InformationVo>builder()
                .page(currentPage)
                .total(informationPage.getTotal())
                .size(pageSize)
                .values(informationVos)
                .build();
    }

    @Override
    public PageVo<InformationInVo> searchIn(Integer userId, Integer currentPage, Integer pageSize) {
        LambdaQueryWrapper<Information> queryWrapper = new LambdaQueryWrapper<>();
        Page<Information> page = new Page<>(currentPage,pageSize);
        queryWrapper
                .select(Information::getSubjectId,Information::getTypeId,Information::
                        getDataId,Information::getStudyTime,Information::getProcess)
                .like(Information::getUserId,userId);
        List<Information> informations = informationMapper.selectList(queryWrapper);
        LinkedList<Integer> userIds = new LinkedList<>();
        for (Information info : informations){
            userIds.add(info.getSubjectId());
            userIds.add(info.getTypeId());
            userIds.add(info.getDataId());
        }
        queryWrapper.in(Information::getUserId,userIds);
        LinkedList<InformationInVo> informationInVos = new LinkedList<>();
        Page<Information> informationPage = informationMapper.selectPage(page,queryWrapper);
        List<Information> informationList = informationPage.getRecords();
        for (Information in : informationList){
            InformationInVo informationInVo = copy(in);
            informationInVos.add(informationInVo);
        }
        return PageVo.<InformationInVo>builder()
                .values(informationInVos)
                .page(currentPage)
                .size(pageSize)
                .total(informationPage.getTotal())
                .build();
    }

    @Override
    public InformationAllVo getStudyDurationByUserId(Integer userId) {
        LambdaQueryWrapper<Information> lambdaQueryWrapper = new LambdaQueryWrapper<>();
        lambdaQueryWrapper.eq(Information::getUserId, userId);

        InformationAllVo informationAllVo = new InformationAllVo();
        User user = userMapper.selectById(userId);
        if (user != null) {
            informationAllVo.setUsername(user.getName());
            informationAllVo.setDepartment(departmentMapper.selectById(user.getDepartmentId()).getName());
            informationAllVo.setNums(user.getNums());
            informationAllVo.setIdentity(user.getIdentity());
        }
        int totalTime = 0;
        List<Information> informationList = informationMapper.selectList(lambdaQueryWrapper);
        if (!informationList.isEmpty()) {
            List<LinkedHashMap<String, Object>> value = new LinkedList<>();
            String totalTime1 = null;
            String studyTime1 = null;
            for (Information information : informationList) {
                LinkedHashMap<String, Object> map = new LinkedHashMap<>();
                map.put("subject", subjectMapper.selectById(information.getSubjectId()).getName());
                map.put("name", studyMapper.selectById(information.getDataId()).getName());
                map.put("beizhu", studyMapper.selectById(information.getDataId()).getBeizhu());
                map.put("time", studyMapper.selectById(information.getDataId()).getTime());
                int iST = Integer.parseInt(information.getStudyTime());
                int xSS = iST / 60;
                int fZS = iST % 60;
                if (xSS != 0 && fZS != 0){
                    studyTime1 = xSS+"小时"+fZS+"分钟";
                }else if (xSS !=0 && fZS ==0){
                    studyTime1 = xSS+"小时";
                }else {
                    studyTime1 = fZS+"分钟";
                }
                map.put("studyTime", studyTime1);
                totalTime += iST;
                int xS = totalTime / 60;
                int fZ = totalTime % 60;
                if (xS != 0 && fZ != 0){
                    totalTime1 = xS+"小时"+fZ+"分钟";
                }else if (xS !=0 && fZ ==0){
                    totalTime1 = xS+"小时";
                }else {
                    totalTime1 = fZ+"分钟";
                }
                int time = Integer.parseInt(studyMapper.selectById(information.getDataId()).getTime());
                int time1 = (int) (time * 0.6);
                int process = iST / time1;
                map.put("process", process);
                value.add(map);
            }
            informationAllVo.setTotalTime(totalTime1);
            informationAllVo.setValue(value);
        }

        return informationAllVo;
    }

    @Override
    public int insert(Information information) {

        return informationMapper.insert(information);
    }

    @Override
    public int update(Integer userId, Integer dataId,Integer totalTime,Integer studyTime) {

        return informationMapper.update(userId,dataId,totalTime,studyTime);
    }

    @Override
    public InfoAddVo find(Integer dataId) {
        Study study = studyMapper.selectById(dataId);
        int departmentId = study.getDepartmentid();
        int subjectId = study.getSubjectid();
        int typeId = study.getTypeid();
        String time = study.getTime();
//        System.out.println(departmentId+"========="+subjectId+"==========="+typeId+"======="+time);
        return new InfoAddVo(departmentId,subjectId,typeId,time);
    }

    @Override
    public InformationAllVo findTime(Integer userId,Integer dataId) {
        LambdaQueryWrapper<Information> lambdaQueryWrapper = new LambdaQueryWrapper<>();
        lambdaQueryWrapper.eq(Information::getUserId, userId)
                          .eq(Information::getDataId,dataId);
        int totalTime = 0;
        InformationAllVo informationAllVo = new InformationAllVo();
        List<Information> informationList = informationMapper.selectList(lambdaQueryWrapper);
        if (!informationList.isEmpty()) {
            List<LinkedHashMap<String, Object>> value = new LinkedList<>();
            for (Information information : informationList) {
                LinkedHashMap<String, Object> map = new LinkedHashMap<>();
                String studyTime = information.getStudyTime();
                map.put("studyTime", studyTime);
                int iST = 0;
                if (studyTime != null){
                    iST = Integer.parseInt(studyTime);
                }
                totalTime += iST;
                map.put("process", information.getProcess());
                value.add(map);
            }
            String totalTime1 = String.valueOf(totalTime);
            informationAllVo.setTotalTime(totalTime1);
            informationAllVo.setValue(value);
        }
        return informationAllVo;
    }

    @Override
    public String time(Integer userId) {
        LambdaQueryWrapper<Information> lambdaQueryWrapper = new LambdaQueryWrapper<>();
        lambdaQueryWrapper.eq(Information::getUserId, userId);
        int totalTime = 0;
        String totalTime1 = null;
        List<Information> informationList = informationMapper.selectList(lambdaQueryWrapper);
        if (!informationList.isEmpty()) {
            for (Information information : informationList) {
                int iST = Integer.parseInt(information.getStudyTime());
                totalTime += iST;
            }
            totalTime1 = String.valueOf(totalTime);
        }
        return totalTime1;
    }

    private InformationVo copy(InformationVo informationVo,Information information){
        BeanUtils.copyProperties(information,informationVo);
        int userId = information.getUserId();
        if (userId != 0){
            information.setUserId(userId);
            informationVo.setIdentity(userMapper.selectById(userId).getIdentity());
//            informationVo.setTotalTime(userMapper.selectById(userId).getTime());
        }
        return informationVo;
    }
    
    private InformationInVo copy(Information information){
        InformationInVo informationInVo = new InformationInVo();
        BeanUtils.copyProperties(information,informationInVo);
        Integer subjectId = information.getSubjectId();
        Integer typeId = information.getTypeId();
        Integer dataId = information.getDataId();
        String studyTime = information.getStudyTime();
        int time = Integer.parseInt(studyMapper.selectById(dataId).getTime());
        if (subjectId != 0){
            informationInVo.setSubject(subjectMapper.selectById(subjectId).getName());
        }
        if (typeId != 0){
            informationInVo.setType(subjectTypeMapper.selectById(typeId).getName());
        }
        if (dataId != 0){
            informationInVo.setTime(studyMapper.selectById(dataId).getTime());
        }
        int iST = Integer.parseInt(studyTime);
        int xS = iST / 60;
        int fZ = iST % 60;
        String studyTime1 = null;
        if (xS != 0 && fZ != 0){
            studyTime1 = xS+"小时"+fZ+"分钟";
        }else if (xS !=0 && fZ ==0){
            studyTime1 = xS+"小时";
        }else {
            studyTime1 = fZ+"分钟";
        }
//        int process = studyTime / (time * 60) * 100;
        informationInVo.setStudyTime(studyTime1);
        int time1 = (int) (time * 0.6);
        int process = iST / time1;
//        System.out.println(studyTime+"======="+time+"======"+process);

        informationInVo.setProcess(process);
        return informationInVo;
    }

    private InformationAllVo copy1(Information information){
        InformationAllVo informationAllVo = new InformationAllVo();
        BeanUtils.copyProperties(information,informationAllVo);
        int userId = information.getUserId();
        Integer subjectId = information.getSubjectId();
        Integer typeId = information.getTypeId();
        Integer dataId = information.getDataId();
        Integer departmentId = information.getDepartmentId();
        String totalTime = information.getTotalTime();
        String studyTime = information.getStudyTime();
        int process = information.getProcess();
        if (userId != 0){
            informationAllVo.setUsername(userMapper.selectById(userId).getName());
            informationAllVo.setNums(userMapper.selectById(userId).getNums());
            informationAllVo.setIdentity(userMapper.selectById(userId).getIdentity());
        }
        if (departmentId != 0){
            informationAllVo.setDepartment(departmentMapper.selectById(departmentId).getName());
        }
        informationAllVo.setTotalTime(totalTime);
        InformationInVo informationInVo = new InformationInVo();
        if (subjectId != 0){
            informationInVo.setSubject(subjectMapper.selectById(subjectId).getName());
        }
        if (typeId != 0){
            informationInVo.setType(subjectTypeMapper.selectById(typeId).getName());
        }
        if (dataId != 0){
            informationInVo.setTime(studyMapper.selectById(dataId).getTime());
        }
        informationInVo.setStudyTime(studyTime);
        informationInVo.setProcess(process);
//        informationAllVo.setInformationInVo(informationInVo);
        return informationAllVo;
    }
}
