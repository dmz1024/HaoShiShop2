package haoshi.com.shop.bean;

import java.util.ArrayList;

import base.bean.ListBaseBean;
import interfaces.ImageUrlBaseBean;

/**
 * Created by dengmingzhi on 2017/3/6.
 */

public class ShopIndexBean extends ListBaseBean<ArrayList<ShopIndexBean.Data>> {
    public InfoBean info;
    public static class InfoBean {
        public ArrayList<AdsBean> ads;

        public static class AdsBean extends ImageUrlBaseBean {
            public int adId;
            public String adName;
            public String adURL;
            public String adFile;
            public int positionWidth;
            public int positionHeight;
            public boolean isOpen;

            @Override
            public String getUrl() {
                return adFile;
            }
        }
    }

    public static class Data {
        public String catId;
        public String catName;
        public String catsImg;
        public ArrayList<GoodsBean> goods;

        public static class GoodsBean {
            public String goodsId;
            public String goodsName;
            public String marketPrice;
            public String shopPrice;
            public String goodsImg;
        }
    }
}
