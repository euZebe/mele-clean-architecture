package me.euzebe.mele.entrypoints.draw;

import lombok.Data;

@Data
public class DrawRequest {
	private String[] participants;
	private Constraint[] constraints;

	public DrawRequest withParticipants(String... participants) {
		this.participants = participants;
		return this;
	}
}
