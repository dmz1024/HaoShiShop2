package haoshi.com.shop.fragment.index;

import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.ViewPager;
import android.view.View;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

import api.ApiRequest;
import base.bean.TipLoadingBean;
import base.bean.rxbus.AddFragmentBean;
import base.fragment.SingleNetWorkBaseFragment;
import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import haoshi.com.shop.R;
import haoshi.com.shop.bean.discover.AllDiscoverClassifyBean;
import haoshi.com.shop.bean.discover.DiscoverTabBean;
import haoshi.com.shop.bean.discover.PublishArticleBean;
import haoshi.com.shop.constant.ApiConstant;
import haoshi.com.shop.constant.UserInfo;
import haoshi.com.shop.fragment.discover.AllDiscoverClassifyFragment;
import haoshi.com.shop.fragment.discover.DiscoverFragment;
import haoshi.com.shop.fragment.my.MyGiveFragment;
import haoshi.com.shop.pop.PopSendDiscover;
import interfaces.OnSingleRequestListener;
import interfaces.OnTitleBarListener;
import util.RxBus;
import view.DefaultTitleBarView;
import view.pop.TipMessage;

/**
 * Created by dengmingzhi on 2017/2/21.
 */

public class IndexThreeFragment extends SingleNetWorkBaseFragment<AllDiscoverClassifyBean> implements OnTitleBarListener {
    @BindView(R.id.tab)
    TabLayout tab;
    @BindView(R.id.vp_content)
    ViewPager vp_content;

    @Override
    protected String url() {
        return ApiConstant.GETTREES;
    }
//    ApiConstant.GETTREES

    @Override
    protected Class<AllDiscoverClassifyBean> getTClass() {
        return AllDiscoverClassifyBean.class;
    }

    @Override
    protected View getHaveDataView() {
        View view = View.inflate(getContext(), R.layout.fragment_index_two, null);
        ButterKnife.bind(this, view);
        return view;
    }

    private AllDiscoverClassifyBean bean;

    @Override
    protected void writeData(boolean isWrite, final AllDiscoverClassifyBean bean) {
        super.writeData(isWrite, bean);
        this.bean = bean;
        final ArrayList<Fragment> fragments = new ArrayList<>();
        for (int i = 0; i < bean.data.size(); i++) {
            fragments.add(DiscoverFragment.getInstance(bean.data.get(i).catId, i == 0));
        }
        vp_content.setOffscreenPageLimit(fragments.size());
        vp_content.setAdapter(new FragmentPagerAdapter(getChildFragmentManager()) {
            @Override
            public Fragment getItem(int position) {
                return fragments.get(position);
            }

            @Override
            public int getCount() {
                return fragments.size();
            }

            @Override
            public CharSequence getPageTitle(int position) {
                return bean.data.get(position).catName;
            }
        });

        tab.setupWithViewPager(vp_content);
    }


    @OnClick(R.id.iv_send)
    void send() {
        new ApiRequest<PublishArticleBean>() {
            @Override
            protected Map<String, String> getMap() {
                Map<String, String> map = new HashMap<>();
                map.put("userId", UserInfo.userId);
                map.put("token", UserInfo.token);
                return map;
            }

            @Override
            protected String getUrl() {
                return ApiConstant.ISPUBLISHARTICLE;
            }

            @Override
            protected boolean getShowSucces() {
                return false;
            }

            @Override
            protected Class<PublishArticleBean> getClx() {
                return PublishArticleBean.class;
            }
        }.setOnRequestListeren(new OnSingleRequestListener<PublishArticleBean>() {
            @Override
            public void succes(boolean isWrite, PublishArticleBean b) {
                if (b.data.isPublish != 1) {
                    int status = getStatus(b.data.isTrue, b.data.shops, b.data.person);
                    String statusString = getCheckInfo(status);

                    switch (status) {
                        case 3:
                        case 6:
                            if (bean != null) {
                                new PopSendDiscover(getContext(), bean.data).showAtLocation(false);
                            }
                            break;
                        default: {
                            new TipMessage(getContext(), new TipMessage.TipMessageBean("提示", statusString + "\n暂不能发送信息", "", "确定")).showAtLocation(true);
                            break;
                        }

                    }


                } else {
                    if (bean != null) {
                        new PopSendDiscover(getContext(), bean.data).showAtLocation(false);
                    }
                }


            }

            @Override
            public void error(boolean isWrite, PublishArticleBean bean, String msg) {

            }
        }).post(new TipLoadingBean());

    }

    @Override
    protected boolean isCanFirstInitData() {
        return false;
    }


    @Override
    protected void initTitleView() {
        ((DefaultTitleBarView) getTitleBar())
                .setTitleContent("发现")
                .setLeftImage(R.mipmap.leimu2)
                .setOnTitleBarListener(this);
//        .setRightImage(R.mipmap.seach2)
    }

    @Override
    protected boolean isCanRefresh() {
        return false;
    }


    @Override
    public void left() {
        RxBus.get().post("addFragment", new AddFragmentBean(new AllDiscoverClassifyFragment()));
    }

    @Override
    public void right() {
        RxBus.get().post("addFragment", new AddFragmentBean(new MyGiveFragment()));
    }

    @Override
    public void center() {

    }


    public static int getStatus(int isTrue, int isShop, int isPerson) {
        int checkStatus = 0;
        if (isTrue == 1) {
            switch (isShop) {
                case 0:
                    switch (isPerson) {
                        case 0:
                            checkStatus = 1;//未认证
                            break;
                        case 1:
                            checkStatus = 2;//审核中
                            break;
                        case 2:
                            checkStatus = 3;//已认证，个人认证
                            break;
                        default: {
                            checkStatus = 4;//不通过
                        }
                    }
                    break;
                case 1:
                    checkStatus = 5;//商家审核中
                    break;
                case 2:
                    checkStatus = 6;//商家已认证
                    break;
                default: {
                    checkStatus = 7;//商家认证不通过
                    break;
                }
            }
        } else {
            checkStatus = 8;
        }

        return checkStatus;
    }


    public static String getCheckInfo(int checkStatus) {
        String checkInfo = "";
        switch (checkStatus) {
            case 1:
                checkInfo = "您的账号未认证";
                break;
            case 2:
            case 5:
                checkInfo = "您的账号正在审核中";
                break;
            case 4:
            case 7:
                checkInfo = "您的账号认证失败";
                break;
            case 8:
                checkInfo = "您的账号信息未完善";
                break;
        }

        return checkInfo;
    }
}
