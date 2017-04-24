package haoshi.com.shop.bean.zongqinghui;

import android.os.Parcel;
import android.os.Parcelable;

import java.util.ArrayList;

import base.bean.ListBaseBean;

/**
 * Created by dengmingzhi on 2017/3/17.
 */

public class FriendDynamicBean extends ListBaseBean<ArrayList<FriendDynamicBean.Data>> {
    public Info info;

    public static class Data implements Parcelable{
        public ListBean list;
        public int type;

        protected Data(Parcel in) {
            type = in.readInt();
        }

        public static final Creator<Data> CREATOR = new Creator<Data>() {
            @Override
            public Data createFromParcel(Parcel in) {
                return new Data(in);
            }

            @Override
            public Data[] newArray(int size) {
                return new Data[size];
            }
        };

        @Override
        public int describeContents() {
            return 0;
        }

        @Override
        public void writeToParcel(Parcel parcel, int i) {
            parcel.writeInt(type);
        }

        public static class ListBean {
            public String goodsName;
            public String forwardDesc;
            public String goodsCatId;
            public String goodsId;
            public String userId;
            public String userName;
            public String userPhoto;
            public String visitNum;
            public String createTime;
            public int shareType;
            public String groupName;
            public String remarks;
            public int zan;
            public int liulan;
            public int isZan;
            public String did;
            public int article_appraises;
            public ArrayList<String> gallery;
        }

    }

    public static class Info {
        public String userId;
        public String userName;
        public String userPhoto;
    }


}
