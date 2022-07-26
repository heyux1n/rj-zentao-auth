package com.youngyedu.rjzentaoauth.controller;

import com.youngyedu.rjzentaoauth.config.SsoConfig;
import org.springframework.http.HttpHeaders;
import org.springframework.http.ResponseEntity;
import org.springframework.http.converter.HttpMessageConverter;
import org.springframework.stereotype.Controller;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.client.RestTemplate;

import javax.annotation.Resource;
import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.List;

/**
 * @Author: heyuxin
 * @Create: 2022-07-04
 * @Description: 单点其他内网服务
 */
@Controller
@RequestMapping("/sso")
public class SsoController {
    @Resource
    private RestTemplate restTemplate;
    @Resource
    private SsoConfig ssoConfig;

    @GetMapping("/zentao")
    public void redirectZentao(HttpServletRequest request, HttpServletResponse response) throws IOException {
        MultiValueMap<Object, Object> form = new LinkedMultiValueMap<>();
        form.add("account", "heyuxin");
        form.add("password", "heyuxin");
        form.add("keepLogin[]", "on");
        ResponseEntity<String> responseEntity = restTemplate.postForEntity(ssoConfig.getZentaoAuthUrl(), form, String.class);
        HttpHeaders headers = responseEntity.getHeaders();
        List<String> cookieList = headers.get("set-cookie");

        cookieList.forEach(cookie -> {
            String[] split = cookie.split(";");
            for (String value : split) {
                String[] map = value.split("=");
                if("za".equals(map[0]) || "zp".equals(map[0]) || "zentaosid".equals(map[0])) {
                    Cookie c = new Cookie(map[0], map[1]);
//                    c.setDomain("192.168.12.13:8888");
                    c.setPath("/");
                    response.addCookie(c);
                    System.out.println(map);
                }
            }
        });

        response.sendRedirect("http://192.168.12.13:8888/zentao/user-login-L3plbnRhby8=.html");
    }
}
