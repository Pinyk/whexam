package com.exam.demo.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.core.toolkit.StringUtils;
import com.exam.demo.Utils.FileCommit;
import com.exam.demo.entity.ExamSelect;
import com.exam.demo.entity.Subject;
import com.exam.demo.mapper.ExamSelectMapper;
import com.exam.demo.mapper.SubjectMapper;
import com.exam.demo.otherEntity.SelectQuestionVo;
import com.exam.demo.params.submit.SelectSubmitParam;
import com.exam.demo.results.vo.ExamSelectVo;
import com.exam.demo.results.vo.PageVo;
import com.exam.demo.service.ExamSelectService;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.*;

@Service
public class ExamSelectServiceImpl implements ExamSelectService {

    @Autowired
    private ExamSelectMapper examSelectMapper;

    @Autowired
    SubjectMapper subjectMapper;

    @Autowired
    FileCommit fileCommit;

    /**
     * 对查询的 ExamSelect 进行处理后，将内容复制到 SelectQuestion 中
     * @param examSelect
     * @return
     */
    private SelectQuestionVo change(ExamSelect examSelect) {
        SelectQuestionVo selectQuestionVo = new SelectQuestionVo();
        BeanUtils.copyProperties(examSelect,selectQuestionVo);
        selectQuestionVo.setSubject(subjectMapper.selectById(examSelect.getSubjectId()).getName());
        return selectQuestionVo;
    }

    /**
     * 2.0版的change方法，目前只适用于组合查询search
     * @return
     */
    private ExamSelectVo copy(ExamSelect examSelect) {
        ExamSelectVo examSelectVo = new ExamSelectVo();
        BeanUtils.copyProperties(examSelect,examSelectVo);
        Integer subjectId = examSelect.getSubjectId();
        if (subjectId != null && subjectId != 0) {
            examSelectVo.setSubject(subjectMapper.selectById(subjectId).getName());
        }
        return examSelectVo;
    }
//========================================================================================================================
    @Override
    public List<SelectQuestionVo> findAll() {
        List<SelectQuestionVo> selectQuestionVoList = new ArrayList<>();

        List<ExamSelect> examSelectList = examSelectMapper.selectList(new LambdaQueryWrapper<>());
        for(ExamSelect examSelect : examSelectList) {
            SelectQuestionVo selectQuestionVo = change(examSelect);
            selectQuestionVoList.add(selectQuestionVo);
        }
        return selectQuestionVoList;
    }

    @Override
    public List<SelectQuestionVo> findSingleOrMultipleSelection(int type) {
        List<SelectQuestionVo> selectQuestionVoList = new ArrayList<>();

        QueryWrapper<ExamSelect> queryWrapper = new QueryWrapper<>();
        queryWrapper.eq("type", type);
        List<ExamSelect> examSelectList = examSelectMapper.selectList(queryWrapper);
        for(ExamSelect examSelect : examSelectList) {
            SelectQuestionVo selectQuestionVo = change(examSelect);
            selectQuestionVoList.add(selectQuestionVo);
        }
        return selectQuestionVoList;
    }

    @Override
    public List<SelectQuestionVo> findPage(int current, int pageSize, int type) {
        List<SelectQuestionVo> selectQuestionVos = new ArrayList<>();

        QueryWrapper<ExamSelect> queryWrapper = new QueryWrapper<>();
        queryWrapper.eq("type", type);

        Page<ExamSelect> page = new Page<>(current, pageSize);
        Page<ExamSelect> examSelectPage = examSelectMapper.selectPage(page, queryWrapper);
        List<ExamSelect> examSelectList = examSelectPage.getRecords();
        for(ExamSelect examSelect : examSelectList) {
            SelectQuestionVo selectQuestionVo = change(examSelect);
            selectQuestionVos.add(selectQuestionVo);
        }
        return selectQuestionVos;
    }



    @Override
    public SelectQuestionVo findById(Integer id) {
        ExamSelect examSelect = examSelectMapper.selectById(id);
        SelectQuestionVo selectQuestionVo = change(examSelect);
        return selectQuestionVo;
    }

    @Override
    public ExamSelectVo selectById(Integer id) {
        ExamSelect examSelect = examSelectMapper.selectById(id);
        ExamSelectVo examSelectVo = new ExamSelectVo();
        BeanUtils.copyProperties(examSelect, examSelectVo);
        examSelectVo.setSubject(subjectMapper.selectById(examSelect.getSubjectId()).getName());
        return examSelectVo;
    }

