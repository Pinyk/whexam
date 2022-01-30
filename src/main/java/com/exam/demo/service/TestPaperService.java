package com.exam.demo.service;

import com.exam.demo.entity.TestPaper;

import java.util.List;

public interface TestPaperService {

    List<TestPaper> findAll();

    List<TestPaper> findTesting();

    List<TestPaper> findTested();

    Integer addTestPaper(TestPaper testPaper);
}
