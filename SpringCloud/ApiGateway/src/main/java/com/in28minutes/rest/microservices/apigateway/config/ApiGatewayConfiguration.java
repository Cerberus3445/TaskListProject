package com.in28minutes.rest.microservices.apigateway.config;
import org.springframework.cloud.gateway.route.RouteLocator;
import org.springframework.cloud.gateway.route.builder.RouteLocatorBuilder;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class ApiGatewayConfiguration {

    @Bean
    public RouteLocator gatewayRouter(RouteLocatorBuilder builder){
        return builder
                .routes()
                .route(p -> p
                        .path("/get") //если переходим по этой ссылке , то перенаправляем на ссылку ниже
                        .filters(f -> f.addRequestHeader("MyHeader", "MyURI"))
                        .uri("http://httpbin.org:80")) //на этот)
                .route(p -> p.path("/currency-exchange/**") //если запрос начинается с такого адреса
                        .uri("lb://currency-exchange")) //то регистрируемся на Eureka как currency-exchange и выполняем Load Balancing, запрос идёт на сервис с этим именем
                .route(p -> p.path("/currency-conversion/**")
                        .uri("lb://conversion-service"))
                .route(p -> p.path("/currency-conversion-feign/**")
                        .uri("lb://conversion-service"))
//                .route(p -> p.path("/currency-conversion-new/**")
//                        .filters(f -> f.rewritePath("/currency-conversion-new/?<segment>.*", //заменить это с содержим
//                                "/currency-conversion-feign/${segment}")) //на это
//                        .uri("lb://conversion-service"))
                .build();
    }
}
