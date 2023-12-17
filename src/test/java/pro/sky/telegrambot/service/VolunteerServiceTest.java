package pro.sky.telegrambot.service;

import org.junit.jupiter.api.Test;
import pro.sky.telegrambot.generator.VolunteerGenerator;
import pro.sky.telegrambot.model.Volunteer;

import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static pro.sky.telegrambot.generator.VolunteerGenerator.*;

class VolunteerServiceTest {

    private VolunteerService volunteerService;

    @Test
    void addVolunteer_success() {
//        Входные данные.

        Volunteer volunteer = new Volunteer(ID, NAME, PHONENUMBER, SHELTER);

//        Ожидаемый результат.

        Volunteer expected = getVolunteer();

//        Тест.

        Volunteer actual = volunteerService.addVolunteer(volunteer);
        assertEquals(expected, actual);
    }

    @Test
    void findVolunteerById_success() {
//        Входные данные.

        Volunteer volunteer = new Volunteer(ID, NAME, PHONENUMBER, SHELTER);

//        Ожидаемый результат.

        volunteerService.addVolunteer(volunteer);
        Volunteer expected = getVolunteer();

//        Тест.

        Optional<Volunteer> actual = volunteerService.findVolunteerById(ID);
        assertEquals(expected, actual);
    }

    @Test
    void findVolunteerByShelterId_success() {
//        Входные данные.

        Volunteer volunteer = new Volunteer(ID, NAME, PHONENUMBER, SHELTER);

//        Ожидаемый результат.

        volunteerService.addVolunteer(volunteer);
        Volunteer expected = getVolunteer();

//        Тест.

        List<Volunteer> actual = volunteerService.findVolunteerByShelterId(SHELTER.getId());
        assertEquals(expected, actual);
    }

    @Test
    void deleteVolunteer_success() {
//        Тест.

        assertThrows(RuntimeException.class, () -> volunteerService.deleteVolunteer(new Volunteer(ID, NAME, PHONENUMBER, SHELTER)));
    }
}