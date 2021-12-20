package me.wemeet.kele;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
@MapperScan("me.wemeet.kele.mapper")
public class KeleApplication {

    public static void main(String[] args) {
        SpringApplication.run(KeleApplication.class, args);
    }

}
