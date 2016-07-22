package kr.swkang.nestedrecyclerview.main.list.data;

import kr.swkang.nestedrecyclerview.main.list.data.subcontents.BodySection;
import kr.swkang.nestedrecyclerview.main.list.data.subcontents.HeaderContents;

/**
 * @author KangSung-Woo
 * @since 2016/07/20
 */
public enum ContentsType {
  HEADER(HeaderContents.VIEWTYPE_VALUE),
  BODY_FULL(BodySection.FULL_VIEWTYPE_VALUE),
  BODY_HALF(BodySection.HALF_VIEWTYPE_VALUE),
  SECTION_HEADER(SectionHeader.VIEWTYPE_VALUE);

  private int v;

  ContentsType(int v) {
    this.v = v;
  }

  public int getValue() {
    return v;
  }

  public static ContentsType parseFromValue(int v) {
    switch (v) {
      case HeaderContents.VIEWTYPE_VALUE:
        return HEADER;
      case BodySection.FULL_VIEWTYPE_VALUE:
        return BODY_FULL;
      case BodySection.HALF_VIEWTYPE_VALUE:
        return BODY_HALF;
      default:
        return SECTION_HEADER;
    }
  }

}
