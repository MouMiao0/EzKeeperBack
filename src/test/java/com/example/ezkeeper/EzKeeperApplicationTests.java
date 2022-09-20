package com.example.ezkeeper;

import org.apache.shiro.crypto.hash.SimpleHash;
import org.junit.jupiter.api.Test;
import org.mybatis.spring.annotation.MapperScan;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.test.context.SpringBootTest;

@MapperScan("com.example.ezkeeper.mappers")
@SpringBootTest
class EzKeeperApplicationTests {

    @Value("${CredentialsMatcher.A}")
    private String algorithmName;

    @Value("${CredentialsMatcher.C}")
    private int iterationCount;

    @Test
    void contextLoads() {
        String hashPW = new SimpleHash(algorithmName, "aa", 21, iterationCount).toHex();
        System.out.println(hashPW);

    }

}
