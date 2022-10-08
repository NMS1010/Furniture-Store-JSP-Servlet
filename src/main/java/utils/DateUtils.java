package utils;

import java.sql.Date;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

public class DateUtils {
    public static LocalDate dateNow() {
        return LocalDate.now();
    }
    public static LocalDateTime dateTimeNow() {
        return LocalDateTime.now();
    }

    public static String toString(Date date, String format){
        DateFormat dateFormat = new SimpleDateFormat(format);

        return dateFormat.format(date);
    }

    public static LocalDate stringToLocalDate(String date, String format){
        DateTimeFormatter dtf = DateTimeFormatter.ofPattern(format);
        LocalDate res = LocalDate.now();
        try {
            res = LocalDate.parse(date, dtf);
        }catch(Exception e){

        }
        return res;
    }
}
