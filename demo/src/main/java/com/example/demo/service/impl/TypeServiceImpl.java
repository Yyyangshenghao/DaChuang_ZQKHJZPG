package com.example.demo.service.impl;

import com.baomidou.mybatisplus.core.conditions.Wrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.example.demo.entity.Class1;
import com.example.demo.entity.Type;
import com.example.demo.entity.User;
import com.example.demo.mapper.Class1Mapper;
import com.example.demo.mapper.TypeMapper;
import com.example.demo.mapper.UserMapper;
import com.example.demo.service.TypeService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;

/**
 * <p>
 *  服务实现类
 * </p>
 *
 * @author demo
 * @since 2023-04-08
 */
@Service
public class TypeServiceImpl extends ServiceImpl<TypeMapper, Type> implements TypeService {
    @Resource//如果下面userMapper爆红就用@Resourse
    private TypeMapper typeMapper;

    @Override
    public IPage pageC1(IPage<Type> page, Wrapper wrapper) {
        return typeMapper.pageC1(page, wrapper);
    }
}
