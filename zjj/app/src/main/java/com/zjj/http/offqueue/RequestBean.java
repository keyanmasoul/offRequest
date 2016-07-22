package com.zjj.http.offqueue;

import com.litesuits.orm.db.annotation.Column;
import com.litesuits.orm.db.annotation.Default;
import com.litesuits.orm.db.annotation.NotNull;
import com.litesuits.orm.db.annotation.PrimaryKey;
import com.litesuits.orm.db.annotation.Table;
import com.litesuits.orm.db.enums.AssignType;

/**
 * RequestBean
 * Created by zjj on 2016/7/14.
 */
@Table("off_request")
public class RequestBean {

    @PrimaryKey(AssignType.AUTO_INCREMENT)
    private int id;

    @NotNull
    @Column("url")
    private String url;

    @Column("params")
    private String params;

    @Column("class_name")
    private String className;

    @Column("created_time")
    private String createdTime;

    @Default("0")
    @Column("group_count")
    private int groupCount;

    @Column("impl_offresponse_name")
    private String implOffResponseName;

    @Default("0")
    @Column("type")
    private int type;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public String getParams() {
        return params;
    }

    public void setParams(String params) {
        this.params = params;
    }

    public String getClassName() {
        return className;
    }

    public void setClassName(String className) {
        this.className = className;
    }

    public String getCreatedTime() {
        return createdTime;
    }

    public void setCreatedTime(String createdTime) {
        this.createdTime = createdTime;
    }

    public int getGroupCount() {
        return groupCount;
    }

    public void setGroupCount(int groupCount) {
        this.groupCount = groupCount;
    }

    public int getType() {
        return type;
    }

    public void setType(int type) {
        this.type = type;
    }

    public String getImplOffResponseName() {
        return implOffResponseName;
    }

    public void setImplOffResponseName(String implOffResponseName) {
        this.implOffResponseName = implOffResponseName;
    }
}
