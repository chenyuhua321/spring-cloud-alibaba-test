package com.cyh.dubbo.service;

import com.cyh.common.domain.IResponse;

/**
 * @author Chenyuhua
 * @date 2020/5/24 20:51
 */
public interface IUserService {
    IResponse userInfo(String token);
}
