package me.euzebe.mele.entrypoints;

import java.util.Map;

import javaslang.Tuple;
import javaslang.collection.List;
import me.euzebe.mele.usecase.generatedraw.Draw;
import me.euzebe.mele.usecase.generatedraw.Participant;

import org.springframework.stereotype.Component;

@Component
public class DrawMapper {

	public DrawResponse toResponse(Draw draw) {
		return DrawResponse.builder() //
				.drawID(draw.getId()) //
				.assignments(toHashMap(draw.getParticipants())) //
				.build();
	}

	private Map<String, String> toHashMap(List<Participant> participants) {
		participants //
				.toMap(p -> Tuple.of(p.getName(), p.getAssigned())) //
				.toJavaMap();
		return null;
	}
}
