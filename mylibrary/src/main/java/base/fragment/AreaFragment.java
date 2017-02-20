package base.fragment;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.PagerAdapter;
import android.text.TextUtils;
import android.view.View;
import android.widget.TextView;


import com.mall.naiqiao.mylibrary.R;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

import base.bean.ChooseAreaBean;
import base.bean.SerializableMap;
import butterknife.BindView;
import rx.Observable;
import rx.functions.Action1;
import util.RxBus;
import view.NoScrollViewPager;

/**
 * Created by dengmingzhi on 2017/2/13.
 */

public class AreaFragment extends NotNetWorkBaseFragment {
    private ArrayList<Fragment> fragments = new ArrayList<>();
    private TextView tv_content;
    private StringBuffer areaSb = new StringBuffer();

    @Override
    protected void initData() {
        regAreaRxbus();
        regAreaNoRxbus();
        Map<String, String> map = new HashMap<>();
        map.put("act", "area");
        map.put("id", "1");
        SerializableMap serializableMap = new SerializableMap();
        serializableMap.setMap(map);
        getChildFragmentManager().beginTransaction().add(R.id.fg_content, BaseAreaFragment.getInstance("http://nq.website-art.com/app/areaa.php", serializableMap)).commit();
    }


    @Override
    protected int getRId() {
        return R.layout.pop_choose_area;
    }

    @Override
    protected void initView() {
        tv_content = (TextView) rootView.findViewById(R.id.tv_content);
    }

    private Observable<ChooseAreaBean.Data> area;

    private void regAreaRxbus() {
        if (area == null) {
            area = RxBus.get().register("area", ChooseAreaBean.Data.class);
            area.subscribe(new Action1<ChooseAreaBean.Data>() {
                @Override
                public void call(ChooseAreaBean.Data data) {
                    if (areaSb.length() > 0) {
                        areaSb.append(",");
                    }
                    areaSb.append(data.name).append("-").append(data.id);
                    Map<String, String> map = new HashMap<>();
                    String name = tv_content.getText().toString();
                    tv_content.setText(name.length() == 4 ? data.name : name + "-" + data.name);
                    map.put("act", "area");
                    map.put("id", data.id);
                    SerializableMap serializableMap = new SerializableMap();
                    serializableMap.setMap(map);
                    getChildFragmentManager().beginTransaction().add(R.id.fg_content, BaseAreaFragment.getInstance("http://nq.website-art.com/app/areaa.php", serializableMap)).commit();
                }
            });
        }

    }


    private Observable<Boolean> area_no;

    private void regAreaNoRxbus() {
        if (area_no == null) {
            area_no = RxBus.get().register("area_no", Boolean.class);
            area_no.subscribe(new Action1<Boolean>() {
                @Override
                public void call(Boolean data) {
                    RxBus.get().post("area_data", areaSb.toString());
                    RxBus.get().post("back", "back");
                }
            });
        }

    }

    @Override
    protected String getBackColor() {
        return "#00000000";
    }

    @Override
    protected View getTitleBarView() {
        return null;
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        RxBus.get().unregister("area", area);
        RxBus.get().unregister("area_no", area_no);
    }
}
