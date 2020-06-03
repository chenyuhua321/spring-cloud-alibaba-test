package com.cyh.user.dao;

import com.cyh.user.domain.LagouToken;
import com.cyh.user.domain.LagouUser;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

/**
 * @author Chenyuhua
 * @date 2020/5/24 14:45
 */
@Repository
public interface UserDao extends JpaRepository<LagouUser, Long> {
}
