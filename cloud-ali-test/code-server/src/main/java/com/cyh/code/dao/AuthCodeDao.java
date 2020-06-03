package com.cyh.code.dao;

import com.cyh.code.domain.LagouAuthCode;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.Date;

/**
 * @author Chenyuhua
 * @date 2020/5/24 14:29
 */
@Repository
public interface AuthCodeDao extends JpaRepository<LagouAuthCode, Long> {

    @Query(value = "select * from lagou_auth_code where email = ?1 order by  create_time DESC limit 1",nativeQuery = true)
    LagouAuthCode getCode(String email);
}