    @Override
    public List<ExamSelect> findBySubjectId(Integer subjectId, Integer type) {
        QueryWrapper<ExamSelect> queryWrapper = new QueryWrapper<>();
        queryWrapper.eq("subject_id",subjectId);
        queryWrapper.eq("type",type);
        return examSelectMapper.selectList(queryWrapper);
    }
//=====================================================组合查询===========================================================
    @Override
    public PageVo<ExamSelectVo> search(Integer id, String name, String subject, Integer currentPage, Integer pageSize, Integer type, Integer materialQuestion) {
        Page<ExamSelect> page = new Page<>(currentPage, pageSize);
        LambdaQueryWrapper<ExamSelect> wrapperSelect = Wrappers.lambdaQuery(ExamSelect.class);
        wrapperSelect.eq(ExamSelect::getMaterialQuestion, materialQuestion);
        if (id != null) {
            wrapperSelect.eq(ExamSelect::getId, id);
        }
        wrapperSelect.like(StringUtils.isNotBlank(name),ExamSelect::getContext, name);
        wrapperSelect.eq(ExamSelect::getType, type);
        if (!StringUtils.isBlank(subject)) {
            LambdaQueryWrapper<Subject> subjectwrapper = Wrappers.lambdaQuery(Subject.class);
            subjectwrapper
                    .select(Subject::getId)
                    .like(Subject::getName,subject);
            List<Subject> subjects = subjectMapper.selectList(subjectwrapper);
            if (!subjects.isEmpty()) {
                LinkedList<Integer> subjectIds = new LinkedList<>();
                for (Subject sub : subjects) {
                    subjectIds.add(sub.getId());
                }
                wrapperSelect.in(ExamSelect::getSubjectId, subjectIds);
            }
        }
        LinkedList<ExamSelectVo> examSelectVos = new LinkedList<>();
        Page<ExamSelect> examSelectPage = examSelectMapper.selectPage(page, wrapperSelect);
        List<ExamSelect> examSelectList = examSelectPage.getRecords();
        for(ExamSelect examSelect : examSelectList) {
            ExamSelectVo examSelectVo = copy(examSelect);
            examSelectVos.add(examSelectVo);
        }
        return PageVo.<ExamSelectVo>builder()
                .values(examSelectVos)
                .page(currentPage)
                .size(pageSize)
                .total(examSelectPage.getTotal())
                .build();
    }

    @Override
    public ExamSelect findByIdAndMaterialQuestion(Integer id, Integer materialQuestion) {
        LambdaQueryWrapper<ExamSelect> wrapperSelect = Wrappers.lambdaQuery(ExamSelect.class);

        wrapperSelect.eq(id != null, ExamSelect::getId, id);
        wrapperSelect.eq(materialQuestion!= null, ExamSelect::getMaterialQuestion, materialQuestion);

        return examSelectMapper.selectOne(wrapperSelect);
    }
//================================新增保存业务============================================================================
    @Override
    public Integer saveSingleSelection(SelectSubmitParam selectSubmitParam, MultipartFile image) {
        return save(selectSubmitParam, image, 1);
    }

    @Override
    public Integer saveMultipleSelection(SelectSubmitParam selectSubmitParam, MultipartFile image) {
        return save(selectSubmitParam, image, 2);
    }
//======================================================================================================================
    private Integer save(SelectSubmitParam selectSubmitParam, MultipartFile image,Integer type) {
        //实例化选择题对象
        ExamSelect examSelect = new ExamSelect();
        //添加题目内容
        if (selectSubmitParam.getContext() != null) {
            examSelect.setContext(selectSubmitParam.getContext());
        }
        //添加题目选项
        if(selectSubmitParam.getSelectionA() != null && selectSubmitParam.getSelectionB() != null
                && selectSubmitParam.getSelectionC() != null && selectSubmitParam.getSelectionD() != null) {
            StringBuffer stringBuffer = new StringBuffer();
            stringBuffer
                    .append(selectSubmitParam.getSelectionA())
                    .append(";")
                    .append(selectSubmitParam.getSelectionB())
                    .append(";")
                    .append(selectSubmitParam.getSelectionC())
                    .append(";")
                    .append(selectSubmitParam.getSelectionD());
            examSelect.setSelection(stringBuffer.toString());
        }
        //添加题目答案
        examSelect.setAnswer(selectSubmitParam.getAnswer());

        //添加所属科目Id
        examSelect.setSubjectId(selectSubmitParam.getSubjectId());

        //添加题目难度
        examSelect.setDifficulty(1);
        //添加分数
        examSelect.setScore(selectSubmitParam.getScore());
        //添加选择题type
        examSelect.setType(type);
        //添加materialQuestion
        examSelect.setMaterialQuestion(0);
        //添加图片
        if (image != null) {
            //调用COS存储图片
            try {
                fileCommit.fileCommit(image);
                //将图片对应的url存入数据库
                String downLoadUrl = fileCommit.downLoad(image);
                String url = downLoadUrl.split("\\?sign=")[0];
                examSelect.setImgUrl(url);
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        examSelectMapper.insert(examSelect);

        return examSelect.getId();
    }
//============================================================================================


    @Override
    public Integer saveExamSelect(ExamSelect examSelect) {
        return examSelectMapper.insert(examSelect);
    }

    @Override
    public Integer updateExamSelect(ExamSelect examSelect) {
        return examSelectMapper.updateById(examSelect);
    }

    @Override
    public Integer deleteExamSelect(Integer id) {
        return examSelectMapper.deleteById(id);
    }
}