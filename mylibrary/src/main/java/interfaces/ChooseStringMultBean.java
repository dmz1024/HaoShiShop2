package interfaces;

/**
 * Created by dengmingzhi on 2017/3/24.
 */

public class ChooseStringMultBean implements OnChooseMultInterenface {
    private String title;
    private boolean isChoose;

    public ChooseStringMultBean(String title, boolean isChoose) {
        this.title = title;
        this.isChoose = isChoose;
    }

    public ChooseStringMultBean() {
    }

    public ChooseStringMultBean(String title) {
        this.title = title;
    }

    @Override
    public String getTitle() {
        return title;
    }

    @Override
    public boolean getChoose() {
        return isChoose;
    }

    @Override
    public void setChoose(boolean choose) {
        this.isChoose = choose;
    }
}
