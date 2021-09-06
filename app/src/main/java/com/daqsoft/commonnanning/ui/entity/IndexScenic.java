package com.daqsoft.commonnanning.ui.entity;

import android.os.Parcel;
import android.os.Parcelable;

/**
 * 功能
 *
 * @author 严博
 * @version 1.0.0
 * @date 2019-3-20.11:28
 * @since JDK 1.8
 */

public class IndexScenic implements Parcelable {
    private String picture;
    /**
     * 图片
     */
    private String pictureOneToOne;
    /**
     * 2:1图片
     */
    private String pictureTwoToOne;
    /**
     * 图片
     */
    private String coverOneToOne;
    private String coverTwoToOne;
    /**
     * 4:3
     */
    private String coverFourToThree;

    protected IndexScenic(Parcel in) {
        picture = in.readString();
        pictureOneToOne = in.readString();
        pictureTwoToOne = in.readString();
        coverOneToOne = in.readString();
        coverTwoToOne = in.readString();
        coverFourToThree = in.readString();
        name = in.readString();
        address = in.readString();
        summary = in.readString();
        phone = in.readString();
        latitude = in.readString();
        longitude = in.readString();
        levelName = in.readString();
        id = in.readString();
    }

    public IndexScenic() {

    }

    public static final Creator<IndexScenic> CREATOR = new Creator<IndexScenic>() {
        @Override
        public IndexScenic createFromParcel(Parcel in) {
            return new IndexScenic(in);
        }

        @Override
        public IndexScenic[] newArray(int size) {
            return new IndexScenic[size];
        }
    };

    public String getCoverTwoToOne() {
        return coverTwoToOne;
    }

    public void setCoverTwoToOne(String coverTwoToOne) {
        this.coverTwoToOne = coverTwoToOne;
    }

    /**
     * 名字
     */
    private String name;
    /**
     * 地址
     */
    private String address;
    /**
     * 描述
     */
    private String summary;
    /**
     * 电话
     */
    private String phone;
    private String latitude;
    private String longitude;
    /**
     * 等级名称
     */

    private boolean selected = false;

    public boolean isSelected() {
        return selected;
    }

    public void setSelected(boolean selected) {
        this.selected = selected;
    }

    private String levelName;

    public String getPicture() {
        return picture;
    }

    public void setPicture(String picture) {
        this.picture = picture;
    }

    public void setCoverFourToThree(String coverFourToThree) {
        this.coverFourToThree = coverFourToThree;
    }

    public String getLevelName() {
        return levelName;
    }

    public void setLevelName(String levelName) {
        this.levelName = levelName;
    }

    public String getSummary() {
        return summary;
    }

    public void setSummary(String summary) {
        this.summary = summary;
    }

    public String getPictureTwoToOne() {
        return pictureTwoToOne;
    }

    public void setPictureTwoToOne(String pictureTwoToOne) {
        this.pictureTwoToOne = pictureTwoToOne;
    }

    public String getCoverFourToThree() {
        return coverFourToThree;
    }

    public String getLatitude() {
        return latitude;
    }

    public void setLatitude(String latitude) {
        this.latitude = latitude;
    }

    public String getLongitude() {
        return longitude;
    }

    public void setLongitude(String longitude) {
        this.longitude = longitude;
    }

    /**
     * 景区id
     */
    private String id;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getPictureOneToOne() {
        return pictureOneToOne;
    }

    public void setPictureOneToOne(String pictureOneToOne) {
        this.pictureOneToOne = pictureOneToOne;
    }

    public String getCoverOneToOne() {
        return coverOneToOne;
    }

    public void setCoverOneToOne(String coverOneToOne) {
        this.coverOneToOne = coverOneToOne;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(picture);
        dest.writeString(pictureOneToOne);
        dest.writeString(pictureTwoToOne);
        dest.writeString(coverOneToOne);
        dest.writeString(coverTwoToOne);
        dest.writeString(coverFourToThree);
        dest.writeString(name);
        dest.writeString(address);
        dest.writeString(summary);
        dest.writeString(phone);
        dest.writeString(latitude);
        dest.writeString(longitude);
        dest.writeString(levelName);
        dest.writeString(id);
    }
}
