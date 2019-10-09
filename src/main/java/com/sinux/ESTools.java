package com.sinux;

import com.sinux.parse.base.CenterConfig;
import org.elasticsearch.action.admin.indices.settings.get.GetSettingsRequest;
import org.elasticsearch.action.admin.indices.settings.get.GetSettingsResponse;
import org.elasticsearch.action.delete.DeleteRequest;
import org.elasticsearch.action.delete.DeleteResponse;
import org.elasticsearch.action.index.IndexRequest;
import org.elasticsearch.action.index.IndexResponse;
import org.elasticsearch.action.search.SearchRequest;
import org.elasticsearch.action.search.SearchResponse;
import org.elasticsearch.action.update.UpdateRequest;
import org.elasticsearch.action.update.UpdateResponse;
import org.elasticsearch.client.RequestOptions;
import org.elasticsearch.client.RestHighLevelClient;
import org.elasticsearch.client.indices.CreateIndexRequest;
import org.elasticsearch.client.indices.CreateIndexResponse;
import org.elasticsearch.client.indices.GetMappingsRequest;
import org.elasticsearch.client.indices.GetMappingsResponse;
import org.elasticsearch.cluster.metadata.MappingMetaData;
import org.elasticsearch.common.settings.Settings;
import org.elasticsearch.common.xcontent.XContentBuilder;
import org.elasticsearch.common.xcontent.XContentType;
import org.elasticsearch.index.VersionType;
import org.elasticsearch.index.query.QueryBuilder;
import org.elasticsearch.index.query.QueryBuilders;
import org.elasticsearch.index.reindex.BulkByScrollResponse;
import org.elasticsearch.index.reindex.DeleteByQueryRequest;
import org.elasticsearch.index.reindex.ReindexRequest;
import org.elasticsearch.index.reindex.UpdateByQueryRequest;
import org.elasticsearch.script.Script;
import org.elasticsearch.script.ScriptType;
import org.elasticsearch.search.SearchHit;
import org.elasticsearch.search.aggregations.AggregationBuilders;
import org.elasticsearch.search.aggregations.Aggregations;
import org.elasticsearch.search.aggregations.bucket.histogram.DateHistogramAggregationBuilder;
import org.elasticsearch.search.aggregations.bucket.histogram.DateHistogramInterval;
import org.elasticsearch.search.aggregations.bucket.histogram.Histogram;
import org.elasticsearch.search.aggregations.bucket.range.DateRangeAggregationBuilder;
import org.elasticsearch.search.aggregations.bucket.range.Range;
import org.elasticsearch.search.aggregations.bucket.terms.Terms;
import org.elasticsearch.search.aggregations.bucket.terms.TermsAggregationBuilder;
import org.elasticsearch.search.aggregations.metrics.Stats;
import org.elasticsearch.search.builder.SearchSourceBuilder;
import org.elasticsearch.search.sort.FieldSortBuilder;

import java.io.IOException;
import java.util.*;

/**
 * @author kevin
 * @date 2019-09-11 17:28
 */
public class ESTools {
    //高级客户端
    private RestHighLevelClient client;
    //查询条件默认为空
    private QueryBuilder queryBuilders = null;
    //排序条件默认为空
    private FieldSortBuilder sortBuilder = null;


    /**
     * 构造方法，初始化配置和client
     */
    public ESTools(){
        //获取单例类对象
        ESSingleton esSingleton = ESSingleton.getInstance();
        if (esSingleton.getClient() == null){
            //解析配置文件
            Map map = new CenterConfig().parseFile();
            //选择使用的配置
            esSingleton.setConfigures(map);
            //选择要使用的配置文件
            esSingleton.setParams(esSingleton.getConfigures().get("elasticsearch-config.xml"));
            //初始化client对象并放入单例
            esSingleton.setClient(new CreateESClient().getRestHighLevelClient());
            this.client = esSingleton.getClient();
        }else{
            this.client = esSingleton.getClient();
        }
    }

    /**
     * 获取client
     * @return 返回highCLient对象
     */
    public RestHighLevelClient getClient(){
        return client;
    }


