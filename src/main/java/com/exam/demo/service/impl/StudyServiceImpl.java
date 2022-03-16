package com.exam.demo.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.toolkit.StringUtils;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.exam.demo.entity.Study;
import com.exam.demo.mapper.DatatypeMapper;
import com.exam.demo.mapper.StudyMapper;
import com.exam.demo.mapper.SubjectMapper;
import com.exam.demo.mapper.SubjectTypeMapper;
import com.exam.demo.results.vo.PageVo;
import com.exam.demo.results.vo.StudyVo;
import com.exam.demo.service.StudyService;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.LinkedList;
import java.util.List;
/**
 * @Author: csx,wxn
 * @Date: 2022/1/23
 */
@Service

public class StudyServiceImpl implements StudyService {
    @Autowired
    private StudyMapper studyMapperr;

    @Autowired
    private SubjectMapper subjectMapper;

    @Autowired
    private DatatypeMapper dataTypeMapper;

    @Autowired
    private SubjectTypeMapper subjectTypeMapper;

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

    public PageVo<Study> findPage(int current, int pageSize){
        //分页查询
        Page<Study> page=new Page<>(current,pageSize);
        Page<Study> dataPage=studyMapperr.selectPage(page,new LambdaQueryWrapper<>());
        return PageVo.<Study>builder()
                .values(dataPage.getRecords())
                .page(current)
                .size(pageSize)
                .total(dataPage.getTotal())
                .build();
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

    @Override
    public List<Study> findBySubjectType(int typeid) {
        LambdaQueryWrapper<Study> queryWrapper=new LambdaQueryWrapper<>();
        queryWrapper.eq(Study::getTypeid,typeid);
        return studyMapperr.selectList(queryWrapper);
    }

    @Override
    public Study findById(int id) {

        return studyMapperr.selectById(id);
    }

    /**
     * 根据条件查询资料
     * @param name 名称
     * @param beizhu 备注
     * @param subjectId 科目
     * @param currentPage
     * @param pageSize
     * @return
     */
    @Override
    public PageVo<StudyVo> search(String name, String beizhu, Integer subjectId, Integer typeId,Integer currentPage, Integer pageSize) {
        Page<Study> page = new Page<>(currentPage, pageSize);
        LambdaQueryWrapper<Study> wrapperStudy = new LambdaQueryWrapper<>();
        if(!StringUtils.isBlank(name)){
            wrapperStudy.eq(Study::getName,name);
        }
        if(!StringUtils.isBlank(beizhu)){
            wrapperStudy.eq(Study::getBeizhu,beizhu);
        }
        wrapperStudy.eq(Study::getTypeid,typeId);
        wrapperStudy.eq(Study::getSubjectid,subjectId);
        LinkedList<StudyVo> studyVos = new LinkedList<>();
        Page<Study> studyPage = studyMapperr.selectPage(page,wrapperStudy);
        List<Study> studyList = studyPage.getRecords();
        StudyVo studyVo = new StudyVo();
        for (Study s : studyList){
            StudyVo studyVo1 = copy(studyVo,s);
            studyVos.add(studyVo1);
        }
        return PageVo.<StudyVo>builder()
                .values(studyVos)
                .page(currentPage)
                .size(pageSize)
                .total(studyPage.getTotal())
                .build();
    }
    private StudyVo copy(StudyVo studyVo1,Study study) {
        BeanUtils.copyProperties(study,studyVo1);
        int subjectId = study.getSubjectid();
        int dataTypeId = study.getDatatypeid();
        int typeId = study.getTypeid();
        if (subjectId != 0) {
            studyVo1.setSubject(subjectMapper.selectById(subjectId).getName());
        }
        if (dataTypeId != 0){
            studyVo1.setDatatype(dataTypeMapper.selectById(dataTypeId).getName());
        }
        if (typeId != 0){
            studyVo1.setSubject_type(subjectTypeMapper.selectById(typeId).getName());
        }
        return studyVo1;
    }
}
