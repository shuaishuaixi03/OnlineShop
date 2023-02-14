package com.wcx.pay;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
@MapperScan("com.wcx.pay.dao")
public class WcxPayApplication {

    public static void main(String[] args) {
        SpringApplication.run(WcxPayApplication.class, args);
    }

}
