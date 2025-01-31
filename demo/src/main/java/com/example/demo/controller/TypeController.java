package com.example.demo.controller;


import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.example.demo.common.QueryPageParam;
import com.example.demo.common.Result;
import com.example.demo.entity.Type;
import com.example.demo.service.TypeService;
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
@RequestMapping("/type")
public class TypeController {
    @Autowired
    private TypeService typeService;
    //新增
    @PostMapping("/save")
    public Result save(@RequestBody Type type){
        return typeService.save(type) ? Result.suc():Result.fail();
    }
    //更新
    @PostMapping("/update")
    public Result update(@RequestBody Type type){
        return typeService.updateById(type) ? Result.suc() : Result.fail();
    }
    //删除
    @GetMapping("/delete")
    public Result delete(@RequestParam String id){
        return typeService.removeById(id) ? Result.suc() : Result.fail();
    }

    @PostMapping("/listPage")
    //自定义类QueryPageParam方法，将数据封装到类中，不固定的数据依然要用HashMap方法
    public Result listPage(@RequestBody QueryPageParam query){
        HashMap param = query.getParam();
        String name = (String)param.get("name");
        Page<Type> page = new Page();
        //上面参数加了，下面可以不用加这两条
        page.setCurrent(query.getPageNum());
        page.setSize(query.getPageSize());

        LambdaQueryWrapper<Type> lambdaQueryWrapper = new LambdaQueryWrapper();
        if(StringUtils.isNotBlank(name) && !"null".equals(name)){
            lambdaQueryWrapper.like(Type::getName, name);
        }

        IPage result = typeService.pageC1(page, lambdaQueryWrapper);
        return Result.suc(result.getRecords(), result.getTotal());
    }

    @GetMapping("/list")
    public Result list(){
        List list = typeService.list();
        return list.size()>0?Result.suc(list):Result.fail();
    }
}
