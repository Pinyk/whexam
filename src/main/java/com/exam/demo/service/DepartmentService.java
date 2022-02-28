package com.exam.demo.service;

import com.exam.demo.entity.Department;

/**
 * @Author: csx
 * @Date: 2022/2/28
 */


public interface DepartmentService {

    //根据ID查询
    Department findById(int id);
}
