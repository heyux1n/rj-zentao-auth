package com.youngyedu.rjzentaoauth.config;

import com.google.common.collect.ImmutableMap;
import org.mitre.dsmiley.httpproxy.ProxyServlet;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.web.servlet.ServletRegistrationBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import javax.servlet.Servlet;
import java.util.Map;

/**
 * @Author: heyuxin
 * @Create: 2022-07-05
 * @Description:
 */
//@Configuration
public class ProxyServletConfiguration {
    /**
     * 读取配置文件中路由设置
     */
    @Value("${proxy.servlet_url}")
    private String servletUrl;

    /**
     * 读取配置中代理目标地址
     */
    @Value("${proxy.target_url}")
    private String targetUrl;

    /**
     * 读取配置文件中路由设置
     */
    @Value("${proxy.servlet_url2}")
    private String servletUrl2;

    /**
     * 读取配置中代理目标地址
     */
    @Value("${proxy.target_url2}")
    private String targetUrl2;


    @Bean
    public ServletRegistrationBean proxyServletRegistration() {
        ServletRegistrationBean servletRegistrationBean = new ServletRegistrationBean(new ProxyServlet(), servletUrl);
        //设置网址以及参数
        Map<String, String> params = ImmutableMap.of("targetUri", targetUrl, "log", "true");
        servletRegistrationBean.setInitParameters(params);
        return servletRegistrationBean;
    }

    @Bean
    public ServletRegistrationBean proxyServletRegistration2() {
        ServletRegistrationBean servletRegistrationBean = new ServletRegistrationBean(new ProxyServlet(), servletUrl2);
        // 这个setName必须要设置，并且多个的时候，名字需要不一样
        servletRegistrationBean.setName("site2");
        // 设置网址以及参数
        Map<String, String> params = ImmutableMap.of("targetUri", targetUrl2, "log", "true");
        servletRegistrationBean.setInitParameters(params);
        return servletRegistrationBean;
    }
}
