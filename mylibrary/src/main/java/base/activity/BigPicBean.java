package base.activity;

import android.os.Parcel;
import android.os.Parcelable;

import util.Util;

/**
 * Created by dengmingzhi on 2017/4/23.
 */

public class BigPicBean implements Parcelable {
    private String big_pic;
    private int w;
    private int h;
    private String small_pic;

    public BigPicBean(String big_pic) {
        this.big_pic = big_pic;
    }

    public BigPicBean(String big_pic, int w, int h) {
        this.big_pic = big_pic;
        this.w = w;
        this.h = h;
    }

    public BigPicBean(String big_pic, String small_pic, int w, int h) {
        this.big_pic = big_pic;
        this.w = w;
        this.h = h;
        this.small_pic = small_pic;
    }

    protected BigPicBean(Parcel in) {
        big_pic = in.readString();
        w = in.readInt();
        h = in.readInt();
        small_pic = in.readString();
    }

    public static final Creator<BigPicBean> CREATOR = new Creator<BigPicBean>() {
        @Override
        public BigPicBean createFromParcel(Parcel in) {
            return new BigPicBean(in);
        }

        @Override
        public BigPicBean[] newArray(int size) {
            return new BigPicBean[size];
        }
    };

    public String getBig_pic() {
        return big_pic;
    }

    public void setBig_pic(String big_pic) {
        this.big_pic = big_pic;
    }

    public int getW() {
        return w;
    }

    public void setW(int w) {
        this.w = w;
    }

    public int getH() {
        return h;
    }

    public void setH(int h) {
        this.h = h;
    }

    public String getSmall_pic() {
        return small_pic;
    }

    public void setSmall_pic(String small_pic) {
        this.small_pic = small_pic;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel parcel, int i) {
        parcel.writeString(big_pic);
        parcel.writeInt(w);
        parcel.writeInt(h);
        parcel.writeString(small_pic);
    }
}
