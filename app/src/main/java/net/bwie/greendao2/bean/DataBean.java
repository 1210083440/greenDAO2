package net.bwie.greendao2.bean;

import org.greenrobot.greendao.annotation.Entity;
import org.greenrobot.greendao.annotation.Id;
import org.greenrobot.greendao.annotation.Generated;

// 将表映射为实体类
@Entity
public class DataBean {

    // 表的主键_id
    @Id(autoincrement = true)
    private long _id;

    private String id;// 文章id，本质是一个内容
    private String title;
    private String pic;

    @Generated(hash = 1106435488)
    public DataBean(long _id, String id, String title, String pic) {
        this._id = _id;
        this.id = id;
        this.title = title;
        this.pic = pic;
    }

    @Generated(hash = 908697775)
    public DataBean() {
    }

    public long get_id() {
        return this._id;
    }

    public void set_id(long _id) {
        this._id = _id;
    }

    public String getId() {
        return this.id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getTitle() {
        return this.title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getPic() {
        return this.pic;
    }

    public void setPic(String pic) {
        this.pic = pic;
    }

}