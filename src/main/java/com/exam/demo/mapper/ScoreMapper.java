package com.exam.demo.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.exam.demo.entity.Score;
import com.exam.demo.entity.UserTestPaperScore;
import org.apache.ibatis.annotations.Mapper;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
@Mapper
public interface ScoreMapper extends BaseMapper<Score> {

    List<UserTestPaperScore> findByUserId(Integer userId);

    List<UserTestPaperScore> findByTestPaperId(Integer testPaperId);

    Double avgScoreByTestPaperId(Integer testPaperId);
}
