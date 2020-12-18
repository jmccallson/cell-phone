package cellphoneusage.controllers;

import cellphoneusage.dao.CellPhone;
import cellphoneusage.services.GeneratePdfReport;
import cellphoneusage.services.PrintReportService;
import org.springframework.core.io.InputStreamResource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import java.io.ByteArrayInputStream;
import java.util.List;

@RestController
public class ReportController {
  private final PrintReportService printReportService;

  public ReportController(PrintReportService printReportService){
    this.printReportService = printReportService;
  }
  @RequestMapping(value = "/pdfreport", method = RequestMethod.GET, produces = MediaType.APPLICATION_PDF_VALUE)
  public ResponseEntity<InputStreamResource> printReport() {
    List<CellPhone> cellPhoneList = printReportService.printData();

    ByteArrayInputStream bis = GeneratePdfReport.genReport(cellPhoneList, printReportService.getTotalMinutes(),
      printReportService.getTotalData());

    HttpHeaders headers = new HttpHeaders();
    headers.add("Content-Disposition", "inline; filename=cell-phone-usage-report.pdf");
    return ResponseEntity
      .ok()
      .headers(headers)
      .contentType(MediaType.APPLICATION_PDF)
      .body(new InputStreamResource(bis));
  }
}
