package com.example.demo.controller;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.example.demo.common.QueryPageParam;
import com.example.demo.common.Result;
import com.example.demo.entity.Securities;
import com.example.demo.service.SecuritiesService;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;

/**
 * <p>
 *  前端控制器
 * </p>
 *
 * @author demo
 * @since 2023-04-09
 */
@RestController
@RequestMapping("/securities")
public class SecuritiesController {
    @Autowired
    private SecuritiesService securitiesService;
    //新增
    @PostMapping("/save")
    public Result save(@RequestBody Securities securities){
        return securitiesService.save(securities) ? Result.suc():Result.fail();
    }
    //更新
    @PostMapping("/update")
    public Result update(@RequestBody Securities securities){
        return securitiesService.updateById(securities) ? Result.suc() : Result.fail();
    }
    //删除
    @GetMapping("/delete")
    public Result delete(@RequestParam String id){
        return securitiesService.removeById(id) ? Result.suc() : Result.fail();
    }

    @PostMapping("/listPage")
    //自定义类QueryPageParam方法，将数据封装到类中，不固定的数据依然要用HashMap方法
    public Result listPage(@RequestBody QueryPageParam query){
        HashMap param = query.getParam();
        String name = (String)param.get("name");
        String class1 = (String)param.get("securitiesclass");
        String type = (String)param.get("securitiestype");
        Page<Securities> page = new Page();
        //上面参数加了，下面可以不用加这两条
        page.setCurrent(query.getPageNum());
        page.setSize(query.getPageSize());

        LambdaQueryWrapper<Securities> lambdaQueryWrapper = new LambdaQueryWrapper();
        if(StringUtils.isNotBlank(name) && !"null".equals(name)){
            lambdaQueryWrapper.like(Securities::getName, name);
        }
        if(StringUtils.isNotBlank(class1) && !"null".equals(class1)){
            lambdaQueryWrapper.eq(Securities::getSecuritiesclass, class1);
        }
        if(StringUtils.isNotBlank(type) && !"null".equals(type)){
            lambdaQueryWrapper.eq(Securities::getSecuritiestype, type);
        }

        IPage result = securitiesService.pageC1(page, lambdaQueryWrapper);
        return Result.suc(result.getRecords(), result.getTotal());
    }
}
