package com.excilys.cdb.config;

import java.util.Locale;

import javax.sql.DataSource;

import org.springframework.boot.jdbc.DataSourceBuilder;
import org.springframework.context.MessageSource;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.support.ReloadableResourceBundleMessageSource;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.web.servlet.LocaleResolver;
import org.springframework.web.servlet.i18n.CookieLocaleResolver;

import com.excilys.cdb.util.PropertyReader;

@Configuration
@ComponentScan("com.excilys.cdb.persistence.dao")
@ComponentScan("com.excilys.cdb.mapper")
@ComponentScan("com.excilys.cdb.service")
@ComponentScan("com.excilys.cdb.validator")
@ComponentScan("com.excilys.cdb.ui.cli")
public class SpringConfig {

    /**
     * Bean initializer.
     *
     * @return DataSource
     */
    @Bean
    public DataSource getDataSource() {
        PropertyReader reader = new PropertyReader();
        return DataSourceBuilder.create().driverClassName(reader.get("app.datasource.driver-class-name"))
                .password(reader.get("app.datasource.password")).username(reader.get("app.datasource.username"))
                .url(reader.get("app.datasource.url")).build();
    }

    /**
     * Bean initializer.
     *
     * @param ds DataSource
     * @return NamedParameterJdbcTemplate
     */
    @Bean
    public NamedParameterJdbcTemplate getTemplate(DataSource ds) {
        return new NamedParameterJdbcTemplate(ds);
    }

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
}
