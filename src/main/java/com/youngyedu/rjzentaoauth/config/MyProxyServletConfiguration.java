package com.youngyedu.rjzentaoauth.config;

import org.mitre.dsmiley.httpproxy.ProxyServlet;
import org.springframework.boot.web.servlet.ServletRegistrationBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * @Author: heyuxin
 * @Create: 2022-07-05
 * @Description:
 */
@Configuration
public class MyProxyServletConfiguration {


    @Bean
    public ServletRegistrationBean proxyServletRegistration2() {
        ServletRegistrationBean servletRegistrationBean = new ServletRegistrationBean(new CommonProxyServlet(), "/*");
        return servletRegistrationBean;
    }
}
