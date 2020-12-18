package cellphoneusage.services;

import cellphoneusage.dao.CellPhone;
import cellphoneusage.dao.Person2Usage;
import com.itextpdf.text.*;
import com.itextpdf.text.pdf.PdfPCell;
import com.itextpdf.text.pdf.PdfPTable;
import com.itextpdf.text.pdf.PdfWriter;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.sql.Timestamp;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.*;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

public class GeneratePdfReport {
  public static ByteArrayInputStream genReport(List<CellPhone> cellPhoneList, int totalMinutes, double totalData){
    Document document = new Document();
    ByteArrayOutputStream out = new ByteArrayOutputStream();

    DateFormat dateFormat = new SimpleDateFormat("MM/dd/yyyy");
    Date date = new Date();

    Phrase phrase = new Phrase();
    Font fontBlue = FontFactory.getFont(FontFactory.HELVETICA_BOLD, 14, BaseColor.BLUE);
    Font fontBlack = FontFactory.getFont(FontFactory.HELVETICA_BOLD, 14, BaseColor.BLACK);
    Chunk chunkRRD = new Chunk("Report Run Date : " + dateFormat.format(date), fontBlue);
    phrase.add(chunkRRD);
    Chunk chunkNoP = new Chunk("Number of Phones : " + cellPhoneList.size(), fontBlue);
    Chunk chunkTM = new Chunk("Total Minutes : " + totalMinutes, fontBlue);
    Chunk chunkTD = new Chunk("Total Data : " + (Math.round(totalData*100.00)/100.00), fontBlue);
    Chunk chunkAM = new Chunk("Average Minutes : " + (totalMinutes/cellPhoneList.size()), fontBlue);
    Chunk chunkAD = new Chunk("Average Data : " + (Math.round((totalData/cellPhoneList.size())*100.00)/100.00), fontBlue);

    try {
      Phrase phraseEmployee = new Phrase();

      Font headFont = FontFactory.getFont(FontFactory.HELVETICA_BOLD);

      for(CellPhone cellPhone: cellPhoneList){
        Chunk chunkEmployee = new Chunk("Employee Id : " + cellPhone.getEmployeeId(), fontBlack);
        Chunk chunkEmployeeName = new Chunk("Employee Name : " + cellPhone.getEmployeeName(), fontBlack);
        Chunk chunkModel = new Chunk("Model : " + cellPhone.getModel(), fontBlack);
        Chunk chunkPurchaseDate = new Chunk("Purchase Date : " + dateFormat.format(cellPhone.getPurchaseDate()), fontBlack);
        Chunk chunkMinutesUsage = new Chunk("Minutes Usage", fontBlack);
        phraseEmployee.add(chunkEmployee);
        phraseEmployee.add(Chunk.NEWLINE);
        phraseEmployee.add(chunkEmployeeName);
        phraseEmployee.add(Chunk.NEWLINE);
        phraseEmployee.add(chunkModel);
        phraseEmployee.add(Chunk.NEWLINE);
        phraseEmployee.add(chunkPurchaseDate);
        phraseEmployee.add(Chunk.NEWLINE);
        phraseEmployee.add(chunkMinutesUsage);
        phraseEmployee.add(Chunk.NEWLINE);
        phraseEmployee.add(Chunk.NEWLINE);
        phraseEmployee.add(Chunk.NEWLINE);

        PdfPTable table = new PdfPTable(3);
        table.setWidthPercentage(80);
        table.setWidths(new int[]{3, 3, 3});
        PdfPCell hcell;

        hcell = new PdfPCell(new Phrase("Date", headFont));
        hcell.setHorizontalAlignment(Element.ALIGN_CENTER);
        table.addCell(hcell);

        hcell = new PdfPCell(new Phrase("Minutes Used", headFont));
        hcell.setHorizontalAlignment(Element.ALIGN_CENTER);
        table.addCell(hcell);

        hcell = new PdfPCell(new Phrase("Data Usage", headFont));
        hcell.setHorizontalAlignment(Element.ALIGN_CENTER);
        table.addCell(hcell);

        PdfPCell cell;

        for(Map person2UsageMap : cellPhone.getPerson2UsageList()) {
          Map<Timestamp, Person2Usage> treeMap = new TreeMap<Timestamp, Person2Usage>(person2UsageMap);
          Collection<Person2Usage> hashMap = treeMap.values();
          for(Person2Usage person2Usage:hashMap){
            cell = new PdfPCell(new Phrase(dateFormat.format(person2Usage.getDate())));
            cell.setVerticalAlignment(Element.ALIGN_MIDDLE);
            cell.setHorizontalAlignment(Element.ALIGN_CENTER);
            table.addCell(cell);

            cell = new PdfPCell(new Phrase(person2Usage.getTotalMinutes() + ""));
            cell.setVerticalAlignment(Element.ALIGN_MIDDLE);
            cell.setHorizontalAlignment(Element.ALIGN_CENTER);
            table.addCell(cell);

            cell = new PdfPCell(new Phrase((Math.round(person2Usage.getTotalData()*100.00)/100.00) + ""));
            cell.setVerticalAlignment(Element.ALIGN_MIDDLE);
            cell.setHorizontalAlignment(Element.ALIGN_CENTER);
            table.addCell(cell);
            System.out.println(person2Usage.getDate());
          }
        }
        phraseEmployee.add(table);
        phraseEmployee.add(Chunk.NEWLINE);
        phraseEmployee.add(Chunk.NEWLINE);
      }

      PdfWriter.getInstance(document, out);
      document.open();
      document.add(phrase);
      document.add(Chunk.NEWLINE);
      document.add(chunkNoP);
      document.add(Chunk.NEWLINE);
      document.add(chunkTM);
      document.add(Chunk.NEWLINE);
      document.add(chunkTD);
      document.add(Chunk.NEWLINE);
      document.add(chunkAM);
      document.add(Chunk.NEWLINE);
      document.add(chunkAD);
      document.add(Chunk.NEWLINE);
      document.add(Chunk.NEWLINE);
      document.add(phraseEmployee);
      //document.add(table);

      document.close();
    }
    catch (DocumentException ex) {
      Logger.getLogger(GeneratePdfReport.class.getName()).log(Level.SEVERE, null, ex);
    }

    return new ByteArrayInputStream(out.toByteArray());
  }
}
