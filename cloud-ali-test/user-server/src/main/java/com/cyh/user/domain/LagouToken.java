package com.cyh.user.domain;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name="lagou_token")
public class LagouToken {
    /**
    * ⾃增主键
    */
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    /**
    * 邮箱地址
    */
    private String email;

    /**
    * 令牌
    */
    private String token;
}