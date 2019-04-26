package com.excilys.cdb.persistence.config;

import java.util.Properties;

import javax.sql.DataSource;

import org.hibernate.SessionFactory;
import org.springframework.boot.jdbc.DataSourceBuilder;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.orm.hibernate5.HibernateTransactionManager;
import org.springframework.orm.hibernate5.LocalSessionFactoryBean;
import org.springframework.transaction.annotation.EnableTransactionManagement;

import com.excilys.cdb.persistence.util.PropertyReader;

@Configuration
@EnableTransactionManagement
@ComponentScan("com.excilys.cdb.persistence.dao")
public class Config {

    /**
     * Bean initializer.
     *
     * @return LocalSessionFactoryBean
     */
    @Bean
    public LocalSessionFactoryBean getSessionFactory(DataSource ds) {
        LocalSessionFactoryBean sessionFactory = new LocalSessionFactoryBean();
        sessionFactory.setDataSource(ds);
        sessionFactory.setPackagesToScan("com.excilys.cdb.core");
        sessionFactory.setHibernateProperties(hibernateProperties());
        return sessionFactory;
    }

    private final Properties hibernateProperties() {
        Properties hibernateProperties = new Properties();
        hibernateProperties.setProperty("hibernate.hbm2ddl.auto", "update");
        hibernateProperties.setProperty("hibernate.dialect", "org.hibernate.dialect.MySQL5Dialect");

        return hibernateProperties;
    }

    /**
     * Bean initializer.
     *
     * @param sessionFactory SessionFactory
     * @return HibernateTransactionManager
     */
    @Bean
    public HibernateTransactionManager transactionManager(SessionFactory sessionFactory) {

        HibernateTransactionManager txManager = new HibernateTransactionManager();
        txManager.setSessionFactory(sessionFactory);

        return txManager;
    }

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
}
