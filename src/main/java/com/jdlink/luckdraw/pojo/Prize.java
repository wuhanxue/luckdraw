package com.jdlink.luckdraw.pojo;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

import javax.persistence.*;
import java.util.Date;

@Entity
@Table(name = "config_prize")
@JsonIgnoreProperties({ "handler","hibernateLazyInitializer" })

/**
 * 奖品数据结构
 */
public class Prize {

    /**
     * 主键编号
     */
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    /**
     * 奖品名称
     */
    @Column(name = "prize_name")
    private String name;

    /**
     * 奖品数量
     */
    @Column(name = "prize_number")
    int number;

    /**
     * 图片路径
     */
    @Column(name = "prize_image_url")
    private String imgUrl;

    /**
     * 中奖方式
     */
    @Column(name = "prize_mode")
    private Boolean mode;

    /**
     *创建时间
     */
    @Column(name = "gmt_create_time")
    private Date createTime;

    /**
     * 修改时间
     */
    @Column(name = "gmt_modify_time")
    private Date modifyTime;


    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getNumber() {
        return number;
    }

    public void setNumber(int number) {
        this.number = number;
    }

    public String getImgUrl() {
        return imgUrl;
    }

    public void setImgUrl(String imgUrl) {
        this.imgUrl = imgUrl;
    }

    public Boolean getMode() {
        return mode;
    }

    public void setMode(Boolean mode) {
        this.mode = mode;
    }

    public Date getCreateTime() {
        return createTime;
    }

    public void setCreateTime(Date createTime) {
        this.createTime = createTime;
    }

    public Date getModifyTime() {
        return modifyTime;
    }

    public void setModifyTime(Date modifyTime) {
        this.modifyTime = modifyTime;
    }

    @Override
    public String toString() {
        return "Prize{" +
                "id=" + id +
                ", name='" + name + '\'' +
                ", number=" + number +
                ", imgUrl='" + imgUrl + '\'' +
                ", mode=" + mode +
                ", createTime=" + createTime +
                ", modifyTime=" + modifyTime +
                '}';
    }
}
