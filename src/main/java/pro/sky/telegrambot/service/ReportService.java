package pro.sky.telegrambot.service;

import javassist.NotFoundException;
import org.springframework.stereotype.Service;
import pro.sky.telegrambot.model.Report;
import pro.sky.telegrambot.repository.ReportRepository;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.Optional;

@Service
public class ReportService {

    private final ReportRepository reportRepository;

    public ReportService(ReportRepository reportRepository) {
        this.reportRepository = reportRepository;
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
//    public List<Report> findReportByClientId(Long clientsId) {
//        return reportRepository.getByClientId(clientsId);
//    }

    public Collection<Report> findReportByCheckReport(boolean checkReport) {
        return reportRepository.findByCheckReport(checkReport);
    }
}
