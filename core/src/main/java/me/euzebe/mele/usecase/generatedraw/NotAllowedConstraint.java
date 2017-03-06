package me.euzebe.mele.usecase.generatedraw;

import lombok.Data;

@Data
public class NotAllowedConstraint {
	private String owner;
	private String notToBeAssigned;

	public NotAllowedConstraint() {
	}
	
	public NotAllowedConstraint(String owner, String notToBeAssociated) {
		this.owner = owner;
		this.notToBeAssigned = notToBeAssociated;
	}

	@Override
	public String toString() {
		return notToBeAssigned + "is not assigned to " + owner;
	}
}