    /**
     * 创建新的索引并设置mapping和setting
     * @param indexName     新创建的索引名
     * @param mapping       mapping MAP
     * @param setting       setting MAP
     * @return              true成功，false失败
     */
    public CreateIndexResponse createIndex(String indexName,Map<String,?> mapping,Settings setting){
        CreateIndexRequest createIndexRequest = new CreateIndexRequest(indexName);
        //添加Mapping
        createIndexRequest.mapping(mapping);
        //添加Setting
        createIndexRequest.settings(setting);
        return exeCreateIndex(createIndexRequest);
    }

    /**
     * 创建新的索引并设置mapping
     * @param indexName     新创建的索引名
     * @param mapping       mapping MAP
     * @return              true成功，false失败
     */
    public CreateIndexResponse createIndex(String indexName,Map<String,?> mapping){
        CreateIndexRequest createIndexRequest = new CreateIndexRequest(indexName);
        //添加Mapping
        createIndexRequest.mapping(mapping);
        return exeCreateIndex(createIndexRequest);
    }


    /**
     * 创建新的索引
     * @param indexName     新创建的索引名
     * @return              true成功，false失败
     */
    public CreateIndexResponse createIndex(String indexName){
        CreateIndexRequest createIndexRequest = new CreateIndexRequest(indexName);
        return exeCreateIndex(createIndexRequest);
    }

    /**
     * 执行新建索引的操作
     * @param createIndexRequest    传入配置好的creteIndexRequest
     * @return                      true成功，false失败
     */
    public CreateIndexResponse exeCreateIndex(CreateIndexRequest createIndexRequest){
        CreateIndexResponse createIndexResponse = null;
        try {
            createIndexResponse = client.indices().create(createIndexRequest, RequestOptions.DEFAULT);
        } catch (IOException e) {
            e.printStackTrace();
        }

        return createIndexResponse;
    }


    /**
     * 通过json字符串添加文档
     * @param index         要添加的索引名
     * @param id            要添加的id
     * @param jsonString    添加的json内容
     * @return              返回Response
     */
    public IndexResponse addDocument(String index, String id, String jsonString){

        IndexRequest indexRequest = new IndexRequest(index).id(id);
        indexRequest.source(jsonString, XContentType.JSON);


        return exeAddDocument(indexRequest);
    }



    /**
     * 通过map添加文档
     * @param index     要添加的索引名
     * @param id        要添加的id
     * @param jsonMap   添加的json内容
     * @return          返回Response
     */
    public IndexResponse addDocument(String index, String id,HashMap<String,Object> jsonMap){
        IndexRequest indexRequest = new IndexRequest(index).id(id);
        indexRequest.source(jsonMap);
        return exeAddDocument(indexRequest);
    }

    /**
     * 通过XContentBuilder添加文档
     * @param index     要添加的索引名
     * @param id        要添加的id
     * @param builder   添加的json内容
     * @return          返回Response
     */
    public IndexResponse addDocument(String index, String id, XContentBuilder builder){
        IndexRequest indexRequest = new IndexRequest(index).id(id);
        indexRequest.source(builder);

        return exeAddDocument(indexRequest);
    }

    /**
     * 执行添加文档的操作
     * @param indexRequest      传入配置好的indexRequest
     * @return                  返回response
     */
    public IndexResponse exeAddDocument(IndexRequest indexRequest){
        IndexResponse indexResponse = null;
        try {
            indexResponse = client.index(indexRequest, RequestOptions.DEFAULT);
        } catch (IOException e) {
            e.printStackTrace();
        }
        return indexResponse;
    }





    /**
     * 通过路径删除文档
     * @param index     索引路径
     * @param id        id路径
     * @return          返回response
     */
    public DeleteResponse deleteById(String index,String id){
        DeleteResponse response = null;
        try {
            DeleteRequest request = new DeleteRequest(index, id);
            response = client.delete(request, RequestOptions.DEFAULT);
        } catch (IOException e) {
            e.printStackTrace();
        }
        return response;
    }

