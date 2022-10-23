package com.example.ezkeeper.mq.listener;

import com.alibaba.fastjson.JSONObject;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.example.ezkeeper.model.Bill;
import com.example.ezkeeper.mq.RabbitMQConfig;
import org.springframework.amqp.rabbit.annotation.Queue;
import org.springframework.amqp.rabbit.annotation.RabbitHandler;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.messaging.handler.annotation.SendTo;

/**
 * 查询账单页面监听
 */
@RabbitListener(queuesToDeclare = @Queue(RabbitMQConfig.GET_USER_BILL_PAGE))
public interface GetUserBillPageListener {
    /**
     * 返回页面数据
     * @param
     * @return
     */
    @RabbitHandler
    @SendTo(RabbitMQConfig.GET_USER_BILL_PAGE)
    Page<Bill> browseBills(JSONObject obj);
}
