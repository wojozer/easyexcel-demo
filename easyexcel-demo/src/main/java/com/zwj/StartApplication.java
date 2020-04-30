package com.zwj;


import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.ConfigurableApplicationContext;
import org.springframework.scheduling.annotation.EnableAsync;
import org.springframework.scheduling.annotation.EnableScheduling;


/**
 * 主启动类
 * 与WebApplication相同
 * @SpringBootApplication 注解的程序入口类已经包含@Configuration，不含@ServletComponentScan
 * @author admin
 *
 */
@SpringBootApplication
@EnableAsync
public class StartApplication {
	private static final Logger log = LoggerFactory.getLogger(StartApplication.class);


	public static void main(String[] args) {
		log.info("启动服务中...");
		ConfigurableApplicationContext context = SpringApplication.run(StartApplication.class, args);
		log.info("服务已启动...");
	}



}
