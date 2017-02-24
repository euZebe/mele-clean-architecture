package me.euzebe.mele.usecase.generatedraw;

import static org.assertj.core.api.Assertions.assertThat;

import org.apache.commons.lang3.RandomStringUtils;
import org.junit.Test;

public class ParticipantTest {

	@Test
	public void test() {
		String randomName = RandomStringUtils.random(41);

		Participant participant = new Participant(randomName);
		assertThat(participant.getName()).isEqualTo(randomName);
		assertThat(participant.getAssigned()).isNull();
		assertThat(participant.getAssignmentToString()).contains(randomName);

		String randomAssignee = RandomStringUtils.random(27);
		participant.setAssigned(randomAssignee);
		assertThat(participant.getAssigned()).isEqualTo(randomAssignee);
		assertThat(participant.getAssignmentToString()).contains(randomName);
		assertThat(participant.getAssignmentToString()).contains(randomAssignee);
	}

}
