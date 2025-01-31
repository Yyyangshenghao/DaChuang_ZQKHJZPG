package com.example.demo.mapper;

import com.baomidou.mybatisplus.core.conditions.Wrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.core.toolkit.Constants;
import com.example.demo.entity.Class1;
import com.example.demo.entity.Type;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

/**
 * <p>
 *  Mapper 接口
 * </p>
 *
 * @author demo
 * @since 2023-04-08
 */
@Mapper
public interface TypeMapper extends BaseMapper<Type> {
    IPage pageC1(IPage<Type> page, @Param(Constants.WRAPPER) Wrapper wrapper);
}
