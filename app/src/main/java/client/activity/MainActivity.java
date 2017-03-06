package client.activity;

import android.content.Intent;
import android.net.Uri;

import com.canyinghao.canphotos.CanPhotoHelper;
import com.yanzhenjie.album.Album;

import base.activity.BaseActivity;
import base.bean.rxbus.AddFragmentBean;
import base.bean.rxbus.ChooseFileRxBus;
import base.bean.rxbus.PhotoRxbus;
import client.fragment.login.LoginFragment;
import constant.ChooseFileIndex;
import constant.ConstantForResult;
import constant.PhotoIndex;
import util.RxBus;
import util.Util;

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
        if (resultCode == RESULT_OK && data != null) {
            if (requestCode == ConstantForResult.CHOOSE_FILE) {
                Uri uri = data.getData();//得到uri，后面就是将uri转化成file的过程。
                RxBus.get().post("chooseFileRxBus", new ChooseFileRxBus(ChooseFileIndex.INDEX, Util.getPathByUri4kitkat(this, uri)));
            } else if (requestCode == ConstantForResult.CHOOSE_PHOTO_SINGLE) {
                RxBus.get().post("photoRxBus", new PhotoRxbus(PhotoIndex.INDEX, Album.parseResult(data).get(0)));
            } else if (requestCode == ConstantForResult.CHOOSE_PHOTO_LIST) {
                RxBus.get().post("photoRxBus", new PhotoRxbus(PhotoIndex.INDEX, Album.parseResult(data)));
            } else if (data.hasExtra(CanPhotoHelper.PHOTO_COLLECTION)) {
                RxBus.get().post("photoRxBus", new PhotoRxbus(PhotoIndex.INDEX, data.getStringArrayListExtra(CanPhotoHelper.PHOTO_COLLECTION)));
            }
        }
    }
}
