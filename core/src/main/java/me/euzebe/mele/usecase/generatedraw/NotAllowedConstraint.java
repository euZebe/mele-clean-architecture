package me.euzebe.mele.usecase.generatedraw;

import lombok.Getter;

public class NotAllowedConstraint {

    @Getter
	private String owner;

    @Getter
	private String notToBeAssigned;

	public NotAllowedConstraint(String owner, String notToBeAssociated) {
		this.owner = owner;
		this.notToBeAssigned = notToBeAssociated;
	}

	@Override
	public String toString() {
		return notToBeAssigned + "is not assigned to " + owner;
	}
}
