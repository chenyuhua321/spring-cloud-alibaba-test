package com.lagou.edu;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;
import org.springframework.cloud.openfeign.EnableFeignClients;
import org.springframework.cloud.openfeign.FeignAutoConfiguration;

@SpringBootApplication(exclude = {FeignAutoConfiguration.class})
@EnableDiscoveryClient
public class GateWayApplication9002 {

    public static void main(String[] args) {
        SpringApplication.run(GateWayApplication9002.class,args);
    }
}
