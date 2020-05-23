package com.cfl.myproject.config;

import lombok.Getter;
import lombok.Setter;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

@Component
@Getter
@Setter
public class ApplicationValue {

    @Value("${spring.mail.username}")
    public String senderEmail;

    @Value("${customize.exception.email}")
    public String receiveEmail;

    @Value("${spring.application.name}")
    public String applicationName;
}
