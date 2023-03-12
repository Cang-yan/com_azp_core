package com.azp.core;

import cc.eamon.open.flow.spring.scanner.FlowScan;
import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.web.servlet.ServletComponentScan;
import org.springframework.cache.annotation.EnableCaching;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;
import org.springframework.cloud.openfeign.EnableFeignClients;
import org.springframework.transaction.annotation.EnableTransactionManagement;
import springfox.documentation.swagger2.annotations.EnableSwagger2;

@SpringBootApplication
@EnableFeignClients(basePackages = {"com.horsecoder.storage.domain", "com.azp.data.sys.domain"})
@EnableDiscoveryClient
@EnableCaching
@ServletComponentScan("com.azp.core")
@EnableTransactionManagement
@MapperScan(value = {
        "com.azp.core.*.datainterface"
})
@EnableSwagger2
@FlowScan(flowPackages = {"com.azp.core.flow"})
public class AppStarter {

    public static void main(String[] args) {
        SpringApplication.run(AppStarter.class, args);
    }

}