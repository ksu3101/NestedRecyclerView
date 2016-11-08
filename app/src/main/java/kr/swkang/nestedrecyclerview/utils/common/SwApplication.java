package kr.swkang.nestedrecyclerview.utils.common;

import android.app.Application;

import kr.swkang.nestedrecyclerview.utils.common.imageloader.cache.BitmapCacheLoader;

/**
 * @author KangSung-Woo
 * @since 2016-11-08
 */

public class SwApplication
    extends Application {

  @Override
  public void onCreate() {
    BitmapCacheLoader.initializeCache(4 * 1024 * 1024);
  }

}
