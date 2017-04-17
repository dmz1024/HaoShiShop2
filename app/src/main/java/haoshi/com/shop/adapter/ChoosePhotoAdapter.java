package haoshi.com.shop.adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import com.bumptech.glide.Glide;
import com.canyinghao.canphotos.CanPhotoHelper;

import java.util.ArrayList;

import base.adapter.BaseAdapter;
import base.adapter.BaseViewHolder;
import base.bean.rxbus.PhotoRxbus;
import butterknife.BindView;
import haoshi.com.shop.R;
import haoshi.com.shop.activity.MainActivity;
import constant.PhotoIndex;
import rx.Observable;
import rx.functions.Action1;
import util.RxBus;


/**
 * Created by dengmingzhi on 2017/1/18.
 */

public class ChoosePhotoAdapter extends BaseAdapter<String> {
    private boolean isDelete;
    private int max = 9;
    private int index = 10;
    private boolean isCanChoose = true;

    public ChoosePhotoAdapter setCanChoose(boolean canChoose) {
        isCanChoose = canChoose;
        return this;
    }

    public ChoosePhotoAdapter setMax(int max) {
        this.max = max;
        return this;
    }

    public ChoosePhotoAdapter(Context ctx, ArrayList<String> list) {
        super(ctx, list);
    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        return new ViewHolder(View.inflate(ctx, R.layout.item_choose_photo, null));
    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, int position) {
        ViewHolder viewHolder = (ViewHolder) holder;

        if (list.size() == max) {
            viewHolder.iv_delete.setVisibility(isDelete ? View.VISIBLE : View.GONE);
            Glide.with(ctx).load(list.get(position)).into(viewHolder.iv_img);
        } else {
            if (position == list.size()) {
                viewHolder.iv_delete.setVisibility(View.GONE);
                Glide.with(ctx).load(R.mipmap.wode_tianjiatu).into(viewHolder.iv_img);
            } else {
                viewHolder.iv_delete.setVisibility(isDelete ? View.VISIBLE : View.GONE);
                Glide.with(ctx).load(list.get(position)).into(viewHolder.iv_img);
            }
        }
    }

    @Override
    public int getItemCount() {
        int count = list.size();
        if (count != max) {
            count = count + 1;
        }
        return count;
    }

    public class ViewHolder extends BaseViewHolder {
        @BindView(R.id.iv_img)
        ImageView iv_img;
        @BindView(R.id.iv_delete)
        ImageView iv_delete;

        public ViewHolder(View itemView) {
            super(itemView);
            if (isCanChoose) {
                itemView.setOnClickListener(this);
                iv_delete.setOnClickListener(this);
                itemView.setOnLongClickListener(this);
            }

        }

        @Override
        protected void onLongClick(int layoutPosition) {
            isDelete = true;
            notifyDataSetChanged();
        }

        @Override
        protected void itemOnclick(int id, int layoutPosition) {
            remove(layoutPosition);
        }

        @Override
        protected void onClick(final int layoutPosition) {
            if (isDelete) {
                isDelete = false;
                notifyDataSetChanged();
            }
            PhotoIndex.INDEX = index;
            initPhotoRxBus();
            CanPhotoHelper.getInstance().gotoPhotoSelect(((MainActivity) ctx), list, max);
        }
    }

    public ChoosePhotoAdapter setIndex(int index) {
        this.index = index;
        return this;
    }

    private Observable<PhotoRxbus> photoRxBus;

    private void initPhotoRxBus() {
        if (photoRxBus == null) {
            photoRxBus = RxBus.get().register("photoRxBus", PhotoRxbus.class);
            photoRxBus.subscribe(new Action1<PhotoRxbus>() {
                @Override
                public void call(PhotoRxbus photoRxbus) {
                    if (PhotoIndex.INDEX == index) {
                        list.clear();
                        list.addAll(((ArrayList<String>) photoRxbus.result));
                        notifyDataSetChanged();
                    }
                }
            });
        }
    }

    public void onDestroy() {
        RxBus.get().unregister("photoRxBus", photoRxBus);
    }
}
