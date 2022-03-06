package com.exam.demo.service;

import com.exam.demo.entity.Datatype;

import java.util.List;

public interface DataTypeService {

    /**
     * @Author: csx
     * @Date: 2022/1/23
     */


    /**
     * 根据id查询
     * @return
     */
    Datatype findById(int id);

    int add (Datatype datatype);

    int deleteById(int id);

    //查询所有
    List<Datatype> findAll();


}
