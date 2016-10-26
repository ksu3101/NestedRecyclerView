package kr.swkang.nestedrecyclerview.utils;

import android.content.Context;
import android.database.Cursor;
import android.graphics.Color;
import android.graphics.Typeface;
import android.net.Uri;
import android.support.annotation.ColorInt;
import android.support.annotation.IntRange;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.text.Html;
import android.text.Spannable;
import android.text.SpannableStringBuilder;
import android.text.Spanned;
import android.text.TextUtils;
import android.text.style.ForegroundColorSpan;
import android.text.style.StyleSpan;
import android.util.Log;
import android.util.Patterns;
import android.widget.TextView;

import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import kr.swkang.snstemplate.utils.common.ConstantParams;

/**
 * @author KangSung-Woo
 * @since 2016/09/01
 */
public class StringUtils {
  private static final String TAG = StringUtils.class.getSimpleName();

  /**
   * 입력한 문자열이 Null이거나 비어있는지 여부를 반환 한다.
   *
   * @param str 검사할 문자열
   * @return 문자열의 길이가 1 이상(비어있지 않음)일 경우 true, 아니면 false를 반환
   */
  public static boolean isEmpty(String str) {
    return TextUtils.isEmpty(str);
  }

  /**
   * 입력한 두개의 문자열이 동일한 문자열인지 검사한다
   *
   * @param str  검사할 문자열 1
   * @param str2 검사할 문자열 2
   * @return 동일한 문자열 일 경우 true, 아니면 false
   */
  public static boolean isSameString(String str, String str2) {
    if (!TextUtils.isEmpty(str) && !TextUtils.isEmpty(str2)) {
      return str.equals(str2);
    }
    return false;
  }

  /**
   * 문자열 str에 특수문자가 존재하는지 여부를 판단 한다.
   *
   * @param str 특수문자 존재 여부를 확인할 문자열.
   * @return true일 경우 특수문자가 존재. false일 경우 없음.
   */
  public static boolean hasSpecialCharacter(@NonNull String str) {
    if (str.length() == 0) return false;
    final String strU = str.toUpperCase();
    Pattern p = Pattern.compile(".*[^ㄱ-ㅎㅏ-ㅣ가-힣-a-zA-Z0-9].*");
    Matcher m = p.matcher(strU);
    return m.matches();
  }

  /**
   * 문자열 str이 숫자로 되어 있는지 체크 한다.
   *
   * @param str 순자인지 확인 할 문자열.
   * @return true일 경우 숫자. false일 경우 숫자가 아니다.
   */
  public static boolean isNumeric(@NonNull String str) {
    if (str.length() == 0) return false;
    try {
      double d = Double.parseDouble(str);
      return true;
    } catch (NumberFormatException nfe) {
      Log.e(TAG, nfe.getMessage());
    }
    return false;
  }

  /**
   * 특정한 문자열 집합에서 정규식으로 표현된 문자열이 존재하는지 확인 하고 난 뒤
   * 해당하는 문자열들의 목록을 반환한다.
   *
   * @param regEx  찾을 대상의 정규 표현식
   * @param target 찾을 대상 문자열
   * @return 찾은 문자열 목록. 없을경우 비어있는 목록.
   */
  public static ArrayList<String> findPatternMatch(@NonNull String regEx, @Nullable String target) {
    ArrayList<String> result = new ArrayList<>();
    if (!TextUtils.isEmpty(target)) {
      Matcher m = Pattern.compile(regEx)
                         .matcher(target);
      while (m.find()) {
        result.add(m.group());
      }
    }
    return result;
  }

