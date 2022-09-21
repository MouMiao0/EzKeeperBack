package com.example.ezkeeper;

import com.baomidou.mybatisplus.test.autoconfigure.MybatisPlusTest;
import com.example.ezkeeper.mappers.UserTokenMapper;
import com.example.ezkeeper.model.UserToken;
import com.example.ezkeeper.service.UserTokenService;
import lombok.extern.slf4j.Slf4j;
import org.apache.shiro.crypto.hash.SimpleHash;
import org.apache.shiro.util.ByteSource;
import org.assertj.core.internal.Bytes;
import org.junit.jupiter.api.Test;
import org.junit.runner.RunWith;
import org.mybatis.spring.annotation.MapperScan;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

@Slf4j
@RunWith(SpringRunner.class)
@SpringBootTest
class EzKeeperApplicationTests {

    @Autowired(required = false)
    UserTokenMapper userTokenMapper;

    @Test
    public  void testMapper(){
        UserToken userToken = userTokenMapper.getTokenByName("aa");
        log.info(userToken.toString());
    }

}
