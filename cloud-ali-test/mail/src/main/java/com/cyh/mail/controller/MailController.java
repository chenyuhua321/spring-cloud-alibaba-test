package com.cyh.mail.controller;

import com.cyh.mail.service.MailService;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;

/**
 * @author Chenyuhua
 * @date 2020/5/23 16:29
 */
@RestController
@RequestMapping("/email")
public class MailController {
    @Resource
    private MailService mailService;

    @GetMapping("/send/{email}/{code}")
    public String sendMail(@PathVariable("email") String email, @PathVariable("code") String code) {
        mailService.sendMail(email, code);
        return "success";
    }
}
