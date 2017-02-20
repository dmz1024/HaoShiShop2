package client.fragment.reg;

import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;

import java.util.ArrayList;

import base.bean.rxbus.AddFragmentBean;
import base.fragment.NotNetWorkBaseFragment;
import butterknife.BindView;
import butterknife.OnClick;
import client.R;
import client.adapter.GeneralAdapter;
import client.bean.GeneralBean;
import util.MyToast;
import util.RxBus;
import view.DefaultTitleBarView;

/**
 * Created by dengmingzhi on 2017/2/20.
 */

public class PerfectRegChooseUserInfoFragment extends NotNetWorkBaseFragment {
    @BindView(R.id.rv_content)
    RecyclerView rv_content;
    private ArrayList<GeneralBean> datas;

    @Override
    protected void initData() {
//       String title, int rid, int type, boolean isChoose, ArrayList<String> strings, String content
        datas = new ArrayList<>();
        datas.add(new GeneralBean("姓名", R.mipmap.zhuce_qianming, 10, true, "name", null));
        datas.add(new GeneralBean("姓氏", R.mipmap.zhuce_xingshi, 10, false, "lastname", null));
        datas.add(new GeneralBean("年龄", R.mipmap.zhuce_nianling, 10, true, "age", null));
        datas.add(new GeneralBean("性别", R.mipmap.zhuce_xingbie, 10, true, "sex", new StringBuilder().append("男").append(",").append("女").toString()));
        datas.add(new GeneralBean("现居住地址", R.mipmap.zhuce_juzhudi, 10, false, "nowaddress", null));
        datas.add(new GeneralBean("单位地址", R.mipmap.zhuce_danweidizi, 10, false, "wordaddress", null));
        datas.add(new GeneralBean("老家地址", R.mipmap.zhuce_laojiadizhi, 10, false, "familyaddress", null));
        datas.add(new GeneralBean("星座", R.mipmap.zhuce_xingzuo, 10, false, "constellation",
                new StringBuilder()
                        .append("白羊座").append(",")
                        .append("金牛座").append(",")
                        .append("双子座").append(",")
                        .append("巨蟹座").append(",")
                        .append("狮子座").append(",")
                        .append("处女座").append(",")
                        .append("天秤座").append(",")
                        .append("天蝎座").append(",")
                        .append("射手座").append(",")
                        .append("摩羯座").append(",")
                        .append("水瓶座").append(",")
                        .append("双鱼座").toString()));

        datas.add(new GeneralBean("生肖", R.mipmap.zhuce_shengxiao, 10, false, "zodiac", new StringBuilder()
                .append("鼠").append(",")
                .append("牛").append(",")
                .append("虎").append(",")
                .append("兔").append(",")
                .append("龙").append(",")
                .append("蛇").append(",")
                .append("马").append(",")
                .append("羊").append(",")
                .append("猴").append(",")
                .append("鸡").append(",")
                .append("狗").append(",")
                .append("猪").toString()));
        datas.add(new GeneralBean("血型", R.mipmap.zhuce_xuexing, 10, false, "blood", null));
        datas.add(new GeneralBean("爱好", R.mipmap.zhuce_xingquaihao, 10, false, "like", null));
        datas.add(new GeneralBean("行业", R.mipmap.zhuce_hangye, 10, false, "work", null));

        GridLayoutManager manager = new GridLayoutManager(getContext(), 3);
        GeneralAdapter mAdapter = new GeneralAdapter(getContext(), datas);
        rv_content.setLayoutManager(manager);
        rv_content.setAdapter(mAdapter);
    }

    @Override
    protected int getRId() {
        return R.layout.fragment_perfect_reg_userinfo;
    }

    @Override
    protected void initTitleView() {
        ((DefaultTitleBarView) getTitleBar()).setTitleContent("完善信息");
    }

    @OnClick(R.id.bt_choose)
    void choose() {
        ArrayList<GeneralBean> data = new ArrayList<>();
        for (GeneralBean bean : datas) {
            if (bean.isChoose) {
                data.add(new GeneralBean(bean.title,11,bean.content,bean.strings));
            }
        }
        if (data.size() < 3) {
            MyToast.showToast("请至少选择三项");
            data.clear();
            return;
        }

        RxBus.get().post("addFragment", new AddFragmentBean(PerfectRegWriteUserInfoFragment.getInstance(data)));
    }

}
