package cellphoneusage.services;

import cellphoneusage.dao.CellPhone;
import cellphoneusage.dao.Person2Usage;
import cellphoneusage.dao.UsageByMonth;
import cellphoneusage.repository.CellPhoneRepo;
import cellphoneusage.repository.Person2UsageRepo;
import cellphoneusage.repository.UsageByMonthRepo;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

import java.sql.Timestamp;
import java.util.*;

@Component
public class PrintReportService {
  private static final Logger LOG = LoggerFactory.getLogger(PrintReportService.class);
  private UsageByMonthRepo usageByMonthRepo;
  private CellPhoneRepo cellPhoneRepo;
  private Person2UsageRepo person2UsageRepo;

  private int totalMinutes = 0;
  private double totalData = 0.0;

  public PrintReportService(UsageByMonthRepo usageByMonthRepo, CellPhoneRepo cellPhoneRepo, Person2UsageRepo person2UsageRepo){
    this.usageByMonthRepo = usageByMonthRepo;
    this.cellPhoneRepo = cellPhoneRepo;
    this.person2UsageRepo = person2UsageRepo;
  }

  public List<CellPhone> printData(){
    List<UsageByMonth> listUBM = usageByMonthRepo.getData("usagebymonth.csv");
    List<CellPhone> listCP = cellPhoneRepo.getData("cellphone.csv");
    List<Map<Timestamp, Person2Usage>> person2UsageList = new ArrayList<>();
    List<CellPhone> finalList = new ArrayList<>();
    setValues(listUBM,listCP );
    for(CellPhone cellPhone: listCP) {
      person2UsageList = person2UsageRepo.getData(cellPhone.getEmployeeId(), listUBM);
      cellPhone.setPerson2UsageList(person2UsageList);
      finalList.add(cellPhone);
    }

    return finalList;
  }

  public int getTotalMinutes(){
    return totalMinutes;
  }

  public double getTotalData(){
    return totalData;
  }

  private void setValues( List<UsageByMonth> listUBM, List<CellPhone> listCP){

    int count = 0;
    for(UsageByMonth usageByMonth: listUBM){
      totalMinutes = totalMinutes + usageByMonth.getTotalMinutes();
      totalData = totalData + usageByMonth.getTotalData();
      count++;
    }
    System.out.println(totalMinutes);
    System.out.println(totalMinutes/listCP.size());
    System.out.println(totalData);
    System.out.println(totalData/listCP.size());
    System.out.println(Math.round(totalData*100.00)/100.00);
    System.out.println(Math.round((totalData/listCP.size())*100.00)/100.00);
  }
}
