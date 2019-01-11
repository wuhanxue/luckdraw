package com.jdlink.luckdraw.pojo;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.Data;
import org.springframework.stereotype.Component;
import org.springframework.web.multipart.MultipartFile;

import javax.persistence.*;
import java.util.Date;
@Data
@Entity
@Table(name = "config_prize")
@JsonIgnoreProperties({ "handler","hibernateLazyInitializer" })

/**
 * 奖品数据结构
 */
//@Component
public class Prize {

    /**
     * 主键编号
     */
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private int id;

    /**
     * 奖品名称
     */
    @Column(name = "prize_name")
    private String name;

    /**
     * 奖品剩余数量
     */
    @Column(name = "prize_number")
    private int number;

    /**
     * 奖品数
     */
    @Column(name = "prize_all_number")
    private int allNumber;

    /**
     * 奖品等级
     */
    @Column(name = "prize_level")
    private String level;

    /**
     * 图片路径
     */
    @Column(name = "prize_image_url")
    private String imgUrl;

    /**
     * 中奖方式
     */
    @Column(name = "prize_mode")
    private int mode;

    /**
     * 奖品-中奖者一对一绑定
     */
   // @OneToOne(mappedBy = "prize",cascade = CascadeType.ALL)
    @Transient//字段不映射为列
    private Winner winners;

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

    /**
     * 中奖方式==》用作显示
     */
    @Transient//字段不映射为列
    private String modeName;

    /**
     * 图片文件
     * @return
     */
   @Transient//字段不映射为列
    private MultipartFile imageFile;

    public MultipartFile getImageFile() {
        return imageFile;
    }

//    public void setImageFile(MultipartFile imageFile) {
//        this.imageFile = imageFile;
//    }
//
//
//    public String getLevel() {
//        return level;
//    }
//
//    public void setLevel(String level) {
//        this.level = level;
//    }
//
//    public String getModeName() {
//        return modeName;
//    }
//
//    public void setModeName(String modeName) {
//        this.modeName = modeName;
//    }
//
//    public int getId() {
//        return id;
//    }
//
//    public void setId(int id) {
//        this.id = id;
//    }
//
//    public String getName() {
//        return name;
//    }
//
//    public void setName(String name) {
//        this.name = name;
//    }
//
//    public int getNumber() {
//        return number;
//    }
//
//    public void setNumber(int number) {
//        this.number = number;
//    }
//
//    public String getImgUrl() {
//        return imgUrl;
//    }
//
//    public void setImgUrl(String imgUrl) {
//        this.imgUrl = imgUrl;
//    }
//
//    public Boolean getMode() {
//        return mode;
//    }
//
//    public void setMode(Boolean mode) {
//        this.mode = mode;
//    }
//
//    public Date getCreateTime() {
//        return createTime;
//    }
//
//    public void setCreateTime(Date createTime) {
//        this.createTime = createTime;
//    }
//
//    public Date getModifyTime() {
//        return modifyTime;
//    }
//
//    public void setModifyTime(Date modifyTime) {
//        this.modifyTime = modifyTime;
//    }
//
//    public Winner getWinners() {
//        return winners;
//    }
//
//    public void setWinners(Winner winners) {
//        this.winners = winners;
//    }
//
//    @Override
//    public String toString() {
//        return "Prize{" +
//                "id=" + id +
//                ", name='" + name + '\'' +
//                ", number=" + number +
//                ", imgUrl='" + imgUrl + '\'' +
//                ", mode=" + mode +
//                ", createTime=" + createTime +
//                ", modifyTime=" + modifyTime +
//                '}';
//    }
}
