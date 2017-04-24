package base.activity;

import android.support.v4.view.PagerAdapter;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;


import com.ToxicBakery.viewpager.transforms.AccordionTransformer;
import com.bumptech.glide.Glide;
import com.mall.naiqiao.mylibrary.R;

import java.util.ArrayList;

import uk.co.senab.photoview.PhotoView;
import uk.co.senab.photoview.PhotoViewAttacher;

public class LookBigPicActivity extends AppCompatActivity {
    private ViewPager rv_content;
    private ArrayList<BigPicBean> pics;
    private int posotion;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_look_big_pic2);
        final TextView tv_page = (TextView) findViewById(R.id.tv_page);
        rv_content = (ViewPager) findViewById(R.id.rv_content);
        posotion = getIntent().getIntExtra("position", 0);
        pics = getIntent().getParcelableArrayListExtra("data");
        tv_page.setText((posotion + 1) + "/" + pics.size());
        LookBigPicAdapter adapter = new LookBigPicAdapter();
        rv_content.setOnPageChangeListener(new ViewPager.SimpleOnPageChangeListener() {
            @Override
            public void onPageSelected(int position) {
                super.onPageSelected(position);
                tv_page.setText((position + 1) + "/" + pics.size());
            }
        });
        rv_content.setAdapter(adapter);
        rv_content.setCurrentItem(posotion);
        rv_content.setPageTransformer(true, new AccordionTransformer());

    }


    public class LookBigPicAdapter extends PagerAdapter {

        @Override
        public int getCount() {
            return pics.size();
        }

        @Override
        public View instantiateItem(final ViewGroup container, final int position) {
            PhotoView photoView = new PhotoView(LookBigPicActivity.this);

            Glide.with(LookBigPicActivity.this).load(pics.get(position).getBig_pic()).into(photoView);
            photoView.setOnViewTapListener(new PhotoViewAttacher.OnViewTapListener() {
                @Override
                public void onViewTap(View view, float x, float y) {
                    finish();
                }
            });

            container.addView(photoView);

            return photoView;
        }

        @Override
        public void destroyItem(ViewGroup container, int position, Object object) {
            container.removeView((View) object);
        }

        @Override
        public boolean isViewFromObject(View view, Object object) {
            return view == object;
        }

    }
}
