package kr.swkang.nestedrecyclerview.utils.common.imageloader.cache;

import android.graphics.Bitmap;
import android.support.annotation.NonNull;

import java.io.File;

/**
 * @author KangSung-Woo
 * @since 2016-10-26
 */

public class BitmapDiskLruCache
  implements BitmapCache {



  @Override
  public void addBitmap(@NonNull String key, @NonNull Bitmap bitmap) {

  }

  @Override
  public void addBitmap(@NonNull String key, @NonNull File bitmapFile) {

  }

  @Override
  public Bitmap getBitmap(@NonNull String key) {
    return null;
  }

  @Override
  public void clear() {

  }
}
