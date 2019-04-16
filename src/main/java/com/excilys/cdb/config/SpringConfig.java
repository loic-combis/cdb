package com.excilys.cdb.config;

import javax.sql.DataSource;

import org.springframework.boot.jdbc.DataSourceBuilder;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;

import com.excilys.cdb.util.PropertyReader;

@Configuration
@ComponentScan("com.excilys.cdb.persistence.dao")
@ComponentScan("com.excilys.cdb.mapper")
@ComponentScan("com.excilys.cdb.service")
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
}
