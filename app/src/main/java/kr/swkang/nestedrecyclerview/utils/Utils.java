package kr.swkang.nestedrecyclerview.utils;

import android.app.Activity;
import android.app.ActivityManager;
import android.content.Context;
import android.content.pm.ApplicationInfo;
import android.content.pm.PackageManager;
import android.content.res.Resources;
import android.graphics.Point;
import android.graphics.Rect;
import android.location.LocationManager;
import android.os.Build;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.view.ViewConfigurationCompat;
import android.telephony.TelephonyManager;
import android.text.Layout;
import android.text.Selection;
import android.util.DisplayMetrics;
import android.util.Log;
import android.util.TypedValue;
import android.view.Display;
import android.view.KeyCharacterMap;
import android.view.KeyEvent;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewConfiguration;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.view.inputmethod.InputMethodManager;
import android.widget.EditText;
import android.widget.Toast;


/**
 * @author KangSung-Woo
 * @since 2016/08/18
 */
public class Utils {
  private static final String TAG = Utils.class.getSimpleName();

  private static Toast toast;

  /**
   * show Toast message
   *
   * @param context Context instance
   * @param msg     message text.
   */
  public static void showToast(@NonNull Context context, @NonNull String msg) {
    if (toast != null) {
      toast.setText(msg);
    }
    else {
      toast = Toast.makeText(context, msg, Toast.LENGTH_SHORT);
    }
    toast.show();
  }

  /**
   * 안드로이드 운영체제의 버전(2.3, 4.4)를 얻는다.
   *
   * @return 안드로이드 운영체제 버전 문자열.
   */
  public static String getOSVersion() {
    return Build.VERSION.RELEASE;
  }

  /**
   * 앱의 이름을 얻는다. 앱을 찾을 수 없는 경우 "UNKNOWN"을 반혼 한다.
   *
   * @param context Context
   * @return App name or UNKNOWN.
   */
  public static String getApplicationName(@NonNull Context context) {
    PackageManager packageManager = context.getPackageManager();
    ApplicationInfo applicationInfo = null;
    try {
      applicationInfo = packageManager.getApplicationInfo(context.getApplicationInfo().packageName, 0);
    } catch (final PackageManager.NameNotFoundException e) {
      Log.e(TAG, e.getMessage());
    }
    return (String) (applicationInfo != null ? packageManager.getApplicationLabel(applicationInfo) : "UNKNOWN");
  }

  /**
   * 앱의 버전 코드를 얻는다.
   *
   * @param context Context
   * @return 0 or Version code integer value.
   */
  public static int getApplicationVersionCode(@NonNull Context context) {
    int versionCode = 0;
    try {
      versionCode = context.getPackageManager()
                           .getPackageInfo(context.getPackageName(), 0).versionCode;
    } catch (PackageManager.NameNotFoundException nne) {
      Log.e(TAG, nne.getMessage());
    }
    return versionCode;
  }

  /**
   * 디바이스의 width, height를 구한다.
   *
   * @param context Context
   * @return Point object.
   */
  public static Point getDeviceSize(@NonNull Context context) {
    WindowManager wm = (WindowManager) context.getSystemService(Context.WINDOW_SERVICE);
    Display display = wm.getDefaultDisplay();
    Point size = new Point();
    if (Build.VERSION.SDK_INT > 13) {
      display.getSize(size);
    }
    else {
      size.x = display.getWidth();
      size.y = display.getHeight();
    }
    return size;
  }


  /**
   * Display의 Size를 구한다. (상단 인디케이터 영역을 제외한 실제 표시 영역)
   *
   * @param context Context
   * @return device size Point object.
   */
  public static Point getDisplaySize(@NonNull Context context) {
    DisplayMetrics metrics = context.getResources()
                                    .getDisplayMetrics();

    Point size = new Point();
    size.x = metrics.widthPixels;
    size.y = (int) (metrics.heightPixels - 25 * metrics.density);

    return size;
  }

  /**
   * 디바이스의 Width값을 구한다.
   *
   * @param context Context
   * @return device width.
   */
  public static int getDeviceWidth(Context context) {
    return getDeviceSize(context).x;
  }

  /**
   * 디바이스의 Height값을 구한다.
   *
   * @param context Context
   * @return device height.
   */
  public static int getDeviceHeight(Context context) {
    return getDeviceSize(context).y;
  }

  /**
   * 해상도에 따른 비율 배수를 얻는다.
   *
   * @param context Context.
   * @return Density multiplier value.
   */
  public static float getDensityValue(@NonNull Context context) {
    return context.getResources().getDisplayMetrics().density;
  }

