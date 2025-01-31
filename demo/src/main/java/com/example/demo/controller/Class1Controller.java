package com.example.demo.controller;


import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.example.demo.common.QueryPageParam;
import com.example.demo.common.Result;
import com.example.demo.entity.Class1;
import com.example.demo.entity.Menu;
import com.example.demo.service.Class1Service;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.List;

/**
 * <p>
 *  前端控制器
 * </p>
 *
 * @author demo
 * @since 2023-04-08
 */
@RestController
@RequestMapping("/class1")
public class Class1Controller {
    @Autowired
    private Class1Service class1Service;
    //新增
    @PostMapping("/save")
    public Result save(@RequestBody Class1 class1){
        return class1Service.save(class1) ? Result.suc():Result.fail();
    }
    //更新
    @PostMapping("/update")
    public Result update(@RequestBody Class1 class1){
        return class1Service.updateById(class1) ? Result.suc() : Result.fail();
    }
    //删除
    @GetMapping("/delete")
    public Result delete(@RequestParam String id){
        return class1Service.removeById(id) ? Result.suc() : Result.fail();
    }

    @PostMapping("/listPage")
    //自定义类QueryPageParam方法，将数据封装到类中，不固定的数据依然要用HashMap方法
    public Result listPage(@RequestBody QueryPageParam query){
        HashMap param = query.getParam();
        String name = (String)param.get("name");
        Page<Class1> page = new Page();
        //上面参数加了，下面可以不用加这两条
        page.setCurrent(query.getPageNum());
        page.setSize(query.getPageSize());

        LambdaQueryWrapper<Class1> lambdaQueryWrapper = new LambdaQueryWrapper();
        if(StringUtils.isNotBlank(name) && !"null".equals(name)){
            lambdaQueryWrapper.like(Class1::getName, name);
        }

        IPage result = class1Service.pageC1(page, lambdaQueryWrapper);
        return Result.suc(result.getRecords(), result.getTotal());
    }

    @GetMapping("/list")
    public Result list(){
        List list = class1Service.list();
        return list.size()>0?Result.suc(list):Result.fail();
    }
}
