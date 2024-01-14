package pro.sky.telegrambot.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import pro.sky.telegrambot.model.Report;

import java.util.Collection;


public interface ReportRepository extends JpaRepository<Report, Long> {

    Collection<Report> getByClientId(Long clientId);

    Collection<Report> findByCheckReport(boolean checkReport);
}
