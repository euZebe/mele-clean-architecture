package me.euzebe.mele.generatedraw;

import java.util.Map;

import javaslang.Tuple;
import javaslang.collection.List;
import javaslang.collection.Seq;
import me.euzebe.mele.usecase.generatedraw.Draw;
import me.euzebe.mele.usecase.generatedraw.NotAllowedConstraint;
import me.euzebe.mele.usecase.generatedraw.Participant;

import org.springframework.stereotype.Component;

@Component
public class DrawMapper {

	public DrawResponse toResponse(Draw draw) {
		return new DrawResponse() //
				.withDrawID(draw.getId()) //
				.withAssignments(toHashMap(draw.getParticipants())) //
				.withResult(draw.getGenerationResult()) //
		;
	}

	private Map<String, String> toHashMap(Seq<Participant> participants) {
		return participants //
				.toMap(p -> Tuple.of(p.getName(), p.getAssigned())) //
				.toJavaMap();
	}

	public List<NotAllowedConstraint> toDomainConstraints(Constraint[] constraints) {
		return List.of(constraints) //
				.map(c -> new NotAllowedConstraint(c.getParticipant(), c.getNotTo()));
	}
}
