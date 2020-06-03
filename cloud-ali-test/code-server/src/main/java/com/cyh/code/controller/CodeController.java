package com.cyh.code.controller;

import com.cyh.code.service.CodeService;
import com.cyh.common.domain.IResponse;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;

/**
 * @author Chenyuhua
 * @date 2020/5/23 20:40
 */
@RestController
public class CodeController {
    @Resource
    private CodeService codeService;

    @GetMapping("/code/create/{email}")
    public IResponse createCode(@PathVariable("email") String mail) {
        return codeService.createCode(mail);
    }

    @GetMapping("/code/validate/{email}/{code}")
    public IResponse validateCode(@PathVariable("email") String mail, @PathVariable("code") String code) {
        return codeService.validateCode(mail, code);
    }
}