    /**
     * 通过查询条件删除文档
     *
     * @param indices       要查询的索引
     * @return              返回response
     */
    public BulkByScrollResponse deleteByQuery(String... indices){
        BulkByScrollResponse response = null;
        try {
            DeleteByQueryRequest request = new DeleteByQueryRequest(indices);
            //添加要搜索的字段
            if(queryBuilders!= null){
                request.setQuery(queryBuilders);
            }else {
                System.err.println("请添加查询条件");
            }

            response = client.deleteByQuery(request, RequestOptions.DEFAULT);
        } catch (IOException e) {
            e.printStackTrace();
        }
        return response;
    }



    /**
     * 通过json字符串修改文档
     * @param index         index路径
     * @param id            id路径
     * @param jsonString    修改的json内容
     * @return              返回Response
     */
    public UpdateResponse updateById(String index,String id,String jsonString){
        UpdateRequest updateRequest = new UpdateRequest().index(index).id(id);
        updateRequest.doc(jsonString,XContentType.JSON);
        return exeUpdateById(updateRequest);
    }

    /**
     * 通过map修改文档
     * @param index     index路径
     * @param id        id路径
     * @param jsonMap   修改的json内容
     * @return          返回Response
     */
    public UpdateResponse updateById(String index,String id,Map<String,Object> jsonMap){
        UpdateRequest updateRequest = new UpdateRequest().index(index).id(id);
        updateRequest.doc(jsonMap);
        return exeUpdateById(updateRequest);
    }

    /**
     * 通过XContentBuilder修改文档
     * @param index     index路径
     * @param id        id路径
     * @param builder   修改的json内容
     * @return          返回Response
     */
    public UpdateResponse updateById(String index,String id,XContentBuilder builder){
        UpdateRequest updateRequest = new UpdateRequest().index(index).id(id);
        updateRequest.doc(builder);
        return exeUpdateById(updateRequest);
    }

    /**
     * 执行通过路径修改文档的操作
     * @param updateRequest 传入配置好的request
     * @return              返回response
     */
    public UpdateResponse exeUpdateById(UpdateRequest updateRequest){
        UpdateResponse updateResponse = null;
        try {
            updateResponse = client.update(updateRequest, RequestOptions.DEFAULT);
        } catch (IOException e) {
            e.printStackTrace();
        }
        return updateResponse;
    }


    /**
     * 通过查询条件条件修改字段值
     * @param updateFieldName   要修改的字段名
     * @param updateValue       字段值修改为
     * @param indices           要修改哪些索引
     * @return                  返回Response
     */
    public BulkByScrollResponse updateByQuery(String updateFieldName,String updateValue,String... indices){
        BulkByScrollResponse response = null;
        try {
            UpdateByQueryRequest request = new UpdateByQueryRequest(indices);
            //设置查询条件
            if(queryBuilders != null){
                request.setQuery(queryBuilders);
            }else{
                System.err.println("请添加查询条件");
            }
            //设置painless脚本语言
            request.setScript(new Script(ScriptType.INLINE,"painless","ctx._source."+updateFieldName+"="+updateValue, Collections.emptyMap()));
            response = client.updateByQuery(request, RequestOptions.DEFAULT);
        } catch (IOException e) {
            e.printStackTrace();
        }
        return response;
    }



    /**
     * 查询所有的index并返回String列表
     * @return      返回装有json字符串的list
     */
    public List<String> searchAndGetString(){
        SearchRequest searchRequest = configSearchRequest();

        return exeSearchAndGetString(searchRequest);
    }

    /**
     * 按index查询，并返回String列表
     * @param indices       要查询的索引s
     * @return              返回装有json字符串的list
     */
    public List<String> searchAndGetString(String... indices){
        SearchRequest searchRequest = configSearchRequest(indices);
        return exeSearchAndGetString(searchRequest);
    }

//    /**
//     * 通过field的key和value查询,并返回String列表
//     * @param fieldName     查询的字段名
//     * @param value         匹配的字段值
//     * @return              返回装有json字符串的list
//     */
//    public List<String> searchAndGetString(String fieldName,String value){
//        SearchRequest searchRequest = configSearchRequest(fieldName, value);
//
//        return exeSearchAndGetString(searchRequest);
//    }

//    /**
//     * 通过index加field的key和value查询,并返回String列表
//     * @param fieldName     查询的字段名
//     * @param value         匹配的字段值
//     * @param indices       要查询的索引
//     * @return              返回装有json字符串的list
//     */
//    public List<String> searchAndGetString(String fieldName,String value,String... indices){
//        SearchRequest searchRequest = configSearchRequest(fieldName, value, indices);
//
//        return exeSearchAndGetString(searchRequest);
//    }

