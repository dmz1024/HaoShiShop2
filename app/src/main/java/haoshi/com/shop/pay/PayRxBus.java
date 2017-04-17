package haoshi.com.shop.pay;

import rx.Observable;
import rx.functions.Action1;
import util.RxBus;

/**
 * Created by dengmingzhi on 2017/4/17.
 */

public class PayRxBus {
    public static Observable<Integer> getVavle(Action1<Integer> action) {
        Observable<Integer> payRxBus;
        payRxBus = RxBus.get().register("payRxBus", Integer.class);
        payRxBus.subscribe(action);
        return payRxBus;
    }
}
