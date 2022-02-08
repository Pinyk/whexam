package com.exam.demo.service.impl;

import com.exam.demo.entity.Exam;
import com.exam.demo.mapper.ExamMapper;
import com.exam.demo.service.ExamService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service
public class ExamServiceImpl implements ExamService {

    @Autowired
    private ExamMapper examMapper;

    /**
     * 查找每张试卷对应的所有试题
     * @param testPaperId
     * @return
     */
    @Override
    public Map<String, List<Object>> findByTestPaperId(Integer testPaperId) {
        Map<String, List<Object>> map = new HashMap<>();
        map.put("examJudge", examMapper.findExamJudgeByTestPaperId(testPaperId));
        map.put("examSelect", examMapper.findExamSelectByTestPaperId(testPaperId));
        map.put("examSubject", examMapper.findExamSubjectByTestPaperId(testPaperId));
        return map;
    }

    /**
     * 添加试卷试题
     * @param exam
     * @return
     */
    @Override
    public Integer addProblem(Exam exam) {
        return examMapper.insert(exam);
    }

    /**
     * 删除试卷试题
     * @param id
     * @return
     */
    @Override
    public Integer deleteProblem(Integer id) {
        return examMapper.deleteById(id);
    }
}
