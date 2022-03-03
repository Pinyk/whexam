package com.exam.demo.service;

import com.exam.demo.entity.Subject;
import com.exam.demo.entity.SubjectType;

import java.util.List;

/**
 * @Author: csx
 * @Date: 2022/3/1
 */
public interface SubjectTypeService {

    //根据id查科目
    SubjectType findById(int id);
    // 返回所有学科
    List<SubjectType> findAll();
    //增加
    int add(SubjectType subjectType);

}
