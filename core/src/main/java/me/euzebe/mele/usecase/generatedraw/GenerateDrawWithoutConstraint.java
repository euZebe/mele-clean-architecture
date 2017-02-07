package me.euzebe.mele.usecase.generatedraw;

import java.util.List;

import me.euzebe.mele.model.Participant;

public class GenerateDrawWithoutConstraint implements GenerateDraw {

	@Override
	public List<Participant> generate(List<Participant> participants) {
		// TODO associate participant to each

		participants.forEach(p -> System.out.println(p.getAssignmentToString()));
		return participants;
	}

}
