package cellphoneusage.repository;

import cellphoneusage.dao.CellPhone;
import org.springframework.stereotype.Component;

import java.io.File;
import java.io.FileNotFoundException;
import java.sql.Timestamp;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.*;

@Component
public class CellPhoneRepo {
  public List<CellPhone> getData(String fileName){
    List<CellPhone> listData = new ArrayList<>();
    File file = new File(
      getClass().getClassLoader().getResource(fileName).getFile()
    );

    Scanner inputStream;
    try{
      inputStream = new Scanner(file).useDelimiter("(\\r\\n)");//|\r

      while(inputStream.hasNext()){
        String line= inputStream.next();
        String[] values = line.split(",");
        // this adds the currently parsed line to the array of usage by month values
        if(listData.size()==0) {
          line= inputStream.next();
          values = line.split(",");
        }
        listData.add(cellPhoneMapper(Arrays.asList(values)));
      }

      inputStream.close();
    }catch (FileNotFoundException e) {
      e.printStackTrace();
    }
    return listData;
  }

  private CellPhone cellPhoneMapper(List dataList){
    SimpleDateFormat datetimeFormatter1 = new SimpleDateFormat(
      "yyyyMMdd");

    CellPhone cellPhone = new CellPhone();
    cellPhone.setEmployeeId(dataList.get(0).toString());
    cellPhone.setEmployeeName(dataList.get(1).toString());
    try {
      Date date = datetimeFormatter1.parse(dataList.get(2).toString());
      Timestamp timestamp = new Timestamp(date.getTime());
      cellPhone.setPurchaseDate(timestamp);
    } catch (ParseException e) {
      e.printStackTrace();
    }
    cellPhone.setModel(dataList.get(3).toString());
    return cellPhone;
  }
}
