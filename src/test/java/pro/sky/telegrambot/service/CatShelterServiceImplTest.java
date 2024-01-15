package pro.sky.telegrambot.service;

import com.pengrad.telegrambot.request.SendMessage;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import pro.sky.telegrambot.model.Volunteer;
import pro.sky.telegrambot.model.shelter.Shelter;
import pro.sky.telegrambot.repository.ShelterRepository;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class CatShelterServiceImplTest {
    @Mock
    private ShelterRepository shelterRepository;
    @Mock
    private VolunteerService volunteerService;
    @InjectMocks
    private CatShelterServiceImpl catShelterService;


    @Test
    void getWorkScheduleFromDB() {
        Shelter shelter = new Shelter("Расписание", "Адрес", "Телефон", "Меры предосторожности");
        when(shelterRepository.findById(1L)).thenReturn(Optional.of(shelter));

        SendMessage actual = catShelterService.getWorkScheduleFromDB(1L);
        SendMessage expect = new SendMessage(1L, "Расписание");

        verify(shelterRepository, times(1)).findById(1L);
        assertEquals(expect.getParameters(), actual.getParameters());
    }

    @Test
    void getAddressFromDB() {
        Shelter shelter = new Shelter("Расписание", "Адрес", "Телефон", "Меры предосторожности");
        when(shelterRepository.findById(1L)).thenReturn(Optional.of(shelter));

        SendMessage actual = catShelterService.getAddressFromDB(1L);
        SendMessage expect = new SendMessage(1L, "Адрес");

        verify(shelterRepository, times(1)).findById(1L);
        assertEquals(expect.getParameters(), actual.getParameters());
    }

    @Test
    void getShelterPhoneNumberSecurityFromDB() {
        Shelter shelter = new Shelter("Расписание", "Адрес", "Телефон", "Меры предосторожности");
        when(shelterRepository.findById(1L)).thenReturn(Optional.of(shelter));

        SendMessage actual = catShelterService.getShelterPhoneNumberSecurityFromDB(1L);
        SendMessage expect = new SendMessage(1L, "Для оформления пропуска на территорию приюта позвоните по номеру телефона - " + shelter.getSecurity());

        verify(shelterRepository, times(1)).findById(1L);
        assertEquals(expect.getParameters(), actual.getParameters());
    }

    @Test
    void getShelterSafetyPrecautionsSecurityFromDB() {
        Shelter shelter = new Shelter("Расписание", "Адрес", "Телефон", "Меры предосторожности");
        when(shelterRepository.findById(1L)).thenReturn(Optional.of(shelter));

        SendMessage actual = catShelterService.getShelterSafetyPrecautionsSecurityFromDB(1L);
        SendMessage expect = new SendMessage(1L, "Меры предосторожности");

        verify(shelterRepository, times(1)).findById(1L);
        assertEquals(expect.getParameters(), actual.getParameters());
    }

    @Test
    void getVolunteersShelterWithEmptyTable() {
        List<Volunteer> volunteerList = new ArrayList<>();
        when(volunteerService.findVolunteerByShelterId(1L)).thenReturn(volunteerList);

        SendMessage result = catShelterService.getVolunteersShelter(1L);
        SendMessage expected = new SendMessage(1L, "Нет доступных волонтеров в приюте.");

        assertEquals(expected.getParameters(), result.getParameters());
    }

    @Test
    void getVolunteersShelter() {
        Shelter shelter1 = new Shelter("Расписание", "Адрес", "Телефон", "Меры предосторожности");
        when(volunteerService.findVolunteerByShelterId(1L)).thenReturn(List.of(new Volunteer(1L, "Иван", "+89999999991", shelter1)));

        SendMessage result = catShelterService.getVolunteersShelter(1L);
        SendMessage expected = new SendMessage(1L, "Вот контакты наших волонтеров:\n" +
                "Телефонный номер волонтера: Иван: +89999999991\n");

        assertEquals(expected.getParameters(), result.getParameters());
    }


}