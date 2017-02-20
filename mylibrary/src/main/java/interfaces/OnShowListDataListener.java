package interfaces;

import android.support.v7.widget.RecyclerView;

/**
 * Created by dengmingzhi on 2016/11/21.
 */

public interface OnShowListDataListener {

    void loadMore();

    void showPage(int currentPage, int totalPage);

    void lastPage();

    RecyclerView getRecy();

    void hide(int time);
}
