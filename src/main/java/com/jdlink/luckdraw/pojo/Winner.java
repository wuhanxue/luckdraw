package com.jdlink.luckdraw.pojo;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

import javax.persistence.*;
import java.util.Date;

/**
 * 中奖者数据结构
 */
@Entity
@Table(name="main_winning")
@JsonIgnoreProperties({ "handler","hibernateLazyInitializer" })
public class Winner {

    /**
     * 主键编号
     */
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private int id;

    /**
     * 位置表外键
     */
    @Column(name = "seat_id")
    private int seatId;

    /**
     * 奖品表外键
     */
    @Column(name = "prize_id")
    private int prizeId;

    /**
     *
     */
    @Column(name = "is_receive")
    private boolean receive;

    /**
     * 创建时间
     */
    @Column(name = "gmt_creation_time")
    private Date creationTime;

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

    public int getSeatId() {
        return seatId;
    }

    public void setSeatId(int seatId) {
        this.seatId = seatId;
    }

    public int getPrizeId() {
        return prizeId;
    }

    public void setPrizeId(int prizeId) {
        this.prizeId = prizeId;
    }

    public boolean isReceive() {
        return receive;
    }

    public void setReceive(boolean receive) {
        this.receive = receive;
    }

    public Date getCreationTime() {
        return creationTime;
    }

    public void setCreationTime(Date creationTime) {
        this.creationTime = creationTime;
    }

    public Date getModifyTime() {
        return modifyTime;
    }

    public void setModifyTime(Date modifyTime) {
        this.modifyTime = modifyTime;
    }

    @Override
    public String toString() {
        return "Winner{" +
                "id=" + id +
                ", seatId=" + seatId +
                ", prizeId=" + prizeId +
                ", receive=" + receive +
                ", creationTime=" + creationTime +
                ", modifyTime=" + modifyTime +
                '}';
    }
}
