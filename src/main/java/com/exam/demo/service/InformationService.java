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
    PageVo<InformationAllVo> searchAll(Integer userId,Integer currentPage, Integer pageSize);
    int insert(Information information);
    double findTime(Integer dataId);
}
