package com.example.ezkeeper;

import com.example.ezkeeper.service.BillService;
import com.example.ezkeeper.service.impl.BillServiceImpl;
import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.Test;
import org.junit.runner.RunWith;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

@Slf4j
@RunWith(SpringRunner.class)
@SpringBootTest
class EzKeeperApplicationTests {
    @Autowired
    BillService billService;

    @Test
    public void testMapper(){
        billService.browseBills(0);
        billService.browseBills(0);
        billService.browseBills(0);
    }


}
