package client;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import activity.CommonUtils;
import activity.EaluationListBean;
import activity.LookBigPicActivity;
import base.fragment.ListNetWorkBaseFragment;
import base.fragment.SingleNetWorkBaseFragment;
import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import client.activity.MainActivity;

/**
 * Created by dengmingzhi on 2017/3/8.
 */

public class ImageTestFragment extends SingleNetWorkBaseFragment<ImageTestBean> {
    @BindView(R.id.iv_img)
    ImageView iv_img;

    @Override
    protected String url() {
        return CeshiUrl.TEST;
    }

    @Override
    protected Map<String, String> map() {
        map.put("act", "single");
        return super.map();
    }

    @Override
    protected void writeData(boolean isWrite, ImageTestBean bean) {
        super.writeData(isWrite, bean);
        Glide.with(getContext()).load("http://123.57.255.1:8068/group1/M00/02/B9/ezn_AVap8DmAL311AAAPHa33rQk099.jpg").into(iv_img);
    }

    @Override
    protected Class<ImageTestBean> getTClass() {
        return ImageTestBean.class;
    }

    @Override
    protected View getHaveDataView() {
        View view = View.inflate(getContext(), R.layout.fragment_buy_car, null);
        ButterKnife.bind(this, view);
        return view;
    }

    @OnClick(R.id.iv_img)
    void img() {
        Intent intent = new Intent(getContext(), LookBigPicActivity.class);
        Bundle bundle = new Bundle();
        List<EaluationListBean.EaluationPicBean> attachments = new ArrayList<EaluationListBean.EaluationPicBean>();
        EaluationListBean.EaluationPicBean ealuationPicBean = new EaluationListBean().new EaluationPicBean();

        int height = iv_img.getHeight();
        int width = iv_img.getWidth();
        int[] points = new int[2];
        iv_img.getLocationInWindow(points);
        ealuationPicBean.height = height;
        ealuationPicBean.width = width;
        ealuationPicBean.x = points[0];
        ealuationPicBean.y = points[1] - CommonUtils.getStatusBarHeight(iv_img);
        ealuationPicBean.smallImageUrl = "http://123.57.255.1:8068/group1/M00/02/B9/ezn_AVap8DmAL311AAAPHa33rQk099.jpg";
        ealuationPicBean.imageUrl = "http://123.57.255.1:8068/group1/M00/02/B9/ezn_AVap8DmADopDAAGtRCAjGyg590.jpg";
        attachments.add(ealuationPicBean);

        bundle.putSerializable(LookBigPicActivity.PICDATALIST, (Serializable) attachments);
        intent.putExtras(bundle);
        intent.putExtra(LookBigPicActivity.CURRENTITEM, 0);
        getActivity().startActivity(intent);
//                动画处理
        ((MainActivity) getContext()).overridePendingTransition(0, 0);
    }

}
