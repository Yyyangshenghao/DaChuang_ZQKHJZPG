package com.example.demo.service;

import com.baomidou.mybatisplus.core.conditions.Wrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.example.demo.entity.Record;
import com.baomidou.mybatisplus.extension.service.IService;
import com.example.demo.entity.Securities;

/**
 * <p>
 *  服务类
 * </p>
 *
 * @author demo
 * @since 2023-04-09
 */
public interface RecordService extends IService<Record> {
    IPage pageC1(IPage<Record> page, Wrapper wrapper);
}
