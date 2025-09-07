package br.dev.mission.simplewallet;

import java.util.TimeZone;
import javax.annotation.PostConstruct;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class SimpleWalletApplication {
	@Value("${spring.jackson.time-zone:America/Sao_Paulo}")
	private String timeZone;

	@PostConstruct
	public void init() {
		TimeZone.setDefault(TimeZone.getTimeZone(timeZone));
	}

	public static void main(String[] args) {
		SpringApplication.run(SimpleWalletApplication.class, args);
	}
}
