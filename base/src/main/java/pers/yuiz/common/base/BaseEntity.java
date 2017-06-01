package pers.yuiz.common.base;

import java.io.Serializable;
import java.util.Date;

public abstract class BaseEntity implements Serializable {
    /**
     * 主键id
     */
    private Long id;

    /**
     * 创建时间
     */
    private Date gmtCreate;

    /**
     * 修改时间
     */
    private Date gmtModified;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Date getGmtCreate() {
        return gmtCreate;
    }

    public void setGmtCreate(Date gmtCreate) {
        this.gmtCreate = gmtCreate;
    }

    public Date getGmtModified() {
        return gmtModified;
    }

    public void setGmtModified(Date gmtModified) {
        this.gmtModified = gmtModified;
    }

    /**
     * 准备新插入一条数据时,调用给予记录新建时间与修改时间初始值以及Id主键为空值
     */
    public void newCreate() {
        this.id = null;
        this.gmtCreate = new Date();
        this.gmtModified = new Date();
    }
}
