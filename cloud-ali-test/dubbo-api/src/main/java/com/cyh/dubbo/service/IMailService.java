package com.cyh.dubbo.service;

/**
 * @author Chenyuhua
 * @date 2020/6/3 22:59
 */
public interface IMailService {
    void sendMail(String email, String code);
}
