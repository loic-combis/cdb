package com.excilys.cdb.webapp.config;

import java.util.Locale;

import org.springframework.context.MessageSource;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Import;
import org.springframework.context.support.ReloadableResourceBundleMessageSource;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.servlet.LocaleResolver;
import org.springframework.web.servlet.i18n.CookieLocaleResolver;

@Configuration
@Import({ com.excilys.cdb.service.config.Config.class, com.excilys.cdb.binding.config.Config.class })
@ComponentScan("com.excilys.cdb.webapp.controller")
@ComponentScan("com.excilys.cdb.webapp.validator")
public class Config {

    /**
     * Bean initializer.
     *
     * @return LocaleResolver
     */
    @Bean
    public LocaleResolver localeResolver() {
        CookieLocaleResolver r = new CookieLocaleResolver();
        r.setDefaultLocale(Locale.ENGLISH);
        r.setCookieName("localeInfo");
        r.setCookieMaxAge(24 * 60 * 60);
        return r;
    }

    /**
     * Bean initializer.
     *
     * @return
     */
    @Bean
    public MessageSource messageSource() {
        ReloadableResourceBundleMessageSource messageSource = new ReloadableResourceBundleMessageSource();
        messageSource.setBasename("classpath:i18n/messages");
        messageSource.setUseCodeAsDefaultMessage(true);
        messageSource.setDefaultEncoding("UTF-8");
        return messageSource;
    }

    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }
}
