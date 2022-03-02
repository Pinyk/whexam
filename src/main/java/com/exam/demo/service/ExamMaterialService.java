package com.exam.demo.service;

import com.exam.demo.results.vo.ExamMaterialVo;


public interface ExamMaterialService {


    /**
     * 根据材料题id查询所有
     * @param id 材料题Id
     * @param context 材料题题目内容
     * @return
     */
    ExamMaterialVo findMaterialProblemByIdAndContext(Integer id, String context);

}
