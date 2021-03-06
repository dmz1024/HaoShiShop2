package haoshi.com.shop.constant;

/**
 * Created by dengmingzhi on 2017/3/6.
 */

public interface ApiConstant {
    String CHAT = "ws://mall.east-profit.com:7272";
    String BASE = "http://haoshishop.com/";
    String SHOP_INDEX = BASE + "app.php/Home/Index/index";//商城首页
    String GOOD_DESC = BASE + "app.php/Home/goods/goodsinfo";//商品详情
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
    String LISTGOODSQUERY = BASE + "app.php/home/Favorites/LISTGOODSQUERY";
    String ONEARTICLE = BASE + "app.php/home/Helpcenter/oneArticle";
    String LISTQUERY = BASE + "app.php/home/Helpcenter/listQuery";
    String GETTREES = BASE + "app.php/home/Helpcenter/getTrees";
    String EVN_INDEX = BASE + "app.php/home/evn/index";
    String FRIEND_INFO = BASE + "app.php/home/evn/friend";
    String FRIENDS_LIST = BASE + "app.php/home/evn/friends_list";
    String MYBROWSINGS = BASE + "app.php/home/Helpcenter/myBrowsings";
    String ARLECOLLECTS = BASE + "app.php/home/Helpcenter/arleCollects";
    String BOOMJUICES = BASE + "app.php/home/Helpcenter/boomJuices";
    String COMMENTS = BASE + "app.php/home/Helpcenter/comments";
    String ISSEES = BASE + "app.php/home/Helpcenter/isSees";
    String ZANME = BASE + "app.php/home/Helpcenter/zanMe";
    String ISZAN = BASE + "app.php/home/Helpcenter/isZan";
    String CANCELZAN = BASE + "app.php/home/Helpcenter/cancelZan";
    String ISCOMMENTSEES = BASE + "app.php/home/Helpcenter/isCommentSees";
    String PJARTICLE = BASE + "app.php/home/Helpcenter/pjArticle";
    String CUSTOMDYNAMICS = BASE + "app.php/home/Helpcenter/customDynamics";
    String REG_COMMEND = BASE + "app.php/home/users/reg_commend";
    String FIND_FAMILY = BASE + "app.php/home/users/find_family";
    String FIND_GROUP = BASE + "app.php/home/evn/find_group";
    String JOIN_GROUP = BASE + "app.php/home/evn/join_group";
    String JOIN_FRIEND = BASE + "app.php/home/evn/join_friend";
    String ADDCART = BASE + "app.php/home/carts/addCart";
    String SETTLEMENT = BASE + "app.php/home/carts/settlement";
    String ALLADDRESS = BASE + "app.php/home/userAddress/allAddress";
    String ADDRESS_LISTQUERY = BASE + "app.php/home/Areas/listQuery";
    String ADDRESS_SETDEFAULTS = BASE + "app.php/home/userAddress/setDefaults";
    String ADDRESS_DELETE = BASE + "app.php/home/userAddress/delAddress";
    String ADDRESS_ADDS = BASE + "app.php/home/userAddress/adds";
    String IMG = BASE + "app.php/home/Helpcenter/img";
    String BACKIMG = BASE + "app.php/home/OrderRefunds/img";
    String PERFECT_REG_INFO_TAG = BASE + "app.php/home/Users/tag";
    String PERFECT_EDITFRIEND = BASE + "app.php/home/users/editfriend";
    String WAITPAYBYPAGES = BASE + "app.php/home/Orders/waitPayByPages";
    String SUBMIT_ORDER = BASE + "app.php/home/orders/submit";
    String ORDER_ADD_COMMENT = BASE + "app.php/home/Goodsappraises/add";
    String DISCOVER_DETAIL = BASE + "app.php/home/Helpcenter/goodsDetail";
    String REALFULLREVIEWS = BASE + "app.php/home/Helpcenter/realFullReviews";
    String BUY_CAR = BASE + "app.php/home/Carts/index";
    String CATCALSSLIST = BASE + "app.php/home/Helpcenter/catCalssList";
    String NEWDYNAMICS = BASE + "app.php/home/Helpcenter/newDynamics";
    String ADD_GOOD_COLLECTIONS = BASE + "app.php/home/Goods/myCollections";
    String CANCELCOLLECTIONS = BASE + "app.php/home/Users/cancelCollections";
    String MYCOLLECTIONLISTS = BASE + "app.php/home/Users/myCollectionLists";
    String DELCART = BASE + "app.php/home/carts/delCart";
    String EDIT_FRIEND = BASE + "app.php/home/evn/edit_friend";
    String DEL_SUBGROUP = BASE + "app.php/home/evn/del_subgroup";
    String EDIT_SUBGROUP = BASE + "app.php/home/evn/edit_subgroup";
    String JOIN_LIST = BASE + "app.php/home/evn/join_list";
    String SEND_FILE = BASE + "app.php/home/Helpcenter/evn";
    String TXEXPRESS = BASE + "app.php/home/Orderrefunds/txexpress";

