package com.exam.demo.controller;

import com.exam.demo.entity.Datatype;
import com.exam.demo.service.DataTypeService;

import com.exam.demo.utils.WebResult;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

import static com.exam.demo.utils.WebResult.REQUEST_STATUS_SUCCESS;
/**
 * @Author: csx
 * @Date: 2022/1/23
 * 学习控制器
 */
@RestController
@RequestMapping ("datatype")
@Api(value="/datatype",tags={"资料类型接口"})
public class DataTypeController {

    @Autowired
    private DataTypeService dataTypeService;


    @GetMapping("findById")
    @ApiOperation(notes = "csx",value = "根据id查询类型名称接口")

    public WebResult<Datatype> findById(@RequestParam @ApiParam(name="id") Integer id){
        return  WebResult.<Datatype>builder()
                .code(200)
                .message(REQUEST_STATUS_SUCCESS)
                .data(dataTypeService.findById(id))
                .build();

    }


    @RequestMapping(value = "/add",method = RequestMethod.POST)
    @ApiOperation(notes = "csx",value = "新增类型接口")
    public WebResult<Integer> add(@RequestParam @ApiParam(name="name") String name) {
        Datatype datatype=new Datatype();
        System.out.println(name);

        datatype.setName(name);
        return  WebResult.<Integer>builder()
                .code(200)
                .message(REQUEST_STATUS_SUCCESS)
                .data(dataTypeService.add(datatype))
                .build();

    }
    //根据id进行删除
    @DeleteMapping("/delete")
    @ApiOperation(notes = "csx",value = "删除文件类型接口")
    public WebResult<Integer> delete(@RequestParam @ApiParam(name="datatype_id") Integer datatype_id){
        return WebResult.<Integer>builder()
                .code(200)
                .message(REQUEST_STATUS_SUCCESS)
                .data(dataTypeService.deleteById(datatype_id))
                .build();

    }






}
