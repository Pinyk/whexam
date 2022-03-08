package com.exam.demo.Utils;

import com.qcloud.cos.COSClient;
import com.qcloud.cos.ClientConfig;
import com.qcloud.cos.auth.BasicCOSCredentials;
import com.qcloud.cos.auth.COSCredentials;
import com.qcloud.cos.http.HttpMethodName;
import com.qcloud.cos.model.GeneratePresignedUrlRequest;
import com.qcloud.cos.model.ObjectMetadata;
import com.qcloud.cos.model.PutObjectRequest;
import com.qcloud.cos.region.Region;
import org.springframework.stereotype.Component;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.io.InputStream;
import java.util.Date;

@Component
public class FileCommit {
    String bucketName = "xiaoningya-1302847510";
    String region = "ap-chongqing";
    String appId = "1302847510";
    String SecretId = "AKIDQdbIjuymJZXFgqjLgkX3IGyQbV8UZMt9";
    String SecretKey = "0jCOJxKVQ3PUIq77qITN0MRGo68tMYgb";
//    String url="https://"+bucketName+".cos."+region+".myqcloud.com";

    public COSClient getCoSClient4Picture() {
        //初始化用户身份信息
        COSCredentials cosCredentials = new BasicCOSCredentials(SecretId,SecretKey);
        //设置bucket区域，
        //clientConfig中包含了设置region
        ClientConfig clientConfig = new ClientConfig(new Region(region));
        //生成cos客户端
        return new COSClient(cosCredentials,clientConfig);

    }

    /**
     * 文件上传，wxn
     * @param mpFile
     * @throws IOException
     */
    public void fileCommit(MultipartFile mpFile) throws IOException {
        if (!mpFile.isEmpty()) {
            //获得文件名
            String originalFilename = mpFile.getOriginalFilename();
            assert originalFilename != null;
//            String suffix = originalFilename.substring(originalFilename.lastIndexOf("."));
            // 如果文件格式正确
            COSClient cosClient = getCoSClient4Picture();
            ObjectMetadata objectMetadata = new ObjectMetadata();

            // 获取文件流
            InputStream inputStream = mpFile.getInputStream();
            PutObjectRequest putObjectRequest = new PutObjectRequest(bucketName, originalFilename, inputStream, objectMetadata);
            cosClient.putObject(putObjectRequest);

            // 关闭cosClient
            cosClient.shutdown();

            // 指定要上传到 COS 上的路径
//            String url = fileCommit.getUrl() + "/" + originalFilename;
        }
    }

    /**
     * 文件下载,wxn
     * @return
     */
    public String downLoad(MultipartFile mpFile){
        // 1 初始化用户身份信息(appid, secretId, secretKey)
        COSCredentials cred = new BasicCOSCredentials(SecretId, SecretKey);
        // 2 设置bucket的区域, COS地域的简称 https://cloud.tencent.com/document/product/436/6224
        ClientConfig clientConfig = new ClientConfig(new Region(region));
        // 3 生成cos客户端
        COSClient cosclient = new COSClient(cred, clientConfig);

        GeneratePresignedUrlRequest req =
                new GeneratePresignedUrlRequest(bucketName,mpFile.getOriginalFilename(), HttpMethodName.GET);
        // 设置签名过期时间(可选), 过期时间不做限制，只需比当前时间大, 若未进行设置, 则默认使用ClientConfig中的签名过期时间(5分钟)
        // 这里设置签名在半个小时后过期
        Date expirationDate = new Date(System.currentTimeMillis() + 30 * 60 * 1000);
        req.setExpiration(expirationDate);
        String  url = String.valueOf(cosclient.generatePresignedUrl(req));
        //输出可供下载的文件路径
        cosclient.shutdown();
        return url;
    }
    public void delete(String key){
        // 1 初始化用户身份信息(appid, secretId, secretKey)
        COSCredentials cred = new BasicCOSCredentials(SecretId, SecretKey);
        // 2 设置bucket的区域, COS地域的简称 https://cloud.tencent.com/document/product/436/6224
        ClientConfig clientConfig = new ClientConfig(new Region(region));
        // 3 生成cos客户端
        COSClient cosclient = new COSClient(cred, clientConfig);

        cosclient.deleteObject(bucketName,key);

    }




}
