package kr.swkang.nestedrecyclerview.utils.common.imageloader.cache;

/**
 * @author KangSung-Woo
 * @since 2016-10-26
 */

public class BitmapCacheLoader {

  private static BitmapCacheLoader instance = new BitmapCacheLoader();

  public static BitmapCacheLoader getInstance() {
    return instance;
  }

}
