package com.galenyoder.spring.security.sample;

import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Import;

import edu.liberty.config.spring.tomcat.SslTomcatConfig;

@Configuration
@Import(SslTomcatConfig.class)
public class ServerConfig {

}