package pro.sky.telegrambot.service;

import com.pengrad.telegrambot.TelegramBot;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;
import pro.sky.telegrambot.model.Client;
import pro.sky.telegrambot.model.Report;
import pro.sky.telegrambot.repository.ClientRepository;
import pro.sky.telegrambot.repository.ReportRepository;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.Optional;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;
import static org.mockito.ArgumentMatchers.any;

@ExtendWith(MockitoExtension.class)
public class ReportServiceMvcTest {

    @InjectMocks
    private ReportService reportService;

    @Mock
    private ReportRepository reportRepository;

    @Mock
    private TelegramBot telegramBot;

    @Mock
    private ClientRepository clientRepository;

    private LocalDateTime dateTime = LocalDateTime.of(2024,1, 13, 20, 0);
    private Report report1;
    private Client client1;
    private List<Report> reportList = new ArrayList<>();

    @BeforeEach
    public void setUp() {
        reportService = new ReportService(reportRepository, clientRepository, telegramBot);
        report1 = new Report(dateTime, "Здоров", "Ei", false, client1);
        report1.setId(2L);
        client1 = new Client("Ливси", "88005553535");
        reportList.add(report1);
    }

    @Test
    public void findReportByIdTest() {
        Optional<Report> reportOptional = Optional.ofNullable(report1);
        Mockito.when(reportRepository.findById(2L)).thenReturn(Optional.ofNullable(report1));
        Assertions.assertEquals(reportOptional, reportService.findReportById(2L));
    }

    @Test
    public void findReportByCheckReportTest() {
        Collection<Report> reportCollection = List.of(report1);
        Mockito.when(reportRepository.findByCheckReport(false)).thenReturn(reportCollection);
        assertThat(reportService.findReportByCheckReport(false))
                .isNotNull()
                .isEqualTo(reportCollection);
    }

    @Test
    public void acceptanceOfTheReportTest() {
        Mockito.when(reportRepository.findReportById(any(Long.class))).thenReturn(report1);
        Mockito.when(reportRepository.save(report1)).thenReturn(report1);
        String expected = "Отчет принят";
        assertThat(reportService.acceptanceOfTheReport(any(Long.class),true))
                .isNotNull()
                .isEqualTo(expected);
    }
}
