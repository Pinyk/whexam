package com.exam.demo.service.impl;

import com.exam.demo.entity.Exam;
import com.exam.demo.entity.ExamSelect;
import com.exam.demo.mapper.ExamMapper;
import com.exam.demo.rtEntity.SelectQuestion;
import com.exam.demo.service.ExamService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.*;

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
        List<Object> selectQuestionList = new ArrayList<>();
        List<Object> examSelectList = examMapper.findExamSelectByTestPaperId(testPaperId);
        for(Object object : examSelectList) {
            ExamSelect examSelect = (ExamSelect) object;
            SelectQuestion selectQuestion = new SelectQuestion();
            selectQuestion.setContext(examSelect.getContext());
            selectQuestion.setSelections(Arrays.asList(examSelect.getSelection().split("；")));
            selectQuestionList.add(selectQuestion);
        }
        map.put("examJudge", examMapper.findExamJudgeByTestPaperId(testPaperId));
        map.put("examSelect", selectQuestionList);
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
