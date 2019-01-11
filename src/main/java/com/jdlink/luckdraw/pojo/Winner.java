package com.jdlink.luckdraw.pojo;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.Data;

import javax.persistence.*;
import java.util.Date;

/**
 * 中奖者数据结构
 */
@Data // update 2018年12月10日 by ljc 去除getset
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
     * 一对一绑定seat
     */
//    @OneToOne
//    @JoinColumn(name = "id")
    @Transient//字段不映射为列
    private Seat seat;

    /**
     * 奖品表外键
     */
    @Column(name = "prize_id")
    private int prizeId;

    /**
     * 一对一绑定奖品
     */
//    @OneToOne
//    @JoinColumn(name = "id")
    @Transient//字段不映射为列
    private Prize prize;
    /**
     * 是否领奖
     */
    @Column(name = "is_receive")
    private boolean receive;

    /**
     * 第几次中奖
     */
    @Column(name = "win_number")
    private int number;

    /**
     * 创建时间
     */
    @Column(name = "gmt_create_time")
    private Date creationTime;

    /**
     * 修改时间
     */
    @Column(name = "gmt_modify_time")
    private Date modifyTime;

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
