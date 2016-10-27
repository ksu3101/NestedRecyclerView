package kr.swkang.nestedrecyclerview.utils.common.imageloader.cache;

import android.graphics.Bitmap;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;

/**
 * @author KangSung-Woo
 * @since 2016-10-26
 */

public class BitmapCacheLoader {
  private BitmapMemLruCache  lruCache;
  private BitmapDiskLruCache diskLruCache;

  private static class Singleton {
    private static final BitmapCacheLoader instance = new BitmapCacheLoader();
  }

  private BitmapCacheLoader() {
  }

  public static BitmapCacheLoader getInstance() {
    return Singleton.instance;
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
      this.lruCache.addBitmap(key, bitmap);
    }
    if (storeDiskCache) {
      this.diskLruCache.addBitmap(key, bitmap);
    }
  }

}
