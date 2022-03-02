package com.exam.demo.service.impl;

import com.aliyun.oss.OSS;
import com.aliyun.oss.OSSClientBuilder;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.exam.demo.entity.Study;
import com.exam.demo.mapper.StudyMapper;
import com.exam.demo.service.StudyService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;
import java.io.IOException;
import java.io.InputStream;
import java.util.List;
/**
 * @Author: csx,wxn
 * @Date: 2022/1/23
 */
@Service

public class StudyServiceImpl implements StudyService {
    @Autowired
    private StudyMapper studyMapperr;

    public List<Study> findAll() {
        //查询所有

        return studyMapperr.selectList(new LambdaQueryWrapper<>());

    }
    public List<Study> findByType(int datatype){
        //根据学习类型进行查询
        LambdaQueryWrapper<Study> queryWrapper=new LambdaQueryWrapper<>();
        queryWrapper.eq(Study::getDatatypeid,datatype);
        return studyMapperr.selectList(queryWrapper);

//        return studyMapperr.findStudyByType(datatype);
    }

    @Override
    public List<Study> findBySubject(int datatype) {
        LambdaQueryWrapper<Study> queryWrapper=new LambdaQueryWrapper<>();
        queryWrapper.eq(Study::getSubjectid,datatype);
        return studyMapperr.selectList(queryWrapper);
    }

    public List<Study> findPage(int current, int pageSize){
        //分页查询
        Page<Study> page=new Page<>(current,pageSize);
        Page<Study> dataPage=studyMapperr.selectPage(page,new LambdaQueryWrapper<>());
        return dataPage.getRecords();
    }

    public int delete (int study_id ){
        //根据id进行删除
        return studyMapperr.deleteById(study_id);
    }

    public int insert(Study study){

        return studyMapperr.insert(study);
    }
    public int update(Study study){
        //根据ID进行修改课程
        return studyMapperr.updateById(study);
    }

    public String uploadFileAvatar(MultipartFile file) {
        String endpoint = "https://oss-cn-beijing.aliyuncs.com";
        String accessKeyId = "LTAI5tGtGgwJkpyb9UDrAPj7";
        String accessKeySecret = "gTTvD1103beS004i2Cv9fCumY0JftH";
        String bucketName = "xiaoningya";

        OSS ossClient = new OSSClientBuilder().build(endpoint, accessKeyId, accessKeySecret);
        InputStream inputStream = null;
        try {
            //获取文件流
            inputStream = file.getInputStream();
        } catch (IOException e) {
            e.printStackTrace();
        }
        //获取文件名称
        String filename = file.getOriginalFilename();
        //1.在文件名称中添加随机唯一的值
        //String uuid = UUID.randomUUID().toString().replaceAll("-","");
        //filename = uuid+filename;
        //2.把文件按日期分类
        //String datePath = new Date().toString();
        //filename = datePath +"/"+filename;
        //调用OSS方法实现上传
        ossClient.putObject(bucketName, filename, inputStream);
        ossClient.shutdown();
        //把上传之后的文件路径返回
        //需要把上传到阿里云oss路径手动拼接出来
        //路径规则：https://edu-cgq.oss-cn-chengdu.aliyuncs.com/QQ%E5%9B%BE%E7%89%8720200502111905.jpg
        return endpoint+"/"+filename;
    }

}
