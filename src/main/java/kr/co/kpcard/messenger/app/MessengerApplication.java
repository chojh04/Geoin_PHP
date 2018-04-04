package kr.co.kpcard.messenger.app;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.PropertySource;

@SpringBootApplication
@PropertySource("classpath:application.properties")
public class MessengerApplication {

	private static final Logger logger = LoggerFactory.getLogger(MessengerApplication.class);

	
	public static void main(String[] args) 
	{		
		logger.info("==========================> MessengerApplication Application is starting!!! ");
		
		SpringApplication.run(MessengerApplication.class, args);
	}
}
