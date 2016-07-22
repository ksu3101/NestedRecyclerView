package kr.swkang.nestedrecyclerview.main.list.data.subcontents;

import android.support.annotation.IntRange;

import java.util.ArrayList;

import kr.swkang.nestedrecyclerview.main.list.data.Contents;
import kr.swkang.nestedrecyclerview.main.list.data.ContentsType;

/**
 * @author KangSung-Woo
 * @since 2016/07/20
 */
public class BodyContents
    extends Contents {
  public static final int FULL_VIEWTYPE_VALUE = 1;
  public static final int HALF_VIEWTYPE_VALUE = 2;

  private String                      sectionHeader;
  private ArrayList<BodyContentsItem> bodyContentsItems;

  public BodyContents(@IntRange(from = 1, to = 2) int bodyContentsType, String sectionHeaderString) {
    super(ContentsType.parseFromValue(bodyContentsType));
    this.sectionHeader = sectionHeaderString;
  }

  public String getSectionHeader() {
    return sectionHeader;
  }

  public void setSectionHeader(String sectionHeader) {
    this.sectionHeader = sectionHeader;
  }

  public ArrayList<BodyContentsItem> getBodyContentsItems() {
    return bodyContentsItems;
  }

  public void setBodyContentsItems(ArrayList<BodyContentsItem> dataSet) {
    this.bodyContentsItems = dataSet;
  }

}
