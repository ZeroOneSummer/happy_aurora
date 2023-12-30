package com.aurora.happy;

import com.aurora.happy.annotation.EnableApiLog;
import lombok.extern.slf4j.Slf4j;
import org.mybatis.spring.annotation.MapperScan;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.context.event.ApplicationReadyEvent;
import org.springframework.context.ApplicationListener;
import springfox.documentation.swagger2.annotations.EnableSwagger2;

@Slf4j
@EnableApiLog   //自定义开关
@EnableSwagger2
@MapperScan(basePackages = "com.aurora.happy.mapper")
@SpringBootApplication
public class WebApp implements ApplicationListener<ApplicationReadyEvent> {

    @Value("${server.port:-1}")
    Integer port;

    public static void main(String[] args) {
        SpringApplication.run(WebApp.class, args);
    }

    @Override
    public void onApplicationEvent(ApplicationReadyEvent event) {
        log.info("{} 启功成功，端口号: {}", this.getClass().getName().split("\\$\\$")[0], port);
    }
}
