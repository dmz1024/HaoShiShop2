package haoshi.com.shop.fragment.chat;

import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.TextUtils;
import android.view.View;

import java.util.ArrayList;
import java.util.Map;

import base.bean.rxbus.AddFragmentBean;
import base.fragment.SingleNetWorkBaseFragment;
import butterknife.BindView;
import butterknife.ButterKnife;
import haoshi.com.shop.R;
import haoshi.com.shop.adapter.ChatMessageAdapter;
import haoshi.com.shop.bean.chat.MessageBean;
import haoshi.com.shop.bean.chat.impl.MessagesImpl;
import haoshi.com.shop.constant.ApiConstant;
import haoshi.com.shop.constant.UserInfo;
import haoshi.com.shop.fragment.index.CommentFragment;
import haoshi.com.shop.fragment.my.MessageFragment;
import haoshi.com.shop.fragment.my.ZanFragment;
import haoshi.com.shop.fragment.shop.MyOrderRootFragment;
import rx.Observable;
import rx.functions.Action1;
import util.RxBus;
import util.SharedPreferenUtil;

/**
 * Created by dengmingzhi on 2017/3/2.
 */

public class ChatMessageFragment extends SingleNetWorkBaseFragment<ChatMessageNetBean> {
    @BindView(R.id.rv_content)
    RecyclerView rv_content;
    private ArrayList<MessageBean> datas = new ArrayList<>();
    private ChatMessageAdapter mAdapter;

    @Override
    protected void writeData(boolean isWrite, ChatMessageNetBean bean) {
        super.writeData(isWrite, bean);
        bean.getData();
        datas.clear();
        datas.addAll(MessagesImpl.getInstance().getDatas());
        mAdapter.notifyDataSetChanged();
    }


    @Override
    protected String url() {
        return ApiConstant.EVN_INDEX;
    }

    @Override
    protected Map<String, String> map() {
        map.put("uid", UserInfo.userId);
        map.put("token", UserInfo.token);
        return super.map();
    }

    @Override
    protected Class<ChatMessageNetBean> getTClass() {
        return ChatMessageNetBean.class;
    }

    @Override
    protected View getHaveDataView() {
        View view = View.inflate(getContext(), R.layout.recycle_view, null);
        ButterKnife.bind(this, view);
        nativeData();
        initNoti();
        return view;
    }

    /**
     * 处理从通知栏过来的消息
     */
    private void initNoti() {
        Map<String, String> types = new SharedPreferenUtil(getContext(), "from_no").getData(new String[]{"type", "id"});
        new SharedPreferenUtil(getContext(), "from_no").setData(new String[]{"type", "", "id", ""});
        if (types != null) {
            String type = types.get("type");
            if (!TextUtils.isEmpty(type)) {
                switch (type) {
                    case "say":
                        RxBus.get().post("addFragment", new AddFragmentBean(ChatViewFragment.getInstance(types.get("id"))));
                        break;
                    case "dianzan":
                        RxBus.get().post("addFragment", new AddFragmentBean(new ZanFragment()));
                        break;
                    case "pinglun":
                        RxBus.get().post("addFragment", new AddFragmentBean(new CommentFragment()));
                        break;
                    case "tongzhi":
                        RxBus.get().post("addFragment", new AddFragmentBean(new MessageFragment()));
                        break;
                    case "fahuo":
                        RxBus.get().post("addFragment", new AddFragmentBean(MyOrderRootFragment.getInstance(3)));
                        break;
                }
            }
        }
    }

    private void nativeData() {
        LinearLayoutManager manager = new LinearLayoutManager(getContext());
        datas.addAll(MessagesImpl.getInstance().getDatas());
        mAdapter = new ChatMessageAdapter(getContext(), datas);
        rv_content.setLayoutManager(manager);
        rv_content.setAdapter(mAdapter);
        initNotifyRxBus();
    }

    @Override
    protected ShowCurrentViewENUM getDefaultView() {
        return ShowCurrentViewENUM.VIEW_HAVE_DATA;
    }


    @Override
    protected View getTitleBarView() {
        return null;
    }

    private Observable<String> messageRxBus;

    private void initNotifyRxBus() {
        if (messageRxBus == null) {
            messageRxBus = RxBus.get().register("message", String.class);
            messageRxBus.subscribe(new Action1<String>() {
                @Override
                public void call(String s) {
                    datas.clear();
                    datas.addAll(MessagesImpl.getInstance().getDatas());
                    mAdapter.notifyDataSetChanged();
                }
            });
        }
    }

    @Override
    protected boolean isCanRefresh() {
        return false;
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        RxBus.get().unregister("message", messageRxBus);
    }
}
