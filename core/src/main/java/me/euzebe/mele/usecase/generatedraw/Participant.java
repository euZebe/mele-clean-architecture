package me.euzebe.mele.usecase.generatedraw;

import lombok.Getter;
import lombok.Setter;

public class Participant {

    @Getter
	private String name;

    @Getter
    @Setter
	private Participant assigned;

	public Participant(String name) {
		this.name = name;
	}



	public String getAssignmentToString() {
		return new StringBuilder(name) //
				.append(assigned == null ? "" : "gets " + assigned.getName()) //
				.toString();
	}
}
