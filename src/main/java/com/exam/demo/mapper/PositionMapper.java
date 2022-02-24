package com.exam.demo.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.exam.demo.entity.Department;
import com.exam.demo.entity.Position;
import org.springframework.stereotype.Repository;

/**
 * @Author: gaoyk
 * @Date: 2022/2/24 17:35
 */
@Repository
public interface PositionMapper extends BaseMapper<Position> {
}
