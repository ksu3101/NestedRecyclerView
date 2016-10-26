package kr.swkang.nestedrecyclerview.utils.common.imageloader.cache;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.support.annotation.NonNull;
import android.support.v4.util.LruCache;
import android.util.Log;

import java.io.File;

/**
 * @author KangSung-Woo
 * @since 2016-10-26
 */

public class BitmapMemLruCache
  implements BitmapCache {
  private static final String TAG = BitmapMemLruCache.class.getSimpleName();

  private LruCache<String, Bitmap> lruCache;

  public BitmapMemLruCache(int cacheMaxSize) {
    this.lruCache = new LruCache<>(cacheMaxSize);
  }

  @Override
  public void addBitmap(@NonNull String key, @NonNull Bitmap bitmap) {
    lruCache.put(key, bitmap);
  }

  @Override
  public void addBitmap(@NonNull String key, @NonNull File bitmapFile) {
    if(!bitmapFile.exists()) {
      Log.w(TAG, bitmapFile.getAbsolutePath() + " file is not exists.");
      return;
    }
    final Bitmap bitmap = BitmapFactory.decodeFile(bitmapFile.getAbsolutePath());
    lruCache.put(key, bitmap);
  }

  @Override
  public Bitmap getBitmap(@NonNull String key) {
    return lruCache.get(key);
  }

  @Override
  public void clear() {
    lruCache.evictAll();
  }
}
