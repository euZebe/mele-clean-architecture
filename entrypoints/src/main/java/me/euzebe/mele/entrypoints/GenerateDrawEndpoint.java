package me.euzebe.mele.entrypoints;

import javaslang.control.Option;
import me.euzebe.mele.usecase.generatedraw.Draw;
import me.euzebe.mele.usecase.generatedraw.DrawWithRandom;
import me.euzebe.mele.usecase.generatedraw.GenerateDraw;
import me.euzebe.mele.usecase.generatedraw.JsonMapper;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api")
public class GenerateDrawEndpoint {

    @Autowired
    private GenerateDraw generateDrawController;

    @Autowired
    private JsonMapper mapper;

	@Autowired
	private DrawMapper drawMapper;

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

	@PostMapping(path = "/generateDraw", produces = "application/json")
	public DrawResponse generate(@RequestBody DrawRequest request) {
		Option<Draw> draw = generateDrawController.generateDraw(request.getParticipants());

		return drawMapper.toResponse(draw.getOrElse(DrawWithRandom.EMPTY));
	}

}
