package com.exam.demo.service;

import com.exam.demo.entity.Information;
import com.exam.demo.results.vo.InformationAllVo;
import com.exam.demo.results.vo.InformationInVo;
import com.exam.demo.results.vo.InformationVo;
import com.exam.demo.results.vo.PageVo;

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

    double findTime(Integer dataId);
}
