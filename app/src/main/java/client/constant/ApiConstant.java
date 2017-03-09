package client.constant;

/**
 * Created by dengmingzhi on 2017/3/6.
 */

public interface ApiConstant {
    String BASE = "http://mall.east-profit.com/";
    String SHOP_INDEX = BASE + "app.php/Home/Index/index";//商城首页
    String GOOD_DESC = BASE + "app.php/Home/Goods/goodsInfo";//商品详情
    String MERCHANT_SIFT = BASE + "app.php/Home/Shops/shopStreet";//商家精选
    String GOOD_ALL_CLASSIFY = BASE + "app.php/Home/Cats/allCategorys";//全部分类
    String GOODS_LIST = BASE + "app.php/Home/Cats/catsGoodsList";
}
