package cn.albumenj.switchmonitor.util;

import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.TimeZone;

/**
 * 日期工具
 * 用于返回过期时间
 *
 * @author Albumen
 */
public class DateUtil {
    public static Date beforeNowDate(Integer day) {
        Date date=new Date();
        Calendar calendar = new GregorianCalendar();
        calendar.setTime(date);
        calendar.add(Calendar.DATE,-day);
        date=calendar.getTime();

        return date;
    }

    public static Date beforeNowMinute(Integer minute) {
        Date date = new Date();
        Calendar calendar = new GregorianCalendar();
        calendar.setTime(date);
        calendar.add(Calendar.MINUTE, -minute);
        date = calendar.getTime();

        return date;
    }

    public static String getTime(Long milliseconds) {
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(new Date(milliseconds));
        calendar.setTimeZone((TimeZone.getTimeZone("GMT+0:00")));

        StringBuffer result = new StringBuffer();
        int yearStart = 1970;
        if ((calendar.get(Calendar.YEAR) - yearStart) > 0) {
            result.append(calendar.get(Calendar.YEAR) - 1970).append("年");
            result.append(calendar.get(Calendar.MONTH)).append("月");
            result.append(calendar.get(Calendar.DAY_OF_MONTH) - 1).append("日 ");
        } else if (calendar.get(Calendar.MONTH) > 0) {
            result.append(calendar.get(Calendar.MONTH)).append("月");
            result.append(calendar.get(Calendar.DAY_OF_MONTH) - 1).append("日 ");
        } else if (calendar.get(Calendar.DAY_OF_MONTH) - 1 > 0) {
            result.append(calendar.get(Calendar.DAY_OF_MONTH) - 1).append("日 ");
        }
        result.append(calendar.get(Calendar.HOUR)).append("时");
        result.append(calendar.get(Calendar.MINUTE)).append("分");
        result.append(calendar.get(Calendar.SECOND)).append("秒");

        return result.toString();
    }
}
