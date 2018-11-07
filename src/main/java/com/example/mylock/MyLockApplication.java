package com.example.mylock;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import tk.mybatis.spring.annotation.MapperScan;

@MapperScan(basePackages = "com.example.mylock.mapper")
@SpringBootApplication
public class MyLockApplication {

    public static void main(String[] args) {
        SpringApplication.run(MyLockApplication.class, args);
    }
}
