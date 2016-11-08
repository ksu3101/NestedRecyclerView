package kr.swkang.nestedrecyclerview.utils.common.imageloader.cache;

import android.graphics.Bitmap;
import android.support.annotation.IntRange;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.util.Log;

/**
 * @author KangSung-Woo
 * @since 2016-10-26
 */

public class BitmapCacheLoader {
  private static final String TAG = BitmapCacheLoader.class.getSimpleName();

  private static BitmapCacheLoader  instance;
  private static BitmapMemLruCache  lruCache;
  private static BitmapDiskLruCache diskLruCache;
  private static boolean showingLog = true;

  public static BitmapCacheLoader getInstance() {
    if (instance == null) {
      synchronized (BitmapCacheLoader.class) {
        if (instance == null) {
          instance = new BitmapCacheLoader();
        }
      }
    }
    return instance;
  }

  public static void initializeCache(int memCacheSize) {
    lruCache = new BitmapMemLruCache(memCacheSize);
  }

  public static void showLog(boolean isShowingLog) {
    showingLog = isShowingLog;
  }

  @Nullable
  public Bitmap getSavedImage(@NonNull String target) {
    Bitmap result = null;
    if (lruCache != null) {
      result = lruCache.getBitmap(target);
    }
    if (result == null && diskLruCache != null) {
      result = diskLruCache.getBitmap(target);
    }
    return result;
  }

  public void storeImage(@NonNull String key, @NonNull Bitmap bitmap, boolean storeMemCache, boolean storeDiskCache) {
    if (storeMemCache) {
      lruCache.addBitmap(key, bitmap);
    }
    if (storeDiskCache) {
      diskLruCache.addBitmap(key, bitmap);
    }
  }

  private boolean checkMemCacheInstance() {
    if (showingLog && lruCache == null) {
      Log.e(TAG, "LruCache is Null.");
    }
    return (lruCache != null);
  }

}
