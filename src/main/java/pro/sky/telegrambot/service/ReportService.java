package pro.sky.telegrambot.service;

import com.pengrad.telegrambot.TelegramBot;
import com.pengrad.telegrambot.request.SendMessage;
import com.pengrad.telegrambot.response.SendResponse;
import javassist.NotFoundException;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.RequestParam;
import pro.sky.telegrambot.model.Client;
import pro.sky.telegrambot.model.Report;
import pro.sky.telegrambot.repository.ClientRepository;
import pro.sky.telegrambot.repository.ReportRepository;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.Optional;

@Service
public class ReportService {

    private final ReportRepository reportRepository;
    private final ClientRepository clientRepository;
    private final TelegramBot telegramBot;


    public ReportService(ReportRepository reportRepository, ClientRepository clientRepository, TelegramBot telegramBot) {
        this.reportRepository = reportRepository;
        this.clientRepository = clientRepository;
        this.telegramBot = telegramBot;
    }

    /**
     * Поиск отчета по его идентификатору в БД.
     * Используется метод репозитория {@link org.springframework.data.jpa.repository.JpaRepository#findById(Object)}
     * @param id Идентификатор искомого отчета.
     * @return Найденного отчета.
     */
    public Optional<Report> findReportById(Long id) {
        return reportRepository.findById(id);
    }

    public Collection<Report> findReportByCheckReport(boolean checkReport) {
        return reportRepository.findByCheckReport(checkReport);
    }

    public String acceptanceOfTheReport(Long id, boolean checkReport) {
        Report findReport = reportRepository.findReportById(id);
        if (checkReport == findReport.isCheckReport()) {
            return "Отчет уже принят";
        } else {
            findReport.setCheckReport(checkReport);
            reportRepository.save(findReport);
            return "Отчет принят";
        }
    }

    public SendResponse sendMessage(String firstName, String message) {
        Client findClient = clientRepository.findClientByFirstName(firstName);
        return telegramBot.execute(new SendMessage(findClient.getChatId(), message));
    }
}
