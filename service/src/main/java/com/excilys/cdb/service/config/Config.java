package com.excilys.cdb.service.config;

import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Import;

@Configuration
@Import({ com.excilys.cdb.persistence.config.Config.class })
@ComponentScan("com.excilys.cdb.service.service")
public class Config {

}
