package me.euzebe.mele.entrypoints;

import static org.assertj.core.api.Assertions.assertThat;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.context.embedded.LocalServerPort;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.context.SpringBootTest.WebEnvironment;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.test.context.junit4.SpringRunner;

@RunWith(SpringRunner.class)
@SpringBootTest(webEnvironment = WebEnvironment.RANDOM_PORT)
public class GenerateDrawEndpointTest {

	@LocalServerPort
	private int port;

	@Autowired
	private TestRestTemplate restTemplate;

	@Test
	public void validates_that_the_app_is_up() throws Exception {
		assertThat(restTemplate.getForObject(getEndpointBaseRoute(), String.class)) //
				.contains("Hi all!");
	}

	@Test
	public void should_return_a_draw() {
		DrawRequest request = new DrawRequest().withParticipants("Niobé", "Ernest", "Eusèbe");
		DrawResponse drawResponse = restTemplate.postForObject(getEndpointBaseRoute() + "generateDraw", request, DrawResponse.class);

		assertThat(drawResponse).isNotNull();
		assertThat(drawResponse.getDrawID()).isNotNull();
		assertThat(drawResponse.getAssignments().size()).isEqualTo(3);
		drawResponse.getAssignments().forEach((participant, assignee) -> {
			assertThat(participant).isNotNull();
			assertThat(assignee).isNotNull();
		});
	}

	private String getEndpointBaseRoute() {
		return "http://localhost:" + port + "/api/";
	}

}
