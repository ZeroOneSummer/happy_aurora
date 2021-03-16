package com.aurora.happy.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import springfox.documentation.builders.ApiInfoBuilder;
import springfox.documentation.builders.PathSelectors;
import springfox.documentation.builders.RequestHandlerSelectors;
import springfox.documentation.service.Contact;
import springfox.documentation.spi.DocumentationType;
import springfox.documentation.spring.web.plugins.Docket;

@Configuration
public class Swagger2Config {

    @Bean
    public Docket productApi() {
        return new Docket(DocumentationType.SWAGGER_2)
                .select()
                .paths(PathSelectors.any())
                .apis(RequestHandlerSelectors.basePackage("com.aurora.happy.controller"))  //暴露某些接口
                .build()
                .pathMapping("/")
                .apiInfo(new ApiInfoBuilder()
                        .title("北极光计划绝密文档")
                        .description("测试专用")
                        .contact(new Contact("Aurora-2021", null, "666666@qq.com"))
                        .version("1.0")
                        .build()
                );
    }
}