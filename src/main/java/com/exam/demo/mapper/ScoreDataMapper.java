package com.exam.demo.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.exam.demo.entity.Scoredata;
import com.exam.demo.otherEntity.UserMaterialAnswer;
import com.exam.demo.otherEntity.UserOtherAnswer;
import com.exam.demo.otherEntity.UserSelectionAnswer;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ScoreDataMapper extends BaseMapper<Scoredata> {

    /**
     * 根据试卷ID和用户ID查询用户的判断题目作答情况
     * @param testPaperId
     * @return
     */
    List<UserOtherAnswer> findExamJudgeAnswerByTidAndUid(@Param("testPaperId") Integer testPaperId, @Param("userID") Integer userID);

    /**
     * 根据试卷ID和用户ID查询用户的单选题目作答情况
     * @param testPaperId
     * @return
     */
    List<UserSelectionAnswer> findSingleSelectionAnswerByTidAndUid(@Param("testPaperId") Integer testPaperId, @Param("userID") Integer userID);

    /**
     * 根据试卷ID和用户ID查询用户的多选题目作答情况
     * @param testPaperId
     * @return
     */
    List<UserSelectionAnswer> findMultipleSelectionAnswerByTidAndUid(@Param("testPaperId") Integer testPaperId,@Param("userID") Integer userID);

    /**
     * 根据试卷ID和用户ID查询用户的主观题目作答情况
     * @param testPaperId
     * @return
     */
    List<UserOtherAnswer> findExamSubjectAnswerByTidAndUid(@Param("testPaperId") Integer testPaperId,@Param("userID") Integer userID);

    /**
     * 根据试卷ID和用户ID查询用户的填空题目作答情况
     * @param testPaperId
     * @return
     */
    List<UserOtherAnswer> findExamFillBlankAnswerByTidAndUid(@Param("testPaperId") Integer testPaperId,@Param("userID") Integer userID);

    List<UserMaterialAnswer> findExamMaterialAnswerByTidAndUid(@Param("testPaperId") Integer testPaperId, @Param("userID") Integer userID);

    List<UserSelectionAnswer> findSingleSelectionAnswerByMaterialId(@Param("testPaperId") Integer testPaperId,@Param("userID") Integer userID,@Param("emId") Integer emId);
    List<UserSelectionAnswer> findMutipleSelectionAnswerByMaterialId(@Param("testPaperId") Integer testPaperId,@Param("userID") Integer userID,@Param("emId") Integer emId);

    List<UserOtherAnswer> findExamJudgeAnswerByMaterialId(@Param("testPaperId") Integer testPaperId,@Param("userID") Integer userID,@Param("emId") Integer emId);
    List<UserOtherAnswer> findExamSubjectAnswerByMaterialId(@Param("testPaperId") Integer testPaperId,@Param("userID") Integer userID,@Param("emId") Integer emId);
    List<UserOtherAnswer> findExamFillBlankAnswerByMaterialId(@Param("testPaperId") Integer testPaperId,@Param("userID") Integer userID,@Param("emId") Integer emId);

    /**
     * 修改scoredata的记录
     * @param scoredata
     * @return
     */
    Integer updateScoreDataByUserIdAndTestPaperId(Scoredata scoredata);

    Integer insertScoreData(Scoredata scoredata);
}
