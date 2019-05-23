package com.excilys.service.config;

import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Import;

@Configuration
@Import({ com.excilys.persistence.config.Config.class })
@ComponentScan("com.excilys.service.service")
public class Config {

}
