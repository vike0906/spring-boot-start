package com.hengxunda.vike0906.config;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import springfox.documentation.builders.ApiInfoBuilder;
import springfox.documentation.builders.PathSelectors;
import springfox.documentation.builders.RequestHandlerSelectors;
import springfox.documentation.service.ApiInfo;
import springfox.documentation.service.Contact;
import springfox.documentation.spi.DocumentationType;
import springfox.documentation.spring.web.plugins.Docket;
import springfox.documentation.swagger2.annotations.EnableSwagger2;

@Configuration
@EnableSwagger2
public class SwaggerConfig {
    @Value(value = "${online}")
    private Boolean online;

    @Bean
    public Docket api(){
        return new Docket(DocumentationType.SWAGGER_2)
                .groupName("Manager System")
                .enable(!online)
                .apiInfo(apiInfo())
                .select()
                .apis(RequestHandlerSelectors.basePackage("com.hengxunda.follow8.manager.controller"))
                .paths(PathSelectors.any())
                .build();
    }

    public ApiInfo apiInfo(){
        return new ApiInfoBuilder()
                .title("跟单吧管理后台API")
                .contact(new Contact("hengxunda","hengxunda.com",""))
                .version("0.0.1")
                .build();
    }

//    @Bean
//    public Docket accessToken() {
//        return new Docket(DocumentationType.SWAGGER_2)
//                .groupName("api")// 定义组
//                .select() // 选择那些路径和 api 会生成 document
//                .apis(RequestHandlerSelectors.basePackage("com.hengxunda.follow8.manager")) // 拦截的包路径
//                .paths(PathSelectors.regex("/*/.*"))// 拦截的接口路径
//                .build() // 创建
//                .apiInfo(apiInfo()); // 配置说明
//    }
//
//    private ApiInfo apiInfo() {
//        return new ApiInfoBuilder()//
//                .title("跟单吧API文档")// 标题
//                .description("跟单吧API")// 描述
//                .termsOfServiceUrl("http://www.hengxunda.com")//
//                .contact(new Contact("follow8", "http://www.hengxunda.com", "421580052@qq.com"))// 联系
//                .version("1.0")// 版本
//                .build();
//    }

}
