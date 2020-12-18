package cellphoneusage.repository;

import cellphoneusage.dao.UsageByMonth;
import org.springframework.stereotype.Component;

import java.io.File;
import java.io.FileNotFoundException;
import java.sql.Timestamp;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.*;

@Component
public class UsageByMonthRepo {

  public List<UsageByMonth> getData(String fileName){
    List<UsageByMonth> listData = new ArrayList<>();
    File file = new File(
      getClass().getClassLoader().getResource(fileName).getFile()
    );

    Scanner inputStream;
    try{
      inputStream = new Scanner(file);

      while(inputStream.hasNext()){
        String line= inputStream.next();
        String[] values = line.split(",");
        // this adds the currently parsed line to the array of usage by month values
        if(listData.size()==0) {
          line= inputStream.next();
          values = line.split(",");
        }
        listData.add(usageByMonthMapper(Arrays.asList(values)));
      }

      inputStream.close();
    }catch (FileNotFoundException e) {
      e.printStackTrace();
    }
    return listData;
  }

  private UsageByMonth usageByMonthMapper(List dataList) {
    SimpleDateFormat datetimeFormatter1 = new SimpleDateFormat(
      "MM/dd/yyyy");

    UsageByMonth usageByMonth = new UsageByMonth();
    usageByMonth.setEmplyeeId(dataList.get(0).toString());
    try {
      Date date = datetimeFormatter1.parse(dataList.get(1).toString());
      Timestamp timestamp = new Timestamp(date.getTime());
      usageByMonth.setDate(timestamp);
    } catch (ParseException e) {
      e.printStackTrace();
    }
    usageByMonth.setTotalMinutes(new Integer(dataList.get(2).toString()));
    usageByMonth.setTotalData(new Double(dataList.get(3).toString()));
    return usageByMonth;
  }
}
