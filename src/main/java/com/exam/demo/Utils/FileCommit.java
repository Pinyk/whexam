package com.exam.demo.Utils;

import com.aliyun.oss.OSS;
import com.aliyun.oss.OSSClientBuilder;
import org.springframework.stereotype.Component;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.io.InputStream;
@Component
public class FileCommit {
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
