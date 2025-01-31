package com.example.demo.mapper;

import com.baomidou.mybatisplus.core.conditions.Wrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.core.toolkit.Constants;
import com.example.demo.entity.Record;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.example.demo.entity.Securities;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

/**
 * <p>
 *  Mapper 接口
 * </p>
 *
 * @author demo
 * @since 2023-04-09
 */
@Mapper
public interface RecordMapper extends BaseMapper<Record> {
    IPage pageC1(IPage<Record> page, @Param(Constants.WRAPPER) Wrapper wrapper);
}
