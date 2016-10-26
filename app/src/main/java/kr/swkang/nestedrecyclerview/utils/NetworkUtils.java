package kr.swkang.nestedrecyclerview.utils;

import android.content.Context;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.support.annotation.NonNull;
import android.text.TextUtils;
import android.util.Log;
import android.util.Patterns;

import java.io.UnsupportedEncodingException;
import java.net.Inet4Address;
import java.net.InetAddress;
import java.net.NetworkInterface;
import java.net.SocketException;
import java.net.URLDecoder;
import java.net.URLEncoder;
import java.util.Enumeration;
import java.util.HashMap;
import java.util.Map;
import java.util.regex.Pattern;

import kr.swkang.snstemplate.utils.common.ConstantParams;

/**
 * @author KangSung-Woo
 * @since 2016/09/01
 */
public class NetworkUtils {
  private static final String TAG = NetworkUtils.class.getSimpleName();

  /**
   * 디바이스의 IP 주소를 얻는다.
   *
   * @return IP 주소 문자열 혹은 비어있는 문자열.
   */
  public static String getLocalIPAddress_IPv6() {
    try {
      //Enumerate all the network interfaces
      for (Enumeration<NetworkInterface> en = NetworkInterface.getNetworkInterfaces();
           en.hasMoreElements(); ) {
        NetworkInterface intf = en.nextElement();
        // Make a loop on the number of IP addresses related to each Network Interface
        for (Enumeration<InetAddress> enumIpAddr = intf.getInetAddresses();
             enumIpAddr.hasMoreElements(); ) {
          InetAddress inetAddress = enumIpAddr.nextElement();
          //Check if the IP address is not a loopback address, in that case it is
          //the IP address of your mobile device
          if (!inetAddress.isLoopbackAddress()) {
            return inetAddress.getHostAddress();
          }
        }
      }
    } catch (SocketException e) {
      Log.e(TAG, e.toString());
    }
    return "";
  }

  /**
   * 디바이스의 IP 주소를 얻는다.
   *
   * @return IP 주소 문자열 혹은 비어있는 문자열.
   */
  public static String getLocalIPAddress_IPv4() {
    try {
      for (Enumeration en = NetworkInterface.getNetworkInterfaces(); en.hasMoreElements(); ) {
        NetworkInterface intf = (NetworkInterface) en.nextElement();
        for (Enumeration enumIpAddr = intf.getInetAddresses(); enumIpAddr.hasMoreElements(); ) {
          InetAddress inetAddress = (InetAddress) enumIpAddr.nextElement();
          if (!inetAddress.isLoopbackAddress() && inetAddress instanceof Inet4Address) {
            return inetAddress.getHostAddress().toString();
          }
        }
      }
    } catch (SocketException ex) {
      Log.e(TAG, ex.toString());
    }
    return "";
  }


  /**
   * 디바이스의 네트워크 연결 여부를 확인 한다.
   *
   * @param context Context
   * @return true일 경우 온라인 상태. false 일 경우 오프라인 상태.
   */
  public static boolean isOnline(Context context) {
    if (context != null) {
      ConnectivityManager connMgr = (ConnectivityManager) context.getSystemService(Context.CONNECTIVITY_SERVICE);
      NetworkInfo networkInfo = connMgr.getActiveNetworkInfo();
      return (networkInfo != null && networkInfo.isConnected());
    }
    return false;
  }

  /**
   * 연결되어진 데이터 네트워크의 타입을 얻는다.
   *
   * @param context Context
   * @return TYPE_MOBILE, TYPE_WIFI or -1
   * @see ConnectivityManager
   */
  public static int getNetworkConnectionType(@NonNull Context context) {
    ConnectivityManager mgr = (ConnectivityManager) context.getSystemService(Context.CONNECTIVITY_SERVICE);
    if (mgr.getNetworkInfo(ConnectivityManager.TYPE_MOBILE).isConnected()) {
      return ConnectivityManager.TYPE_MOBILE;
    }
    else if (mgr.getNetworkInfo(ConnectivityManager.TYPE_WIFI).isConnected()) {
      return ConnectivityManager.TYPE_WIFI;
    }
    else {
      return -1;
    }
  }

  /**
   * str을 URLEncode 한다
   *
   * @param str 원본 문자열
   * @return URNEncode로 encode 한 문자열
   */
  public static String urlEncode(String str) {
    String buf = str;
    try {
      if (!TextUtils.isEmpty(str)) {
        buf = URLEncoder.encode(str, ConstantParams.DEFAULT_CHAR_SET);
      }
    } catch (UnsupportedEncodingException uee) {
      Log.e(TAG, uee.getMessage());
    }
    return buf;
  }

  /**
   * str을 URLDecode 하고 공백문자를 일괄적으로 바꿔 준다.
   *
   * @param str 원본 문자열
   * @return URLDecode로 decode 한 문자열
   */
  public static String urlDecode(String str) {
    String buf = str;
    try {
      if (!TextUtils.isEmpty(str)) {
        buf = URLDecoder.decode(str, ConstantParams.DEFAULT_CHAR_SET);
      }
    } catch (UnsupportedEncodingException uee) {
      Log.e(TAG, uee.getMessage());
    }
    return buf;
  }

  /**
   * Map(Key, Value pair set)으로 구성된 Request parameter 집합체를 Request post body에 실을 수
   * 있게 String 으로 만들어 준다.
   *
   * @param requestParams Map으로 구성된 Request parameters.
   * @return 만들어진 query String.
   */
  public static String createRequestQuery(@NonNull Map<String, String> requestParams) {
    StringBuilder sb = new StringBuilder();
    for (HashMap.Entry<String, String> e : requestParams.entrySet()) {
      if (sb.length() > 0) {
        sb.append('&');
      }
      try {
        sb.append(URLEncoder.encode(e.getKey(), "UTF-8"))
          .append('=')
          .append(URLEncoder.encode(e.getValue(), "UTF-8"));
      } catch (UnsupportedEncodingException uee) {
        Log.e(TAG, uee.getMessage());
      }
    }
    return sb.toString();
  }

  /**
   * 입력받은 key와 value를 바탕으로 Request post body에 실을 String request parameter를
   * 만들어 준다.
   *
   * @param key1   parameter key 1
   * @param value1 parameter value 1
   * @param key2   parameter key 2
   * @param value2 parameter value 2
   * @return query String.
   */
  public static String createRequestQuery(String key1, String value1, String key2, String value2) {
    return createRequestQuery(key1, value1) + "&" + createRequestQuery(key2, value2);
  }

  /**
   * 입력받은 key와 value를 바탕으로 Request post body에 실을 String request parameter를
   * 만들어 준다.
   *
   * @param key   Parameter key
   * @param value Parameter value
   * @return 만들어진 query String.
   */
  public static String createRequestQuery(@NonNull String key, String value) {
    return key + "=" + value;
  }

}
