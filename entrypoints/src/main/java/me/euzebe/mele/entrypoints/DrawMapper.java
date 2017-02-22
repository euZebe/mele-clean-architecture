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
		return new DrawResponse() //
				.withDrawID(draw.getId()) //
				.withAssignments(toHashMap(draw.getParticipants())) //
		;
	}

	private Map<String, String> toHashMap(List<Participant> participants) {
		return participants //
				.toMap(p -> Tuple.of(p.getName(), p.getAssigned())) //
				.toJavaMap();
	}
}
