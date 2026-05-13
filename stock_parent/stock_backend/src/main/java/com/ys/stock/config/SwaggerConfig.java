package com.ys.stock.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import springfox.documentation.builders.ApiInfoBuilder;
import springfox.documentation.builders.PathSelectors;
import springfox.documentation.builders.RequestHandlerSelectors;
import springfox.documentation.service.ApiInfo;
import springfox.documentation.service.Contact;
import springfox.documentation.spi.DocumentationType;
import springfox.documentation.spring.web.plugins.Docket;

/**
 * @author 杨森
 * @version 1.0
 * @date 2023/7/14 14:43
 * @swagger配置
 */
@Configuration
public class SwaggerConfig {
    @Bean
    public Docket customdocket(){
        return new Docket(DocumentationType.SWAGGER_2)
                .apiInfo(apiInfo())
                .select()
                .apis(RequestHandlerSelectors.basePackage("com.ys.stock.controller"))
                .paths(PathSelectors.any())
                .build();
    }

    private ApiInfo apiInfo() {
        Contact contact = new Contact("今日指数股票", "http://localhost:8081/swagger-ui.html", "952332000@qq.com");
        return new ApiInfoBuilder()
                .title("今日指数API文档")
                .description("今日指数的API文档")
                .contact(contact)   // 联系方式
                .version("1.1.0")  // 版本
                .build();
    }

}
