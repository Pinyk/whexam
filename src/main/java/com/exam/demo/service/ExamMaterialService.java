package com.exam.demo.service;

import com.alibaba.fastjson.JSONObject;
import com.exam.demo.params.submit.MaterialSubmitParam;
import com.exam.demo.results.vo.ExamMaterialVo;
import com.exam.demo.results.vo.PageVo;

import java.util.Map;


public interface ExamMaterialService {


    /**
     * 根据材料题id查询所有
     * @param id 材料题Id
     * @param context 材料题题目内容
     * @return
     */
    PageVo<ExamMaterialVo> searchMaterial(Integer id, String context, Integer subjectId, Integer currentPage, Integer pageSize);


    /**
     * 新增材料题
     */
    Map<String, Object> saveExamMaterial(JSONObject jsonObject);

    /**
     * 材料题详情预览
     * @param id
     * @return
     */
    Map<String, Object> previewById(Integer id);
}
