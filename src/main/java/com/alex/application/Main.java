package com.alex.application;

import com.alex.application.configuration.AppConfig;
import com.alex.application.service.EmployeeService;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;

public class Main {
    private static final Logger logger = LogManager.getLogger(Main.class);

    public static void main(String[] args) {
        logger.info("Starting Application...");
        ApplicationContext context = new AnnotationConfigApplicationContext(AppConfig.class);
        EmployeeService service = context.getBean(EmployeeService.class);
        Application application = context.getBean(Application.class);
    }
}
