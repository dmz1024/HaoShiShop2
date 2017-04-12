package haoshi.com.shop.pop;

import android.content.Context;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;

import base.other.PopBaseView;
import haoshi.com.shop.R;

/**
 * Created by dengmingzhi on 2017/3/17.
 */

public class PopAddFlockDesc extends PopBaseView {
    private String title;


    public PopAddFlockDesc(Context ctx, String title) {
        super(ctx);
        this.title = title;
    }


    @Override
    protected View getView() {
        View view = View.inflate(ctx, R.layout.pop_add_flock_desc, null);
        TextView tv_title = (TextView) view.findViewById(R.id.tv_title);
        final EditText et_desc = (EditText) view.findViewById(R.id.et_desc);
       tv_title.setText(title);
        view.findViewById(R.id.bt_add).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                dismiss();
                add(et_desc.getText().toString());
            }
        });

        return view;
    }

    protected void add(String string) {

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
