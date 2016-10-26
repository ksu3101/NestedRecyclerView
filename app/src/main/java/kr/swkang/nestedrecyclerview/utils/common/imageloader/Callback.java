package kr.swkang.nestedrecyclerview.utils.common.imageloader;

import android.graphics.Bitmap;

/**
 * @author KangSung-Woo
 * @since 2016-10-26
 */

public interface Callback {
  /**
   * 이미지를 비동기 로드 하기 전에 불리는 메소드
   */
  void onStarted();

  /**
   * 이미지를 불러오는 중 오류 발생시 불리는 메소드
   *
   * @param errorMsg 오류 메시지
   * @param e        오류에 대한 예외 객체
   */
  void onError(String errorMsg, Exception e);

  /**
   * 비동기로 이미지를 불리고 난 뒤에 불리는 메소드
   *
   * @param bmp 불리어진 이미지의 비트맵 객체 혹은 null.
   */
  void onLoadCompleted(Bitmap bmp);
}
