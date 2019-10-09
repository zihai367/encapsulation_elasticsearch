package com.sinux;

import org.apache.http.HttpHost;
import org.elasticsearch.client.RestClient;
import org.elasticsearch.client.RestHighLevelClient;

import java.util.Map;

/**
 * @author kevin
 * @date 2019-09-10 14:02
 */
public class CreateESClient {
    /**
     * 获取high_client对象
     * @return
     */
    public RestHighLevelClient getRestHighLevelClient(){
        //取到单例中的参数集合
        Map<String, Object> params = ESSingleton.getInstance().getParams();
        //初始化client
        RestHighLevelClient client = new RestHighLevelClient(
                RestClient.builder(
                        new HttpHost(String.valueOf(params.get("host")),
                                Integer.parseInt(String.valueOf(params.get("port"))),
                                String.valueOf(params.get("scheme")))));
        return client;
    }
}
