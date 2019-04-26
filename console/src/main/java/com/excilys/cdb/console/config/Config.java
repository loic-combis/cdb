package com.excilys.cdb.console.config;

import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Import;

@Configuration
@Import({ com.excilys.cdb.service.config.Config.class })
@ComponentScan("com.excilys.cdb.console.cli")
public class Config {

}
