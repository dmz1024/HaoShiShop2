package view.pop;

import android.content.Context;
import android.text.Editable;
import android.text.TextUtils;
import android.text.TextWatcher;
import android.view.Gravity;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.Button;
import android.widget.EditText;


import com.mall.naiqiao.mylibrary.R;

import base.other.PopBaseView;


/**
 * Created by dengmingzhi on 2016/11/2.
 */

public class PopEdit extends PopBaseView {
    private boolean canEm=true;
    private String content;

    public PopEdit(Context ctx) {
        super(ctx);
    }

    public PopEdit(Context ctx, boolean canEm, String content) {
        super(ctx);
        this.canEm = canEm;
        this.content = content;
    }


    public PopEdit(Context ctx, String content) {
        super(ctx);
        this.content = content;
    }

    @Override
    protected int getGravity() {
        return Gravity.BOTTOM;
    }

    @Override
    protected View getView() {
        View view = View.inflate(ctx, R.layout.pop_edit, null);
        final EditText et_content = (EditText) view.findViewById(R.id.et_content);
        final Button bt_ok = (Button) view.findViewById(R.id.bt_ok);
        bt_ok.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dismiss();
                content(et_content.getText().toString());
            }
        });

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
                if (TextUtils.equals(content, et_content.getText().toString())) {
                    bt_ok.setEnabled(false);
                    bt_ok.setAlpha(0.5f);
                } else {

                    if (!TextUtils.isEmpty(et_content.getText().toString())) {
                        bt_ok.setEnabled(true);
                        bt_ok.setAlpha(1.0f);
                    } else {
                        if (!canEm) {
                            bt_ok.setEnabled(false);
                            bt_ok.setAlpha(0.5f);
                        } else {
                            bt_ok.setEnabled(true);
                            bt_ok.setAlpha(1.0f);
                        }
                    }
                }


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


}
