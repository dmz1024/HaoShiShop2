package haoshi.com.shop.pop;

import android.content.Context;
import android.text.TextUtils;
import android.view.View;
import android.widget.EditText;

import base.other.PopBaseView;
import haoshi.com.shop.R;
import haoshi.com.shop.controller.FriendAndFlockController;
import util.MyToast;

/**
 * Created by dengmingzhi on 2017/3/14.
 */

public class PopAddFriendGroup extends PopBaseView {
    public PopAddFriendGroup(Context ctx) {
        super(ctx);
    }

    @Override
    protected View getView() {
        View view = View.inflate(ctx, R.layout.pop_add_flock_group, null);
        final EditText et_name = (EditText) view.findViewById(R.id.et_name);
        view.findViewById(R.id.tv_ok).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String name = et_name.getText().toString();
                if (TextUtils.isEmpty(name)) {
                    MyToast.showToast("请输入分组名称");
                    return;
                }
                addGroup(name);
            }
        });
        return view;
    }

    private void addGroup(final String name) {
        dismiss();
        FriendAndFlockController.getInstance().addGroup(name);
    }

    @Override
    protected int getAnimation() {
        return com.mall.naiqiao.mylibrary.R.style.small_2_big;
    }

    @Override
    protected int width() {
        return 100;
    }
}
