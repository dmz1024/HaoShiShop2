package client.bean;

import android.os.Parcel;
import android.os.Parcelable;
import android.support.v4.app.Fragment;

import java.util.ArrayList;

import interfaces.OnStringInterface;

/**
 * Created by dengmingzhi on 2017/1/17.
 */

public class GeneralBean implements Parcelable{
    public String title;
    public int rid;
    public Fragment fragment;
    public int num;
    public String content;
    public int type;
    public boolean isChoose;
    public String strings;
    public String result;
    public GeneralBean(String title, int rid, Fragment fragment, int num, String content, int type) {
        this.title = title;
        this.rid = rid;
        this.fragment = fragment;
        this.num = num;
        this.content = content;
        this.type = type;
    }

    public GeneralBean(String title, Fragment fragment, int type) {
        this.title = title;
        this.fragment = fragment;
        this.type = type;
    }

    public GeneralBean(String title, int num, int type) {
        this.title = title;
        this.num = num;
        this.type = type;
    }

    public GeneralBean(String title, int rid, Fragment fragment, int num, int type) {
        this.title = title;
        this.rid = rid;
        this.fragment = fragment;
        this.num = num;
        this.type = type;
    }

    public GeneralBean(String title, int rid, Fragment fragment, String content, int type) {
        this.title = title;
        this.rid = rid;
        this.fragment = fragment;
        this.content = content;
        this.type = type;
    }

    public GeneralBean(String title, int rid, Fragment fragment, int type) {
        this.title = title;
        this.rid = rid;
        this.fragment = fragment;
        this.type = type;
    }

    public GeneralBean(String title, Fragment fragment, int type, String content) {
        this.title = title;
        this.fragment = fragment;
        this.type = type;
        this.content = content;
    }public GeneralBean(String title, Fragment fragment, String content, int type) {
        this.title = title;
        this.fragment = fragment;
        this.type = type;
        this.content = content;
    }

    public GeneralBean(String title, int type) {
        this.title = title;
        this.type = type;
    }
    public GeneralBean(int type) {
        this.type = type;
    }

    public GeneralBean(String title, int rid, int type, boolean isChoose, String content, String strings) {
        this.title = title;
        this.rid = rid;
        this.type = type;
        this.isChoose = isChoose;
        this.strings = strings;
        this.content = content;
    }

    public GeneralBean(String title,int type,String content,String strings){
        this.title = title;
        this.type = type;
        this.strings = strings;
        this.content = content;
    }

    protected GeneralBean(Parcel in) {
        title = in.readString();
        rid = in.readInt();
        num = in.readInt();
        content = in.readString();
        type = in.readInt();
        isChoose = in.readByte() != 0;
        strings = in.readString();
    }

    public static final Creator<GeneralBean> CREATOR = new Creator<GeneralBean>() {
        @Override
        public GeneralBean createFromParcel(Parcel in) {
            return new GeneralBean(in);
        }

        @Override
        public GeneralBean[] newArray(int size) {
            return new GeneralBean[size];
        }
    };

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel parcel, int i) {
        parcel.writeString(title);
        parcel.writeInt(rid);
        parcel.writeInt(num);
        parcel.writeString(content);
        parcel.writeInt(type);
        parcel.writeByte((byte) (isChoose ? 1 : 0));
        parcel.writeString(strings);
    }

}
