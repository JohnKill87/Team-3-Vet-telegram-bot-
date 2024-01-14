package pro.sky.telegrambot.service;

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
import pro.sky.telegrambot.repository.ReportRepository;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.Optional;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;

@ExtendWith(MockitoExtension.class)
public class ReportServiceMvcTest {

    @InjectMocks
    private ReportService reportService;

    @Mock
    private ReportRepository reportRepository;

    private LocalDateTime dateTime = LocalDateTime.of(2024,1, 13, 20, 0);
    private Report report1;

    private Client client1;

    private List<Report> reportList = new ArrayList<>();

    @BeforeEach
    public void setUp() {
        reportService = new ReportService(reportRepository);
        report1 = new Report(dateTime, "Здоров", "Ei", true, client1);
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
        Mockito.when(reportRepository.findByCheckReport(true)).thenReturn(reportCollection);
        assertThat(reportService.findReportByCheckReport(true))
                .isNotNull()
                .isEqualTo(reportCollection);
    }
}
