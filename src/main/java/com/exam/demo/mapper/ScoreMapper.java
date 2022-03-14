package com.exam.demo.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.exam.demo.entity.Score;
import com.exam.demo.entity.UserTestPaperScore;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ScoreMapper extends BaseMapper<Score> {

    List<UserTestPaperScore> findByUserId(Integer userId);

    List<UserTestPaperScore> findByTestPaperId(Integer testPaperId);

    List<UserTestPaperScore> findByTestPaperIdAndUserId(@Param("testPaperId") Integer testPaperId, @Param("userID") Integer userID);

    Double avgScoreByTestPaperId(Integer testPaperId);

    /**
     * 根据用户id和试卷id修改总成绩
     * @param score
     * @return
     */
    Integer updateScoreByUserIdAndTestPaperId(Score score);

    /**
     * 查询该用户是否提交过考试
     * @param UserId
     * @param testPaperId
     * @return
     */
    List<Score> findIsSubmitByUserIdAndTestPaperId(Integer UserId,Integer testPaperId);
}
