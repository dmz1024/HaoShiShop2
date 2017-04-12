package base.adapter;

import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import com.bumptech.glide.Glide;
import com.jude.rollviewpager.RollPagerView;
import com.jude.rollviewpager.adapter.LoopPagerAdapter;

import java.util.ArrayList;

import interfaces.ImageUrlBaseBean;
import util.GlideUtil;

/**
 * Created by dengmingzhi on 2016/12/19.
 */

public class MyLoopPagerAdapter<D extends ImageUrlBaseBean > extends LoopPagerAdapter {
    private ArrayList<D> urls;

    public MyLoopPagerAdapter(RollPagerView viewPager, ArrayList<D> urls) {
        super(viewPager);
        this.urls = urls;
    }

    @Override
    public View getView(ViewGroup container, int position) {
        ImageView view = new ImageView(container.getContext());
        view.setScaleType(ImageView.ScaleType.FIT_XY);
        view.setLayoutParams(new ViewGroup.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.MATCH_PARENT));
        GlideUtil.GlideErrAndOc(container.getContext(),urls.get(position).getUrl(),view);
        return view;
    }

    @Override
    public int getRealCount() {
        return urls.size();
    }
}
