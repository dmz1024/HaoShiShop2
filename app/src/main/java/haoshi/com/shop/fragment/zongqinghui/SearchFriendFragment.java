package haoshi.com.shop.fragment.zongqinghui;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.Editable;
import android.text.TextUtils;
import android.text.TextWatcher;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;

import java.util.ArrayList;

import base.bean.rxbus.AddFragmentBean;
import base.fragment.NotNetWorkBaseFragment;
import butterknife.BindView;
import butterknife.OnClick;
import haoshi.com.shop.R;
import haoshi.com.shop.adapter.MySearchFriendHistoryAdapter;
import haoshi.com.shop.constant.UserInfo;
import util.JsonUtil;
import util.RxBus;
import util.SharedPreferenUtil;

/**
 * Created by dengmingzhi on 2017/3/15.
 */

public class SearchFriendFragment extends NotNetWorkBaseFragment {
    private int type;

    public static SearchFriendFragment getInstance(int type) {
        SearchFriendFragment fragment = new SearchFriendFragment();
        Bundle bundle = new Bundle();
        bundle.putInt("type", type);
        fragment.setArguments(bundle);
        return fragment;
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Bundle bundle = getArguments();
        if (bundle != null) {
            type = bundle.getInt("type");
        }
    }

    @BindView(R.id.tv_cancel)
    TextView tv_cancel;
    @BindView(R.id.et_search)
    EditText et_search;
    @BindView(R.id.rv_history)
    RecyclerView rv_history;
    ArrayList<String> datas;
    private MySearchFriendHistoryAdapter mAdapter;

    @Override
    protected void initData() {
        datas = JsonUtil.json2List(new SharedPreferenUtil(getContext(), type == 0 ? "friend_history" : "flock_history" + UserInfo.userId).getString("datas"), String.class);
        if (datas == null) {
            datas = new ArrayList<>();
        }
        LinearLayoutManager manager = new LinearLayoutManager(getContext());

        mAdapter = new MySearchFriendHistoryAdapter(getContext(), datas, type);
        rv_history.setLayoutManager(manager);
        rv_history.setAdapter(mAdapter);

    }

    @Override
    protected void initView() {

        if (type != 0) {
            et_search.setHint("群组名称/群号");
        }

        et_search.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
                tv_cancel.setText(charSequence.toString().length() > 0 ? "搜索" : "取消");
            }

            @Override
            public void afterTextChanged(Editable editable) {

            }
        });
    }

    @Override
    protected int getRId() {
        return R.layout.fragment_search_friend;
    }


    @OnClick(R.id.tv_clear)
    void clear() {
        datas.clear();
        new SharedPreferenUtil(getContext(), type == 0 ? "friend_history" : "flock_history" + UserInfo.userId).setData("datas", JsonUtil.javaBean2Json(datas));
        mAdapter.notifyDataSetChanged();
    }

    @OnClick(R.id.tv_cancel)
    void cancel() {
        if (TextUtils.equals("取消", tv_cancel.getText())) {
            RxBus.get().post("back", "back");
        } else {
            String value = et_search.getText().toString();

            datas.add(0, value);
            mAdapter.notifyDataSetChanged();
            new SharedPreferenUtil(getContext(), type == 0 ? "friend_history" : "flock_history" + UserInfo.userId).setData("datas", JsonUtil.javaBean2Json(datas));
            if (type == 0) {
                RxBus.get().post("addFragment", new AddFragmentBean(MoreFriendFragment.getInstance("name", value, value)));
            } else {
                RxBus.get().post("addFragment", new AddFragmentBean(SexFlockFragment.getInstance("name", value, value)));
            }
        }
    }


    @Override
    protected String getBackColor() {
        return "#f6f6f6";
    }

    @Override
    protected View getTitleBarView() {
        return null;
    }
}
