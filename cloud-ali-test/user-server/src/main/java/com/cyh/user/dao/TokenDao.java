package com.cyh.user.dao;

import com.cyh.user.domain.LagouToken;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

/**
 * @author Chenyuhua
 * @date 2020/5/24 14:14
 */
@Repository
public interface TokenDao extends JpaRepository<LagouToken, Long> {
}
