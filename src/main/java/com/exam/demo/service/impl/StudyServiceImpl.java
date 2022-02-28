package com.exam.demo.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.exam.demo.entity.Study;
import com.exam.demo.mapper.StudyMapper;
import com.exam.demo.service.StudyService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
/**
 * @Author: csx
 * @Date: 2022/1/23
 */
@Service

public class StudyServiceImpl implements StudyService {
    @Autowired
    private StudyMapper studyMapperr;

    public List<Study> findAll() {
        //查询所有

        return studyMapperr.selectList(new LambdaQueryWrapper<>());

    }
    public List<Study> findByType(int datatype){
        //根据学习类型进行查询
        LambdaQueryWrapper<Study> queryWrapper=new LambdaQueryWrapper<>();
        queryWrapper.eq(Study::getDatatypeid,datatype);
        return studyMapperr.selectList(queryWrapper);

//        return studyMapperr.findStudyByType(datatype);
    }

    @Override
    public List<Study> findBySubject(int datatype) {
        LambdaQueryWrapper<Study> queryWrapper=new LambdaQueryWrapper<>();
        queryWrapper.eq(Study::getSubjectid,datatype);
        return studyMapperr.selectList(queryWrapper);
    }

    public List<Study> findPage(int current, int pageSize){
        //分页查询
        Page<Study> page=new Page<>(current,pageSize);
        Page<Study> dataPage=studyMapperr.selectPage(page,new LambdaQueryWrapper<>());
        return dataPage.getRecords();
    }

    public int delete (int study_id ){
        //根据id进行删除
        return studyMapperr.deleteById(study_id);
    }

    public int insert(Study study){

        return studyMapperr.insert(study);
    }
    public int update(Study study){
        //根据ID进行修改课程
        return studyMapperr.updateById(study);
    }



}
