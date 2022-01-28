package com.exam.demo.service;

import com.exam.demo.entity.UserTestPaperScore;

import java.util.List;

public interface ScoreService {

    List<UserTestPaperScore> findByUserId(Integer userId);

    List<UserTestPaperScore> findByTestPaperId(Integer testPaperId);

    Double avgScoreByTestPaperId(Integer testPaperId);
}
