package me.euzebe.mele.entrypoints;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import javaslang.control.Option;
import me.euzebe.mele.usecase.generatedraw.Draw;
import me.euzebe.mele.usecase.generatedraw.GenerateDraw;
import me.euzebe.mele.usecase.generatedraw.JsonMapper;

@RestController
@RequestMapping("/api")
public class GenerateDrawEndpoint {

    @Autowired
    private GenerateDraw generateDrawController;

    @Autowired
    private JsonMapper mapper;

	@GetMapping("/")
	public String sayHi() {
		return "Hi all!";
	}

	// localhost:7777/api/generateDraw?participants=jean,eusebe,ezechiel
	@GetMapping(path = "/generateDraw", produces = "application/json")
	public String generateDraw(@RequestParam("participants") String participants) {
        Option<Draw> draw = generateDrawController.generateDraw(participants.split(","));

		return mapper.toJson(draw.get());
	}

}
