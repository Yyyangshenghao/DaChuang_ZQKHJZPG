package com.example.demo.service.impl;

import com.baomidou.mybatisplus.core.conditions.Wrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.example.demo.entity.Record;
import com.example.demo.entity.Securities;
import com.example.demo.mapper.RecordMapper;
import com.example.demo.mapper.SecuritiesMapper;
import com.example.demo.service.RecordService;
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
public class RecordServiceImpl extends ServiceImpl<RecordMapper, Record> implements RecordService {
    @Resource//如果下面userMapper爆红就用@Resourse
    private RecordMapper recordMapper;

    @Override
    public IPage pageC1(IPage<Record> page, Wrapper wrapper) {
        return recordMapper.pageC1(page, wrapper);
    }
}
