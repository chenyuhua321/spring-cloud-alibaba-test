package com.lagou.edu.service;

import com.lagou.edu.domain.AccessLog;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.Date;

/**
 * @author Chenyuhua
 * @date 2020/5/24 21:32
 */
@Repository
public interface AccessLogDao extends JpaRepository<AccessLog, Long> {
    @Query(value = "select count(*) from lagou_access_log where ip = ?1 and access_time > ?2",nativeQuery = true)
    int getCount(String ip, Date acccessTime);
}
