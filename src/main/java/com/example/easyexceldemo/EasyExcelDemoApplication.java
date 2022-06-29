package com.example.easyexceldemo;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

/**
 * @author Administrator
 */
@SpringBootApplication
@MapperScan(basePackages = {"com.example.easyexceldemo.dao"})
public class EasyExcelDemoApplication {

    public static void main(String[] args) {
        SpringApplication.run(EasyExcelDemoApplication.class, args);
    }

}
