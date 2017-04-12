package haoshi.com.shop.bean.rxbus;

/**
 * Created by dengmingzhi on 2016/12/26.
 */

public class AddressRxBus {
    public String index;
    public int oldPosition;
    public int position;
    public String id;
    public boolean isFromEdit;

    public AddressRxBus(String id, String index, int oldPosition, int position ) {
        this.index = index;
        this.oldPosition = oldPosition;
        this.position = position;
        this.id = id;
    }

    public AddressRxBus(String id, String index, int position ) {
        this.index = index;
        this.position = position;
        this.id = id;
    }

    public AddressRxBus(String id, String index, int oldPosition, int position, boolean isFromEdit) {
        this.index = index;
        this.oldPosition = oldPosition;
        this.position = position;
        this.id = id;
        this.isFromEdit = isFromEdit;
    }

    public AddressRxBus(String index) {
        this.index = index;
    }
}
