package com.exam.demo.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.exam.demo.entity.Study;
import org.apache.ibatis.annotations.Select;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * @Author: csx
 * @Date: 2022/1/23
 */
@Repository
public interface StudyMapper extends BaseMapper<Study> {

//    @Select("SELECT user.name, user.age, course.course_name FROM user right join course on user.id = course.user_id WHERE id = #{id}")
//    List<Study> getUserList(@Param("id") int id);
}
