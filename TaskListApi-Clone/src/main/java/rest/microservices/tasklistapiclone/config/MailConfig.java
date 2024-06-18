package rest.microservices.tasklistapiclone.config;

import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.JavaMailSenderImpl;
import rest.microservices.tasklistapiclone.props.MailProperties;

@Configuration
@RequiredArgsConstructor
public class MailConfig {

    private final MailProperties mailProperties;

    @Bean
    public JavaMailSender mailSender(){
        JavaMailSenderImpl javaMailSender = new JavaMailSenderImpl();
        javaMailSender.setHost(mailProperties.getHost());
        javaMailSender.setPort(mailProperties.getPort());
        javaMailSender.setUsername(mailProperties.getUsername());
        javaMailSender.setPassword(mailProperties.getPassword());
        javaMailSender.setJavaMailProperties(mailProperties.getProperties());
        javaMailSender.getJavaMailProperties();
        return javaMailSender;
    }
}
