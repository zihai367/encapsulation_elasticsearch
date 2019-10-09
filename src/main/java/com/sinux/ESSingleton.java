package com.sinux;

import org.elasticsearch.client.RestHighLevelClient;

import java.util.Map;

/**
 * @author kevin
 * @date 2019-09-09 10:06
 */
public class ESSingleton {

    //存放所有配置文件的配置信息
    private Map<String,Map<String,Object>> configures = null;

    //存放当前使用的配置文件信息
    private Map<String,Object> params = null;

    //存放client
    private RestHighLevelClient client = null;

    //内部类单例写法
    private ESSingleton(){}
    private static class GetESSingleton{
        private final static ESSingleton instance = new ESSingleton();
    }
    public static ESSingleton getInstance(){
        return GetESSingleton.instance;
    }



    //get and set
    public Map<String, Map<String, Object>> getConfigures() {
        return configures;
    }

    public Map<String, Object> getParams() {
        return params;
    }

    public void setConfigures(Map<String, Map<String, Object>> configures) {
        this.configures = configures;
    }

    public void setParams(Map<String, Object> params) {
        this.params = params;
    }

    public RestHighLevelClient getClient() {
        return client;
    }

    public void setClient(RestHighLevelClient client) {
        this.client = client;
    }
}
