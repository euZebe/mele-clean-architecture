package me.euzebe.mele.generatedraw;


import javaslang.collection.List;
import lombok.Data;

@Data
public class DrawRequest {

	private String[] participants;
	private Constraint[] constraints = new Constraint[0];

	public DrawRequest withParticipants(String... participants) {
		this.participants = participants;
		return this;
	}

	public DrawRequest withConstraint(String participant, String forbiddenAssignment) {
		constraints = List.of(constraints) //
				.append(new Constraint(participant, forbiddenAssignment)) //
				.toArray() //
				.toJavaArray(Constraint.class);

		return this;
	}
}
