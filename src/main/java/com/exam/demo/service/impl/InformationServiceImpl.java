package com.exam.demo.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.toolkit.StringUtils;
import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.exam.demo.entity.Information;
import com.exam.demo.entity.Study;
import com.exam.demo.entity.User;
import com.exam.demo.mapper.*;
import com.exam.demo.results.WebResult;
import com.exam.demo.results.vo.InformationAllVo;
import com.exam.demo.results.vo.InformationInVo;
import com.exam.demo.results.vo.InformationVo;
import com.exam.demo.results.vo.PageVo;
import com.exam.demo.service.InformationService;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.HashMap;
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
        for (Information info : informationList){
            InformationVo informationVo1 = copy(informationVo,info);
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
        Double totalTime = 0.0;
        List<Information> informationList = informationMapper.selectList(lambdaQueryWrapper);
        if (!informationList.isEmpty()) {
            List<LinkedHashMap<String, Object>> value = new LinkedList<>();
            for (Information information : informationList) {
                LinkedHashMap<String, Object> map = new LinkedHashMap<>();
                map.put("subject", subjectMapper.selectById(information.getSubjectId()).getName());
                map.put("name", studyMapper.selectById(information.getDataId()).getName());
                map.put("beizhu", studyMapper.selectById(information.getDataId()).getBeizhu());
                map.put("time", studyMapper.selectById(information.getDataId()).getTime());
                map.put("studyTime", information.getStudyTime());
                totalTime += information.getStudyTime();
                map.put("process", information.getProcess());
                value.add(map);
            }
            informationAllVo.setTotalTime(totalTime);
            informationAllVo.setValue(value);
        }

        return informationAllVo;
    }

    @Override
    public int insert(Information information) {

        return informationMapper.insert(information);
    }

    @Override
    public double findTime(Integer dataId) {
        User user = userMapper.selectById(dataId);
        return user.getTime();
    }

    private InformationVo copy(InformationVo informationVo,Information information){
        BeanUtils.copyProperties(information,informationVo);
        int userId = information.getUserId();
        if (userId != 0){
            information.setUserId(userId);
            informationVo.setIdentity(userMapper.selectById(userId).getIdentity());
            informationVo.setTotalTime(userMapper.selectById(userId).getTime());
        }
        return informationVo;
    }
    
    private InformationInVo copy(Information information){
        InformationInVo informationInVo = new InformationInVo();
        BeanUtils.copyProperties(information,informationInVo);
        Integer subjectId = information.getSubjectId();
        Integer typeId = information.getTypeId();
        Integer dataId = information.getDataId();
        double studyTime = information.getStudyTime();
        int process = information.getProcess();
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
        double totalTime = information.getTotalTime();
        double studyTime = information.getStudyTime();
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
