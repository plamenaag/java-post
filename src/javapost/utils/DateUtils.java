package javapost.utils;

import java.sql.Timestamp;
import java.text.SimpleDateFormat;

public class DateUtils {

    private static SimpleDateFormat dateFormat = new SimpleDateFormat("dd.MM.yyyy hh:mm:ss");

    public static String getDateTime(Long date) {
        Timestamp timeStamp = new Timestamp(date);

        return dateFormat.format(timeStamp);

    }

}
