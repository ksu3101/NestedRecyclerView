package kr.swkang.nestedrecyclerview.main.list.data.subcontents;

import android.support.annotation.NonNull;

import kr.swkang.nestedrecyclerview.main.list.data.Contents;
import kr.swkang.nestedrecyclerview.main.list.data.ContentsType;

/**
 * @author KangSung-Woo
 * @since 2016/07/20
 */
public class BodyContents
    extends Contents {
  public static final int VIEWTYPE_VALUE = 1;

  private String section;

  public BodyContents(String sectionHeaderString) {
    super(ContentsType.BODY);
    this.section = sectionHeaderString;
  }

  public String getSection() {
    return section;
  }

  public void setSection(String section) {
    this.section = section;
  }
}
