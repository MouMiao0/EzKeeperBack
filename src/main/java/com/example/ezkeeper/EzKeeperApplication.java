package com.example.ezkeeper;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;


@SpringBootApplication
@MapperScan("com.example.ezkeeper.mappers")
public class EzKeeperApplication {

    public static void main(String[] args) {
        SpringApplication.run(EzKeeperApplication.class, args);
    }

}
