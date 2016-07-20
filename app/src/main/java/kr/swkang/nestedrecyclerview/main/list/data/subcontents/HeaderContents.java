package kr.swkang.nestedrecyclerview.main.list.data.subcontents;

import kr.swkang.nestedrecyclerview.main.list.data.Contents;
import kr.swkang.nestedrecyclerview.main.list.data.ContentsType;

/**
 * @author KangSung-Woo
 * @since 2016/07/20
 */
public class HeaderContents
    extends Contents {
  public static final int VIEWTYPE_VALUE = 0;

  public HeaderContents() {
    super(ContentsType.HEADER);
  }

}
