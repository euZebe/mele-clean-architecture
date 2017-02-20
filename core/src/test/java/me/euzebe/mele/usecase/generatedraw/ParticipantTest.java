package me.euzebe.mele.usecase.generatedraw;

import org.assertj.core.api.Assertions;
import org.junit.Test;

public class ParticipantTest {

	Participant p = new Participant("Niobé");

	@Test
	public void should_not_contain_assigned_if_null() {
		Assertions.assertThat(p.toJson()).isEqualTo("{name:\"Niobé\"}");
	}

	@Test
	public void should_contain_assigned_if_exists() {
		p.setAssigned(new Participant("Eusèbe"));
		Assertions.assertThat(p.toJson()).isEqualTo("{name:\"Niobé\", assigned:\"Eusèbe\"}");
	}

}
