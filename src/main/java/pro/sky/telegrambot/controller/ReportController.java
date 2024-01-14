package pro.sky.telegrambot.controller;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import javassist.NotFoundException;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import pro.sky.telegrambot.model.Report;
import pro.sky.telegrambot.model.Volunteer;
import pro.sky.telegrambot.service.ReportService;

import java.util.Collection;
import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/report_tg")
public class ReportController {

    @ExceptionHandler({RuntimeException.class})
    public String handleException(Exception e) {
        return  e.getMessage();
    }

    private final ReportService reportService;

    public ReportController(ReportService reportService) {
        this.reportService = reportService;
    }

//    @GetMapping("/by-clientId")
//    public ResponseEntity<List<Report>> findReportByClientId(@RequestParam Long clientId) {
//        return ResponseEntity.ok(reportService.findReportByClientId(clientId));
//    }
    @Operation(
        summary = "Поиск отчета по id",
        responses = {
                @ApiResponse(
                        responseCode = "200",
                        description = "Поиск отчета",
                        content = @Content(
                                mediaType = MediaType.APPLICATION_JSON_VALUE,
                                schema = @Schema(implementation = Report.class)
                        )
                )
        })
    @GetMapping("/by-id")
    public ResponseEntity<Optional<Report>> findReportById(@Parameter(description = "Поиск отчета по id")
                                                               @RequestParam Long id) {
        return ResponseEntity.ok(reportService.findReportById(id));
    }

    @GetMapping("/not-checked-report")
    public ResponseEntity<Collection<Report>> findNotCheckedReport(@RequestParam boolean checkReport) {
        return ResponseEntity.ok(reportService.findReportByCheckReport(checkReport));
    }
}
