package com.example.demo.service.impl;

import com.baomidou.mybatisplus.core.conditions.Wrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.example.demo.entity.Securities;
import com.example.demo.entity.Type;
import com.example.demo.mapper.SecuritiesMapper;
import com.example.demo.mapper.TypeMapper;
import com.example.demo.service.SecuritiesService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;

/**
 * <p>
 *  服务实现类
 * </p>
 *
 * @author demo
 * @since 2023-04-09
 */
@Service
public class SecuritiesServiceImpl extends ServiceImpl<SecuritiesMapper, Securities> implements SecuritiesService {
    @Resource//如果下面userMapper爆红就用@Resourse
    private SecuritiesMapper securitiesMapper;

    @Override
    public IPage pageC1(IPage<Securities> page, Wrapper wrapper) {
        return securitiesMapper.pageC1(page, wrapper);
    }
}
