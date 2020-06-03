package com.cyh.dubbo.service;

import com.cyh.common.domain.IResponse;

/**
 * @author Chenyuhua
 * @date 2020/5/24 15:41
 */
public interface ICodeService {
    IResponse validateCode(String mail, String code);
}
