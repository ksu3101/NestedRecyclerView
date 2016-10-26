package kr.swkang.nestedrecyclerview.utils.common.imageloader.cache;

import android.graphics.Bitmap;
import android.support.annotation.NonNull;

import java.io.File;

/**
 * @author KangSung-Woo
 * @since 2016-10-26
 */

public interface BitmapCache {
  void addBitmap(@NonNull String key, @NonNull Bitmap bitmap);

  void addBitmap(@NonNull String key, @NonNull File bitmapFile);

  Bitmap getBitmap(@NonNull String key);

  void clear();
}