    String DELORDERS = BASE + "app.php/home/Orders/delOrders";
    String USERINTERFACE = BASE + "app.php/home/Users/userInterface";
    String GETALIPAYSAPP = BASE + "app.php/home/Alipays/getAlipaysapp";
    String OUT_FRIEND = BASE + "app.php/home/evn/out_friend";
    String ORDER_DESC = BASE + "app.php/home/Orders/views";
    String GETPHONEVERIFYCODE = BASE + "app.php/home/Sms/getPhoneVerifyCode";
    String PERSONACNS = BASE + "app.php/home/Users/personAcns";
    String PERSONACNSUP = BASE + "app.php/home/Users/personAcnsUp";
    String USERSIMG = BASE + "app.php/home/Users/img";
    String SHOPDISPLAYS = BASE + "app.php/home/Shops/shopDisplays";
    String DATINGMATERIALS = BASE + "app.php/home/Users/datingMaterials";
    String USEREXHIBITION = BASE + "app.php/home/Users/userExhibition";
    String EDITUSERINFO = BASE + "app.php/home/Users/editUserInfo";
    String SUBGROUP_LIST = BASE + "app.php/home/evn/subgroup_list";
    String GROUPINFO = BASE + "app.php/home/evn/groupinfo";
    String ADD_GROUP = BASE + "app.php/home/evn/add_group";
    String FRIENDDYNAMICLIST = BASE + "app.php/home/Helpcenter/friendDynamicList";
    String UPPWDVERIFYCODE = BASE + "app.php/home/Sms/upPwdVerifyCode";
    String UPPWD = BASE + "app.php/home/Users/upPwd";
    String PHONELOGIN = BASE + "app.php/home/Users/phoneLogin";
    String USERSETTINGS = BASE + "app.php/home/Users/userSettings";
    String APPTOKEN = BASE + "app.php/home/Users/appToken";
    String WECHATLOGIN = BASE + "app.php/home/Users/weChatLogin";
    String WXCHATVERIFYCODE = BASE + "app.php/home/Sms/wxChatVerifyCode";
    String CHATCODES = BASE + "app.php/home/Users/chatcodes";
    String REGS = BASE + "app.php/home/Users/regs";
    String MYREWARDS = BASE + "app.php/home/Users/myRewards";
    String MOBILEFRIEND = BASE + "app.php/home/users/mobilefriend";
    String MYARTICLES = BASE + "app.php/home/Users/myArticles";
    String DELETEISSUES = BASE + "app.php/home/Helpcenter/deleteIsSues";
    String CUSTOMCATS = BASE + "app.php/home/Helpcenter/customCats";
    String FORWARDS = BASE + "app.php/home/Helpcenter/forwards";
    String FEEDBACK = BASE + "app.php/home/Messages/feedback";
    String GROUP_LIST = BASE + "app.php/home/evn/group_list";
    String FAMILY_LIST = BASE + "app.php/home/users/family_list";
    String HSMSGSETTINGLIST = BASE + "app.php/home/Users/HsMsgSettingList";
    String HSMSGSETTING = BASE + "app.php/home/Users/HsMsgSetting";
    String EDITSHOPS = BASE + "app.php/home/Shops/editShops";
    String DSSUBMIT = BASE + "app.php/home/orders/dssubmit";
    String CANCELREFUNDS = BASE + "app.php/home/Orderrefunds/cancelRefunds";
    String RECEIVES = BASE + "app.php/home/Orders/receives";
    String REFUND = BASE + "app.php/home/OrderRefunds/refund";
    String REASON = BASE + "app.php/home/OrderRefunds/reason";
    String EXPRESSLIST = BASE + "app.php/home/Express/expressList";
    String HOTSEARCH = BASE + "app.php/home/Cats/hotSearch";
    String WXPAYS = BASE + "app.php/home/Weixinpays/wxpays";
    String ISPUBLISHARTICLE = BASE + "app.php/home/Users/isPublishArticle";
    String CANCELLATIONS = BASE + "app.php/home/Orders/cancellations";
    String SHOPMULTISELECT = BASE + "app.php/home/Shops/shopMultiselect";
    String PAGEQUERY = BASE + "app.php/home/Messages/pageQuery";
    String AGREE = BASE + "app.php/Home/Users/agree";
    String VIEWLOGISTICS = BASE + "app.php/home/Express/viewLogistics";
    String SEARCH_LIST = BASE + "app.php/home/evn/search_list";
    String UNBUNDLING = BASE + "app.php/home/Users/unbundling";
    String BINDTHIRDLOGIN = BASE + "app.php/home/Users/bindThirdLogin";
    String OUT_GROUP = BASE + "app.php/home/evn/out_group";
    String APPBOOTPAGE = BASE + "app.php/Home/Index/appBootPage";


}
