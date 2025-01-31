package com.example.demo.service.impl;

import com.baomidou.mybatisplus.core.conditions.Wrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.example.demo.entity.Class1;
import com.example.demo.entity.User;
import com.example.demo.mapper.Class1Mapper;
import com.example.demo.service.Class1Service;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * <p>
 *  服务实现类
 * </p>
 *
 * @author demo
 * @since 2023-04-08
 */
@Service
public class Class1ServiceImpl extends ServiceImpl<Class1Mapper, Class1> implements Class1Service {
    @Autowired//如果下面爆红就用@Resourse
    private Class1Mapper class1Mapper;

    @Override
    public IPage pageC1(IPage<Class1> page, Wrapper wrapper) {
        return class1Mapper.pageC1(page, wrapper);
    }
}
