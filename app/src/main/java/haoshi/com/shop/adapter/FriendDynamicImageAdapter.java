package haoshi.com.shop.adapter;

import android.content.Context;
import android.content.Intent;
import android.support.v4.app.ActivityCompat;
import android.support.v4.app.ActivityOptionsCompat;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.view.ViewGroup;
import android.widget.FrameLayout;
import android.widget.ImageView;

import java.util.ArrayList;

import base.activity.BigPicBean;
import base.activity.LookBigPicActivity;
import base.adapter.BaseAdapter;
import base.adapter.BaseViewHolder;
import butterknife.BindView;
import haoshi.com.shop.R;
import util.ContextUtil;
import util.GlideUtil;
import util.Util;


/**
 * Created by dengmingzhi on 2017/1/18.
 */

public class FriendDynamicImageAdapter extends BaseAdapter<String> {
    private int height;

    public FriendDynamicImageAdapter(Context ctx, ArrayList<String> list) {
        super(ctx, list);
        height = (Util.getWidth() - (Util.dp2Px(75))) / 3;
    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        return new ViewHolder(getView(R.layout.item_friend_dynamic_image, parent));
    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, int position) {
        GlideUtil.GlideErrAndOc(ctx, list.get(position), ((ViewHolder) holder).iv_img);

    }

    public class ViewHolder extends BaseViewHolder {
        @BindView(R.id.iv_img)
        ImageView iv_img;

        public ViewHolder(View itemView) {
            super(itemView);
            FrameLayout.LayoutParams params = (FrameLayout.LayoutParams) iv_img.getLayoutParams();
            params.height = height;
            iv_img.setLayoutParams(params);
            iv_img.setOnClickListener(this);
        }

        @Override
        protected void itemOnclick(int id, int layoutPosition) {
            super.itemOnclick(id, layoutPosition);
            switch (id) {
                case R.id.iv_img:
                    ArrayList<BigPicBean> pics = new ArrayList<>();

                    for (int i = 0; i < list.size(); i++) {
                        pics.add(new BigPicBean(list.get(i)));
                    }
                    Intent intent = new Intent(ContextUtil.getAct(), LookBigPicActivity.class);
                    intent.putExtra("position", layoutPosition);
                    intent.putParcelableArrayListExtra("data", pics);
                    ActivityOptionsCompat options =
                            ActivityOptionsCompat.makeScaleUpAnimation(iv_img, //The View that the new activity is animating from
                                    (int) iv_img.getWidth() / 2, (int) iv_img.getHeight() / 2, //拉伸开始的坐标
                                    0, 0);//拉伸开始的区域大小，这里用（0，0）表示从无到全屏
                    ActivityCompat.startActivity(ContextUtil.getAct(), intent, options.toBundle());
                    break;
            }
        }
    }
}
