package com.exam.demo.service;

import com.exam.demo.entity.Subject;

import java.util.List;

/**
 * @Author: csx
 * @Date: 2022/2/28
 */
public interface SubjectService {


    //根据id查科目
    Subject findById(int id);


    /**
     * 返回所有学科
     * @return
     */
    List<Subject> findAll();
}
