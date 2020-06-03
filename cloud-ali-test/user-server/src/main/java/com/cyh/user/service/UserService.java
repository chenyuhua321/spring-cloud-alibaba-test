package com.cyh.user.service;

import com.cyh.common.domain.IResponse;
import com.cyh.dubbo.service.ICodeService;
import com.cyh.dubbo.service.IUserService;
import com.cyh.user.dao.TokenDao;
import com.cyh.user.dao.UserDao;
import com.cyh.user.domain.LagouToken;
import com.cyh.user.domain.LagouUser;
import org.apache.dubbo.config.annotation.Reference;
import org.apache.dubbo.config.annotation.Service;
import org.springframework.data.domain.Example;

import javax.annotation.Resource;
import java.util.Objects;
import java.util.Optional;
import java.util.UUID;

/**
 * @author Chenyuhua
 * @date 2020/5/24 2:09
 */
@Service
public class UserService implements IUserService {
    @Reference
    private ICodeService codeService;
    @Resource
    private UserDao userDao;
    @Resource
    private TokenDao tokenDao;

    private static final Integer success = 0;

    public IResponse register(String email, String password, String code) {
        IResponse response = new IResponse();
        isRegistered(email);
        IResponse response1 = codeService.validateCode(email, code);
        if (!success.equals((response1.getCode()))) {
            return response1;
        }
        //注册
        LagouUser lagouUser = LagouUser.builder()
                .email(email)
                .password(password)
                .build();
        userDao.save(lagouUser);
        String token = getToken(email);
        response.setCode(true);
        response.setMsg("success");
        response.setData(token);
        return response;
    }

    public Boolean isRegistered(String email) {
        LagouUser lagouUser = getUserByEmail(email);
        //用户不存在
        return Objects.nonNull(lagouUser);
    }

    public IResponse login(String email, String password) {
        IResponse response = new IResponse();
        LagouUser lagouUser = getUserByEmail(email);
        if (Objects.isNull(lagouUser)) {
            response.setCode(false);
            response.setMsg("该邮箱未注册");
            return response;
        }

        if (!lagouUser.getPassword().equals(password)) {
            response.setCode(false);
            response.setMsg("密码错误");
        }

        String token = getToken(email);
        response.setCode(true);
        response.setMsg("登陆成功");
        response.setData(token);
        return response;
    }

    private String getToken(String email) {
        String token = UUID.randomUUID().toString();
        LagouToken lagouToken = LagouToken.builder()
                .email(email)
                .token(token)
                .build();
        tokenDao.save(lagouToken);
        return token;
    }

    @Override
    public IResponse userInfo(String token) {
        LagouToken lagouToken = LagouToken.builder()
                .token(token)
                .build();
        Example<LagouToken> example = Example.of(lagouToken);
        LagouToken lagouToken1 = tokenDao.findOne(example).orElse(null);
        if (Objects.isNull(lagouToken1)) {
            return new IResponse(false, "该token不存在");
        }

        return new IResponse(true, lagouToken1.getEmail());
    }


    private LagouUser getUserByEmail(String email) {
        LagouUser lagouUser = LagouUser.builder()
                .email(email)
                .build();

        Example<LagouUser> example = Example.of(lagouUser);
        Optional<LagouUser> one = userDao.findOne(example);
        return one.orElse(null);
    }


}
