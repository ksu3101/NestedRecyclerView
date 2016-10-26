package kr.swkang.nestedrecyclerview.utils.common.imageloader;

import android.graphics.Bitmap;

/**
 * @author KangSung-Woo
 * @since 2016-10-26
 */

public class CallbackAdapter
  implements Callback {
  @Override
  public void onStarted() {
  }

  @Override
  public void onError(String errorMsg, Exception e) {
  }

  @Override
  public void onLoadCompleted(Bitmap bmp) {
  }
}
