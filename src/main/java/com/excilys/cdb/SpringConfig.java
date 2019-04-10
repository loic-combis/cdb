package com.excilys.cdb;

import javax.sql.DataSource;

import org.springframework.boot.jdbc.DataSourceBuilder;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;

import com.excilys.cdb.ui.cli.CLIController;
import com.excilys.cdb.util.PropertyReader;

@Configuration
@ComponentScan("com.excilys.cdb.persistence.dao")
@ComponentScan("com.excilys.cdb.mapper")
@ComponentScan("com.excilys.cdb.service")
public class SpringConfig {

    @Bean
    public DataSource getDataSource() {
        PropertyReader reader = new PropertyReader();
        return DataSourceBuilder.create()
            .driverClassName(reader.get("app.datasource.driver-class-name"))
            .password(reader.get("app.datasource.password"))
            .username(reader.get("app.datasource.username"))
            .url(reader.get("app.datasource.url"))
            .build();
    }

    @Bean
    public CLIController getCLI() {
        return new CLIController();
    }
}