    /**
     * 执行查询并返回json字符串
     * @param searchRequest     传入配置好的searchRequest
     * @return                  返回装有json字符串的List
     */
    public List<String> exeSearchAndGetString(SearchRequest searchRequest){
        List<String> jsonStrings = new ArrayList<String>();
        SearchHit[] searchHits = getHitsBySearchResponse(exeSearch(searchRequest));
        for (SearchHit searchHit : searchHits) {
            jsonStrings.add(searchHit.getSourceAsString());
        }
        return jsonStrings;

    }




    /**
     * 查询所有的index，查询到的每一个文档以map形式返回
     * @return      返回装有jsonMap的集合
     */
    public List<Map<String,Object>> searchAndGetMap(){
        SearchRequest searchRequest = configSearchRequest();


        return exeSearchAndGetMap(searchRequest);
    }

    /**
     * 按指定的index查询，查询到的每一个文档以map形式返回
     * @param indices       index
     * @return              返回装有jsonMap的集合
     */
    public List<Map<String,Object>> searchAndGetMap(String... indices){
        SearchRequest searchRequest = configSearchRequest(indices);
        return exeSearchAndGetMap(searchRequest);
    }

//    /**
//     * 按field的key和value进行匹配查询，并返回查询到的文档的Map集合
//     * @param fieldName     查询字段值
//     * @param value         匹配的值
//     * @return              返回装有jsonMap的集合
//     */
//    public List<Map<String,Object>> searchAndGetMap(String fieldName,String value){
//        SearchRequest searchRequest = configSearchRequest(fieldName, value);
//        return exeSearchAndGetMap(searchRequest);
//    }

//    /**
//     * 按指定的index和field的key和value匹配查询，并返回查询到的每一个文档的map集合
//     * @param fieldName     查询的字段名
//     * @param value         匹配的字段值
//     * @param indices       查询的索引
//     * @return              返回装有jsonMap的list集合
//     */
//    public List<Map<String,Object>> searchAndGetMap(String fieldName,String value,String... indices){
//        SearchRequest searchRequest = configSearchRequest(fieldName, value, indices);
//        return exeSearchAndGetMap(searchRequest);
//    }

    /**
     * 执行搜索并获取map集合
     * @param searchRequest     传入配置好的request
     * @return                  返回装有map信息的list集合
     */
    public List<Map<String,Object>> exeSearchAndGetMap(SearchRequest searchRequest){
        List<Map<String,Object>> jsonMaps = new ArrayList<Map<String,Object>>();
        SearchHit[] searchHits = getHitsBySearchResponse(exeSearch(searchRequest));
        for (SearchHit searchHit : searchHits) {
            jsonMaps.add(searchHit.getSourceAsMap());
        }
        return jsonMaps;
    }

//    /**
//     * 配置searchRequest
//     * @param fieldName     字段名
//     * @param value         字段值
//     * @param indices       索引
//     * @return              返回配置好的searchRequest
//     */
//    public SearchRequest configSearchRequest(String fieldName,String value,String... indices){
//        SearchRequest searchRequest = new SearchRequest(indices);
//        SearchSourceBuilder sourceBuilder = new SearchSourceBuilder();
//        sourceBuilder.query(QueryBuilders.termQuery(fieldName,value));
//        //添加排序和查询条件(QueryBuilder)
//        searchAddSortAndQueryBuild(sourceBuilder);
//
//        searchRequest.source(sourceBuilder);
//        return searchRequest;
//    }

