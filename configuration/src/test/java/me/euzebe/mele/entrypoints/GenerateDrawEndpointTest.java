package me.euzebe.mele.entrypoints;

import static org.assertj.core.api.Assertions.assertThat;

import java.util.Random;

import org.apache.commons.lang3.RandomStringUtils;
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

    private Random random = new Random();

    @Test
    public void validates_that_the_app_is_up() throws Exception {
        assertThat(restTemplate.getForObject(getEndpointBaseRoute(), String.class)) //
                .contains("Hi all!");
    }

    @Test
    public void should_return_a_draw_when_input_is_valid() {
        DrawRequest request = new DrawRequest().withParticipants(
                RandomStringUtils.random(random.nextInt(20)), //
                RandomStringUtils.random(random.nextInt(20)),  //
                RandomStringUtils.random(random.nextInt(20)));
        DrawResponse drawResponse = restTemplate.postForObject(getEndpointBaseRoute() + "generateDraw", request,
                DrawResponse.class);

        assertThat(drawResponse).isNotNull();
        assertThat(drawResponse.getDrawID()).isNotNull();
        assertThat(drawResponse.getAssignments().size()).isEqualTo(3);
        drawResponse.getAssignments().forEach((participant, assignee) -> {
            assertThat(participant).isNotNull();
            assertThat(assignee).isNotNull();
        });
    }

    @Test
    public void should_return_no_draw_when_no_participant_is_defined() {
        DrawResponse drawResponse = restTemplate.postForObject(getEndpointBaseRoute() + "generateDraw", null,
                DrawResponse.class);

        assertThat(drawResponse.getDrawID()).isNull();
        assertThat(drawResponse.getAssignments()).isNull();
    }

    private String getEndpointBaseRoute() {
        return "http://localhost:" + port + "/api/";
    }

}
