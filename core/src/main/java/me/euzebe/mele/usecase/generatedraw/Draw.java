package me.euzebe.mele.usecase.generatedraw;

import java.util.List;
import java.util.UUID;

public class Draw {

	private List<Participant> participants;
	private String id;

	public Draw(List<Participant> participants) {
		this.setParticipants(participants);
		id = UUID.randomUUID().toString();
	}

	public List<Participant> getParticipants() {
		return participants;
	}

	private void setParticipants(List<Participant> participants) {
		this.participants = participants;
	}

	public String getId() {
		return id;
	}
}
