package com.example.demo.controller;


import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.example.demo.common.QueryPageParam;
import com.example.demo.common.Result;
import com.example.demo.entity.Record;
import com.example.demo.entity.Securities;
import com.example.demo.service.RecordService;
import com.example.demo.service.SecuritiesService;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;

import org.springframework.web.bind.annotation.RestController;

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
@RequestMapping("/record")
public class RecordController {

    @Autowired
    private RecordService recordService;
    @Autowired
    private SecuritiesService securitiesService;

    @PostMapping("/listPage")
    //自定义类QueryPageParam方法，将数据封装到类中，不固定的数据依然要用HashMap方法
    public Result listPage(@RequestBody QueryPageParam query){
        HashMap param = query.getParam();
        String name = (String)param.get("name");
        String class1 = (String)param.get("securitiesclass");
        String type = (String)param.get("securitiestype");
        String roleId = (String)param.get("roleId");
        String userId = (String) param.get("userId");
        Page<Record> page = new Page();
        //上面参数加了，下面可以不用加这两条
        page.setCurrent(query.getPageNum());
        page.setSize(query.getPageSize());

        QueryWrapper<Record> queryWrapper = new QueryWrapper();
        queryWrapper.apply(" a.security = b.id and b.securitiesClass = c.id and b.securitiesType = d.id ");

        if("2".equals(roleId)){
            queryWrapper.apply("a.userId= "+userId);
        }

        if(StringUtils.isNotBlank(name) && !"null".equals(name)){
            //lambdaQueryWrapper.like(Record::getName, name);
            queryWrapper.like("b.name", name);
        }
        if(StringUtils.isNotBlank(class1) && !"null".equals(class1)){
            //lambdaQueryWrapper.eq(Record::getSecuritiesclass, class1);
            queryWrapper.eq("c.id", class1);
        }
        if(StringUtils.isNotBlank(type) && !"null".equals(type)){
            //lambdaQueryWrapper.eq(Record::getSecuritiestype, type);
            queryWrapper.eq("d.id", type);
        }

        IPage result = recordService.pageC1(page, queryWrapper);
        return Result.suc(result.getRecords(), result.getTotal());
    }

    //新增
    @PostMapping("/save")
    public Result save(@RequestBody Record record){
        Securities securities = securitiesService.getById(record.getSecurity());
        int n = record.getCount();
        if("2".equals(record.getAction())){
            n = -n;
            record.setCount(n);
        }
        int num = securities.getCount()+n;
        securities.setCount(num);
        securitiesService.updateById(securities);
        return recordService.save(record) ? Result.suc():Result.fail();
    }
}
