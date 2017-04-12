package haoshi.com.shop.fragment;

import base.bean.BaseBean;
import base.bean.rxbus.AddFragmentBean;
import base.fragment.SingleNetWorkBaseFragment;
import haoshi.com.shop.bean.chat.dao.SendBean;
import haoshi.com.shop.controller.SendMessageController;
import haoshi.com.shop.controller.ShareUtil;
import haoshi.com.shop.fragment.chat.ChatFlockFragment;
import haoshi.com.shop.fragment.chat.ChatFriendFragment;
import haoshi.com.shop.pop.PopDiscoverShare;
import rx.Observable;
import rx.functions.Action1;
import util.MyToast;
import util.RxBus;

/**
 * Created by dengmingzhi on 2017/4/11.
 */

public abstract class BaseShapeFragment<D extends BaseBean> extends SingleNetWorkBaseFragment<D> {
    protected abstract ShareUtil.ShareInfo getShareInfo();

    protected void showShare() {
        new PopDiscoverShare(getContext(), getShareShow()) {
            @Override
            protected void choose(int position) {
                switch (position) {
                    case 0:
                    case 1:
                        ShareUtil.ShareInfo qqInfo = getShareInfo();
                        qqInfo.type = position == 1;
                        ShareUtil.getInstance().Wechat(qqInfo);
                        break;
                    case 2:
                    case 3:
                        ShareUtil.ShareInfo wxInfo = getShareInfo();
                        wxInfo.type = position == 3;
                        ShareUtil.getInstance().QQFriend(wxInfo);
                        break;
                    case 7:
                        initSendFriendRxBus();
                        AddFragmentBean friendFragment = new AddFragmentBean();
                        friendFragment.setFragment(ChatFriendFragment.getInstance(true));
                        friendFragment.setInAnimation(com.mall.naiqiao.mylibrary.R.anim.form_2_up);
                        friendFragment.setOutAnimation(com.mall.naiqiao.mylibrary.R.anim.go_2_down);
                        RxBus.get().post("addFragment", friendFragment);
                        break;
                    case 5:
                        initSendFriendRxBus();
                        AddFragmentBean flockFragment = new AddFragmentBean();
                        flockFragment.setFragment(ChatFlockFragment.getInstance(true));
                        flockFragment.setInAnimation(com.mall.naiqiao.mylibrary.R.anim.form_2_up);
                        flockFragment.setOutAnimation(com.mall.naiqiao.mylibrary.R.anim.go_2_down);
                        RxBus.get().post("addFragment", flockFragment);
                        break;
                }
            }
        }.showAtLocation(false);
    }


    protected boolean getShareShow() {
        return true;
    }


    protected boolean isCanShape;

    @Override
    protected void writeData(boolean isWrite, D bean) {
        super.writeData(isWrite, bean);
        isCanShape = true;
    }

    private Observable<String[]> initSendFriendRxBus;

    private void initSendFriendRxBus() {
        if (initSendFriendRxBus == null) {
            initSendFriendRxBus = RxBus.get().register("initSendFriendRxBus", String[].class);
            initSendFriendRxBus.subscribe(new Action1<String[]>() {
                @Override
                public void call(String[] strings) {
                    MyToast.showToast("已发送");
                    SendBean sendBean = getSendBean();
                    SendMessageController.getInstance().sendSend(getIsGood(), strings[0], strings[1], sendBean.getDesc(), sendBean.getName(), sendBean.getId(), sendBean.getImg());
                }
            });
        }

    }

    protected abstract boolean getIsGood();

    protected abstract SendBean getSendBean();

    @Override
    public void onDestroy() {
        super.onDestroy();
        RxBus.get().unregister("initSendFriendRxBus", initSendFriendRxBus);
    }
}
