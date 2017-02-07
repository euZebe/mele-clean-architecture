package me.euzebe.mele.usecase.generatedraw;

import java.util.List;

import me.euzebe.mele.model.Participant;

public interface GenerateDraw {
	public List<Participant> generate(List<Participant> participants);
}