  /**
   * 특정한 문자열 집합에서 정규식으로 표현된 문자열이 존재하는지 확인 하고 난 뒤
   * 찾은 목록 Matcher object를 반환한다.
   *
   * @param regEx  찾을 대상의 정규 표현식
   * @param target 찾을 대상 문자열
   * @return Matcher object. 없을 경우 비어있는 목록.
   */
  public static Matcher findPatternMatcher(@NonNull String regEx, @Nullable String target) {
    Matcher m = null;
    if (!TextUtils.isEmpty(target)) {
      m = Pattern.compile(regEx)
                 .matcher(target);
    }
    return m;
  }

  /**
   * 특정한 문자열 집합에서 정규식으로 표현된 문자열이 존재하는지 확인 하고 난 뒤
   * 찾은 목록 Matcher object를 반환한다.
   *
   * @param regEx  찾을 대상의 정규 표현식
   * @param target 찾을 대상 문자열
   * @return Matcher object. 없을 경우 비어있는 목록.
   */
  public static Matcher findPatternMatcherUnicodes(@NonNull String regEx, @Nullable String target) {
    Matcher m = null;
    if (!TextUtils.isEmpty(target)) {
      m = Pattern.compile(regEx, Pattern.UNICODE_CASE)
                 .matcher(target);
    }
    return m;
  }

  /**
   * 특정 문자열집합 str에서 문자 c의 갯수를 반환한다. 없을 경우 0을 반환 한다.
   *
   * @param c   카운팅 할 찾을 문자
   * @param str 대상 문자열 집합
   * @return 찾은 갯수
   */
  public static int getCountCharactorInString(char c, String str) {
    int result = 0;
    if (!TextUtils.isEmpty(str)) {
      for (int i = 0; i < str.length(); i++) {
        if (str.charAt(i) == c) {
          result++;
        }
      }
    }
    return result;
  }

  /**
   * 문자열 str이 Email형식을 갖추고 있는지 여부를 확인한다.
   *
   * @param str Email형식을 체크할 문자열.
   * @return true일 경우 Email형식.
   */
  public static boolean isEmailString(String str) {
    if (str != null) {
      return Patterns.EMAIL_ADDRESS.matcher(str).matches();
    }
    return false;
  }

  /**
   * 문자열 str이 URL형식을 갖추고 있는지 여부를 확인 한다.
   *
   * @param str url 형식을 체크할 문자열
   * @return true일 경우 url 형식
   */
  public static boolean isUrlString(String str) {
    if (!TextUtils.isEmpty(str)) {
      return Pattern.compile(ConstantParams.REGEX_VALIDATE_URL).matcher(str).matches();
      //return Patterns.WEB_URL.matcher(str).matches();
    }
    return false;
  }


  /**
   * original 문자열에서 startIndex 에 위치한 글자를 target만큼(길이) 삭제 하고 replacement 문자열로
   * 치환 한다.
   *
   * @param original    원본 문자열
   * @param startIndex  replace를 시작할 위치 index
   * @param endWidth    원본 문자열의 길이
   * @param replacement 교체 할 문자열
   * @return 교체된 문자열 or null.
   */
  public static String replacOnIndex(String original,
                                     @IntRange(from = 0) int startIndex,
                                     @IntRange(from = 0) int endWidth,
                                     String replacement) {
    if (original == null || replacement == null) {
      return null;
    }

    StringBuilder b = new StringBuilder(original);
    try {
      b.delete(startIndex, endWidth);
      b.insert(startIndex, replacement);

    } catch (IndexOutOfBoundsException e) {
      Log.e(TAG, e.getMessage());
      return original;
    }
    return b.toString();
  }

  /**
   * 하이라이팅 할 텍스트뷰의 텍스트를 설정 한다. (기본 red컬러)
   *
   * @param textView      대상 텍스트 뷰.
   * @param color         하이라이트 할 텍스트의 컬러. 기본값은 red.
   * @param highlightText 하이리이팅 할 텍스트.
   * @param original      원본 텍스트.
   */
  public static void setHighlightText(@NonNull TextView textView,
                                      @ColorInt int color,
                                      @NonNull String highlightText,
                                      @NonNull String original) {
    if (original.length() > 0 && highlightText.length() > 0) {
      String hexColor = String.format("#%06X", (0xFFFFFF & color));
      if (!TextUtils.isEmpty(hexColor)) {
        String result = original.replaceAll(highlightText, "<font color='" + hexColor + "'>" + highlightText + "</font>");
        textView.setText(Html.fromHtml(result));
      }
    }
    else {
      Log.w(TAG, "Illegal argument Warning.");
    }
  }

