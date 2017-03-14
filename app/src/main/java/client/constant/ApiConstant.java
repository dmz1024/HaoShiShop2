package client.constant;

/**
 * Created by dengmingzhi on 2017/3/6.
 */

public interface ApiConstant {
    String CHAT = "ws://mall.east-profit.com:7272";
    String BASE = "http://mall.east-profit.com/";
    String SHOP_INDEX = BASE + "app.php/Home/Index/index";//商城首页
    String GOOD_DESC = BASE + "app.php/Home/Goods/goodsInfo";//商品详情
    String MERCHANT_SIFT = BASE + "app.php/Home/Shops/shopStreet";//商家精选
    String GOOD_ALL_CLASSIFY = BASE + "app.php/Home/Cats/allCategorys";//全部分类
    String GOODS_LIST = BASE + "app.php/Home/Cats/catsGoodsList";
    String SHOOP_GOODS_LIST = BASE + "app.php/Home/Cats/getShopGoods";
    String SHOP_INFO = BASE + "app.php/home/Shops/getShopMp";
    String GOOD_EVALUATE = BASE + "app.php/home/Shops/getShopAis";
    String REG = BASE + "app.php/home/Users/toRegist";
    String LOGIN = BASE + "app.php/home/Users/checkLogin";
    String SMS_LOGIN = BASE + "app.php/home/Users/phoneLogin";
    String BIND_UID = BASE + "app.php/home/evn/binduid";
    String SEND_MESSAGE = BASE + "app.php/home/evn/send_message";
    String ADD_SUBGROUP = BASE + "app.php/home/evn/add_subgroup";
}