    /**
     * 配置searchRequest
     * @param indices       索引
     * @return              返回配置好的searchRequest
     */
    public SearchRequest configSearchRequest(String... indices){
        SearchRequest searchRequest = new SearchRequest(indices);
        SearchSourceBuilder sourceBuilder = new SearchSourceBuilder();
        sourceBuilder.query(QueryBuilders.matchAllQuery());

        //添加排序和查询条件(QueryBuilder)
        searchAddSortAndQueryBuild(sourceBuilder);

        searchRequest.source(sourceBuilder);
        return searchRequest;
    }
//    /**
//     * 配置searchRequest
//     * @param fieldName     字段名
//     * @param value         字段值
//     * @return              返回配置好的searchRequest
//     */
//    public SearchRequest configSearchRequest(String fieldName,String value){
//        SearchRequest searchRequest = new SearchRequest();
//        SearchSourceBuilder sourceBuilder = new SearchSourceBuilder();
//        sourceBuilder.query(QueryBuilders.termQuery(fieldName, value));
//
//        //添加排序和查询条件(QueryBuilder)
//        searchAddSortAndQueryBuild(sourceBuilder);
//
//        searchRequest.source(sourceBuilder);
//        return searchRequest;
//    }

    /**
     * 配置searchRequest
     * @return              返回配置好的searchRequest
     */
    public SearchRequest configSearchRequest(){
        SearchRequest searchRequest = new SearchRequest();
        SearchSourceBuilder sourceBuilder = new SearchSourceBuilder();
        sourceBuilder.query(QueryBuilders.matchAllQuery());
        //添加排序和查询条件(QueryBuilder)

        searchAddSortAndQueryBuild(sourceBuilder);

        searchRequest.source(sourceBuilder);

        return searchRequest;
    }

    /**
     * 执行搜索
     * @param searchRequest     传入配置好的SearchRequest
     * @return                  返回response
     */
    public SearchResponse exeSearch(SearchRequest searchRequest){
        SearchResponse searchResponse = null;
        try {
            searchResponse = client.search(searchRequest, RequestOptions.DEFAULT);
        } catch (IOException e) {
            e.printStackTrace();
        }
        return searchResponse;
    }

    /**
     * 通过response获取hits
     * @param searchResponse    response
     * @return                  返回hit数组
     */
    public SearchHit[] getHitsBySearchResponse(SearchResponse searchResponse){
        return searchResponse.getHits().getHits();
    }




    /**
     * 普通聚合查询并返回所有Stats
     * @param groupField    指定以什么field字段分组
     * @param statField     指定要聚合运算的字段
     * @param indices       指定要查询的index
     * @return              返回Stats的集合
     */
    public Map<String,Stats> groupByTermsAndGetStates(String groupField,String statField,String... indices){
        //用来存放stats的map
        Map<String,Stats> statsMap = new HashMap<>();
        //获取装有桶的list
        List<? extends Terms.Bucket> buckets = exeGroupByFiled(groupField, statField, indices);
        //遍历桶，将所有的Stat放到Map中返回
        for (Terms.Bucket bucket : buckets){
            Stats statAge = bucket.getAggregations().get("stats");
            statsMap.put((String) bucket.getKey(),statAge);
        }

        return statsMap;
    }



    /**
     * 普通聚合查询并返回指定key的Stats
     * @param groupField    指定以什么field字段分组
     * @param statField     指定要聚合运算的字段
     * @param indices       指定要查询的index
     * @param target        获取某一分组的计算数据
     * @return              返回Stats的集合
     */
    public Stats groupByTermsAndGetStats(String groupField,String statField,String target,String... indices){
        List<? extends Terms.Bucket> buckets = exeGroupByFiled(groupField, statField, indices);
        //遍历桶,匹配的Stat返回
        for (Terms.Bucket bucket : buckets){
            if (target.equals(bucket.getKey())) {
                return bucket.getAggregations().get("stats");
            }
        }
        return null;
    }


