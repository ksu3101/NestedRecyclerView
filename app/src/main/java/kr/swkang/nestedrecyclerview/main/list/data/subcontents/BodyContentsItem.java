package kr.swkang.nestedrecyclerview.main.list.data.subcontents;

import android.os.Parcel;
import android.os.Parcelable;

/**
 * @author KangSung-Woo
 * @since 2016/07/22
 */
public class BodyContentsItem
    implements Parcelable {
  private int    id;
  private String thumbnailImgUrl;
  private String title;
  private String desc;

  public BodyContentsItem(int id, String thumbnailImgUrl, String title, String desc) {
    this.id = id;
    this.thumbnailImgUrl = thumbnailImgUrl;
    this.title = title;
    this.desc = desc;
  }

  public int getId() {
    return id;
  }

  public void setId(int id) {
    this.id = id;
  }

  public String getThumbnailImgUrl() {
    return thumbnailImgUrl;
  }

  public void setThumbnailImgUrl(String thumbnailImgUrl) {
    this.thumbnailImgUrl = thumbnailImgUrl;
  }

  public String getTitle() {
    return title;
  }

  public void setTitle(String title) {
    this.title = title;
  }

  public String getDesc() {
    return desc;
  }

  public void setDesc(String desc) {
    this.desc = desc;
  }

  @Override
  public int describeContents() {
    return 0;
  }

  @Override
  public void writeToParcel(Parcel dest, int flags) {
    dest.writeInt(this.id);
    dest.writeString(this.thumbnailImgUrl);
    dest.writeString(this.title);
    dest.writeString(this.desc);
  }

  protected BodyContentsItem(Parcel in) {
    this.id = in.readInt();
    this.thumbnailImgUrl = in.readString();
    this.title = in.readString();
    this.desc = in.readString();
  }

  public static final Parcelable.Creator<BodyContentsItem> CREATOR = new Parcelable.Creator<BodyContentsItem>() {
    @Override
    public BodyContentsItem createFromParcel(Parcel source) {
      return new BodyContentsItem(source);
    }

    @Override
    public BodyContentsItem[] newArray(int size) {
      return new BodyContentsItem[size];
    }
  };
}
