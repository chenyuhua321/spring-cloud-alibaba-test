package com.cyh.code.service;

import com.cyh.code.dao.AuthCodeDao;
import com.cyh.code.domain.LagouAuthCode;
import com.cyh.common.domain.IResponse;
import com.cyh.dubbo.service.ICodeService;
import com.cyh.dubbo.service.IMailService;
import org.apache.dubbo.config.annotation.Reference;
import org.apache.dubbo.config.annotation.Service;
import org.springframework.util.StringUtils;

import javax.annotation.Resource;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.util.Date;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

/**
 * @author Chenyuhua
 * @date 2020/5/23 22:23
 */
@Service
public class CodeService implements ICodeService {
    @Reference
    private IMailService mailService;
    @Resource
    private AuthCodeDao authCodeDao;
    private static ExecutorService executor = Executors.newSingleThreadExecutor();

    public IResponse createCode(String email) {
        String code = String.valueOf((int) ((Math.random() * 9 + 1) * 100000));
        executor.submit(() -> {
            mailService.sendMail(email, code);
        });
        LocalDateTime localDateTime = LocalDateTime.now().plusMinutes(10);
        LagouAuthCode authCode = LagouAuthCode.builder()
                .code(code)
                .email(email)
                .expireTime(Date.from(localDateTime.atZone(ZoneId.systemDefault()).toInstant()))
                .build();
        authCodeDao.save(authCode);

        return new IResponse(true, "已发送验证码，如果未收到请一分钟后重新点击发送");
    }

    @Override
    public IResponse validateCode(String mail, String code) {
        IResponse response = new IResponse();
        LagouAuthCode lagouAuthCode = authCodeDao.getCode(mail);
        //验证码不存在
        if (StringUtils.isEmpty(lagouAuthCode.getCode())) {
            response.setCode(1);
            response.setMsg("未发送验证码");
            return response;
        }
        //验证码过期
        if (new Date().after(lagouAuthCode.getExpireTime())) {
            response.setCode(2);
            response.setMsg("验证码已过期");
            return response;
        }
        //验证码错误
        if (!lagouAuthCode.getCode().equals(code)) {
            response.setCode(1);
            response.setMsg("验证码错误，请确认");
            return response;
        }

        response.setCode(0);
        response.setMsg("验证码正确");
        return response;
    }
}
