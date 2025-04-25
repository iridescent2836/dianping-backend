package com.SoftwareEngineeringGroup2.config;

import io.swagger.v3.oas.models.OpenAPI;
import io.swagger.v3.oas.models.info.Contact;
import io.swagger.v3.oas.models.info.Info;
import io.swagger.v3.oas.models.info.License;
import io.swagger.v3.oas.models.Components;
import io.swagger.v3.oas.models.security.SecurityScheme;
import io.swagger.v3.oas.models.media.Schema;
import io.swagger.v3.oas.models.parameters.Parameter;
import org.springdoc.core.customizers.OpenApiCustomizer;
import org.springdoc.core.customizers.OperationCustomizer;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.method.HandlerMethod;

import java.util.Map;

@Configuration
public class Knife4jConfig {

    @Bean
    public OpenAPI customOpenAPI() {
        return new OpenAPI()
                .info(new Info()
                        .title("软件工程第二组项目API文档")
                        .version("1.0")
                        .description("软件工程第二组项目接口文档，包含用户管理、商店管理等功能")
                        .contact(new Contact()
                                .name("软件工程第二组")
                                .email("example@example.com"))
                        .license(new License()
                                .name("Apache 2.0")
                                .url("http://www.apache.org/licenses/LICENSE-2.0.html")))
                .components(new Components()
                        .addSecuritySchemes("Bearer Token", new SecurityScheme()
                                .type(SecurityScheme.Type.HTTP)
                                .scheme("bearer")
                                .bearerFormat("JWT")
                                .description("在下方输入JWT令牌")));
    }

    @Bean
    public OpenApiCustomizer openApiCustomizer() {
        return openApi -> {
            // 设置实体类的显示名称
            Map<String, Schema> schemas = openApi.getComponents().getSchemas();
            if (schemas != null) {
                schemas.forEach((name, schema) -> {
                    // 根据实体类名称设置显示名称
                    switch (name) {
                        case "User":
                            schema.setTitle("用户信息");
                            break;
                        case "Store":
                            schema.setTitle("商店信息");
                            break;
                        case "LoginDto":
                            schema.setTitle("登录请求");
                            break;
                        case "RegisterDto":
                            schema.setTitle("注册请求");
                            break;
                        case "StoreRegistrationDto":
                            schema.setTitle("商店注册信息");
                            break;
                    }
                });
            }
        };
    }

    @Bean
    public OperationCustomizer operationCustomizer() {
        return (operation, handlerMethod) -> {
            // 为每个接口添加默认的响应信息
            operation.addParametersItem(new Parameter()
                    .name("Authorization")
                    .description("JWT令牌")
                    .in("header")
                    .required(false)
                    .schema(new Schema().type("string")));
            return operation;
        };
    }
}