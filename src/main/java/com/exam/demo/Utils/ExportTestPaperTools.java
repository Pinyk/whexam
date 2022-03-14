package com.exam.demo.Utils;

import com.exam.demo.Utils.pdfUtils.PDFHeaderFooter;
import com.exam.demo.Utils.pdfUtils.PDFKit;
import lombok.extern.slf4j.Slf4j;

import java.io.ByteArrayInputStream;
import java.util.HashMap;
import java.util.Map;

/**
 * FileName: ExportTestPaperTools
 * Author:   guzhuangzhuang
 * Date:     2022/3/13 10:56 下午
 * Description: 导出试卷为PDF
 */
@Slf4j
public class ExportTestPaperTools {
    private String ftlTemplateURL = "/ftl/hello.ftl";


    public  ByteArrayInputStream createPDF(Object data){
        //pdf保存路径
        try {
            //设置自定义PDF页眉页脚工具类
            PDFHeaderFooter headerFooter=new PDFHeaderFooter();
            PDFKit kit=new PDFKit();
            kit.setHeaderFooterBuilder(headerFooter);
            //设置输出路径
            kit.setSaveFilePath("/Users/guzhuangzhuang/Desktop/test.pdf");

            ByteArrayInputStream outputStream=kit.exportToFile(ftlTemplateURL,data);
            return  outputStream;
        } catch (Exception e) {
            log.error("PDF生成失败{}", e);
            return null;
        }

    }
}
