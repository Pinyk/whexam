package com.exam.demo.service;

import com.exam.demo.results.vo.*;

/**
 * @Author: wxn
 * @Date: 2022/3/13
 */
public interface InformationService {

    PageVo<InformationVo> search(String nums,String username,Integer departmentId,Integer currentPage, Integer pageSize);

    InformationInVo searchIn(Integer userId);
//
//    /**
//     * 根据用户Id导出学习时长
//     * @param userId
//     * @return
//     */
    InformationAllVo getStudyDurationByUserId(Integer userId);

    Integer addNewStudyRecord(Integer userId, Integer dataId, double studyTime);
}