package interfaces;

import android.content.Context;

/**
 * Created by dengmingzhi on 2016/11/15.
 */

public interface OnBaseControl<T> {
    T setContext(Context ctx);

    T setOnControlListener(OnControlListener onControlListeren);


}
