package haoshi.com.shop.fragment.my;

import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.widget.Button;

import java.util.ArrayList;

import base.bean.rxbus.AddFragmentBean;
import base.fragment.NotNetWorkBaseFragment;
import butterknife.BindView;
import butterknife.OnClick;
import haoshi.com.shop.adapter.GeneralAdapter;
import haoshi.com.shop.bean.GeneralBean;
import haoshi.com.shop.R;
import haoshi.com.shop.bean.chat.ConfigInterface;
import haoshi.com.shop.bean.chat.dao.DaoMaster;
import haoshi.com.shop.bean.chat.impl.ChatFriendsImpl;
import haoshi.com.shop.constant.UserInfo;
import interfaces.OnTitleBarListener;
import util.ImageCatchUtil;
import util.MyToast;
import util.RxBus;
import view.DefaultTitleBarView;
import view.pop.TipMessage;

/**
 * Created by dengmingzhi on 2017/1/17.
 */

public class SetFragment extends NotNetWorkBaseFragment implements OnTitleBarListener {
    @BindView(R.id.rv_content)
    RecyclerView rv_content;

    @Override
    protected void initData() {
        final ArrayList<GeneralBean> datas = new ArrayList<>();
        datas.add(new GeneralBean("个人设置", null, 4));
        datas.add(new GeneralBean("推送设置", new MessageSetFragment(), 4));
        datas.add(new GeneralBean("清理缓存", null, 5, ImageCatchUtil.getInstance().getCacheSize(getContext())));
        datas.add(new GeneralBean("关于我们", null, 4));
        LinearLayoutManager manager = new LinearLayoutManager(getContext());
        GeneralAdapter mAdapter = new GeneralAdapter(getContext(), datas) {
            @Override
            protected void chooseItem(int position) {
                switch (position) {
                    case 0:
                        RxBus.get().post("addFragment", new AddFragmentBean(new PeosonSetFragment()));
                        break;
                    case 1:
                        RxBus.get().post("addFragment", new AddFragmentBean(new MessageSetFragment()));
                        break;
                    case 2:
                        //TODO 清理缓存
                        new TipMessage(getContext(), new TipMessage.TipMessageBean("提示", "清除缓存后将重新加载已加载数据,消耗额外流量,是否继续清除?", "取消", "清除")) {
                            @Override
                            protected void right() {
                                MyToast.showToast("正在清除缓存数据");
                                ImageCatchUtil.getInstance().clearImageAllCache();
                                rv_content.postDelayed(new Runnable() {
                                    @Override
                                    public void run() {
                                        dismiss();
                                        datas.get(2).content = ImageCatchUtil.getInstance().getCacheSize(getContext());
                                        notifyDataSetChanged();
                                    }
                                }, 1000);

                            }
                        }
                                .showAtLocation(false);


                        break;
                    case 3:
                        RxBus.get().post("addFragment", new AddFragmentBean(new AboutUsFragment()));
                        break;
                }
            }
        };
        rv_content.setLayoutManager(manager);
        rv_content.setAdapter(mAdapter);
    }


    @OnClick(R.id.bt_exit)
    void exit() {
        new TipMessage(getContext(), new TipMessage.TipMessageBean("提示", "是否确认退出?", "取消", "退出")) {
            @Override
            protected void right() {
                super.right();
                UserInfo.clearUserInfo();
                ConfigInterface.getInstance().closeConnect();
                RxBus.get().post("clearAll", "");
            }
        }.showAtLocation(false);
    }

    @Override
    protected int getRId() {
        return R.layout.fragment_set;
    }

    @Override
    protected void initTitleView() {
        ((DefaultTitleBarView) getTitleBar()).setTitleContent("设置").setOnTitleBarListener(this);
    }

    @Override
    public void left() {
        RxBus.get().post("back", "back");
    }

    @Override
    public void right() {

    }

    @Override
    public void center() {

    }
}
