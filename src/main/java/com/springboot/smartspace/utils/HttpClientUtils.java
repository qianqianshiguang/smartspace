package com.springboot.smartspace.utils;

import com.springboot.smartspace.config.HttpClientConfigData;
import org.apache.http.client.config.RequestConfig;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.impl.conn.PoolingHttpClientConnectionManager;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

/**
 * @author: gq
 * @createtime: 2021/1/14 17:54
 * @description: TODO
 */
@Component
public class HttpClientUtils {
    @Autowired
    HttpClientConfigData httpClientConfigData;
    private CloseableHttpClient httpClient;
    private PoolingHttpClientConnectionManager connectionManager;

    /**
     * 获取httpclient
     */
    public CloseableHttpClient getHttpClient() {
        if (httpClient == null) {
            httpClient = getCloseableHttpClient();
        }
        return httpClient;
    }

    /**
     * 创建Httpclient客户端连接对象，并配置连接池配置项
     *
     * @return
     */
    public CloseableHttpClient getCloseableHttpClient() {
        //设置连接池
        connectionManager = new PoolingHttpClientConnectionManager();
        connectionManager.setMaxTotal(httpClientConfigData.getMax_total());
        connectionManager.setValidateAfterInactivity(httpClientConfigData.getValidate_after_inactivity());

        //创建http请求配置参数
        RequestConfig requestConfig = RequestConfig.custom()
                //请求超时时间
                .setConnectionRequestTimeout(httpClientConfigData.getConnection_requests_timeout())
                //连接超时时间
                .setConnectTimeout(httpClientConfigData.getConnect_timeout())
                //响应超时时间
                .setSocketTimeout(httpClientConfigData.getSocket_timeout())
                .build();

        CloseableHttpClient httpClient = HttpClients.custom()
                .setConnectionManager(connectionManager)
                .setDefaultRequestConfig(requestConfig)
                .build();
        return httpClient;


    }

}
