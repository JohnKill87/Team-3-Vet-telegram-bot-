package pro.sky.telegrambot.controller;

import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.boot.web.server.LocalServerPort;
import pro.sky.telegrambot.model.Volunteer;
import pro.sky.telegrambot.model.shelter.Shelter;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
class VolunteerControllerTestRestTemplate {

    @LocalServerPort
    private int port;

    @Autowired
    private VolunteerController volunteerController;

    @Autowired
    TestRestTemplate testRestTemplate;

    @Test
    void contextLoads() throws Exception {
        Assertions.assertThat(volunteerController).isNotNull();
    }

    @Test
    void add() {
        Volunteer volunteer = new Volunteer();
        volunteer.setId(5L);
        volunteer.setFirstName("Erik");
        volunteer.setPhoneNumber("+8800553535");
        volunteer.setShelter(new Shelter("Vagro", "Vatran", "Vano", "+89000"));

        Assertions
                .assertThat(this.testRestTemplate.postForObject("http://localhost:" + port + "/volunteers", volunteer, String.class))
                .isNotNull();
    }

    @Test
    void getById() {
        Assertions
                .assertThat(this.testRestTemplate.getForObject("http://localhost:" + port + "/volunteers" + "/by-id", String.class))
                .isNotNull();
    }

    @Test
    void getByShelterId() {
        Assertions
                .assertThat(this.testRestTemplate.getForObject("http://localhost:" + port + "/volunteers" + "/shelter/{shelterId}", String.class))
                .isNotNull();
    }

    @Test
    void deleteVolunteer() {
        Volunteer volunteer = new Volunteer();
        volunteer.setId(5L);
        volunteer.setFirstName("Erik");
        volunteer.setPhoneNumber("+8800553535");
        volunteer.setShelter(new Shelter("Vagro", "Vatran", "Vano", "+89000"));


        Assertions
                .assertThat(this.testRestTemplate.postForObject("http://localhost:" + port + "/volunteers", volunteer, String.class))
                .isNotNull();
    }

}