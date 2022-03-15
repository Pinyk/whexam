package com.exam.demo.service;

import com.exam.demo.entity.Information;
import com.exam.demo.results.vo.*;

/**
 * @Author: wxn
 * @Date: 2022/3/13
 */
public interface InformationService {

    PageVo<InformationVo> search(String nums,String username,String department,Integer currentPage, Integer pageSize);

    PageVo<InformationInVo> searchIn(Integer userId,Integer currentPage, Integer pageSize);

    /**
     * 根据用户Id导出学习时长
     * @param userId
     * @return
     */
    InformationAllVo getStudyDurationByUserId(Integer userId);

    int insert(Information information);

    int update(Integer userId,Integer dataId,Integer totalTime,Integer studyTime);

    InfoAddVo find(Integer dataId);

    InformationAllVo findTime(Integer userId,Integer dataId);

    String time(Integer userId);

    /**
     * 更新课程信息接口
     * @param userId
     * @param dataId
     * @param studyTime
     * @return
     */
    Integer addNewStudyRecord(Integer userId, Integer dataId, Integer studyTime);
}