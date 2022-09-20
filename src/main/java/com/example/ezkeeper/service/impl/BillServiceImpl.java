package com.example.ezkeeper.service.impl;

import com.example.ezkeeper.model.Bill;
import com.example.ezkeeper.mappers.BillMapper;
import com.example.ezkeeper.service.BillService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.stereotype.Service;

/**
 * <p>
 * 账单表

 服务实现类
 * </p>
 *
 * @author MouMeo
 * @since 2022-09-17
 */
@Service
public class BillServiceImpl extends ServiceImpl<BillMapper, Bill> implements BillService {

}
