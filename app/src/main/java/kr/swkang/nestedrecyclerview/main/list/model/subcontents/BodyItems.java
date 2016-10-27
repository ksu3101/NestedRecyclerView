package kr.swkang.nestedrecyclerview.main.list.model.subcontents;

import android.os.Parcel;
import android.os.Parcelable;

import kr.swkang.nestedrecyclerview.main.list.model.Contents;
import kr.swkang.nestedrecyclerview.main.list.model.ContentsType;

/**
 * @author KangSung-Woo
 * @since 2016/07/22
 */
public class BodyItems
  extends Contents
  implements Parcelable {
  public static final Parcelable.Creator<BodyItems> CREATOR = new Parcelable.Creator<BodyItems>() {
    @Override
    public BodyItems createFromParcel(Parcel source) {
      return new BodyItems(source);
    }

    @Override
    public BodyItems[] newArray(int size) {
      return new BodyItems[size];
    }
  };
  private int     id;
  private String  thumbnailImgUrl;
  private String  title;
  private String  desc;
  private boolean isFavorites;

  public BodyItems(int id, String thumbnailImgUrl, String title, String desc) {
    super(ContentsType.BODY_HALF);
    this.id = id;
    this.thumbnailImgUrl = thumbnailImgUrl;
    this.title = title;
    this.desc = desc;
    this.isFavorites = false;
  }

  protected BodyItems(Parcel in) {
    super(in);
    this.id = in.readInt();
    this.thumbnailImgUrl = in.readString();
    this.title = in.readString();
    this.desc = in.readString();
    this.isFavorites = (in.readInt() == 1);
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

  public boolean isFavorites() {
    return isFavorites;
  }

  public void setFavorites(boolean favorites) {
    this.isFavorites = favorites;
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
    dest.writeInt(this.isFavorites ? 1 : 0);
  }
}
