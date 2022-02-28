package com.exam.demo.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.exam.demo.entity.Datatype;
import org.apache.ibatis.annotations.Select;
import org.springframework.stereotype.Repository;
/**
 * @Author: csx
 * @Date: 2022/2/28
 */

@Repository
public interface DatatypeMapper extends BaseMapper<Datatype> {

}
