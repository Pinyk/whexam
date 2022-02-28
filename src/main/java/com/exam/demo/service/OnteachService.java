package com.exam.demo.service;

import com.exam.demo.entity.Onteach;

import java.util.List;

public interface OnteachService {
    List<Onteach> selectAll();
    int insert(Onteach onteach);
    int update(Onteach onteach);
    int delete(int subject_id);
}
