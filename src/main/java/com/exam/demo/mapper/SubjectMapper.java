package com.exam.demo.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.exam.demo.entity.Subject;
import com.exam.demo.entity.SubjectType;
import org.springframework.stereotype.Repository;

@Repository
public interface SubjectMapper extends BaseMapper<Subject> {
}
