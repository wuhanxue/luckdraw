package com.jdlink.luckdraw.dao;

import com.jdlink.luckdraw.pojo.Seat;
import org.springframework.data.jpa.repository.JpaRepository;

public interface LuckDrawDAO extends JpaRepository<Seat,Integer> {

}
