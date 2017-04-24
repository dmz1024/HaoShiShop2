package base;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.view.PagerAdapter;
import android.support.v4.view.ViewPager;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import com.bumptech.glide.Glide;
import com.mall.naiqiao.mylibrary.R;

import java.util.ArrayList;
import java.util.List;

import base.fragment.NotNetWorkBaseFragment;
import butterknife.BindView;
import util.SharedPreferenUtil;

/**
 * Created by dengmingzhi on 2017/4/19.
 */

public class GuideFragment extends NotNetWorkBaseFragment {
    private ArrayList<Integer> images;

    public static GuideFragment getInstance(ArrayList<Integer> images) {
        GuideFragment fragment = new GuideFragment();
        Bundle bundle = new Bundle();
        bundle.putIntegerArrayList("images", images);
        fragment.setArguments(bundle);
        return fragment;
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Bundle bundle = getArguments();
        if (bundle != null) {
            images = bundle.getIntegerArrayList("images");
        }
    }

    @Override
    protected View getTitleBarView() {
        return null;
    }

    private ViewPager vp_content;

    @Override
    protected void initData() {

    }

    @Override
    protected int getRId() {
        return R.layout.fragment_guide;
    }

    @Override
    protected void initView() {
        super.initView();
        vp_content = (ViewPager) rootView.findViewById(R.id.vp_content);
        vp_content.setAdapter(new GuideAdapter());
    }


    class GuideAdapter extends PagerAdapter {
        private List<ImageView> views = new ArrayList<>();

        public GuideAdapter() {
            creatView();
        }

        private void creatView() {
            for (int i = 0; i < images.size(); i++) {

                ImageView view = new ImageView(getContext());
                view.setScaleType(ImageView.ScaleType.FIT_XY);
                view.setLayoutParams(new ViewGroup.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.MATCH_PARENT));
                Glide.with(getContext()).load(images.get(i)).into(view);
                if (i == images.size() - 1) {
                    view.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {
                            new SharedPreferenUtil(getContext(), "FirstLogin").setData("isFirst", true);
                            if (onGuideInterface != null) {
                                onGuideInterface.start();
                            }
                        }
                    });
                }
                views.add(view);
            }
        }

        @Override
        public int getCount() {
            return images.size();
        }

        @Override
        public Object instantiateItem(ViewGroup container, int position) {
            container.addView(views.get(position), 0);//添加页卡
            return views.get(position);
        }

        @Override
        public boolean isViewFromObject(View view, Object object) {
            return view == object;
        }

        @Override
        public void destroyItem(ViewGroup container, int position, Object object) {
            container.removeView(views.get(position));
        }
    }

    private OnGuideInterface onGuideInterface;

    public void setOnGuideInterface(OnGuideInterface onGuideInterface) {
        this.onGuideInterface = onGuideInterface;
    }

    public interface OnGuideInterface {
        void start();
    }
}
