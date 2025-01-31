package com.example.demo.service;

import com.baomidou.mybatisplus.core.conditions.Wrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.example.demo.entity.Securities;
import com.baomidou.mybatisplus.extension.service.IService;
import com.example.demo.entity.Type;

/**
 * <p>
 *  服务类
 * </p>
 *
 * @author demo
 * @since 2023-04-09
 */
public interface SecuritiesService extends IService<Securities> {
    IPage pageC1(IPage<Securities> page, Wrapper wrapper);
}
