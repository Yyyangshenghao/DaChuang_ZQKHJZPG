package com.example.demo.service;

import com.example.demo.entity.StockData;
import com.baomidou.mybatisplus.extension.service.IService;
import com.example.demo.mapper.StockDataMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
/**
 * <p>
 *  服务类
 * </p>
 *
 * @author demo
 * @since 2023-04-26
 */
public interface StockDataService extends IService<StockData> {

}