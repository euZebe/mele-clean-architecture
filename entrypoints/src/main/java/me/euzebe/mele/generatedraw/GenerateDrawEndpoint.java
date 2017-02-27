package me.euzebe.mele.generatedraw;

import java.io.IOException;

import javaslang.control.Option;

import javax.servlet.http.HttpServletResponse;

import me.euzebe.mele.usecase.generatedraw.Draw;
import me.euzebe.mele.usecase.generatedraw.DrawWithRandom;
import me.euzebe.mele.usecase.generatedraw.IGenerateDraws;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/mele")
public class GenerateDrawEndpoint {

    @Autowired
    private IGenerateDraws generateDrawController;


	@Autowired
	private DrawMapper drawMapper;

	@GetMapping("/")
	public String sayHi() {
		return "Hi all!";
	}

	@PostMapping(path = "/generateDraw", produces = "application/json")
	public DrawResponse generate(@RequestBody DrawRequest request) {
		Option<Draw> draw = generateDrawController.generateDraw(request.getParticipants());

		return drawMapper.toResponse(draw.getOrElse(DrawWithRandom.EMPTY));
	}

	@GetMapping(value = "/api-docs")
	@ResponseStatus(HttpStatus.OK)
	public void method(HttpServletResponse httpServletResponse) {
		try {
			httpServletResponse.sendRedirect("/swagger/index.html");
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

}
