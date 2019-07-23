package com.eleganzit.vkcvendor.util;

import java.text.SimpleDateFormat;
import java.util.Calendar;

public class DateUtils {
    // format 24hre ex. 12:12 , 17:15
    private static String  HOUR_FORMAT = "HH:mm";

    private DateUtils() {    }

    public static String getCurrentHour() {
        Calendar cal = Calendar.getInstance();
        SimpleDateFormat sdfHour = new SimpleDateFormat(HOUR_FORMAT);
        String hour = sdfHour.format(cal.getTime());
        return hour;
    }

    /**
     * @param  target  hour to check
     * @param  start   interval start
     * @param  end     interval end
     * @return true    true if the given hour is between
     */
    public static boolean isHourInInterval(String target, String start, String end) {
        return ((target.compareTo(start) >= 0)
                && (target.compareTo(end) <= 0));
    }

    /**
     * @param  start   interval start
     * @param  end     interval end
     * @return true    true if the current hour is between
     */
    public static boolean isNowInInterval(String start, String end) {
        return DateUtils.isHourInInterval
                (DateUtils.getCurrentHour(), start, end);
    }

    //    TEST
    public static void main (String[] args) {
        String now = DateUtils.getCurrentHour();
        String start = "14:00";
        String end   = "14:26";
        System. out.println(now + " between " + start + "-" + end + "?");
        System. out.println(DateUtils.isHourInInterval(now,start,end));
        /*
         * output example :
         *   21:01 between 14:00-14:26?
         *   false
         *
         */
    }

}
