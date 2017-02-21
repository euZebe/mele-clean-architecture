package me.euzebe.mele.usecase.generatedraw;

import org.assertj.core.api.Assertions;
import org.junit.Test;

public class JsonMapperTest {

	Participant p = new Participant("Niobé");

	JsonMapper mapper = new JsonMapper();

	@Test
	public void should_not_contain_assigned_if_null() {
		Assertions.assertThat(mapper.toJson(p)).isEqualTo("{name:\"Niobé\"}");
	}

	@Test
	public void should_contain_assigned_if_exists() {
		p.setAssigned(new Participant("Eusèbe"));
		Assertions.assertThat(mapper.toJson(p)).isEqualTo("{name:\"Niobé\", assigned:\"Eusèbe\"}");
	}

}
