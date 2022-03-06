package com.exam.demo.entity;

import java.util.List;

public class StudyByType {
    Integer id;
    List<Study> data;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public List<Study> getData() {
        return data;
    }

    public void setData(List<Study> data) {
        this.data = data;
    }
}
