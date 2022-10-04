package utils;

import java.util.Date;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
public class DateUtils {
    public static java.sql.Date dateNow() {
        return java.sql.Date.valueOf(LocalDate.now());
    }

    public static String toString(Date date, String format){
        DateFormat dateFormat = new SimpleDateFormat(format);

        return dateFormat.format(date);
    }

    public static Date stringToDate(String date, String format){
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat(format);
        Date res = new Date();
        try {
            res = simpleDateFormat.parse(date);
        }catch(Exception e){

        }
        return res;
    }
}
