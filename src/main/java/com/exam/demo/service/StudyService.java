package com.exam.demo.service;

import com.exam.demo.entity.Study;
import com.exam.demo.results.vo.PageVo;
import com.exam.demo.results.vo.StudyVo;

import java.util.List;
/**
 * @Author: csx
 * @Date: 2022/1/19 13:42
 * 学习service
 */
public interface StudyService {

    public List<Study> findAll() ;
    public List<Study> findByType(int datatype);
    public List<Study> findBySubject(int datatype);
    public PageVo<Study> findPage(int current, int pageSize);

    public int delete (int study_id );

    public int insert(Study study);
    public int update(Study study);

    public List<Study> findBySubjectType(int typeid);

    Study findById(int id);

    PageVo<StudyVo> search(String name,String beizhu,String subject,String type,Integer currentPage, Integer pageSize);

}
