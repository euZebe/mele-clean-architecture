package me.euzebe.mele;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;

import me.euzebe.mele.usecase.generatedraw.GenerateDraw;
import me.euzebe.mele.usecase.generatedraw.GenerateDrawController;

@SpringBootApplication
public class MeleApplication {

	public static void main(String[] args) {
		SpringApplication.run(MeleApplication.class, args);
	}

	@Bean
	GenerateDraw getGenerateDrawBean() {
	    return new GenerateDrawController();
	}
}