    /**
     * 执行聚合搜索并返回装有桶的List集合
     * @param groupField    以什么字段分组（分桶）
     * @param statField     需要聚合计算的值
     * @param indices       查询哪些索引
     * @return              返回装有所有桶的集合
     */
    public List<? extends Terms.Bucket> exeGroupByFiled(String groupField,String statField,String... indices){
        //创建SearchRequest对象
        SearchRequest searchRequest = new SearchRequest(indices);
        //创建SearchSourceBuilder对象
        SearchSourceBuilder searchSourceBuilder = new SearchSourceBuilder();
        //创建一个名为terms的terms聚合，并且以name为聚合点
        TermsAggregationBuilder aggregation = AggregationBuilders.terms("terms").field(groupField.concat(".keyword"));
        //创建名为stats的子聚合并以age为计算单元
        aggregation.subAggregation(AggregationBuilders.stats("stats").field(statField));
        //将创建的聚合放到builder里
        searchSourceBuilder.aggregation(aggregation);
        //将builder放到request中
        searchRequest.source(searchSourceBuilder);


        //执行搜索，并获取到response
        SearchResponse searchResponse = exeSearch(searchRequest);


        //从response中获取聚合对象
        Aggregations aggregations = searchResponse.getAggregations();
        //通过聚合名获取桶聚合
        Terms byCompanyAggregation = aggregations.get("terms");
        //获取聚合中的所有桶
        List<? extends Terms.Bucket> buckets = byCompanyAggregation.getBuckets();
        return buckets;

    }


    //通过时间范围聚合查找
    public Stats groupByDateRangeAndGetStats(String from,String to,String fieldName,String statsField,String... indices){
        SearchRequest searchRequest = new SearchRequest(indices);
        SearchSourceBuilder sourceBuilder = new SearchSourceBuilder();

        DateRangeAggregationBuilder dateRange = AggregationBuilders
                        .dateRange("date")
                        .field(fieldName)
                        .addRange(from, to)
                        .format("yyyy-MM-dd");

        dateRange.subAggregation(AggregationBuilders.stats("stats").field(statsField));
        sourceBuilder.aggregation(dateRange);
        searchRequest.source(sourceBuilder);

        //执行查询操作
        SearchResponse searchResponse = exeSearch(searchRequest);


        Range range = searchResponse.getAggregations().get("date");

        List<? extends Range.Bucket> buckets = range.getBuckets();
        for (Range.Bucket bucket : buckets) {
            return bucket.getAggregations().get("stats");
        }

        return null;
    }


    //通过时间的分段聚合查询
    public Map<Object,Stats> groupByDateHistogramAndGetStates(String fieldName,String statField,DateHistogramInterval interval,String... indices){
        SearchRequest searchRequest = new SearchRequest(indices);
        SearchSourceBuilder sourceBuilder = new SearchSourceBuilder();
        Map<Object,Stats> statsMap = new HashMap<Object,Stats>();


        DateHistogramAggregationBuilder dateHistogram = AggregationBuilders.dateHistogram("date");

        dateHistogram.calendarInterval(interval);
        dateHistogram.field(fieldName);
        dateHistogram.format("yyyy-MM-dd");

        dateHistogram.subAggregation(AggregationBuilders.stats("stats").field(statField));

        sourceBuilder.aggregation(dateHistogram);
        searchRequest.source(sourceBuilder);

        SearchResponse searchResponse = exeSearch(searchRequest);

        Histogram histogram = searchResponse.getAggregations().get("date");
        List<? extends Histogram.Bucket> buckets = histogram.getBuckets();
        for (Histogram.Bucket bucket : buckets) {
            Stats stats = bucket.getAggregations().get("stats");
            statsMap.put(bucket.getKey(),stats);
        }

        return statsMap;

    }




