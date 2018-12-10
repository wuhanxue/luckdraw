package com.jdlink.luckdraw.dao;

import com.jdlink.luckdraw.pojo.Winner;
import org.springframework.data.jpa.repository.JpaRepository;

public interface WinnerDAO extends JpaRepository<Winner,Integer> {

}
