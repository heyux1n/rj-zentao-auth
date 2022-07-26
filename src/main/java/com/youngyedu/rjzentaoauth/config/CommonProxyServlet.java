package com.youngyedu.rjzentaoauth.config;

import com.mysql.cj.util.StringUtils;
import org.apache.http.Header;
import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.HttpStatus;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpEntityEnclosingRequestBase;
import org.apache.http.client.utils.URIBuilder;
import org.apache.http.entity.InputStreamEntity;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;

import javax.servlet.ServletException;
import javax.servlet.ServletInputStream;
import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.Console;
import java.io.IOException;
import java.io.OutputStream;
import java.net.HttpCookie;
import java.net.URI;
import java.util.ArrayList;
import java.util.Enumeration;
import java.util.Iterator;
import java.util.List;

/**
 * @Author: heyuxin
 * @Create: 2022-07-07
 * @Description:
 */
public class CommonProxyServlet extends HttpServlet {


    private HttpClient httpClient;

    /**
     * 禅道
     */
    protected static final String ZENTAO_PLATFORM = "zentao";
    protected static final String ZENTAO_URL = "http://192.168.12.13:8888";
//    protected static final String ZENTAO_URL = "http://127.0.0.1:81";
//    protected static final String ZENTAO_URL = "http://192.168.10.23:3131";
    /**
     * showdoc文档
     */
    protected static final String SHOWDOC_PLATFORM = "showdoc";
//    protected static final String SHOWDOC_URL = "http://192.168.12.24:80";
    protected static final String SHOWDOC_URL = "http://192.168.12.13:10008";
    /**
     * jenkins
     */
    protected static final String JENKINS_PLATFORM = "jenkins";
    protected static final String JENKINS_URL = "http://192.168.12.13:8080";


    private String platform = ZENTAO_PLATFORM;
    private String referer;

    public CommonProxyServlet() {
    }

    @Override
    public void init() throws ServletException {

    }

