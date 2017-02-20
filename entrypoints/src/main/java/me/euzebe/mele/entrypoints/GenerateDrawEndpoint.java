package me.euzebe.mele.entrypoints;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javaslang.control.Option;
import me.euzebe.mele.usecase.generatedraw.Draw;
import me.euzebe.mele.usecase.generatedraw.GenerateDraw;

@RestController
@RequestMapping("/api")
public class GenerateDrawEndpoint {

    @Autowired
    private GenerateDraw generateDrawController;

	@GetMapping("/")
	public String sayHi() {
		return "Hi all!";
	}

	@GetMapping("/generateDraw")
	// public Map<String, String> generateDraw(String... participants) {
	public String generateDraw(String... participants) {
        Option<Draw> draw = generateDrawController.generateDraw(participants);
        return draw.toString();
	}

}
