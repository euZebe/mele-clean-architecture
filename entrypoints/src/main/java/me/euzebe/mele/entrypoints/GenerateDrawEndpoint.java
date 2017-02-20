package me.euzebe.mele.entrypoints;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api")
public class GenerateDrawEndpoint {

	@GetMapping("/")
	public String sayHi() {
		return "Hi all!";
	}

	@GetMapping("/generateDraw")
	// public Map<String, String> generateDraw(String... participants) {
	public String generateDraw(String... participants) {
		return "<h1>generate draw</h1>";
	}

}
