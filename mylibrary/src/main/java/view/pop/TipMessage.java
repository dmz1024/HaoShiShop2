package view.pop;

import android.content.Context;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.mall.naiqiao.mylibrary.R;

import base.other.PopBaseView;
import view.Color2Text;

/**
 * Created by dengmingzhi on 2016/11/14.
 */

public class TipMessage extends PopBaseView implements View.OnClickListener {
    private TipMessageBean tipMessageBean;


    public TipMessage(Context ctx, TipMessageBean tipMessageBean) {
        super(ctx);
        this.tipMessageBean = tipMessageBean;
    }


    @Override
    protected View getView() {
        View view = View.inflate(ctx, R.layout.tip_message, null);
        TextView tv_title = (TextView) view.findViewById(R.id.tv_title);
        TextView tv_message = (TextView) view.findViewById(R.id.tv_message);
        Button bt_left = (Button) view.findViewById(R.id.bt_left);
        Button bt_right = (Button) view.findViewById(R.id.bt_right);
        View view_divider = view.findViewById(R.id.view_divider);
        tv_title.setText(TextUtils.isEmpty(tipMessageBean.title) ? "提示" : tipMessageBean.title);
        tv_message.setText(tipMessageBean.content);
        if (!TextUtils.isEmpty(tipMessageBean.left)) {
            bt_left.setOnClickListener(this);
            bt_left.setVisibility(View.VISIBLE);
            bt_left.setText(tipMessageBean.left);
            view_divider.setVisibility(View.VISIBLE);
        }
        if (!TextUtils.isEmpty(tipMessageBean.right)) {
            bt_right.setOnClickListener(this);
            bt_right.setVisibility(View.VISIBLE);
            bt_right.setText(tipMessageBean.right);
        }

        return view;
    }


    @Override
    protected int width() {
        return 220;
    }

    @Override
    protected int getAnimation() {
        return R.style.small_2_big;
    }

    @Override
    public void onClick(View v) {
        if (v.getId() == R.id.bt_left) {
            left();
        } else if (v.getId() == R.id.bt_right) {
            right();
        }

    }

    protected void right() {
        dismiss();
    }

    protected void left() {
        dismiss();
    }

    public static class TipMessageBean {
        public String title;
        public String content;
        public String left;
        public String right;

        public TipMessageBean(String title, String content, String left, String right) {
            this.title = title;
            this.content = content;
            this.left = left;
            this.right = right;
        }

    }
}
