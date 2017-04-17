package haoshi.com.shop.pop;

import android.content.Context;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.Gravity;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.EditText;

import base.other.PopBaseView;
import haoshi.com.shop.R;


/**
 * Created by dengmingzhi on 2016/11/2.
 */

public class PopChatView extends PopBaseView {
    private boolean canEm=true;
    private String content;

    public PopChatView(Context ctx) {
        super(ctx);
    }

    public PopChatView(Context ctx, boolean canEm, String content) {
        super(ctx);
        this.canEm = canEm;
        this.content = content;
    }


    public PopChatView(Context ctx, String content) {
        super(ctx);
        this.content = content;
    }

    @Override
    protected int getGravity() {
        return Gravity.BOTTOM;
    }

    @Override
    protected View getView() {
        View view = View.inflate(ctx, R.layout.pop_chat_view, null);
        final EditText et_content = (EditText) view.findViewById(R.id.et_content);

       et_content.postDelayed(new Runnable() {
           @Override
           public void run() {
               et_content.requestFocus();
               InputMethodManager inputManager = (InputMethodManager) ctx.getSystemService(Context.INPUT_METHOD_SERVICE);
               inputManager.toggleSoftInput(0, InputMethodManager.HIDE_NOT_ALWAYS);
           }
       },100);

        et_content.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        });


        et_content.setText(content);

        return view;
    }

    protected void content(String content) {

    }

    @Override
    protected float getAlpha() {
        return 1f;
    }
}
