package cellphoneusage.dao;

import org.springframework.stereotype.Component;

import java.sql.Timestamp;

@Component
public class UsageByMonth {
  private String emplyeeId;
  private Timestamp date;
  private Integer totalMinutes;
  private Double totalData;

  public String getEmplyeeId() {
    return emplyeeId;
  }

  public void setEmplyeeId(String emplyeeId) {
    this.emplyeeId = emplyeeId;
  }

  public Timestamp getDate() {
    return date;
  }

  public void setDate(Timestamp date) {
    this.date = date;
  }

  public Integer getTotalMinutes() {
    return totalMinutes;
  }

  public void setTotalMinutes(Integer totalMinutes) {
    this.totalMinutes = totalMinutes;
  }

  public Double getTotalData() {
    return totalData;
  }

  public void setTotalData(Double totalData) {
    this.totalData = totalData;
  }
}
