package com.example.expense_manager_riverty.config;

import jakarta.servlet.Servlet;
import org.h2.server.web.JakartaWebServlet;
import org.springframework.boot.web.servlet.ServletRegistrationBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * Explicitly registers the H2 Console servlet to ensure it is available at /h2-console/*.
 * This is useful when auto-configuration does not kick in due to dependency version mismatches.
 */
@Configuration
public class H2ConsoleConfig {

    @Bean
    public ServletRegistrationBean<Servlet> h2ConsoleServletRegistration() {
        ServletRegistrationBean<Servlet> registration =
                new ServletRegistrationBean<>(new JakartaWebServlet(), "/h2-console/*");
        registration.setName("H2Console");
        registration.setLoadOnStartup(1);
        return registration;
    }
}
