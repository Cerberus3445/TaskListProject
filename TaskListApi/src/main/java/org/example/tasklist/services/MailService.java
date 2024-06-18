package org.example.tasklist.services;

import org.example.tasklist.domain.MailType;
import org.example.tasklist.domain.user.User;

import java.util.Properties;

public interface MailService {

    void sendEmail(User user, MailType type, Properties params);
}
