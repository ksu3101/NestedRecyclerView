package kr.swkang.nestedrecyclerview.main.list.data.subcontents;

import android.os.Parcel;
import android.support.annotation.IntRange;

import java.util.ArrayList;

import kr.swkang.nestedrecyclerview.main.list.data.Contents;
import kr.swkang.nestedrecyclerview.main.list.data.ContentsType;

/**
 * @author KangSung-Woo
 * @since 2016/07/20
 */
public class BodySection
    extends Contents {
  public static final int FULL_VIEWTYPE_VALUE = 1;
  public static final int HALF_VIEWTYPE_VALUE = 2;

  private ArrayList<BodyItems> bodyItemses;

  public BodySection() {
    super(ContentsType.BODY_FULL);
  }

  public ArrayList<BodyItems> getBodyItemses() {
    return bodyItemses;
  }

  public void setBodyItemses(ArrayList<BodyItems> dataSet) {
    this.bodyItemses = dataSet;
  }

  @Override
  public int describeContents() {
    return 0;
  }

  @Override
  public void writeToParcel(Parcel dest, int flags) {
    super.writeToParcel(dest, flags);
    dest.writeTypedList(this.bodyItemses);
  }

  protected BodySection(Parcel in) {
    super(in);
    this.bodyItemses = in.createTypedArrayList(BodyItems.CREATOR);
  }

  public static final Creator<BodySection> CREATOR = new Creator<BodySection>() {
    @Override
    public BodySection createFromParcel(Parcel source) {
      return new BodySection(source);
    }

    @Override
    public BodySection[] newArray(int size) {
      return new BodySection[size];
    }
  };
}
