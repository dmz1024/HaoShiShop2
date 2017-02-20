//package base.recycleBin;
//
//import android.view.View;
//import com.mall.naiqiao.mylibrary.R;
//import base.bean.ListBaseBean;
//import base.fragment.ListNetWorkBaseFragment;
//import base.other.TitleBarBaseView;
//import interfaces.OnTitleBarListener;
//import view.DefaultTitleBarView;
//
///**
// * Created by dengmingzhi on 16/6/14.
// */
//public abstract class DefaultTitleBarListNetWorkBaseFragment<D extends ListBaseBean> extends ListNetWorkBaseFragment<D> implements OnTitleBarListener {
//
//    @Override
//    protected void initTitleView() {
//        DefaultTitleBarView titleBar = (DefaultTitleBarView) getTitleBar();
//        titleBar.setTitleContent(getTitleContent()).setTitleSize(getTitleSize()).setTitleColor(getTitleColor())
//                .setRightContent(getRightContent())
//                .setRightSize(getRightSize())
//                .setRightColor(getRightColor()).setLeftImage(getLeftImage())
//                .showVisiLeft(getLeftVisi())
//                .showVisiView(getViewVisi())
//                .setOnTitleBarListener(this);
//
//    }
//
//    @Override
//    protected TitleBarBaseView getTitleBarView() {
//        return new DefaultTitleBarView(getContext());
//    }
//
//    protected int getViewVisi() {
//        return View.VISIBLE;
//    }
//
//    protected int getLeftVisi() {
//        return View.VISIBLE;
//    }
//
//    protected int getLeftImage() {
//        return R.mipmap.icon_left;
//    }
//
//    protected String getRightColor() {
//        return "#666666";
//    }
//
//    protected int getRightSize() {
//        return 14;
//    }
//
//    protected  String getRightContent(){
//        return "";
//    }
//
//    protected String getTitleColor() {
//        return "#333333";
//    }
//
//    protected int getTitleSize() {
//        return 17;
//    }
//
//    protected String getTitleContent() {
//        return "";
//    }
//
//
//    @Override
//    public void left() {
//
//    }
//
//    @Override
//    public void right() {
//
//    }
//
//    @Override
//    public void center() {
//
//    }
//}
