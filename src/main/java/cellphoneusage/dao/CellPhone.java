package cellphoneusage.dao;

import org.springframework.stereotype.Component;

import java.sql.Timestamp;
import java.util.List;
import java.util.Map;

@Component
public class CellPhone {
  private String employeeId;
  private String employeeName;
  private Timestamp purchaseDate;
  private String model;
  private List<Map<Timestamp, Person2Usage>> person2UsageList;

  public String getEmployeeId() {
    return employeeId;
  }

  public void setEmployeeId(String employeeId) {
    this.employeeId = employeeId;
  }

  public String getEmployeeName() {
    return employeeName;
  }

  public void setEmployeeName(String employeeName) {
    this.employeeName = employeeName;
  }

  public Timestamp getPurchaseDate() {
    return purchaseDate;
  }

  public void setPurchaseDate(Timestamp purchaseDate) {
    this.purchaseDate = purchaseDate;
  }

  public String getModel() {
    return model;
  }

  public void setModel(String model) {
    this.model = model;
  }

  public List<Map<Timestamp, Person2Usage>> getPerson2UsageList() {
    return person2UsageList;
  }

  public void setPerson2UsageList(List<Map<Timestamp, Person2Usage>> person2UsageList) {
    this.person2UsageList = person2UsageList;
  }
}
