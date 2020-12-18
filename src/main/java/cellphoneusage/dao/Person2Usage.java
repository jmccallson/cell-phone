package cellphoneusage.dao;

import org.springframework.stereotype.Component;

import java.sql.Timestamp;

@Component
public class Person2Usage {
  private Timestamp date;
  private Integer totalMinutes;
  private Double totalData;

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
