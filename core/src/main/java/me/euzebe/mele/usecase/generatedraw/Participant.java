package me.euzebe.mele.usecase.generatedraw;

import javaslang.collection.List;
import javaslang.control.Option;
import lombok.Getter;
import lombok.Setter;
import me.euzebe.mele.JsonObject;

public class Participant implements JsonObject {
    
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

	@Override
	public String toJson() {
		String assignedJson = Option.of(assigned) //
				.flatMap(a -> Option.of(", assigned:" + wrapAttribute(a.getName()))) //
				.getOrElse("")
		;

		return List.of("name:" + wrapAttribute(name), assignedJson) //
				.prepend("{").append("}") //
				.fold("", String::concat) //
		;
	}


}
