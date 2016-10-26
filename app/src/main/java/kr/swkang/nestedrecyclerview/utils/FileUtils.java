package kr.swkang.nestedrecyclerview.utils;

import android.os.Environment;
import android.text.TextUtils;
import android.util.Log;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;

/**
 * @author KangSungWoo
 * @since 2016/09/01
 */
public class FileUtils {
  private static final String TAG = FileUtils.class.getSimpleName();

  /**
   * SD카드가 마운트 되어 있는지 여부를 얻는다.
   *
   * @return true일 경우 SdCard가 마운트 되어 있음.
   */
  public static boolean isSDCardMounted() {
    String status = Environment.getExternalStorageState();
    return status != null && status.equals(Environment.MEDIA_MOUNTED);
  }

  /**
   * 입력받은 파일 경로인 path에 파일 이름을 얻어서 반환 한다.
   *
   * @param path 파일 이름을 얻을 대상의 path 문자열.
   * @return 파일 이름 문자열. 없거나 문자열이 형식에 맞지 않은 경우 empty string을 반환.
   */
  public static String getFileNameFromPath(String path) {
    if (path != null && !TextUtils.isEmpty(path) && path.contains("/")) {
      return path.substring(path.lastIndexOf("/") + 1);
    }
    return "";
  }

  /**
   * 외부저장소가 저장(Write) 가능 한 상태인지 여부를 확인한다.
   *
   * @return true 일 경우 저장가능.
   */
  public static boolean isExternalStorageWritable() {
    String state = Environment.getExternalStorageState();
    return Environment.MEDIA_MOUNTED.equals(state);
  }

  /**
   * 문자열 str이 이미지 경로 인지 여부를 확인 한다.
   *
   * @param str 이미지 경로인지 체크할 문자열
   * @return true일 경우 이미지 경로
   */
  public static boolean isImagePath(String str) {
    if (!TextUtils.isEmpty(str)) {
      return (str.endsWith(".png") || str.endsWith(".jpg") || str.endsWith(".jpeg") || str.endsWith(".webp") || str.endsWith(".gif"));
    }
    return false;
  }

  /**
   * 파일 하나를 삭제 한다.
   *
   * @param file 삭제할 파일의 경로 혹은 파일 객체
   * @param <T>  String or File instance
   * @return true일 경우 파일 삭제가 성공적으로 수행 됨.
   */
  public static <T> boolean fileDelete(T file) {
    if (file != null) {
      File targetFile = null;
      if (file instanceof String) {
        targetFile = new File((String) file);
      }
      else if (file instanceof File) {
        targetFile = (File) file;
      }
      else {
        Log.w(TAG, "File이나 String파일 절대 경로만 가능 합니다.");
        return false;
      }
      return targetFile.delete();
    }
    return false;
  }


  /**
   * 파일 하나를 이동 한다. AsyncTask등에 태워서 사용 할 것
   *
   * @param fromPath 이동할 파일의 절대 경로
   * @param target   이동할 경로 혹은 파일 객체
   * @param <T>      String or File instance
   * @return true일 경우 이동이 성공적으로 수행 됨.
   */
  public static <T> boolean fileMove(String fromPath, T target) {
    if (!TextUtils.isEmpty(fromPath)) {
      if (target != null) {
        File targetFile = null;
        if (target instanceof String) {
          targetFile = new File((String) target);
        }
        else if (target instanceof File) {
          targetFile = (File) target;
        }
        else {
          // Uri는 getPathFromUri()메소드를 활용 할 것.
          Log.w(TAG, "File이나 String파일 절대 경로만 가능 합니다.");
          return false;
        }

        File fromFile = new File(fromPath);
        if (fromFile.exists()) {
          if (!fromFile.renameTo(targetFile)) {
            // File의 renameTo는 파일을 정상적으로 이동시키지 못하는 경우가 있다.
            // 또한 이에 대한 예외조차 없다.
            try {
              // 기존 파일을 복사 하고 난 뒤 원본을 삭제 한다.
              FileInputStream fis = new FileInputStream(fromFile);
              FileOutputStream fos = new FileOutputStream(targetFile);

              byte[] buf = new byte[1024];
              int read = 0;
              while ((read = fis.read(buf, 0, buf.length)) != -1) {
                fos.write(buf, 0, read);
              }
              fis.close();
              fos.close();

              return fromFile.delete();

            } catch (FileNotFoundException fnfe) {
              Log.e(TAG, fnfe.getMessage());
              return false;
            } catch (IOException ioe) {
              Log.e(TAG, ioe.getMessage());
              return false;
            }
          }
          else {
            return true;
          }
        }
      }
    }
    return false;
  }

  /**
   * 파일 하나를 복사 한다. AsyncTask등에 태워서 처리 할 것
   *
   * @param from   복사할 파일의 절대 경로 혹은 객체
   * @param target 저장할 파일의 경로 혹은 객체
   * @param <T>    String or File instance
   * @return true일 경우 파일 복사가 성공적으로 수행 됨.
   */
  public static <T> boolean fileCopy(T from, T target) {
    if (from != null) {
      File fromFile = null;
      if (from instanceof String) {
        fromFile = new File((String) from);
      }
      else if (from instanceof File) {
        fromFile = (File) from;
      }
      else {
        Log.w(TAG, "File이나 String파일 절대 경로만 가능 합니다.");
        return false;
      }

      if (fromFile.exists()) {
        File targetFile = null;
        if (target != null) {
          if (target instanceof String) {
            targetFile = new File((String) target);
          }
          else if (target instanceof File) {
            targetFile = (File) target;
          }
          else {
            Log.w(TAG, "File이나 String파일 절대 경로만 가능 합니다.");
            return false;
          }

          try {
            FileInputStream fis = new FileInputStream(fromFile);
            FileOutputStream fos = new FileOutputStream(targetFile);
            int readCount = 0;
            byte[] buffer = new byte[1024];
            while ((readCount = fis.read(buffer, 0, 1024)) != -1) {
              fos.write(buffer, 0, readCount);
            }
            fos.close();
            fis.close();

          } catch (IOException ioe) {
            Log.e(TAG, ioe.getMessage());
            return false;
          }

          if (targetFile.length() > 0) {
            return true;
          }
        }
      }
    }
    return false;
  }

}
