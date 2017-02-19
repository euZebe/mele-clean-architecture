package me.euzebe.mele.usecase.generatedraw;

import lombok.Data;

@Data
public class Participant {
	private String name;
	private Participant assigned;

	public Participant(String name) {
		this.name = name;
	}

	public String getAssignmentToString() {
		return new StringBuilder(name) //
				.append(" gets ") //
				.append(assigned.getName()) //
				.toString();
	}

}
