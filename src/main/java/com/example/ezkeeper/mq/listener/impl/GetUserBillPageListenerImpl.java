package com.example.ezkeeper.mq.listener.impl;

import com.alibaba.fastjson.JSONObject;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.example.ezkeeper.Util.JWTUtil;
import com.example.ezkeeper.mappers.BillMapper;
import com.example.ezkeeper.model.Bill;
import com.example.ezkeeper.mq.RabbitMQConfig;
import com.example.ezkeeper.mq.listener.GetUserBillPageListener;
import org.springframework.amqp.rabbit.annotation.Queue;
import org.springframework.amqp.rabbit.annotation.RabbitHandler;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.messaging.handler.annotation.SendTo;
import org.springframework.stereotype.Component;

@Component
public class GetUserBillPageListenerImpl implements GetUserBillPageListener {
    @Autowired(required = false)
    BillMapper billMapper;

    @Value("${bill.pageSize}")
    Integer pageSize;

    @Override
    public Page<Bill> browseBills(JSONObject obj) {
        Page<Bill> page = new Page<>(obj.getInteger("pageIndex"),pageSize);
        billMapper.getPageByUserId(page, obj.getInteger("userId"));
        return page;
    }
}
