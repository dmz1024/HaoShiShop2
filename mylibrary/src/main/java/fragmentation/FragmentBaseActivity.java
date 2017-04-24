package fragmentation;

import android.os.Bundle;

import com.mall.naiqiao.mylibrary.R;

import me.yokeyword.fragmentation.SupportActivity;

/**
 * Created by dengmingzhi on 2017/4/20.
 */

public class FragmentBaseActivity extends SupportActivity {
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        if (savedInstanceState == null) {
            loadRootFragment(R.id.fg_base, HomeFragment.getInstance());
        }
    }
}
