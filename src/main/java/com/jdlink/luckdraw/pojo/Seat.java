package com.jdlink.luckdraw.pojo;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.Data;

import javax.persistence.*;
import java.util.Date;
@Data // update 2018年12月10日 by ljc 去除getset
@Entity
@Table(name = "main_seat")
@JsonIgnoreProperties({ "handler","hibernateLazyInitializer" })
public class Seat {
    /**
     * 主键编号
     */
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private int id;
    /**
     * 桌号
     */
    @Column(name = "seat_table_id")
    private int tableId;

    /**
     * 座位号
     */
    @Column(name = "seat_location_id")
    private int locationId;
    /**
     * 部门
     */
    @Column(name = "employee_department")
    private String department;
    /**
     * 姓名
     */
    @Column(name = "employee_name")
    private String name;
    /**
     * 员工编号
     */
    @Column(name = "employee_number")
    private String number;
    /**
     * 是否参加下一次抽奖
     */
    @Column(name = "is_join")
    private boolean join;
    /**
     * 按桌抽奖是否中奖
     */
    @Column(name="is_table_win")
    private boolean tableWin;
    /**
     * 奖品-中奖者一对一绑定
     */
//    @OneToOne(mappedBy = "seat",cascade = CascadeType.ALL)
    @Transient//字段不映射为列
    private Winner winners;
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

}
