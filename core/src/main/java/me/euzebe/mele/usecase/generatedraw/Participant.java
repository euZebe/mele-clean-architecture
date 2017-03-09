package me.euzebe.mele.usecase.generatedraw;

import lombok.Getter;
import lombok.Setter;

public class Participant {

    @Getter
	private String name;

    @Getter
    @Setter
	private String assigned;

	public Participant(String name) {
		this.name = name;
	}

	@Override
	public String toString() {
		return new StringBuilder(name) //
				.append(assigned == null ? "" : " gets " + assigned) //
				.toString();
	}
}
