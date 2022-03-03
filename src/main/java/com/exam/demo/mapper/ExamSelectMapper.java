package com.exam.demo.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.exam.demo.entity.ExamJudge;
import com.exam.demo.entity.ExamSelect;
import org.apache.ibatis.annotations.Select;
import org.springframework.stereotype.Repository;

@Repository
public interface ExamSelectMapper extends BaseMapper<ExamSelect> {

}
