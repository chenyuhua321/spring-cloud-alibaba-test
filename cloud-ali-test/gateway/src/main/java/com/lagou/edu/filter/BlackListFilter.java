package com.lagou.edu.filter;

import com.cyh.common.domain.IResponse;
import com.cyh.dubbo.service.IUserService;
import com.lagou.edu.domain.AccessLog;
import com.lagou.edu.service.AccessLogDao;
import lombok.extern.slf4j.Slf4j;
import org.apache.dubbo.config.annotation.Reference;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.cloud.context.config.annotation.RefreshScope;
import org.springframework.cloud.gateway.filter.GatewayFilterChain;
import org.springframework.cloud.gateway.filter.GlobalFilter;
import org.springframework.core.Ordered;
import org.springframework.core.io.buffer.DataBuffer;
import org.springframework.http.HttpCookie;
import org.springframework.http.HttpStatus;
import org.springframework.http.server.reactive.ServerHttpRequest;
import org.springframework.http.server.reactive.ServerHttpResponse;
import org.springframework.stereotype.Component;
import org.springframework.util.CollectionUtils;
import org.springframework.util.MultiValueMap;
import org.springframework.web.server.ServerWebExchange;
import reactor.core.publisher.Mono;

import javax.annotation.Resource;
import java.sql.Date;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.time.format.DateTimeFormatter;
import java.util.List;

/**
 * 定义全局过滤器，会对所有路由生效
 */
@Slf4j
@RefreshScope
@Component  // 让容器扫描到，等同于注册了
public class BlackListFilter implements GlobalFilter, Ordered {

    @Reference
    private IUserService userService;
    @Resource
    private AccessLogDao accessLogDao;

    @Value("${access.minute}")
    private Integer minute;
    @Value("${access.count}")
    private int count;

    private static final Boolean TRUE = true;

    /**
     * 过滤器核心方法
     *
     * @param exchange 封装了request和response对象的上下文
     * @param chain    网关过滤器链（包含全局过滤器和单路由过滤器）
     * @return
     */
    @Override
    public Mono<Void> filter(ServerWebExchange exchange, GatewayFilterChain chain) {
        // 思路：获取客户端ip，判断是否在黑名单中，在的话就拒绝访问，不在的话就放行
        // 从上下文中取出request和response对象
        ServerHttpRequest request = exchange.getRequest();
        ServerHttpResponse response = exchange.getResponse();
        log.info(LocalDateTime.now().format(DateTimeFormatter.ISO_LOCAL_TIME));
        // 从request对象中获取客户端ip
        String clientIp = request.getRemoteAddress().getHostString();
        AccessLog accessLog = AccessLog.builder()
                .ip(clientIp)
                .build();
        accessLogDao.save(accessLog);

        if (request.getURI().getPath().contains("/api/email/")) {
            MultiValueMap<String, HttpCookie> cookies = request.getCookies();
            List<HttpCookie> token = cookies.get("token");
            if (CollectionUtils.isEmpty(token)) {
                response.setStatusCode(HttpStatus.UNAUTHORIZED); // 状态码
                log.debug("未登录");
                String data = "Request be denied!";
                DataBuffer wrap = response.bufferFactory().wrap(data.getBytes());
                return response.writeWith(Mono.just(wrap));
            }
            IResponse response1 = userService.userInfo(token.get(0).getValue());
            if (!TRUE.equals(response1.getCode())) {
                response.setStatusCode(HttpStatus.UNAUTHORIZED); // 状态码
                log.debug("未登录");
                String data = "Request be denied!";
                DataBuffer wrap = response.bufferFactory().wrap(data.getBytes());
                return response.writeWith(Mono.just(wrap));
            }
        }


        int ipCount = accessLogDao.getCount(clientIp, Date.from(LocalDateTime.now().minusMinutes(minute).atZone(ZoneId.systemDefault()).toInstant()));
        if (ipCount >= count) {
            response.setStatusCode(HttpStatus.FORBIDDEN); // 状态码
            log.debug("ip:{} 操作过于频繁 {} 分钟内 超过 {}次", clientIp, minute, count);
            String data = "Request be denied!";
            DataBuffer wrap = response.bufferFactory().wrap(data.getBytes());
            return response.writeWith(Mono.just(wrap));
        }

        // 合法请求，放行，执行后续的过滤器
        return chain.filter(exchange);
    }


    /**
     * 返回值表示当前过滤器的顺序(优先级)，数值越小，优先级越高
     *
     * @return
     */
    @Override
    public int getOrder() {
        return 0;
    }
}
