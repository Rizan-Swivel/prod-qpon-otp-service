package com.swivel.qpon.otp.configuration;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.support.ResourceBundleMessageSource;

/**
 * ResourceBundleMessageSourceBean
 */
@Configuration
public class ResourceBundleMessageSourceBean {

    /**
     * Loads the message bundle properties
     *
     * @return ResourceBundleMessageSource
     */
    @Bean
    public ResourceBundleMessageSource messageSource() {
        var resourceBundleMessageSource = new ResourceBundleMessageSource();
        resourceBundleMessageSource.setBasenames("success", "error", "email_template", "sms_template");
        resourceBundleMessageSource.setDefaultEncoding("UTF-8");
        resourceBundleMessageSource.setUseCodeAsDefaultMessage(true);
        return resourceBundleMessageSource;
    }

}
