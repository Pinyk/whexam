package com.exam.demo.service;

import com.exam.demo.entity.Subject;

/**
 * @Author: csx
 * @Date: 2022/2/28
 */
public interface SubjectService {


    //根据id查科目
    Subject findById(int id);
}
