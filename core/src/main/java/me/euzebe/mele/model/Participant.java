package me.euzebe.mele.model;

import lombok.Data;

@Data
public class Participant {
	private String name;
	private Participant assigned;

	public String getAssignmentToString() {
		return new StringBuilder(name) //
				.append(" gets ") //
				.append(assigned.getName()) //
				.toString();
	}
}