  /**
   * 디바이스의 전화번호(MDN)를 얻는다.
   *
   * @param context Context
   * @return 전화번호 문자열.
   */
  public static String getPhoneNumber(@NonNull Context context) {
    TelephonyManager tMgr = (TelephonyManager) context.getSystemService(Context.TELEPHONY_SERVICE);
    return tMgr.getLine1Number();
  }

  /**
   * 포커싱 된 뷰로 인해 등장한 소프트 키보드를 감 춘다.
   *
   * @param activity 키보드가 보여지고 있는 포커싱 된 뷰가 존재하는 액티비티
   */
  public static void hideSoftKeyboard(@NonNull Activity activity) {
    hideSoftKeyboard(activity, activity.getCurrentFocus());
  }

  /**
   * 포커싱 된 뷰로 인해서 등장한 소프트 키보드를 감춘다.
   *
   * @param context Context
   * @param views   포커싱 된 뷰들
   */
  public static void hideSoftKeyboard(@NonNull Context context, @NonNull View... views) {
    InputMethodManager imm = (InputMethodManager) context.getSystemService(Context.INPUT_METHOD_SERVICE);
    for (View currentView : views) {
      if (currentView == null) continue;
      imm.hideSoftInputFromWindow(currentView.getWindowToken(), 0);
      currentView.clearFocus();
    }
  }

  /**
   * view에 포커스를 요청하고 키보드를 등장 시키게 한다.
   *
   * @param context Context
   * @param view    포커스받고 입력 받을 Input View.
   */
  public static void showSoftKeyboard(@NonNull Context context, @NonNull View view) {
    InputMethodManager imm = (InputMethodManager) context.getSystemService(Context.INPUT_METHOD_SERVICE);
    view.requestFocus();
    imm.showSoftInput(view, 0);
  }

