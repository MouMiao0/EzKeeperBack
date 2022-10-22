package com.example.ezkeeper.service.impl;

import com.alibaba.fastjson.JSONObject;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.example.ezkeeper.Util.JWTUtil;
import com.example.ezkeeper.model.Bill;
import com.example.ezkeeper.mappers.BillMapper;
import com.example.ezkeeper.mq.RabbitMQConfig;
import com.example.ezkeeper.service.BillService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.amqp.core.AmqpTemplate;
import org.springframework.amqp.core.Message;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.messaging.converter.SimpleMessageConverter;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

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
    @Value("${bill.pageSize}")
    Integer pageSize;

    @Autowired
    AmqpTemplate amqpTemplate;

    @Override
    public Page<Bill> loggingBill(Bill bill) {
        save(bill);
        //据用户id查询账目
        return browseBills(1);
    }

    @Override
    public Page<Bill> browseBills(int pageIndex) {
        Page<Bill> billPage;
        //据用户id查询账目
        JSONObject object = new JSONObject();
        object.put("userId",JWTUtil.getUserIdBySubject());
        object.put("pageIndex",pageIndex);
        billPage = (Page<Bill>) amqpTemplate.convertSendAndReceive(RabbitMQConfig.GET_USER_BILL_PAGE, object);
        return billPage;
    }

    @Override
    public List<Bill> getBillsByUser() {
        return baseMapper.getAllByUserId(JWTUtil.getUserIdBySubject());
    }
}
