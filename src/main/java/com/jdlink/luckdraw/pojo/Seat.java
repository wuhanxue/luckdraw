package com.jdlink.luckdraw.pojo;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

import javax.persistence.*;
import java.util.Date;

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
     * 创建时间
     */
    @Column(name = "gmt_create_time")
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

    public int getTableId() {
        return tableId;
    }

    public void setTableId(int tableId) {
        this.tableId = tableId;
    }

    public int getLocationId() {
        return locationId;
    }

    public void setLocationId(int locationId) {
        this.locationId = locationId;
    }

    public String getDepartment() {
        return department;
    }

    public void setDepartment(String department) {
        this.department = department;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getNumber() {
        return number;
    }

    public void setNumber(String number) {
        this.number = number;
    }

    public boolean isJoin() {
        return join;
    }

    public void setJoin(boolean join) {
        this.join = join;
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
        return "Seat{" +
                "id=" + id +
                ", tableId=" + tableId +
                ", locationId=" + locationId +
                ", department='" + department + '\'' +
                ", name='" + name + '\'' +
                ", number='" + number + '\'' +
                ", join=" + join +
                ", creationTime=" + creationTime +
                ", modifyTime=" + modifyTime +
                '}';
    }
}
