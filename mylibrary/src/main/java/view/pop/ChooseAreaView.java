//package view.pop;
//
//import android.content.Context;
//import android.support.design.widget.TabLayout;
//import android.support.v4.app.Fragment;
//import android.support.v4.app.FragmentManager;
//import android.support.v4.app.FragmentPagerAdapter;
//import android.support.v4.view.PagerAdapter;
//import android.view.Gravity;
//import android.view.View;
//
//import com.mall.naiqiao.mylibrary.R;
//
//import java.util.ArrayList;
//import java.util.HashMap;
//import java.util.Map;
//
//import base.bean.ChooseAreaBean;
//import base.bean.SerializableMap;
//import base.fragment.BaseAreaFragment;
//import base.other.PopBaseView;
//import rx.Observable;
//import rx.functions.Action1;
//import util.RxBus;
//import view.NoScrollViewPager;
//
///**
// * Created by dengmingzhi on 2017/2/13.
// */
//
//public class ChooseAreaView extends PopBaseView {
//    private TabLayout tab;
//    private NoScrollViewPager vp_content;
//    private ArrayList<Fragment> fragments = new ArrayList<>();
//
//    public ChooseAreaView(Context ctx) {
//        super(ctx);
//    }
//
//    private FragmentManager manager;
//
//    public ChooseAreaView(Context ctx, FragmentManager manager) {
//        super(ctx);
//        this.manager = manager;
//    }
//
//    private FragmentPagerAdapter mAdapter;
//
//    @Override
//    protected View getView() {
//        regAreaRxbus();
//        View view = View.inflate(ctx, R.layout.pop_choose_area, null);
//        Map<String, String> map = new HashMap<>();
//        map.put("act", "area");
//        map.put("id", "1");
//        SerializableMap serializableMap = new SerializableMap();
//        serializableMap.setMap(map);
//        fragments.add(BaseAreaFragment.getInstance("http://nq.website-art.com/app/areaa.php", serializableMap));
//        vp_content = (NoScrollViewPager) view.findViewById(R.id.vp_content);
//        vp_content.setAdapter(mAdapter = new FragmentPagerAdapter(manager) {
//            @Override
//            public Fragment getItem(int position) {
//                return fragments.get(position);
//            }
//
//            @Override
//            public int getCount() {
//                return fragments.size();
//            }
//
//            @Override
//            public int getItemPosition(Object object) {
//                return PagerAdapter.POSITION_NONE;
//            }
//        });
//        return view;
//    }
//
//    @Override
//    protected int getGravity() {
//        return Gravity.BOTTOM;
//    }
//
//    private Observable<String> area;
//
//    private void regAreaRxbus() {
//        if (area == null) {
//            area = RxBus.get().register("area", String.class);
//            area.subscribe(new Action1<String>() {
//                @Override
//                public void call(String s) {
//                    Map<String, String> map = new HashMap<>();
//                    map.put("act", "area");
//                    map.put("id", s);
//                    SerializableMap serializableMap = new SerializableMap();
//                    serializableMap.setMap(map);
//                    fragments.add(BaseAreaFragment.getInstance("http://nq.website-art.com/app/areaa.php", serializableMap));
//                    mAdapter.notifyDataSetChanged();
//                    vp_content.setCurrentItem(fragments.size()-1);
//                }
//            });
//        }
//
//    }
//}
