package com.exam.demo.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.exam.demo.entity.Scoredata;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ScoreDataMapper extends BaseMapper<Scoredata> {

    /**
     * 根据试卷ID和用户ID查询用户的判断题目作答情况
     * @param testPaperId
     * @return
     */
    List<Object> findExamJudgeAnswerByTidAndUid(Integer testPaperId, Integer userID);

    /**
     * 根据试卷ID和用户ID查询用户的单选题目作答情况
     * @param testPaperId
     * @return
     */
    List<Object> findSingleSelectionAnswerByTidAndUid(Integer testPaperId, Integer userID);

    /**
     * 根据试卷ID和用户ID查询用户的多选题目作答情况
     * @param testPaperId
     * @return
     */
    List<Object> findMultipleSelectionAnswerByTidAndUid(Integer testPaperId, Integer userID);

    /**
     * 根据试卷ID和用户ID查询用户的主观题目作答情况
     * @param testPaperId
     * @return
     */
    List<Object> findExamSubjectAnswerByTidAndUid(Integer testPaperId, Integer userID);

    /**
     * 根据试卷ID和用户ID查询用户的填空题目作答情况
     * @param testPaperId
     * @return
     */
    List<Object> findExamFillBlankAnswerByTidAndUid(Integer testPaperId, Integer userID);
}
