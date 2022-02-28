package com.exam.demo.service;

import com.exam.demo.entity.Paper;

import java.util.List;

public interface PaperService {
    /**
     * @Author: csx
     * @Date: 2022/1/23
     */


    /**
     * 新增
     * @return
     */
    int add(Paper paper);
    int delet(int id);
    List<Paper> findAll();
}
