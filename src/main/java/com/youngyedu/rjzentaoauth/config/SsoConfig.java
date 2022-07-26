package com.youngyedu.rjzentaoauth.config;

import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

/**
 * @Author: heyuxin
 * @Create: 2022-07-04
 * @Description: 单点接口地址配置
 */
@Component
@Data
@ConfigurationProperties(prefix = "sso")
public class SsoConfig {
    private String zentaoAuthUrl;
    private String zentaoRedirectUrl;

    private String jenkinsAuthUrl;
    private String jenkinsRedirectUrl;

    private String showdocAuthUrl;
    private String showdocRedirectUrl;
}
