package client.activity;

import android.content.Intent;

import com.canyinghao.canphotos.CanPhotoHelper;

import base.activity.BaseActivity;
import base.bean.rxbus.AddFragmentBean;
import base.bean.rxbus.PhotoRxbus;
import client.fragment.index.IndexFragment;
import client.fragment.login.LoginFragment;
import constant.PhotoIndex;
import util.RxBus;

public class MainActivity extends BaseActivity {


    @Override
    protected void initData() {
        sendFragment();
    }

    private void sendFragment() {
        AddFragmentBean addFragmentBean = new AddFragmentBean(new LoginFragment());
        addFragmentBean.setAddBack(true);
        addFragmentBean.setHaveAnima(true);
        RxBus.get().post("addFragment", addFragmentBean);
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (resultCode == RESULT_OK) {
            if (data != null && data.hasExtra(CanPhotoHelper.PHOTO_COLLECTION)) {
                RxBus.get().post("photoRxBus", new PhotoRxbus(PhotoIndex.index, data.getStringArrayListExtra(CanPhotoHelper.PHOTO_COLLECTION)));
            }
        }
    }
}
