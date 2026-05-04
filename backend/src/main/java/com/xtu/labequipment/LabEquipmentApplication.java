package com.xtu.labequipment;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@MapperScan("com.xtu.labequipment.mapper")
@SpringBootApplication
public class LabEquipmentApplication {
    public static void main(String[] args) {
        SpringApplication.run(LabEquipmentApplication.class, args);
    }
}