  /**
   * 하이라이팅 할 텍스트뷰의 텍스트를 설정한다. (기본 하이라이팅 컬러 / RED)
   *
   * @param textView      대상 텍스트뷰
   * @param highlightText 하이라이팅 할 텍스트.
   * @param original      원본 텍스트.
   */
  public static void setHighlightText(@NonNull TextView textView,
                                      @NonNull String highlightText,
                                      @NonNull String original) {
    setHighlightText(textView, Color.RED, highlightText, original);
  }

  /**
   * 텍스트에 bold를 설정한 SpannableStringBuilder를 얻는다.
   *
   * @param original 굵음 효과를 설정할 문자열
   * @return 굶음 효과가 설정 된 SpannableStringBuilder instance or null.
   */
  public static SpannableStringBuilder stringToBold(@NonNull String original) {
    SpannableStringBuilder sb = new SpannableStringBuilder(original);
    StyleSpan style = new StyleSpan(Typeface.BOLD);
    sb.setSpan(style, 0, sb.length(), Spannable.SPAN_INCLUSIVE_INCLUSIVE);
    return sb;
  }

  /**
   * 입력한 'original'변수 문자열에서 'findWord'변수로 받은 단어를 찾아서 설정한 color의 span을 설정 해 준다.
   *
   * @param original 원본 텍스트
   * @param findWord 찾을 단어 문자열
   * @param color    설정할 컬러 값
   * @return span이 설정된 객체나 혹은 null
   */
  public static SpannableStringBuilder findTextAndSetColor(@Nullable String original,
                                                           @Nullable String findWord,
                                                           @ColorInt int color) {
    if (!TextUtils.isEmpty(original)) {
      if (!TextUtils.isEmpty(findWord)) {
        final Pattern p = Pattern.compile(findWord);
        final Matcher m = p.matcher(original);

        SpannableStringBuilder sb = new SpannableStringBuilder(original);

        while (m.find()) {
          sb.setSpan(
              new ForegroundColorSpan(color), m.start(), m.end(), Spanned.SPAN_INCLUSIVE_INCLUSIVE
          );
        }
        return sb;
      }
    }
    return null;
  }

  /**
   * 데이터 사이즈에 따른 문자열 텍스트를 얻는다. (예, 10 KB)
   *
   * @param size 사이즈를 알아낼 데이터 사이즈 long value.
   * @return "0" or size text.
   */
  public static String getSizeFormat(@IntRange(from = 0) long size) {
    final String[] units = new String[]{"B", "KB", "MB", "GB", "TB"};
    int digitGroups = (int) (Math.log10(size) / Math.log10(1024));
    return new DecimalFormat("#,##0.#").format(size / Math.pow(1024, digitGroups)) + " " + units[digitGroups];
  }

  /**
   * Uri로부터 file path를 String형태로 얻는다.
   *
   * @param context Context instance
   * @param uri     Uri object
   * @return path or Empty string.
   */
  public static String getPathFromURI(@NonNull Context context, Uri uri) {
    String path = "";
    if (uri != null) {
      Cursor cursor = context.getContentResolver().query(uri, null, null, null, null);
      if (cursor != null) {
        cursor.moveToNext();
        path = cursor.getString(cursor.getColumnIndex("_data"));
        cursor.close();
      }
      else {
        Log.e(TAG, "Cursor is Null.. ");
      }
    }
    else {
      Log.e(TAG, "Uri is Null..");
    }
    return path;
  }

}
