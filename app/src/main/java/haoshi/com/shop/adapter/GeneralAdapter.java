package haoshi.com.shop.adapter;

import android.content.Context;
import android.support.v4.app.Fragment;
import android.support.v7.widget.RecyclerView;
import android.text.TextUtils;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;

import java.util.ArrayList;

import base.adapter.BaseAdapter;
import base.adapter.BaseViewHolder;
import base.bean.ChooseStringBean;
import base.bean.rxbus.AddFragmentBean;
import butterknife.BindView;
import haoshi.com.shop.R;
import haoshi.com.shop.bean.GeneralBean;
import haoshi.com.shop.pop.PopContactService;
import jp.wasabeef.glide.transformations.CropCircleTransformation;

import util.DrawableUtil;
import util.RxBus;
import view.TextImage;
import view.pop.ChooseStringView;

/**
 * Created by dengmingzhi on 2017/1/16.
 */

public class GeneralAdapter extends BaseAdapter<GeneralBean> {


    public GeneralAdapter(Context ctx, ArrayList<GeneralBean> list) {
        super(ctx, list);
    }

    public GeneralAdapter(ArrayList<GeneralBean> list) {
        super(list);
    }

    @Override
    public int getItemViewType(int position) {
        return list.get(position).type;
    }


    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        switch (viewType) {
            case 0:
                return new Viewf5f5f5ViewHolder(getView(R.layout.item_view, parent));
            case 1:
                return new MyOrderNumViewHolder(getView(R.layout.item_my_order_num, parent));
            case 2:
                return new MyItemViewHolder(getView(R.layout.item_my_center_item, parent));
            case 3:
                return new ContactServiceViewHolder(getView(R.layout.item_contact_service, parent));
            case 4:
                return new TitleViewHolder(getView(R.layout.item_title, parent));
            case 5:
                return new TitleContentViewHolder(getView(R.layout.item_title_content, parent));
            case 6:
                return new TitleContentGoViewHolder(getView(R.layout.item_title_content_go, parent));
            case 7:
                return new PeosonSetHeadViewHolder(getView(R.layout.item_person_head, parent));
            case 8:
                return new PeosonSetTitleViewHolder(getView(R.layout.item_peoson_set_title, parent));
            case 9:
                return new MessageSetViewHolder(getView(R.layout.item_message_set, parent));
            case 10:
                return new PerfectRegChooseUserInfoViewHolder(getView(R.layout.item_perfect_reg_userinfo, parent));
            case 11:
                return new PerfectRegWriteUserInfoViewHolder(getView(R.layout.item_perfect_reg_write_userinfo, parent));
            case 12:
                return new ShopIndexMenuViewHolder(getView(R.layout.item_contact_service, parent));
            case 13:
                return new IndexFourViewHolder(getView(R.layout.item_my_center_item, parent));
        }
        return null;
    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, int position) {
        GeneralBean data = list.get(position);
        switch (data.type) {
            case 1:
                MyOrderNumViewHolder mHolder = ((MyOrderNumViewHolder) holder);
                mHolder.tv_title.setText(data.title);
                mHolder.tv_title.setCompoundDrawables(null, DrawableUtil.setBounds(ctx.getResources().getDrawable(data.rid)), null, null);
                if (data.num == 0) {
                    mHolder.tv_num.setVisibility(View.GONE);
                } else {
                    mHolder.tv_num.setVisibility(View.VISIBLE);
                    mHolder.tv_num.setText(data.num + "");
                }
                break;
            case 2:
                MyItemViewHolder mHolderItem = ((MyItemViewHolder) holder);
                mHolderItem.tv_title.setText(data.title);
                mHolderItem.iv_img.setImageResource(data.rid);
                break;
            case 3:
                ContactServiceViewHolder mHolderContact = ((ContactServiceViewHolder) holder);
                mHolderContact.tv_title.setText(data.title);
                mHolderContact.tv_title.setCompoundDrawables(null, DrawableUtil.setBounds(ctx.getResources().getDrawable(data.rid)), null, null);
                break;
            case 4:
                TitleViewHolder mHolderTitle = ((TitleViewHolder) holder);
                mHolderTitle.tv_title.setText(data.title);
                break;
            case 5:
                TitleContentViewHolder mHolderTitleContent = ((TitleContentViewHolder) holder);
                mHolderTitleContent.tv_title.setText(data.title);
                mHolderTitleContent.tv_content.setText(data.content);
                break;
            case 6:
                TitleContentGoViewHolder mHolderTitleContentGo = ((TitleContentGoViewHolder) holder);
                mHolderTitleContentGo.tv_title.setText(data.title);
                mHolderTitleContentGo.tv_content.setText(data.content);
                break;
            case 7:
                PeosonSetHeadViewHolder mHolderPeosonSetHead = ((PeosonSetHeadViewHolder) holder);
                Glide.with(ctx).load(data.content).bitmapTransform(new CropCircleTransformation(ctx)).into(mHolderPeosonSetHead.iv_img);
                mHolderPeosonSetHead.tv_title.setText(data.title);
                break;
            case 8:
                PeosonSetTitleViewHolder mHolderPeosonSetTitle = ((PeosonSetTitleViewHolder) holder);
                mHolderPeosonSetTitle.tv_title.setText(data.title);
                break;
            case 9:
                MessageSetViewHolder mHolderMessageSet = ((MessageSetViewHolder) holder);
                mHolderMessageSet.tv_title.setText(data.title);
                mHolderMessageSet.tv_title.setCompoundDrawables(null, null, DrawableUtil.setBounds(ctx.getResources().getDrawable(list.get(position).num == 0 ? R.mipmap.wode_kaiguan : R.mipmap.wode_kaiguan_you)), null);
                break;
            case 10:
                PerfectRegChooseUserInfoViewHolder regUserInfoViewHolder = (PerfectRegChooseUserInfoViewHolder) holder;
                regUserInfoViewHolder.iv_choose.setImageResource(data.isChoose ? R.mipmap.shangcheng_piont : R.mipmap.shangcheng_piont2);
                regUserInfoViewHolder.tv_choose.setText(data.title);
                regUserInfoViewHolder.iv_icon.setImageResource(data.rid);
                break;
            case 11:
                PerfectRegWriteUserInfoViewHolder writeUserInfoViewHolder = (PerfectRegWriteUserInfoViewHolder) holder;
                perfectWriteUserInfo(writeUserInfoViewHolder.et_content, data, position);
                break;
            case 12:
                ShopIndexMenuViewHolder shopIndexMenuViewHolder = ((ShopIndexMenuViewHolder) holder);
                shopIndexMenuViewHolder.tv_title.setText(data.title);
                shopIndexMenuViewHolder.tv_title.setCompoundDrawables(null, DrawableUtil.setBounds(ctx.getResources().getDrawable(data.rid)), null, null);
                break;
            case 13:
                IndexFourViewHolder indexFourViewHolder = ((IndexFourViewHolder) holder);
                indexFourViewHolder.tv_title.setText(data.title);
                indexFourViewHolder.iv_img.setImageResource(data.rid);
                break;
        }
    }

    private void perfectWriteUserInfo(final EditText et_content, final GeneralBean data, final int p) {
        et_content.setHint(data.title);
        if (!TextUtils.isEmpty(data.result)) {
            et_content.setText(data.result);
        }
        final boolean focus = data.strings == null;
        et_content.setFocusableInTouchMode(focus);
        if (!focus) {
            et_content.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    et_content.clearFocus();
                    String[] ts = data.strings.split(",");
                    final ArrayList<ChooseStringBean> chooses = new ArrayList<>();
                    for (String s : ts) {
                        chooses.add(new ChooseStringBean(s));
                    }
                    new ChooseStringView<ChooseStringBean>(ctx, chooses) {
                        @Override
                        protected void itemClick(int position) {
                            data.result = chooses.get(position).getString();
                            notifyItemChanged(p);
                        }
                    }.showAtLocation(focus);
                }
            });
        }
    }

    public class MyOrderNumViewHolder extends BaseViewHolder {
        @BindView(R.id.tv_title)
        TextView tv_title;
        @BindView(R.id.tv_num)
        TextView tv_num;

        public MyOrderNumViewHolder(View itemView) {
            super(itemView);
            itemView.setOnClickListener(this);
        }

        @Override
        protected void onClick(int layoutPosition) {
            chooseItem(layoutPosition);
        }
    }

    public class ContactServiceViewHolder extends BaseViewHolder {
        TextView tv_title;

        public ContactServiceViewHolder(View itemView) {
            super(itemView);
            tv_title = (TextView) itemView;
            itemView.setOnClickListener(this);
        }

        @Override
        protected void onClick(int layoutPosition) {
            super.onClick(layoutPosition);
            chooseItem(layoutPosition);
        }
    }

    public class ShopIndexMenuViewHolder extends BaseViewHolder {
        TextView tv_title;

        public ShopIndexMenuViewHolder(View itemView) {
            super(itemView);
            tv_title = (TextView) itemView;
            itemView.setOnClickListener(this);
        }

        @Override
        protected void onClick(int layoutPosition) {
            RxBus.get().post("addFragment", new AddFragmentBean(list.get(layoutPosition).fragment));
        }
    }

    public class IndexFourViewHolder extends BaseViewHolder {
        @BindView(R.id.tv_title)
        TextView tv_title;
        @BindView(R.id.iv_img)
        ImageView iv_img;

        public IndexFourViewHolder(View itemView) {
            super(itemView);
            itemView.setOnClickListener(this);
        }

        @Override
        protected void onClick(int layoutPosition) {
            chooseItem(layoutPosition);
        }
    }


    public class MyItemViewHolder extends BaseViewHolder {
        @BindView(R.id.tv_title)
        TextView tv_title;
        @BindView(R.id.iv_img)
        ImageView iv_img;

        public MyItemViewHolder(View itemView) {
            super(itemView);
            tv_title.setOnClickListener(this);
        }

        @Override
        protected void itemOnclick(int id, int layoutPosition) {
            if (layoutPosition == list.size() - 1) {
                new PopContactService(ctx).showAtLocation(false);
            } else if (list.get(layoutPosition).fragment != null) {
                RxBus.get().post("addFragment", new AddFragmentBean(list.get(layoutPosition).fragment));
            }
        }
    }

    public class TitleViewHolder extends BaseViewHolder {
        @BindView(R.id.tv_title)
        TextView tv_title;

        public TitleViewHolder(View itemView) {
            super(itemView);
            itemView.setOnClickListener(this);
        }

        @Override
        protected void onClick(int layoutPosition) {
            Fragment fragment = list.get(layoutPosition).fragment;
            if (fragment != null) {
                RxBus.get().post("addFragment", new AddFragmentBean(fragment));
            }
        }
    }

    public class TitleContentViewHolder extends BaseViewHolder {
        @BindView(R.id.tv_title)
        TextView tv_title;
        @BindView(R.id.tv_content)
        TextView tv_content;

        public TitleContentViewHolder(View itemView) {
            super(itemView);
        }
    }

    public class TitleContentGoViewHolder extends BaseViewHolder {
        @BindView(R.id.tv_title)
        TextView tv_title;
        @BindView(R.id.tv_content)
        TextView tv_content;

        public TitleContentGoViewHolder(View itemView) {
            super(itemView);
            itemView.setOnClickListener(this);
        }

        @Override
        protected void onClick(int layoutPosition) {
            super.onClick(layoutPosition);
            chooseItem(layoutPosition);
        }


    }

    public class PeosonSetHeadViewHolder extends BaseViewHolder {
        @BindView(R.id.tv_title)
        TextView tv_title;
        @BindView(R.id.iv_img)
        ImageView iv_img;

        public PeosonSetHeadViewHolder(View itemView) {
            super(itemView);
            itemView.setOnClickListener(this);
        }

        @Override
        protected void onClick(int layoutPosition) {
            super.onClick(layoutPosition);
            chooseItem(layoutPosition);
        }
    }

    public class PeosonSetTitleViewHolder extends BaseViewHolder {
        @BindView(R.id.tv_title)
        TextImage tv_title;

        public PeosonSetTitleViewHolder(View itemView) {
            super(itemView);
            tv_title = (TextImage) itemView;
        }
    }

    public class MessageSetViewHolder extends BaseViewHolder {
        @BindView(R.id.tv_title)
        TextView tv_title;

        public MessageSetViewHolder(View itemView) {
            super(itemView);
            tv_title.setOnClickListener(this);
        }

        @Override
        protected void itemOnclick(int id, int layoutPosition) {
            list.get(layoutPosition).num = list.get(layoutPosition).num == 0 ? 1 : 0;
            tv_title.setCompoundDrawables(null, null, DrawableUtil.setBounds(ctx.getResources().getDrawable(list.get(layoutPosition).num == 0 ? R.mipmap.wode_kaiguan : R.mipmap.wode_kaiguan_you)), null);
        }
    }

    public class Viewf5f5f5ViewHolder extends BaseViewHolder {
        public Viewf5f5f5ViewHolder(View itemView) {
            super(itemView);
        }
    }


    public class PerfectRegChooseUserInfoViewHolder extends BaseViewHolder {
        @BindView(R.id.iv_choose)
        ImageView iv_choose;
        @BindView(R.id.tv_choose)
        TextView tv_choose;
        @BindView(R.id.iv_icon)
        ImageView iv_icon;

        public PerfectRegChooseUserInfoViewHolder(View itemView) {
            super(itemView);
            itemView.setOnClickListener(this);
        }

        @Override
        protected void onClick(int layoutPosition) {
            list.get(layoutPosition).isChoose = !list.get(layoutPosition).isChoose;
            notifyItemChanged(layoutPosition);
        }
    }


    public class PerfectRegWriteUserInfoViewHolder extends BaseViewHolder {
        @BindView(R.id.et_content)
        EditText et_content;

        public PerfectRegWriteUserInfoViewHolder(View itemView) {
            super(itemView);

        }
    }


    protected void chooseItem(int position){

    }
}
