package com.sh.mihomo.auto.proxy;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.scheduling.annotation.EnableScheduling;

/**
 * @author SH
 */
@EnableScheduling
@SpringBootApplication
public class MihomoAutoProxyApplication {

	static void main(String[] args) {
		SpringApplication.run(MihomoAutoProxyApplication.class, args);
	}

}
