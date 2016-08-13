package mzhr.librenote.models;

import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * Util class for getting a current quick date or formatting a date into a preferred style
 */
public class DateFormater {

    /** Get date as a string in a hh:mm a dd-MM-yyyy form */
    public static String getFormatedDate(Date date) {
        SimpleDateFormat format = new SimpleDateFormat("hh:mm a dd-MM-yyyy");
        return format.format(date);
    }

    /** Quickly grab currents date in a spaceless form */
    public static String getCurrentDateTime() {
        SimpleDateFormat format = new SimpleDateFormat("HH:mm-dd.MM.yyyy");
        return format.format(new Date());
    }
}
