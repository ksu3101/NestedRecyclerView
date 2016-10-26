package kr.swkang.nestedrecyclerview.utils;

import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.text.TextUtils;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.Locale;

/**
 * @author KangSung-Woo
 * @since 2016/09/01
 */
public class DateUtils {
  private static final String TAG = DateUtils.class.getSimpleName();

  /**
   * Calendar를 ISO8601 규격에 맞춘 시간 문자열로 얻는다.
   *
   * @param cal Calendar instance.
   * @return ISO8601 규격에 맞춘 시간 문자열.
   */
  public static String getCalendar(@NonNull Calendar cal) {
    Date date = cal.getTime();
    String format = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ssZ", Locale.getDefault()).format(date);
    return format.substring(0, 22) + ":" + format.substring(22);
  }

  /**
   * 날짜 및 시간정보를 dateFormat에 맞추어서 얻는다.
   *
   * @param cal        날짜 및 시간의 Calendar 객체
   * @param dateFormat SimpleDateFormat을 참고 할 것
   * @return 시간 문자열
   */
  public static String getNow(@NonNull Calendar cal, @NonNull String dateFormat) {
    Date date = cal.getTime();
    return new SimpleDateFormat(dateFormat, Locale.getDefault()).format(date);
  }

  /**
   * 현재의 날짜 정보를 'yyyy. MM. dd.'형태로 얻는다.
   *
   * @return 'yyyy. MM. dd.'형태의 시간 문자열
   */
  public static String getNowDate() {
    return getNow(GregorianCalendar.getInstance(), "yyyy. MM. dd.");
  }

  /**
   * dateStr 문자열이 date format형인지 여부를 판단 한다.
   *
   * @param dateStr 날짜 문자열
   * @param format  SimpleDateFormat의 format 형태 문자열
   * @return true or false
   */
  public static boolean validDateFormat(@Nullable String dateStr, @Nullable String format) {
    if (!TextUtils.isEmpty(dateStr) && !TextUtils.isEmpty(format)) {
      SimpleDateFormat dateFormat = new SimpleDateFormat(format, Locale.getDefault());
      try {
        dateFormat.parse(dateStr);

      } catch (ParseException pe) {
        return false;
      }
      return true;
    }
    return false;
  }

}
