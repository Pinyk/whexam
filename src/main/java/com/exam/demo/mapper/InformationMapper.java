package com.exam.demo.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.exam.demo.entity.Information;
import org.springframework.stereotype.Repository;

/**
 * @Author: wxn
 * @Date: 2022/3/13
 */
@Repository
public interface InformationMapper extends BaseMapper<Information> {

//    int update(Integer userId,Integer dataId,Integer totalTime,Integer studyTime);
}
