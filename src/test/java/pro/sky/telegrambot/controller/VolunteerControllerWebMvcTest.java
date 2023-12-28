package pro.sky.telegrambot.controller;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.json.JSONObject;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.boot.test.mock.mockito.SpyBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;
import pro.sky.telegrambot.generator.VolunteerGenerator;
import pro.sky.telegrambot.model.Volunteer;
import pro.sky.telegrambot.model.shelter.Shelter;
import pro.sky.telegrambot.repository.ShelterRepository;
import pro.sky.telegrambot.repository.VolunteerRepository;
import pro.sky.telegrambot.service.VolunteerService;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import static pro.sky.telegrambot.generator.VolunteerGenerator.*;

@WebMvcTest(VolunteerController.class)
public class VolunteerControllerWebMvcTest {

    private final ObjectMapper objectMapper = new ObjectMapper();

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private VolunteerRepository volunteerRepository;

    @MockBean
    private ShelterRepository shelterRepository;

    @SpyBean
    private VolunteerService volunteerService;

    @InjectMocks
    private VolunteerController volunteerController;

    @Test
    void add_success() throws Exception{

        JSONObject volunteerObject = new JSONObject();
        volunteerObject.put("id", ID);
        volunteerObject.put("firstName", FIRSTNAME);
        volunteerObject.put("phoneNumber", PHONENUMBER);

        Volunteer volunteer = new Volunteer();
        volunteer.setId(ID);
        volunteer.setFirstName(FIRSTNAME);
        volunteer.setPhoneNumber(PHONENUMBER);

        when(volunteerRepository.save(any(Volunteer.class))).thenReturn(volunteer);

        mockMvc.perform(MockMvcRequestBuilders
                        .post("/volunteers")
                        .content(volunteerObject.toString())
                        .contentType(MediaType.APPLICATION_JSON)
                        .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id").value(ID))
                .andExpect(jsonPath("$.firstName").value(FIRSTNAME))
                .andExpect(jsonPath("$.phoneNumber").value(PHONENUMBER));

        verify(volunteerRepository).save(any(Volunteer.class));
        verifyNoMoreInteractions(volunteerRepository);
    }

    @Test
    void getById_success() throws Exception{

        Volunteer volunteer = new Volunteer();
        volunteer.setId(ID);
        volunteer.setFirstName(FIRSTNAME);
        volunteer.setPhoneNumber(PHONENUMBER);

        when(volunteerRepository.findById(any(Long.class))).thenReturn(Optional.of(volunteer));

        mockMvc.perform(MockMvcRequestBuilders
                        .get("/volunteers/by-id")
                        .param("id", String.valueOf(ID)))
                .andExpect(status().isOk())
                .andExpect(MockMvcResultMatchers.jsonPath("$.id").value(ID))
                .andExpect(MockMvcResultMatchers.jsonPath("$.firstName").value(FIRSTNAME))
                .andExpect(MockMvcResultMatchers.jsonPath("$.phoneNumber").value(PHONENUMBER));

        verify(volunteerRepository).findById(ID);
        verifyNoMoreInteractions(volunteerRepository);

    }

    @Test
    void getByShelterId_success() throws Exception{

        Shelter shelter = new Shelter();
        shelter.setId(5L);
        shelter.setWorkSchedule("9:00 - 21:00");
        shelter.setAddress("ул. Воровского д.4");
        shelter.setSecurity("+8800553535");
        shelter.setSafetyPrecautions("Проверка документов");

        List<Volunteer> list = new ArrayList<>(List.of(
                new Volunteer(ID, FIRSTNAME, PHONENUMBER, shelter),
                new Volunteer(ID, FIRSTNAME, PHONENUMBER, shelter),
                new Volunteer(ID, FIRSTNAME, PHONENUMBER, shelter)
        ));

        when(volunteerRepository.findByShelterId(shelter.getId())).thenReturn(list);

        MvcResult result = mockMvc.perform(MockMvcRequestBuilders
                        .get("/volunteers/by-shelterId")
                        .param("shelterId", String.valueOf(shelter.getId())))
                .andExpect(status().isOk())
                .andReturn();
    }

    @Test
    void deleteVolunteer_success() throws Exception{

        Volunteer volunteer = new Volunteer();
        volunteer.setId(ID);
        volunteer.setFirstName(FIRSTNAME);
        volunteer.setPhoneNumber(PHONENUMBER);

        when(volunteerRepository.findById(ID)).thenReturn(Optional.of(volunteer));
        doNothing().when(volunteerRepository).deleteById(ID);

        mockMvc.perform(MockMvcRequestBuilders
                        .delete("/volunteers")
                        .param("id", String.valueOf(ID)))
                .andExpect(status().isOk())
                .andExpect(MockMvcResultMatchers.jsonPath("$.id").value(ID))
                .andExpect(MockMvcResultMatchers.jsonPath("$.firstName").value(FIRSTNAME))
                .andExpect(MockMvcResultMatchers.jsonPath("$.phoneNumber").value(PHONENUMBER));

        verify(volunteerRepository).findById(ID);
        verify(volunteerRepository).deleteById(ID);
        verifyNoMoreInteractions(volunteerRepository);
    }

}