  /**
   * Pixel 단위 숫자를 DPI단위 Float형태의 숫자로 변환한다.
   *
   * @param res   Resources.
   * @param pixel 변환대상 Pixel 단위 숫자.
   * @return Float형태의 DPI.
   */
  public static float convertPixelToDpi(@NonNull Resources res, int pixel) {
    return TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_PX, pixel, res.getDisplayMetrics());
  }

  /**
   * DPI 단위 숫자를 Pixel 단위 Float형태의 숫자로 변환한다.
   *
   * @param res Resources.
   * @param dpi 변환대상 DPI단위의 숫자.
   * @return Float형태의 pixel 숫자.
   */
  public static float convertDpiToPixel(@NonNull Resources res, int dpi) {
    return TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, dpi, res.getDisplayMetrics());
  }

  /**
   * Pixel 단위 숫자를 DPI단위 Float형태의 숫자로 변환한다.
   *
   * @param res   Resources.
   * @param pixel 변환대상 Pixel 단위 숫자.
   * @return Float형태의 DPI.
   */
  public static float convertPixelToDpi(@NonNull Resources res, float pixel) {
    return TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_PX, pixel, res.getDisplayMetrics());
  }

  /**
   * DPI 단위 숫자를 Pixel 단위 Float형태의 숫자로 변환한다.
   *
   * @param res Resources.
   * @param dpi 변환대상 DPI단위의 숫자.
   * @return Float형태의 pixel 숫자.
   */
  public static float convertDpiToPixel(@NonNull Resources res, float dpi) {
    return TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, dpi, res.getDisplayMetrics());
  }

  /**
   * 레이아웃에서 EditText를 상속한 뷰를 제외한 모든 뷰에 키보드를 감추는 기능의 터치 이벤트를 넣는다.
   *
   * @param context Context instance
   * @param v       parent view group
   */
  public static void setOnHideKeyboardTouchUI(@NonNull final Context context, View v) {
    if (v != null) {
      if (!(v instanceof EditText)) {
        v.setOnTouchListener(
          new View.OnTouchListener() {
            @Override
            public boolean onTouch(View et, MotionEvent event) {
              hideSoftKeyboard(context, et);
              return false;
            }
          }
        );
      }
      if (v instanceof ViewGroup) {
        for (int i = 0; i < ((ViewGroup) v).getChildCount(); i++) {
          View childView = ((ViewGroup) v).getChildAt(i);
          setOnHideKeyboardTouchUI(context, childView);
        }
      }
    }
  }

  /**
   * EditText에서 현재 커서의 위치를 EditText 텍스트 상의 Line number를 반환 한다.
   *
   * @param et EditText instance
   * @return line integer number
   */
  public static int getCurrentCursorLineOfEditText(EditText et) {
    if (et != null) {
      final int selectionStart = Selection.getSelectionStart(et.getText());
      Layout layout = et.getLayout();
      if (!(selectionStart == -1)) {
        return layout.getLineForOffset(selectionStart);
      }
    }
    return 0;
  }

  /**
   * serviceNameTag의 서비스가 현재 실행중(Running)인지 여부를 얻는다.
   *
   * @param context        Context
   * @param serviceNameTag 실행중 여부를 확인 할 서비스의 태그 문자열.
   * @return true or false
   */
  public static boolean isServiceRunning(@NonNull Context context, @Nullable String serviceNameTag) {
    if (serviceNameTag != null) {
      ActivityManager manager = (ActivityManager) context.getSystemService(Context.ACTIVITY_SERVICE);
      for (ActivityManager.RunningServiceInfo service : manager.getRunningServices(Integer.MAX_VALUE)) {
        if (service.service.getClassName()
                           .equals(serviceNameTag)) {
          return true;
        }
      }
    }
    return false;
  }

  /**
   * 디바이스에서 NavigationBar를 사용하는지 여부를 얻는다.
   *
   * @param context Context object.
   * @return true일 경우 NavigationBar를 사용 중.
   */
  public static boolean hasNavigationBar(@NonNull Context context) {
    boolean hasMenuKey = ViewConfigurationCompat.hasPermanentMenuKey(ViewConfiguration.get(context));
    boolean hasBackKey = KeyCharacterMap.deviceHasKey(KeyEvent.KEYCODE_BACK);
    return (!hasMenuKey && !hasBackKey);
  }


  /**
   * 하단 Navigation bar의 높이를 얻는다.
   *
   * @param context Context instance
   * @return pixel size of bottom of Navigation bar height or 0
   */
  public static int getNavigationBarHeight(Context context) {
    if (context != null) {
      int statusBarHeightResourceId = context.getResources().getIdentifier("navigation_bar_height", "dimen", "android");
      if (statusBarHeightResourceId > 0) {
        return context.getResources().getDimensionPixelSize(statusBarHeightResourceId);
      }
    }
    return 0;
  }

  /**
   * StatusBar의 높이를 얻는다.
   *
   * @param context Context instance
   * @return pixel size of Statusbar height or 0
   */
  public static int getStatusBarHeight(Context context) {
    if (context != null) {
      int statusBarHeightResourceId = context.getResources().getIdentifier("status_bar_height", "dimen", "android");
      if (statusBarHeightResourceId > 0) {
        return context.getResources().getDimensionPixelSize(statusBarHeightResourceId);
      }
    }
    return 0;
  }

  /**
   * GPS, Network Provider를 사용 할 수 있는지 여부를 얻는다.
   *
   * @param context Context instance
   * @return true일 경우 둘 중 하나 이상의 Provider를 사용 할 수 있다.
   */
  public static boolean isEnableLocationProviders(@NonNull Context context) {
    LocationManager locationManager = (LocationManager) context.getSystemService(Context.LOCATION_SERVICE);
    if (locationManager != null) {
      if (locationManager.isProviderEnabled(LocationManager.GPS_PROVIDER)
        || locationManager.isProviderEnabled(LocationManager.NETWORK_PROVIDER)) {
        return true;
      }
    }
    return false;
  }

  /**
   * 입력한 x, y좌표가 View의 영역 내(Bound)에 존재하는지 여부를 얻는다.
   *
   * @param view  조사할 View의 instance.
   * @param point x, y좌표의 객체.
   * @return true일 경우 x, y좌표가 View내에 존재 한다.
   */
  public static boolean inViewBounds(@NonNull View view, Point point) {
    return inViewBounds(view, point.x, point.y);
  }

  /**
   * 입력한 x, y좌표가 View의 영역 내(Bound)에 존재하는지 여부를 얻는다.
   *
   * @param view 조사할 View의 instance.
   * @param x    x 좌표.
   * @param y    y 좌표.
   * @return true 일 경우 x, y 좌표가 View내에 존재 한다.
   */
  public static boolean inViewBounds(@NonNull View view, int x, int y) {
    final Rect outRect = new Rect();
    int[] location = new int[2];
    view.getDrawingRect(outRect);
    view.getLocationOnScreen(location);
    outRect.offset(location[0], location[1]);
    return outRect.contains(x, y);
  }

  /**
   * OS 버전이 Lollipop버전 이상인지 여부를 얻는다.
   *
   * @return true일 경우 Lollipop 버전 이상.
   */
  public static boolean isOverLollipop() {
    return (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP);
  }

  /**
   * OS 버전이 M(Marshmallow)버전 이상인지 여부를 얻는다.
   *
   * @return true일 경우 M(23) 버전 이상.
   */
  public static boolean isOverMarshmallow() {
    return (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M);
  }

  /**
   * OS 버전이 N(Nougat) 버전 이상인지 여부를 얻는다.
   *
   * @return true일 경우 N(24) 버전 이상.
   */
  public static boolean isOverNougat() {
    return (Build.VERSION.SDK_INT >= Build.VERSION_CODES.N);
  }

}
