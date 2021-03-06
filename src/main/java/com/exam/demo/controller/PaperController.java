package com.exam.demo.controller;

import com.alibaba.fastjson.JSONObject;
import com.exam.demo.Utils.FileCommit;
import com.exam.demo.entity.Paper;
import com.exam.demo.service.PaperService;
import com.exam.demo.results.Consts;
import com.exam.demo.results.WebResult;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.util.Date;
import java.util.List;

import static com.exam.demo.results.WebResult.REQUEST_STATUS_SUCCESS;

/**
 * @Author: csx
 * @Date: 2022/1/23
 * 论文控制器
 */
@RestController
@RequestMapping("paper")
@Api(value="/paper",tags={"论文接口"})
public class PaperController {
    @Autowired
    private PaperService paperService;
    @RequestMapping(value = "/add",method = RequestMethod.POST)
    @ApiOperation(notes = "csx",value = "新增论文接口")
    public Object add(@RequestParam @ApiParam(name="title") String title,
                      @RequestParam @ApiParam(name="context") String context,
                      @RequestParam @ApiParam(name="type") String type,
                      @RequestParam @ApiParam(name="user_id") Integer user_id,
                      @RequestParam("file") MultipartFile mpFile){
        JSONObject jsonObject = new JSONObject();
        if(mpFile.isEmpty()){
            jsonObject.put(Consts.CODE,0);
            jsonObject.put(Consts.MSG,"论文上传失败");
            return jsonObject;
        }

        try {
            FileCommit fileCommit = new FileCommit();
            fileCommit.fileCommit(mpFile);
            String url = fileCommit.downLoad(mpFile);
            Paper paper=new Paper();
            paper.setTitle(title);
            paper.setContext(context);
            paper.setUser_id(user_id);
            paper.setUrl(url);
            Date day=new Date();
            paper.setDate(day);
            this.paperService.add(paper);
            return WebResult.<Integer>builder()
                    .code(200)
                    .message(REQUEST_STATUS_SUCCESS)
                    .data(1)
                    .build();
        } catch (Exception e){
            jsonObject.put(Consts.CODE,0);
            jsonObject.put(Consts.MSG,"新增失败"+e.getMessage());
            return jsonObject;
        }

    }
    //根据id进行删除
    @DeleteMapping("/delete")
    @ApiOperation(notes = "csx",value = "删除论文接口")
    public WebResult<Integer> delete(@RequestParam @ApiParam(name="id") Integer id){
        return WebResult.<Integer>builder()
                .code(200)
                .message(REQUEST_STATUS_SUCCESS)
                .data(paperService.delet(id))
                .build();

    }

    @GetMapping("findAll")
    @ApiOperation(notes = "csx",value = "全部论文查询接口")

    public WebResult<List<Paper>> findAll(){
        return  WebResult.<List<Paper>>builder()
                .code(200)
                .message(REQUEST_STATUS_SUCCESS)
                .data(paperService.findAll())
                .build();

    }
}