    /**
     * Reindex,新建一个索引并把旧索引的settings和mappings以及data拷贝到新索引里
     * @param oldIndex 要拷贝值的索引
     * @param newIndex 新建并赋值的索引
     */
    public void reIndex(String oldIndex,String newIndex){
        //获取mapping
        Map<String, Object> mappings = getMappings(oldIndex);
        //获取setting
        Settings settings = getSettings(oldIndex);
        //新建index，并传入从旧index中获取到的settings和mappings
        createIndex(newIndex, mappings, settings);


        //Reindex
        ReindexRequest reindexRequest = new ReindexRequest();
        reindexRequest.setSourceIndices(oldIndex);
        reindexRequest.setDestIndex(newIndex);
        //保留源中的版本，创建缺少的任何文档，并更新目标索引中具有旧版本的文档
        reindexRequest.setDestVersionType(VersionType.EXTERNAL);
        //设置执行reindex后立马刷新索引
        reindexRequest.setRefresh(true);
        BulkByScrollResponse bulkByScrollResponse = null;
        try {
            bulkByScrollResponse = client.reindex(reindexRequest, RequestOptions.DEFAULT);
        } catch (IOException e) {
            e.printStackTrace();
        }
        System.out.println(bulkByScrollResponse.getTotal());
    }

    /**
     * 通过索引获取mappings
     * @param index     指定索引
     * @return          返回装有mappings的map
     */
    public Map<String,Object> getMappings(String index){
        //获取oldIndex的mappings
        GetMappingsRequest getMappingsRequest = new GetMappingsRequest();
        //设置索引
        getMappingsRequest.indices(index);
        GetMappingsResponse getMappingsResponse = null;
        try {
            getMappingsResponse = client.indices().getMapping(getMappingsRequest, RequestOptions.DEFAULT);
        } catch (IOException e) {
            e.printStackTrace();
        }
        //从response中获取mapping的Map集合
        Map<String, MappingMetaData> allIndexMappings = getMappingsResponse.mappings();
        MappingMetaData indexMappings = allIndexMappings.get(index);


        return indexMappings.getSourceAsMap();
    }

    /**
     * 返回索引获取settings
     * @param index     指定索引
     * @return          返回Settings对象
     */
    public Settings getSettings(String index){
        //获取oldIndex的Settings(暂时只获取分片数和备份数)
        GetSettingsRequest getSettingsRequest = new GetSettingsRequest();
        getSettingsRequest.indices(index);
        //没有获取到的设置给默认值
        getSettingsRequest.names("index.number_of_shards","index.number_of_replicas");
        getSettingsRequest.includeDefaults(true);
        GetSettingsResponse getSettingsResponse = null;
        try {
            getSettingsResponse = client.indices().getSettings(getSettingsRequest, RequestOptions.DEFAULT);
        } catch (IOException e) {
            e.printStackTrace();
        }
        //通过index名获取Settings
        return getSettingsResponse.getIndexToSettings().get(index);
    }


    /**
     * 分页查询
     * @param pageSize      每页的数据大小
     * @param pageNumber    第几页
     * @param indices       要查询的索引
     * @return              返回当前页的信息
     */
    public List<Map<String,Object>> searchByPage(Integer pageSize,Integer pageNumber,String... indices){

        SearchRequest searchRequest = new SearchRequest(indices);
        SearchSourceBuilder sourceBuilder = new SearchSourceBuilder();

        sourceBuilder.size(pageSize);
        sourceBuilder.from(pageSize*(pageNumber-1));
        sourceBuilder.query(QueryBuilders.matchAllQuery());

        sourceBuilder = searchAddSortAndQueryBuild(sourceBuilder);

        searchRequest.source(sourceBuilder);

        return exeSearchAndGetMap(searchRequest);
    }



    public void setQueryBuilders(QueryBuilder queryBuilders) {
        this.queryBuilders = queryBuilders;
    }

    public void setSortBuilder(FieldSortBuilder sortBuilder) {
        this.sortBuilder = sortBuilder;
    }

    /**
     * 为查询添加排序和QueryBuilder
     * @param sourceBuilder
     * @return
     */
    public SearchSourceBuilder searchAddSortAndQueryBuild(SearchSourceBuilder sourceBuilder){
        //添加查询条件
        if (queryBuilders != null){
            sourceBuilder.query(queryBuilders);
        }
        //添加排序
        if (sortBuilder != null){
            sourceBuilder.sort(sortBuilder);
        }
        //用完QueryBuilder和排序后清零
        queryBuilders = null;
        sortBuilder = null;

        return  sourceBuilder;
    }
}













