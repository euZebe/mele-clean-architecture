package me.euzebe.mele;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;

import me.euzebe.mele.infrastructure.DrawMemoryRepository;
import me.euzebe.mele.spi.DrawsCatalog;
import me.euzebe.mele.usecase.generatedraw.GenerateDraw;
import me.euzebe.mele.usecase.generatedraw.GenerateDrawController;
import me.euzebe.mele.usecase.generatedraw.JsonMapper;

@SpringBootApplication
public class MeleApplication {

	public static void main(String[] args) {
		SpringApplication.run(MeleApplication.class, args);
	}

	@Bean
	GenerateDraw getGenerateDrawBean() {
	    return new GenerateDrawController(getDrawsCatalog());
	}

	@Bean
	JsonMapper getJsonMapper() {
	    return new JsonMapper();
	}

	@Bean
	DrawsCatalog getDrawsCatalog() {
	    return new DrawMemoryRepository();
	}
}
