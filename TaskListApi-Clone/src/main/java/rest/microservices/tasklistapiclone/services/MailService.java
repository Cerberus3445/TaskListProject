package rest.microservices.tasklistapiclone.services;

import rest.microservices.tasklistapiclone.domain.MailType;
import rest.microservices.tasklistapiclone.domain.user.User;

import java.util.Properties;

public interface MailService {

    void sendEmail(User user, MailType type, Properties params);
}
