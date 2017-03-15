package me.euzebe.mele.generatedraw;

import lombok.Data;

@Data
public class Constraint {
	private String participant;
	private String notTo;

	public Constraint() {
	}

	public Constraint(String participant, String notTo) {
		this.participant = participant;
		this.notTo = notTo;
	}
}
