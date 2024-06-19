Проект TODO листа с микросервисной архитектурой.

Стэк проекта: Spring Boot, Spring Security(Basic Auth со стороны Rest сервиса), Spring Cloud(Eureka, Load Balancer, Feign. Resilience4j, Configuration), Spring Data Jpa, Spring REST, LomBok, Thymeleaf, Maven, Mail, Hibernate, PostgreSQL, Swagger, Liquibase, Validation, Docker.


url:
http://localhost:9001 - web часть проекта;
http://localhost:9002, http://localhost:9003 - 2 REST API. Почти идентичны(в 9003 отсутствует документация). Load Balancer распределяет нагрузку между этими сервисами;
http://localhost:9002/swagger-ui/index.html#/ - документация по REST API;
http://localhost:8761 - Eureka;
http://localhost:8888 - Cloud Config Server(берёт данные с https://github.com/Cerberus3445/CloudConfig);
Zipkin - в стадии разработки;