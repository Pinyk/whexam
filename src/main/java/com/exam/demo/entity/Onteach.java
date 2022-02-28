/**
 * Created by 小机灵鬼儿
 * 2022/2/28 6:53
 */

package com.exam.demo.entity;

import com.baomidou.mybatisplus.annotation.TableName;
import io.swagger.annotations.ApiModel;
import lombok.Data;

import java.util.Date;
/**
 * 线上授课对应实体
 */
@Data
@ApiModel(value = "onteach")
@TableName("study_info")
public class Onteach {
    private int id;
    //课程id
    private int subject_id;
    //人员id
    private int department_id;
    //课程名称
    private String crouse_name;
    //学习进度
    private double assessment;
    //获得学时
    private double get;
    //课程时间
    private double crouse_time;
    //学习时间
    private double study_time;
    //开始时间
    private Date begin_time;
    //结束时间
    private Date end_time;
}
