package haoshi.com.shop.bean.chat.dao;

/**
 * Created by dengmingzhi on 2017/3/1.
 */

public class ChatMessageBean {
    private Long id;
    private String uid;
    private String touid;
    private String groupid;
    private String content;
    private String file;
    private String name;
    private String logo;
    private int nums;
    private int type;
    private String extend;
    private long createtime;

    public Long getId() {
        return id;
    }

    public ChatMessageBean setId(Long id) {
        this.id = id;
        return this;
    }

    public String getUid() {
        return uid;
    }

    public ChatMessageBean setUid(String uid) {
        this.uid = uid;
        return this;
    }

    public String getTouid() {
        return touid;
    }

    public ChatMessageBean setTouid(String touid) {
        this.touid = touid;
        return this;
    }

    public String getGroupid() {
        return groupid;
    }

    public ChatMessageBean setGroupid(String groupid) {
        this.groupid = groupid;
        return this;
    }

    public String getContent() {
        return content;
    }

    public ChatMessageBean setContent(String content) {
        this.content = content;
        return this;
    }

    public String getFile() {
        return file;
    }

    public ChatMessageBean setFile(String file) {
        this.file = file;
        return this;
    }

    public String getName() {
        return name;
    }

    public ChatMessageBean setName(String name) {
        this.name = name;
        return this;
    }

    public String getLogo() {
        return logo;
    }

    public ChatMessageBean setLogo(String logo) {
        this.logo = logo;
        return this;
    }

    public int getNums() {
        return nums;
    }

    public ChatMessageBean setNums(int nums) {
        this.nums = nums;
        return this;
    }

    public int getType() {
        return type;
    }

    public ChatMessageBean setType(int type) {
        this.type = type;
        return this;
    }

    public String getExtend() {
        return extend;
    }

    public ChatMessageBean setExtend(String extend) {
        this.extend = extend;
        return this;
    }

    public long getCreatetime() {
        return createtime;
    }

    public ChatMessageBean setCreatetime(long createtime) {
        this.createtime = createtime;
        return this;
    }
}