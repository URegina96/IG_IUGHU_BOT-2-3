package io.proj3ct.IG_IUGHU_BOT;

import org.springframework.boot.SpringApplication;

import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Service;


@Component
@SpringBootApplication
@Service
public class IgIughuBotApplication {

	public static void main(String[] args) {
		SpringApplication.run(IgIughuBotApplication.class, args);
	}

}
