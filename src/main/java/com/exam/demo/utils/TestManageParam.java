package com.exam.demo.utils;

import io.swagger.models.auth.In;
import lombok.Data;

@Data
public class TestManageParam {

    private Integer testPaperId;

    private String testPaperName;

    private String departmentName;

    private String subject;

    private Integer currentPage;

    private Integer pageSize;

}