    @Override
    protected void service(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
//        platform = request.getParameter("platform");
//        referer = request.getHeader("referer");
//        if (StringUtils.isNullOrEmpty(platform)) {
//            URL refererUrl = new URL(referer);
//            //未设置目标平台,需要重定向到对应平台
//            StringBuffer url = new StringBuffer(request.getRequestURL());
//            url.append("?");
//            url.append(refererUrl.getQuery());
//            String queryString = request.getQueryString();
//            if (!StringUtils.isNullOrEmpty(queryString)) {
//                url.append("&");
//                url.append(queryString);
//            }
//            response.sendRedirect(url.toString());
//
//        } else {
//            this.doProxy(request, response);
//        }

        try {
            this.doProxy(request, response);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Override
    public void destroy() {
    }

    private void doProxy(HttpServletRequest request, HttpServletResponse response) throws Exception {
        HttpClient httpClient = this.createHttpClient();
        //设置method
        HttpProxyRequest httpProxyRequest = new HttpProxyRequest(request.getMethod());
        //设置header
        Enumeration<String> headerNames = request.getHeaderNames();
        while (headerNames.hasMoreElements()) {
            String name = headerNames.nextElement();
            String value = request.getHeader(name);
//            if (name.equalsIgnoreCase("Host")) {
//                value = "192.168.12.13:8888";
//            }

//            if("Host".equalsIgnoreCase(name) || "Referer".equalsIgnoreCase(name) || "Origin".equalsIgnoreCase(name)) {
//                //更新Host
//                //更新Referer
//                //更新Origin
//                String proxyUrl = getProxyUrl();
//                String substring = proxyUrl.substring(proxyUrl.indexOf("//") + 2);
//                value = value.replace("localhost:2121", substring);
//            }
            httpProxyRequest.addHeader(name, value);
            System.out.println("request: " + name + ": " + value);

        }
        System.out.println("===================="+request.getRequestURL()+"=====================");
        System.out.println("-----------------------------------------");

        //设置请求体
        if (request.getHeader("Content-Length") != null || request.getHeader("Transfer-Encoding") != null) {
            httpProxyRequest.removeHeaders("Content-Length");
            httpProxyRequest.removeHeaders("Transfer-Encoding");
            //存在请求体时,会自动设置Content-Length
            ServletInputStream inputStream = request.getInputStream();
            InputStreamEntity inputStreamEntity = new InputStreamEntity(inputStream);
            httpProxyRequest.setEntity(inputStreamEntity);
        }

        //设置uri
        String url = rewriteUrlFromRequest(request);
        URIBuilder uriBuilder = new URIBuilder(url);
        URI uri = uriBuilder.build();
        httpProxyRequest.setURI(uri);
//        System.out.println("------proxy" + request.getRequestURI() + "===>" + url);


        Header[] allHeaders = httpProxyRequest.getAllHeaders();
        for (Header header : allHeaders) {
            System.out.println("requestHeader: " + header.getName() + ": " + header.getValue());
        }
        System.out.println("---------------------"+httpProxyRequest.getURI()+"--------------------");

        HttpResponse httpResponse = httpClient.execute(httpProxyRequest);

//        System.out.println(request.getRequestURI());

        //得到代理响应,设置原请求响应
        rewriteResponse(request, response, httpResponse);
    }


    /**
     * 重写响应
     *
     * @param response
     * @param httpResponse
     * @throws IOException
     */
    private void rewriteResponse(HttpServletRequest request, HttpServletResponse response, HttpResponse httpResponse) throws IOException {
        //设置响应头
//        System.out.println(httpResponse.getStatusLine().getStatusCode());
        for (Header header : httpResponse.getAllHeaders()) {
//            System.out.println(header.getName() + "----" + header.getValue());
//            response.setHeader(header.getName(), header.getValue());

            if (!header.getName().equalsIgnoreCase("Set-Cookie") && !header.getName().equalsIgnoreCase("Set-Cookie2")) {
                response.addHeader(header.getName(), header.getValue());
            } else {
                this.copyProxyCookie(request, response, header);
            }
            System.out.println("responseHeader: " + header.getName() + ": " + header.getValue());
        }
        System.out.println("");
        System.out.println("++++++++++++++++++++++++++++++++++++++++++++++++");

        //设置相应状态
        int statusCode = httpResponse.getStatusLine().getStatusCode();
        response.setStatus(statusCode);

        if(doResponseRedirect(response, httpResponse, statusCode)) {
            //代理客户端响应重定向
            return;
        }
        //设置响应体
        HttpEntity entity = httpResponse.getEntity();
        if (entity != null) {
            OutputStream servletOutputStream = response.getOutputStream();
            entity.writeTo(servletOutputStream);
        }
    }



    protected void copyProxyCookie(HttpServletRequest servletRequest, HttpServletResponse servletResponse, Header header) {
        List<HttpCookie> cookies = HttpCookie.parse(header.getValue());
        String path = servletRequest.getContextPath();
        path = path + servletRequest.getServletPath();


        for (HttpCookie cookie : cookies) {
//            String proxyCookieName = this.getCookieNamePrefix() + cookie.getName();
            String proxyCookieName = cookie.getName();
            Cookie servletCookie = new Cookie(proxyCookieName, cookie.getValue());
            servletCookie.setComment(cookie.getComment());
            servletCookie.setMaxAge((int)cookie.getMaxAge());
            servletCookie.setPath(path);
            servletCookie.setSecure(cookie.getSecure());
            servletCookie.setVersion(cookie.getVersion());
            servletResponse.addCookie(servletCookie);
        }

    }

    protected String getCookieNamePrefix() {
        return "!Proxy!" + this.getServletConfig().getServletName();
    }


    /**
     *
     * @param response
     * @param httpResponse
     * @param statusCode
     * @return
     */
    private boolean doResponseRedirect(HttpServletResponse response, HttpResponse httpResponse, int statusCode) throws IOException {
        if (statusCode >= HttpStatus.SC_MULTIPLE_CHOICES && statusCode < HttpStatus.SC_NOT_MODIFIED) {
            Header locationHeader = httpResponse.getLastHeader("Location");
            response.sendRedirect(locationHeader.getValue());
            return true;
        } else if (statusCode == HttpStatus.SC_NOT_MODIFIED) {
            response.setIntHeader("Content-Length", 0);
            response.setStatus(304);
            return true;
        }
        return false;
    }


    /**
     * 重写代理请求地址
     *
     * @param request
     * @return
     */
    private String rewriteUrlFromRequest(HttpServletRequest request) {
        StringBuffer uri = new StringBuffer();
        String proxyUrl = getProxyUrl();
        uri.append(proxyUrl);

        String requestURI = request.getRequestURI();
        if (!StringUtils.isNullOrEmpty(requestURI)) {
            uri.append(requestURI);
        }
        String queryString = request.getQueryString();
        if (!StringUtils.isNullOrEmpty(queryString)) {
            uri.append("?");
            uri.append(queryString);
        }
//        System.out.println("prosyUrl:" + uri);
        return uri.toString();
    }




    private String getProxyUrl() {
        switch (platform) {
            case ZENTAO_PLATFORM:
                return ZENTAO_URL;
            case SHOWDOC_PLATFORM:
                return SHOWDOC_URL;
            case JENKINS_PLATFORM:
                return JENKINS_URL;
            default:
                return JENKINS_URL;
        }
    }


    private HttpClient createHttpClient() {
        CloseableHttpClient httpClient = HttpClients.createDefault();
        return httpClient;
    }


    class HttpProxyRequest extends HttpEntityEnclosingRequestBase {

        private String method;

        HttpProxyRequest(String method) {
            this.method = method;
        }

        @Override
        public String getMethod() {
            return this.method;
        }
    }


}
