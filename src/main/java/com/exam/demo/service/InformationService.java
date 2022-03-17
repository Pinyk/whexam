package com.exam.demo.service;

import com.exam.demo.results.vo.*;

import java.util.LinkedHashMap;
import java.util.LinkedList;

/**
 * @Author: wxn
 * @Date: 2022/3/13
 */
public interface InformationService {

    PageVo<LinkedHashMap<String, Object>> search(String nums, String username, Integer departmentId, Integer currentPage, Integer pageSize);

    LinkedList<LinkedHashMap<String, Object>> searchIn(Integer userId);
//
//    /**
//     * 根据用户Id导出学习时长
//     * @param userId
//     * @return
//     */
    InformationAllVo getStudyDurationByUserId(Integer userId);

    Integer addNewStudyRecord(Integer userId, Integer dataId, double studyTime);
}