package pro.sky.telegrambot.service;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;
import pro.sky.telegrambot.model.Volunteer;
import pro.sky.telegrambot.model.shelter.Shelter;
import pro.sky.telegrambot.repository.VolunteerRepository;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;



@ExtendWith(MockitoExtension.class)
class VolunteerServiceMvcTest {
    @InjectMocks
    private VolunteerService volunteerService;

    @Mock
    private VolunteerRepository volunteerRepository;

    private Volunteer volunteer1;
    private Volunteer volunteer2;
    private Shelter shelter1;
    private Shelter shelter2;

    private List<Volunteer> volunteerList = new ArrayList<>();

    @BeforeEach
    public void setUp() {
        volunteerService = new VolunteerService(volunteerRepository);
        shelter1 = new Shelter("schedule1", "adress1", "phone1", "precaution1");
        shelter2 = new Shelter("schedule2", "adress2", "phone2", "precaution2");
        volunteer1 = new Volunteer(1L, "Ivan", "+89999999991", shelter1);
        volunteer2 = new Volunteer(2L, "Petr", "+89999999992", shelter2);
        volunteerList.add(volunteer1);
        volunteerList.add(volunteer2);
    }

    @Test
    public void addVolunteerTest() {
        Mockito.when(volunteerRepository.save(volunteer1)).thenReturn(volunteer1);
        Assertions.assertEquals(volunteer1, volunteerService.addVolunteer(volunteer1));
    }

    @Test
    public void findVolunteerByIdTest() {
        Optional<Volunteer> volunteerOptional = Optional.ofNullable(volunteer2);
        Mockito.when(volunteerRepository.findById(2L)).thenReturn(Optional.ofNullable(volunteer2));
        Assertions.assertEquals(volunteerOptional, volunteerService.findVolunteerById(2L));
    }

    @Test
    public void findVolunteerByShelterIdTest() {
        Mockito.when(volunteerRepository.findByShelterId(2L)).thenReturn(volunteerList);
        volunteerList.remove(volunteer1);
        Assertions.assertEquals(volunteerList, volunteerService.findVolunteerByShelterId(2L));
    }

    @Test
    public void deleteVolunteerTest() {
    }
}