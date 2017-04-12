package haoshi.com.shop.pop;

import android.content.Context;
import android.view.Gravity;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import base.bean.rxbus.AddFragmentBean;
import base.other.PopBaseView;
import haoshi.com.shop.R;
import haoshi.com.shop.fragment.my.IdentityAuthentFragment;
import util.RxBus;

/**
 * Created by dengmingzhi on 2017/1/18.
 */

public class PopRenZTip extends PopBaseView {

    public PopRenZTip(Context ctx) {
        super(ctx);
    }

    @Override
    protected int getGravity() {
        return Gravity.CENTER;
    }

    @Override
    protected int getAnimation() {
        return 0;
    }

    @Override
    protected View getView() {
        View view = View.inflate(ctx, R.layout.pop_ren_z_tip, null);
        ImageView iv_cancel = (ImageView) view.findViewById(R.id.iv_cancel);
        TextView tv_go = (TextView) view.findViewById(R.id.tv_go);
        tv_go.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //TODO 去认证
                dismiss();
                RxBus.get().post("addFragment", new AddFragmentBean(new IdentityAuthentFragment()));
            }
        });
        iv_cancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dismiss();
            }
        });
        return view;
    }
}
