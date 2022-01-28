package com.exam.demo.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.exam.demo.entity.Exam;
import com.exam.demo.entity.Score;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ExamMapper extends BaseMapper<Exam> {
}
