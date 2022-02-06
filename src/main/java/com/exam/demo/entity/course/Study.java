package com.exam.demo.entity.course;


import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;
import org.apache.ibatis.annotations.Options;

@Data
@TableName("data")
public class Study {

    private Integer id;
    private String name;
    private Integer datatype_id;
    private String url;
    private int subject_id;
    private int department_id;
}
