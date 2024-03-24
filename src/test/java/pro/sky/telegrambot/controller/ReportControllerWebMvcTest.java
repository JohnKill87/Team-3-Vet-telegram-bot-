package pro.sky.telegrambot.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.pengrad.telegrambot.TelegramBot;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.boot.test.mock.mockito.SpyBean;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;
import pro.sky.telegrambot.listener.TelegramBotUpdatesListener;
import pro.sky.telegrambot.model.Client;
import pro.sky.telegrambot.model.Report;
import pro.sky.telegrambot.repository.ClientRepository;
import pro.sky.telegrambot.repository.ReportRepository;
import pro.sky.telegrambot.service.ReportService;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Collection;
import java.util.List;
import java.util.Optional;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@WebMvcTest(ReportController.class)
public class ReportControllerWebMvcTest {

    private final ObjectMapper objectMapper = new ObjectMapper();

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private ReportRepository reportRepository;

    @MockBean
    private ClientRepository clientRepository;

    @MockBean
    private TelegramBot telegramBot;

    @MockBean
    private TelegramBotUpdatesListener telegramBotUpdatesListener;

    @SpyBean
    private ReportService reportService;

    @InjectMocks
    private ReportController reportController;

    private final LocalDateTime dateTime = LocalDateTime.of(0,1, 1, 0, 0 );
    private Client client1 = new Client("Ливси", "88005553535");
    private Report report1 = new Report(dateTime, "Здоров", "Ei", false, client1);

    @Test
    void findReportById_success() throws Exception {

        report1.setId(5L);

        when(reportRepository.findById(any(Long.class))).thenReturn(Optional.of(report1));

        mockMvc.perform(MockMvcRequestBuilders
                        .get("/report_tg/by-id")
                        .param("id", String.valueOf(5L)))
                .andExpect(status().isOk())
                .andExpect(MockMvcResultMatchers.jsonPath("$.id").value(5L))
                .andExpect(MockMvcResultMatchers.jsonPath("$.dateAdded").value(dateTime.format(DateTimeFormatter.ISO_DATE_TIME)))
                .andExpect(MockMvcResultMatchers.jsonPath("$.generalWellBeing").value("Здоров"))
                .andExpect(MockMvcResultMatchers.jsonPath("$.photoNameId").value("Ei"))
                .andExpect(MockMvcResultMatchers.jsonPath("$.checkReport").value(false));

        verify(reportRepository).findById(5L);
        verifyNoMoreInteractions(reportRepository);
    }

    @Test
    void findReportByCheckReport_success() throws Exception {

        report1.setId(5L);

        Collection<Report> reportCollection = List.of(report1);

        when(reportRepository.findByCheckReport(any(boolean.class))).thenReturn(reportCollection);

        mockMvc.perform(MockMvcRequestBuilders
                        .get("/report_tg/not-checked-report")
                        .param("checkReport", String.valueOf(false)))
                .andExpect(status().isOk());

        verify(reportRepository).findByCheckReport(false);
        verifyNoMoreInteractions(reportRepository);
    }

    @Test
    void acceptanceOfTheReport_success() throws Exception {

        report1.setId(5L);

        when(reportRepository.findReportById(5L)).thenReturn(report1);
        when(reportRepository.save(report1)).thenReturn(report1);
        String expected = "Отчет принят";
        assertThat(reportService.acceptanceOfTheReport(5L,true))
                .isNotNull()
                .isEqualTo(expected);
    }
}
