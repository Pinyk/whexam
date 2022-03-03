package com.exam.demo.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.exam.demo.mapper.DatatypeMapper;
import io.swagger.annotations.ApiModel;

/**
 *
 * 显示学习类对应实体
 */
@ApiModel(value = "showstudy")
public class ShowStudy {
    private Integer id;
    private String name;
    private Integer datatype_id;
    private String datatype_name;
    private String url;
    private int subject_id;
    private String subject_name;
    private int department_id;
    private String department_name;
    private String time;
    private String beizhu;
    private String typename;

    public String getTypename() {
        return typename;
    }

    public void setTypename(String typename) {
        this.typename = typename;
    }

    Study study=new Study();
    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Integer getDatatype_id() {
        return datatype_id;
    }

    public void setDatatype_id(Integer datatype_id) {
        this.datatype_id = datatype_id;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public int getSubject_id() {
        return subject_id;
    }

    public void setSubject_id(int subject_id) {
        this.subject_id = subject_id;
    }

    public int getDepartment_id() {
        return department_id;
    }

    public void setDepartment_id(int department_id) {
        this.department_id = department_id;
    }

    public String getTime() {
        return time;
    }

    public void setTime(String time) {
        this.time = time;
    }

    public String getBeizhu() {
        return beizhu;
    }

    public void setBeizhu(String beizhu) {
        this.beizhu = beizhu;
    }

    public String getDatatype_name() {
        return datatype_name;
    }

    public void setDatatype_name(String datatype_name) {
        this.datatype_name = datatype_name;
    }

    public String getSubject_name() {
        return subject_name;
    }

    public void setSubject_name(String subject_name) {
        this.subject_name = subject_name;
    }

    public String getDepartment_name() {
        return department_name;
    }

    public void setDepartment_name(String department_name) {
        this.department_name = department_name;
    }
}
