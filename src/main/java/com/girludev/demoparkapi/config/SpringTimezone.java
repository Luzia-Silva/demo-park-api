package com.girludev.demoparkapi.config;

import jakarta.annotation.PostConstruct;
import org.springframework.context.annotation.Configuration;

import java.util.TimeZone;

@Configuration
public class SpringTimezone {
	
	@PostConstruct
	public void timezoneConfig(){
		TimeZone.setDefault(TimeZone.getTimeZone("america/Sao_Paulo"));
	}
}
