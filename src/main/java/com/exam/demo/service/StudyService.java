package com.exam.demo.service;

import com.exam.demo.entity.course.Study;

import java.util.List;

public interface StudyService {

    public List<Study> findAll() ;
    public List<Study> findByType(int datatype);
    public List<Study> findPage(int current, int pageSize);

    public int delete (int study_id );

    public int insert(Study study);
    public int update(Study study);
}
