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

    public static LocalDate changeFormat(LocalDate date, String format){
        DateTimeFormatter dtf = DateTimeFormatter.ofPattern(format);
        String dateStr = date.format(dtf);
        try {
            return LocalDate.parse(dateStr, dtf);
        }catch(Exception e){
            return LocalDate.now();
        }
    }
    public static LocalDate stringToLocalDate(String dateStr, String format){
        LocalDate date = LocalDate.parse(dateStr);
        return changeFormat(date, format);
    }
}
