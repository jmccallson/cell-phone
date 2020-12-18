package cellphoneusage.repository;

import cellphoneusage.dao.CellPhone;
import cellphoneusage.dao.Person2Usage;
import cellphoneusage.dao.UsageByMonth;
import org.springframework.stereotype.Component;

import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Component
public class Person2UsageRepo {
  private UsageByMonthRepo usageByMonthRepo;
  private CellPhoneRepo cellPhoneRepo;

  public Person2UsageRepo(UsageByMonthRepo usageByMonthRepo, CellPhoneRepo cellPhoneRepo){
    this.usageByMonthRepo = usageByMonthRepo;
    this.cellPhoneRepo = cellPhoneRepo;
  }

  public List<Map<Timestamp, Person2Usage>> getData(String employeeId, List<UsageByMonth> listUBM){
    return getPerson2Usage(employeeId, listUBM);
  }

  private List<Map<Timestamp, Person2Usage>> getPerson2Usage(String employeeId, List<UsageByMonth> listUBM){
    List<Map<Timestamp, Person2Usage>> person2UsageList = new ArrayList<>();

    int totalMinutes = 0;
    double totalData = 0.0;
    Map<Timestamp, Person2Usage> finalData = new HashMap<Timestamp, Person2Usage>();

    for(UsageByMonth usageByMonth: listUBM){
      if(employeeId.equals(usageByMonth.getEmplyeeId())) {
        if(finalData.containsKey(usageByMonth.getDate())) {
          totalMinutes = finalData.get(usageByMonth.getDate()).getTotalMinutes() + usageByMonth.getTotalMinutes();
          totalData = finalData.get(usageByMonth.getDate()).getTotalData() + usageByMonth.getTotalData();

          Person2Usage person2Usage = new Person2Usage();
          person2Usage.setDate(usageByMonth.getDate());
          person2Usage.setTotalMinutes(totalMinutes);
          person2Usage.setTotalData(totalData);
          finalData.remove(usageByMonth.getDate());
          finalData.put(usageByMonth.getDate(),person2Usage );
        }
        else {
          Person2Usage person2Usage = new Person2Usage();
          person2Usage.setDate(usageByMonth.getDate());
          person2Usage.setTotalMinutes(usageByMonth.getTotalMinutes());
          person2Usage.setTotalData(usageByMonth.getTotalData());

          finalData.put(usageByMonth.getDate(),person2Usage );
        }

      }
    }
    person2UsageList.add(finalData);
    return person2UsageList;
  }
}
