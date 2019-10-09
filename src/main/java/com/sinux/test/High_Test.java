package com.sinux.test;

import org.apache.http.HttpHost;
import org.elasticsearch.action.search.SearchRequest;
import org.elasticsearch.action.search.SearchResponse;
import org.elasticsearch.client.RequestOptions;
import org.elasticsearch.client.RestClient;
import org.elasticsearch.client.RestHighLevelClient;
import org.elasticsearch.search.SearchHit;
import org.elasticsearch.search.builder.SearchSourceBuilder;
import org.elasticsearch.search.sort.FieldSortBuilder;
import org.elasticsearch.search.sort.SortOrder;

import java.io.IOException;


/**
 * @author kevin
 * @date 2019-09-09 11:48
 */
public class High_Test {
    public static void main(String[] args) throws IOException {

        //建立client
        RestHighLevelClient client = new RestHighLevelClient(RestClient.builder(
                new HttpHost("127.0.0.1",9200,"http")
        ));

        //聚合

        //demo1
//        SearchRequest searchRequest = new SearchRequest();
//        SearchSourceBuilder searchSourceBuilder = new SearchSourceBuilder();
//        TermsAggregationBuilder aggregation = AggregationBuilders.terms("by_sex").field("sex");//参数：聚合名和文档key
//        aggregation.subAggregation(AggregationBuilders.avg("avg_age").field("age"));//参数：子聚合名和文档key
//        searchSourceBuilder.aggregation(aggregation);
//        searchRequest.source(searchSourceBuilder);
//
//
//        SearchResponse searchResponse = null;
//        searchResponse = client.search(searchRequest, RequestOptions.DEFAULT);
//        Aggregations aggregations = searchResponse.getAggregations();
//        Terms byCompanyAggregation = aggregations.get("by_sex");//参数：聚合名
//        Terms.Bucket elasticBucket = byCompanyAggregation.getBucketByKey("1");//参数：文档的value
//        Avg averageAge = elasticBucket.getAggregations().get("avg_age");//参数：子聚合名
//        double avg = averageAge.getValue();
//        System.out.println("性别1的平均年龄："+avg);



        //时间聚合
//        SearchRequest searchRequest1 = new SearchRequest("user");
//        SearchSourceBuilder searchSourceBuilder1 = new SearchSourceBuilder();
//
//        DateRangeAggregationBuilder dateRange = AggregationBuilders.dateRange("date").field("date").addRange("2018-08-10","2018-11-15").addRange("2018-11-15","2018-11-20");
//        dateRange.subAggregation(AggregationBuilders.stats("stats").field("age"));

//        TermsAggregationBuilder dateRange = AggregationBuilders.terms("date").field("date");
//        dateRange.subAggregation(AggregationBuilders.stats("stats").field("age"));
//        DateHistogramAggregationBuilder dateHistogram = AggregationBuilders.dateHistogram("date");
//
//        dateHistogram.calendarInterval(DateHistogramInterval.DAY);
//        dateHistogram.field("date");
//        dateHistogram.format("yyyy-MM-dd");
//        dateHistogram.minDocCount(1);
//        dateHistogram.extendedBounds(new ExtendedBounds("2017-01-01","2017-12-31"));
//
//
//
//        searchSourceBuilder1.aggregation(dateRange);
//        searchRequest1.source(searchSourceBuilder1);
//
//        SearchResponse searchResponse1 = null;
//        searchResponse1 = client.search(searchRequest1, RequestOptions.DEFAULT);
//
//         Histogram histogram = searchResponse1.getAggregations().get("date");
//
//        List<? extends Histogram.Bucket> buckets = histogram.getBuckets();
//        for (Histogram.Bucket bucket : buckets) {
//            System.out.println(bucket.getKeyAsString());
//            System.out.println("每个时间段的个数:"+bucket.getDocCount());
//        }

//        Aggregations aggregations1 = searchResponse1.getAggregations();
//        Terms range = aggregations1.get("date");
//        List<? extends Terms.Bucket> buckets = range.getBuckets();
//        for (Terms.Bucket bucket : buckets) {
//
//
//            Stats stats = bucket.getAggregations().get("stats");
//
//            System.out.println(stats.getMax());
//            System.out.println(stats.getMin());
//            System.out.println(stats.getCount());
//            System.out.println(stats.getAvg());
//
//            System.out.println(bucket.getDocCount());
//
//            System.out.println(bucket.getKeyAsString());
//
//        }

//
//        Aggregations aggregations1 = searchResponse1.getAggregations();
//        Range range = aggregations1.get("date");
//
//        for (Range.Bucket bucket : range.getBuckets()){
//
//            System.out.println(bucket.getDocCount());
//            System.out.println(bucket.getKey());
//            System.out.println(bucket.getKeyAsString());
//            System.out.println(bucket.getFrom());
//            System.out.println(bucket.getTo());
//        }




//        //demo2
//        SearchRequest searchRequest1 = new SearchRequest();
//        //创建sourceBuilder
//        SearchSourceBuilder searchSourceBuilder1 = new SearchSourceBuilder();
//        //创建聚合和聚合字段
//        TermsAggregationBuilder aggregation1 = AggregationBuilders.terms("by_sex").field("name.keyword");
//
//        aggregation1.subAggregation(AggregationBuilders.stats("stat_age").field("age"));//avg_age为子聚合名称，取名随意
//
//
//        searchSourceBuilder1.aggregation(aggregation1);
//        searchRequest1.source(searchSourceBuilder1);
//
//        SearchResponse searchResponse1 = client.search(searchRequest1, RequestOptions.DEFAULT);
//
//
//        Aggregations aggregations1 = searchResponse1.getAggregations();
//        Terms byCompanyAggregation1 = aggregations1.get("by_sex");
//
//
//
//        List<? extends Terms.Bucket> buckets = byCompanyAggregation1.getBuckets();
//        for (Terms.Bucket bucket : buckets){
//
//
//            Stats statAge = bucket.getAggregations().get("stat_age");
//            System.out.println(bucket.getKey());//获取分组名称
//            System.out.println("平均值："+statAge.getAvg());
//            System.out.println("总数："+statAge.getSum());
//            System.out.println("最大值："+statAge.getMaxAsString());
//            System.out.println("最小值："+statAge.getMin());
//            statAge.getCount();
//        }


        /**
         * 存入操作API
         *
         */
        //String存入文档
//        IndexRequest request = new IndexRequest("user");
//        request.id("1");
//        String jsonString = "{" +
//                "\"user\":\"kimchy\"," +
//                "\"postDate\":\"2013-01-30\"," +
//                "\"message\":\"trying out Elasticsearch\"" +
//                "}";
//        request.source(jsonString, XContentType.JSON);


        //Map存入文档
//        Map<String, Object> jsonMap = new HashMap<>();
//        jsonMap.put("user", "kimchy");
//        jsonMap.put("postDate", new Date());
//        jsonMap.put("message", "trying out Elasticsearch");
//        IndexRequest request = new IndexRequest("posts")
//                .id("1").source(jsonMap);
//
//        //XContentFactory存入文档
//        XContentBuilder builder = XContentFactory.jsonBuilder();
//        builder.startObject();
//        {
//            builder.field("user", "kimchy");
//            builder.timeField("postDate", new Date());
//            builder.field("message", "trying out Elasticsearch");
//        }
//        builder.endObject();
//        IndexRequest indexRequest1 = new IndexRequest("posts")
//                .id("1").source(builder);


        //使用IndexRequest存入文档
//        IndexRequest indexRequest = new IndexRequest("posts")
//                .id("1")
//                .source("user", "kimchy",
//                        "postDate", new Date(),
//                        "message", "trying out Elasticsearch");



        //选择可以配置的参数

//        //配置路由
//        request.routing("");
//        //配置超时时间
//        request.timeout(TimeValue.timeValueSeconds(1));
//        request.timeout("1s");
//        //设置刷新策略
//        request.setRefreshPolicy(WriteRequest.RefreshPolicy.WAIT_UNTIL);
//        request.setRefreshPolicy("wait_for");
//        //设置版本
//        request.version(1);
//        request.versionType(VersionType.EXTERNAL);


        //同步执行新增操作
//        IndexResponse indexResponse = client.index(request, RequestOptions.DEFAULT);

        //异步执行新增操作
//        ActionListener<IndexResponse> listener = new ActionListener<IndexResponse>() {
//            @Override
//            public void onResponse(IndexResponse indexResponse) {
//
//            }
//
//            @Override
//            public void onFailure(Exception e) {
//
//            }
//        };
//
//
//        client.indexAsync(request,RequestOptions.DEFAULT,listener);


        /**
         * GET-API
         *
         */

        //执行查询操作
//        GetRequest request = new GetRequest("user","1");


        //禁用源检索，默认情况下启用
//        request.fetchSourceContext(FetchSourceContext.DO_NOT_FETCH_SOURCE);

        //配置特定字段的源包含
//        String[] includes = new String[]{"message", "*Date"};
//        String[] excludes = Strings.EMPTY_ARRAY;
//        FetchSourceContext fetchSourceContext =
//                new FetchSourceContext(true, includes, excludes);
//        request.fetchSourceContext(fetchSourceContext);


//        //为特定字段配置源排除
//        String[] includes = Strings.EMPTY_ARRAY;
//        String[] excludes = new String[]{"message"};
//        FetchSourceContext fetchSourceContext =
//                new FetchSourceContext(true, includes, excludes);
//        request.fetchSourceContext(fetchSourceContext);
//
//
//
//
//        request.storedFields("message");//配置特定存储字段的检索（要求字段分别存储在映射中）
//        GetResponse getResponse = client.get(request, RequestOptions.DEFAULT);
//        String message = getResponse.getField("message").getValue();//检索message存储的字段（要求字段分别存储在映射中）
//
//
//        //配置各种参数
////        request.routing("routing");
////        request.preference("preference");
////        request.realtime(false);
////        request.refresh(true);
//        request.version(2);
////        request.versionType(VersionType.EXTERNAL);
//
//
//
//        //异步执行
////        ActionListener<GetResponse> listener = new ActionListener<GetResponse>() {
////            @Override
////            public void onResponse(GetResponse getResponse) {
////              成功完成时调用
////            }
////
////            @Override
////            public void onFailure(Exception e) {
////              失败时调用
////            }
////        };
////        client.getAsync(request, RequestOptions.DEFAULT, listener);
//
//
//
        //同步执行
//        GetResponse getResponse1 = client.get(request, RequestOptions.DEFAULT);
//        System.out.println(getResponse1.toString());
//
//
//        //返回GetResponse允许检索所请求的文档及其元数据和最终存储的字段。
//        String index = getResponse.getIndex();
//        String id = getResponse.getId();
//        if (getResponse.isExists()) {
//            long version = getResponse.getVersion();
//            String sourceAsString = getResponse.getSourceAsString();//将文档检索为 String
//            Map<String, Object> sourceAsMap = getResponse.getSourceAsMap();//将文档检索为 Map<String, Object>
//            byte[] sourceAsBytes = getResponse.getSourceAsBytes();//将文档检索为 byte[]
//        } else {
//            //处理未找到文档的方案。请注意，虽然返回的响应具有404状态代码
//            //但返回有效GetResponse而不是抛出异常。此类响应不包含任何源文档，其isExists方法返回false。
//        }
//
//
//
//        //当针对不存在的索引执行get请求时，响应具有404状态代码，ElasticsearchException抛出需要按如下方式处理
//        GetRequest request2 = new GetRequest("does_not_exist", "1");
//        try {
//            GetResponse getResponse2 = client.get(request2, RequestOptions.DEFAULT);
//        } catch (ElasticsearchException e) {
//            if (e.status() == RestStatus.NOT_FOUND) {
//                //处理抛出的异常，因为索引不存在
//            }
//        }
//
//        //如果已请求特定文档版本，并且现有文档具有不同的版本号，则会引发版本冲突：
//        try {
//            GetRequest request3 = new GetRequest("posts", "1").version(2);
//            GetResponse getResponse3 = client.get(request3, RequestOptions.DEFAULT);
//        } catch (ElasticsearchException exception) {
//            if (exception.status() == RestStatus.CONFLICT) {
//                //引发的异常表示返回了版本冲突错误
//            }
//        }




        /**
         *
         * 判断文档是否存在API
         */
//        GetRequest getRequest = new GetRequest(
//                "posts",//索引
//                "1");//Id
//        getRequest.fetchSourceContext(new FetchSourceContext(false));//禁用fetching  _source
//        getRequest.storedFields("_none_");//禁用fetching stored fields


        //同步执行
//        boolean exists = client.exists(getRequest, RequestOptions.DEFAULT);
//
//        System.out.println(exists);
        //异步执行
//        ActionListener<Boolean> listener = new ActionListener<Boolean>() {
//            @Override
//            public void onResponse(Boolean exists) {
//
//            }
//
//            @Override
//            public void onFailure(Exception e) {
//
//            }
//        };
//        client.existsAsync(getRequest, RequestOptions.DEFAULT, listener);


        /**
         * 删除API
         */


        //删除一个文档需要两个参数
//        DeleteRequest request = new DeleteRequest(
//                "posts",
//                "1");

        //配置request参数
//        request.routing("routing");
//        request.timeout(TimeValue.timeValueMinutes(2));
//        request.timeout("2m");
//        request.setRefreshPolicy(WriteRequest.RefreshPolicy.WAIT_UNTIL);
//        request.setRefreshPolicy("wait_for");
//        request.version(2);
//        request.versionType(VersionType.EXTERNAL);

        //同步执行
//        DeleteResponse deleteResponse = client.delete(
//                request, RequestOptions.DEFAULT);

//        //异步执行
//        ActionListener<DeleteResponse> listener = new ActionListener<DeleteResponse>() {
//            @Override
//            public void onResponse(DeleteResponse deleteResponse) {
//
//            }
//
//            @Override
//            public void onFailure(Exception e) {
//
//            }
//        };
//        client.deleteAsync(request, RequestOptions.DEFAULT, listener);



//        //检索有关已执行操作的信息
//        String index = deleteResponse.getIndex();
//        String id = deleteResponse.getId();
//        long version = deleteResponse.getVersion();
//        ReplicationResponse.ShardInfo shardInfo = deleteResponse.getShardInfo();
//        if (shardInfo.getTotal() != shardInfo.getSuccessful()) {
//            //处理成功片数小于总分片数时
//        }
//        if (shardInfo.getFailed() > 0) {
//            for (ReplicationResponse.ShardInfo.Failure failure :
//                    shardInfo.getFailures()) {
//                String reason = failure.reason();//处理失败时
//            }
//        }



//        //检查文档是否被找到
//        DeleteRequest request1 = new DeleteRequest("posts", "does_not_exist");
//        DeleteResponse deleteResponse1 = client.delete(
//                request1, RequestOptions.DEFAULT);
//        if (deleteResponse.getResult() == DocWriteResponse.Result.NOT_FOUND) {
//            //找不到时做的操作
//        }

        /**
         *
         * update-API
         */

        //执行修改更新
//        UpdateRequest request = new UpdateRequest(
//                "user",
//                "1");
//
//        //使用脚本更新
//        Map<String, Object> parameters = singletonMap("sex", 1);//脚本参数作为Map对象提供
//
//        Script inline = new Script(ScriptType.INLINE, "painless",
//                "ctx._source.field += params.count", parameters);//使用painless语言和先前的参数创建内联脚本
//        request.script(inline);//将脚本设置为更新请求


//        //或者作为存储脚本
//        Script stored = new Script(
//                ScriptType.STORED, null, "increment-field", parameters);//引用以increment-field该painless语言名称存储的脚本
//        request.script(stored);//在更新请求中设置脚本


        //使用字符串更新文档
//        UpdateRequest request1 = new UpdateRequest("posts", "1");
//        String jsonString = "{" +
//                "\"updated\":\"2017-01-01\"," +
//                "\"reason\":\"daily update\"" +
//                "}";
//        request1.doc(jsonString, XContentType.JSON);//部分文档源String以JSON格式提供



        //使用map更新文档
//        Map<String, Object> jsonMap = new HashMap<>();
//        jsonMap.put("sex", "1");
//        UpdateRequest request2 = new UpdateRequest("user", "1")
//                .doc(jsonMap);

        //XContentBuilder对象更新对象
//        XContentBuilder builder = XContentFactory.jsonBuilder();
//        builder.startObject();
//        {
//            builder.timeField("updated", new Date());
//            builder.field("reason", "daily update");
//        }
//        builder.endObject();
//        UpdateRequest request3 = new UpdateRequest("posts", "1")
//                .doc(builder);


        //另一种更新文档的方式
//        UpdateRequest request4 = new UpdateRequest("posts", "1")
//                .doc("updated", new Date(),
//                        "reason", "daily update");


        //调用upsert方法，如果文档不存在，则进行插入操作
//        String jsonString = "{\"created\":\"2017-01-01\"}";
//        request.upsert(jsonString, XContentType.JSON);


        //可修改参数
//        request.routing("routing");
//        request.timeout(TimeValue.timeValueSeconds(1));
//        request.timeout("1s");
//        request.setRefreshPolicy(WriteRequest.RefreshPolicy.WAIT_UNTIL);
//        request.setRefreshPolicy("wait_for");
//        request.retryOnConflict(3);
//        request.fetchSource(true);


//        //配置包含的字段
//        String[] includes = new String[]{"updated", "r*"};
//        String[] excludes = Strings.EMPTY_ARRAY;
//        request.fetchSource(
//                new FetchSourceContext(true, includes, excludes));

//        //配置不包含的字段
//        String[] includes = Strings.EMPTY_ARRAY;
//        String[] excludes = new String[]{"updated"};
//        request.fetchSource(
//                new FetchSourceContext(true, includes, excludes));

//        request.setIfSeqNo(2L);
//        request.setIfPrimaryTerm(1L);
//        request.detectNoop(false);//禁用noop检测
//        request.scriptedUpsert(true); //指示无论文档是否存在，脚本都必须运行，即如果文档尚不存在，脚本将负责创建文档。
//        request.docAsUpsert(true);//如果尚未存在，则表明必须将部分文档用作upsert文档。
//
//        request.waitForActiveShards(2);//设置在继续更新操作之前必须处于活动状态的分片副本数
//        request.waitForActiveShards(ActiveShardCount.ALL);//提供的分片数量为ActiveShardCount：可以是ActiveShardCount.ALL， ActiveShardCount.ONE或ActiveShardCount.DEFAULT（默认）

        //同步执行
//        UpdateResponse updateResponse = client.update(
//                request2, RequestOptions.DEFAULT);

        //异步执行
//        ActionListener<UpdateResponse> listener = new ActionListener<UpdateResponse>() {
//            @Override
//            public void onResponse(UpdateResponse updateResponse) {
//
//            }
//
//            @Override
//            public void onFailure(Exception e) {
//
//            }
//        };
//        client.updateAsync(request, RequestOptions.DEFAULT, listener);




//        //updateResponse允许检索有关已执行操作的信息
//        String index = updateResponse.getIndex();
//        String id = updateResponse.getId();
//        long version = updateResponse.getVersion();
//        if (updateResponse.getResult() == DocWriteResponse.Result.CREATED) {
//            //处理首次创建文档的情况（upsert）
//        } else if (updateResponse.getResult() == DocWriteResponse.Result.UPDATED) {
//            //处理文档更新的案例
//        } else if (updateResponse.getResult() == DocWriteResponse.Result.DELETED) {
//            //处理删除文档的情况
//        } else if (updateResponse.getResult() == DocWriteResponse.Result.NOOP) {
//            //处理文档未受更新影响的情况，即未对文档执行任何操作（noop）
//        }




//        //UpdateRequest 通过fetchSource方法启用源检索时，响应包含更新文档的来源：
//        GetResult result = updateResponse.getGetResult();//将更新的文档检索为 GetResult
//        if (result.isExists()) {
//            String sourceAsString = result.sourceAsString();//将更新的文档的源检索为 String
//            Map<String, Object> sourceAsMap = result.sourceAsMap();//将更新的文档的源检索为 Map<String, Object>
//            byte[] sourceAsBytes = result.source();//将更新的文档的源检索为 byte[]
//        } else {
//            //处理响应中不存在文档源的场景（默认情况下是这种情况）
//        }



        //检查分片是否失败
//        ReplicationResponse.ShardInfo shardInfo = updateResponse.getShardInfo();
//        if (shardInfo.getTotal() != shardInfo.getSuccessful()) {
//            //处理成功分片数小于总分片数的情况
//        }
//        if (shardInfo.getFailed() > 0) {
//            for (ReplicationResponse.ShardInfo.Failure failure :
//                    shardInfo.getFailures()) {
//                String reason = failure.reason();//处理潜在的失败
//            }
//        }


        /**
         *
         * BulkRequest
         */

        //一次添加三个
//        BulkRequest request = new BulkRequest();//创建BulkRequest
//        request.add(new IndexRequest("user").id("2").source(XContentType.JSON,"name","lisi"));
//        request.add(new IndexRequest("user").id("3").source(XContentType.JSON,"name","wangwu"));
//        request.add(new IndexRequest("user").id("4").source(XContentType.JSON,"name","zhaoliu"));
//
//
//        //另外的写法
//        BulkRequest request1 = new BulkRequest();
//        request1.add(new DeleteRequest("user","3"));
//        request1.add(new UpdateRequest("user","2").doc(XContentType.JSON,"other","test"));
//        request1.add(new IndexRequest("user").id("5").source(XContentType.JSON,"name","wrk"));


        //参数配置
//        request.timeout(TimeValue.timeValueMinutes(2));
//        request.timeout("2m");
//        request.setRefreshPolicy(WriteRequest.RefreshPolicy.WAIT_UNTIL);
//        request.setRefreshPolicy("wait_for");
//        request.waitForActiveShards(2);
//        request.waitForActiveShards(ActiveShardCount.ALL);
//        request.pipeline("pipelineId");
//        request.routing("routingId");

        //同步执行
//        BulkResponse bulkResponse = client.bulk(request, RequestOptions.DEFAULT);

        //异步执行
//        ActionListener<BulkResponse> listener = new ActionListener<BulkResponse>() {
//            @Override
//            public void onResponse(BulkResponse bulkResponse) {
//
//            }
//
//            @Override
//            public void onFailure(Exception e) {
//
//            }
//        };
//        client.bulkAsync(request, RequestOptions.DEFAULT, listener);

//        //批量响应
//        for (BulkItemResponse bulkItemResponse : bulkResponse) {
//            DocWriteResponse itemResponse = bulkItemResponse.getResponse();
//
//            switch (bulkItemResponse.getOpType()){
//                case INDEX:;
//                case CREATE:
//                    IndexResponse indexResponse = (IndexResponse) itemResponse;
//                    break;
//                case UPDATE:
//                    UpdateResponse updateResponse = (UpdateResponse)itemResponse;
//                    break;
//                case DELETE:
//                    DeleteResponse itemResponse1 = (DeleteResponse) itemResponse;
//
//            }
//        }

        //批量响应提供了一种快速检查一个或多个操作是否失败的方法
//        if (bulkResponse.hasFailures()) {
//
//        }

        //在这种情况下，有必要迭代所有操作结果，以检查操作是否失败，如果是，则检索相应的失败：
//        for (BulkItemResponse bulkItemResponse : bulkResponse) {
//            if (bulkItemResponse.isFailed()) {
//                BulkItemResponse.Failure failure =
//                        bulkItemResponse.getFailure();
//            }
//        }

        //批量处理器

        //RestHighLevelClient
        //此客户端用于执行BulkRequest 和检索BulkResponse
        //BulkProcessor.Listener
        //在每次BulkRequest执行之前或之后或BulkRequest失败时调用此侦听器
        //然后该BulkProcessor.builder方法可用于构建新的 BulkProcessor
//        BulkProcessor.Listener listener = new BulkProcessor.Listener() {//创建 BulkProcessor.Listener
//            @Override
//            public void beforeBulk(long executionId, BulkRequest request) {
//                //在每次执行之前调用此方法 BulkRequest
//
//            }
//
//            @Override
//            public void afterBulk(long executionId, BulkRequest request,BulkResponse response) {
//
//                //每次执行a后都会调用此方法 BulkRequest
//            }
//
//            @Override
//            public void afterBulk(long executionId, BulkRequest request,
//                                  Throwable failure) {
//                //BulkRequest失败时调用此方法
//            }
//        };
//
//        BulkProcessor bulkProcessor = BulkProcessor.builder(
//                (request3, bulkListener) ->
//                        client.bulkAsync(request, RequestOptions.DEFAULT, bulkListener),
//                listener).build();//BulkProcessor通过build()从中调用方法来创建BulkProcessor.Builder。该RestHighLevelClient.bulkAsync() 方法将用于执行BulkRequest引擎盖。
//
//
//
//        //该BulkProcessor.Builder提供的方法来配置如何 BulkProcessor应该处理请求的执行：
//        BulkProcessor.Builder builder = BulkProcessor.builder(
//                (request4, bulkListener) ->
//                        client.bulkAsync(request, RequestOptions.DEFAULT, bulkListener),
//                listener);
//        builder.setBulkActions(500);//根据当前添加的操作数设置何时刷新新的批量请求（默认为1000，使用-1禁用它）
//        builder.setBulkSize(new ByteSizeValue(1L, ByteSizeUnit.MB));//设置何时根据当前添加的操作大小刷新新的批量请求（默认为5Mb，使用-1禁用它）
//        builder.setConcurrentRequests(0);//设置允许执行的并发请求数（默认为1，使用0只允许执行单个请求）
//        builder.setFlushInterval(TimeValue.timeValueSeconds(10L));//BulkRequest如果间隔超过，则设置刷新间隔刷新任何挂起（默认为未设置）
//        builder.setBackoffPolicy(BackoffPolicy
//                .constantBackoff(TimeValue.timeValueSeconds(1L), 3));//设置一个最初等待1秒的常量后退策略，最多重试3次。见BackoffPolicy.noBackoff()， BackoffPolicy.constantBackoff()并BackoffPolicy.exponentialBackoff() 为更多的选择。
//
//
//
//        //一旦BulkProcessor创建请求可以被添加到它：
//        IndexRequest one = new IndexRequest("posts").id("1")
//                .source(XContentType.JSON, "title",
//                        "In which order are my Elasticsearch queries executed?");
//        IndexRequest two = new IndexRequest("posts").id("2")
//                .source(XContentType.JSON, "title",
//                        "Current status and upcoming changes in Elasticsearch");
//        IndexRequest three = new IndexRequest("posts").id("3")
//                .source(XContentType.JSON, "title",
//                        "The Future of Federated Search in Elasticsearch");
//
//        bulkProcessor.add(one);
//        bulkProcessor.add(two);
//        bulkProcessor.add(three);
//
//        //请求将由执行BulkProcessor，其负责调用BulkProcessor.Listener每个批量请求。
//        //
//        //监听器提供访问BulkRequest和的方法BulkResponse：
//
//        BulkProcessor.Listener listener1 = new BulkProcessor.Listener() {
//            @Override
//            public void beforeBulk(long executionId, BulkRequest request) {
//                int numberOfActions = request.numberOfActions();
//                logger.debug("Executing bulk [{}] with {} requests",
//                        executionId, numberOfActions);
        //在每次执行a之前调用BulkRequest，此方法允许知道将在其中执行的操作的数量BulkRequest



//            }
//
//            @Override
//            public void afterBulk(long executionId, BulkRequest request,
//                                  BulkResponse response) {
//                if (response.hasFailures()) {
//                    logger.warn("Bulk [{}] executed with failures", executionId);
//                } else {
//                    logger.debug("Bulk [{}] completed in {} milliseconds",
//                            executionId, response.getTook().getMillis());

        //每次执行a后调用BulkRequest，此方法允许知道是否BulkResponse包含错误


//                }
//            }
//
//            @Override
//            public void afterBulk(long executionId, BulkRequest request,
//                                  Throwable failure) {

        //如果BulkRequest失败则调用，此方法允许知道失败
//                logger.error("Failed to execute bulk", failure);
//            }
//        };

////        awaitClose()方法可用于等待所有请求都已处理或指定的时间已过就关闭对象
//        boolean terminated = bulkProcessor.awaitClose(30L, TimeUnit.SECONDS);
//        //close用于立刻关闭
//        bulkProcessor.close();


        /**
         *
         * UpdateByQueryAPI
         */
        //最简单形式的UpdateByQueryRequest
//        UpdateByQueryRequest request = new UpdateByQueryRequest("user", "user1");

//        //默认情况下，版本冲突会中止该UpdateByQueryRequest过程，但您可以使用以下方法计算它们
//        request.setConflicts("proceed");//设置proceed版本冲突
//
//
//        //通过添加查询来限制文档
//        request.setQuery(new TermQueryBuilder("name","kevin"));//查询并修改name字段的值
//
//        //也可以通过设置来限制处理文档的数量maxDocs
//        request.setMaxDocs(10);//限制10份
//        //默认情况下，UpdateByQueryRequest使用批次1000.您可以使用更改批次大小 setBatchSize
//
//        request.setBatchSize(100);//使用100个文档的批次
//        //按查询更新还可以通过指定a来使用摄取功能pipeline
//        request.setPipeline("my_pipeline");//设置管道 my_pipeline
//
//        //UpdateByQueryRequest还支持script修改文档：
//        request.setScript(
//                new Script(
//                        ScriptType.INLINE, "painless",
//                        "if (ctx._source.user == 'kimchy') {ctx._source.likes++;}",
//                        Collections.emptyMap()));//setScriptlikes使用用户增加所有文档的字段kimchy。
//
//
//        //UpdateByQueryRequest可以使用并行sliced-scroll有setSlices：
//        request.setSlices(2);//设置要使用的切片数
//
//        //UpdateByQueryRequest使用该scroll参数来控制“搜索上下文”保持活动的时间。
//        request.setScroll(TimeValue.timeValueMinutes(10)); //设置滚动时间
//
//        //如果提供路由，则路由将复制到滚动查询，从而将进程限制为与该路由值匹配的分片。
//        request.setRouting("=cat");//设置路由
//
//        //设置参数
//        request.setTimeout(TimeValue.timeValueMinutes(2));
//        request.setRefresh(true);
//        request.setIndicesOptions(IndicesOptions.LENIENT_EXPAND_OPEN);


        //同步启动
//        BulkByScrollResponse bulkResponse = client.updateByQuery(request, RequestOptions.DEFAULT);

//        //异步启动
//        ActionListener<BulkByScrollResponse> listener = new ActionListener<BulkByScrollResponse>() {
//            @Override
//            public void onResponse(BulkByScrollResponse bulkResponse) {
//
//            }
//
//            @Override
//            public void onFailure(Exception e) {
//
//            }
//        };
//        client.updateByQueryAsync(request, RequestOptions.DEFAULT, listener);


        //返回UpdateByQueryResponse包含有关已执行操作的信息，并允许迭代每个结果，如下所示
//        TimeValue timeTaken = bulkResponse.getTook();
//        boolean timedOut = bulkResponse.isTimedOut();
//        long totalDocs = bulkResponse.getTotal();
//        long updatedDocs = bulkResponse.getUpdated();
//        long deletedDocs = bulkResponse.getDeleted();
//        long batches = bulkResponse.getBatches();
//        long noops = bulkResponse.getNoops();
//        long versionConflicts = bulkResponse.getVersionConflicts();
//        long bulkRetries = bulkResponse.getBulkRetries();
//        long searchRetries = bulkResponse.getSearchRetries();
//        TimeValue throttledMillis = bulkResponse.getStatus().getThrottled();
//        TimeValue throttledUntilMillis =
//                bulkResponse.getStatus().getThrottledUntil();
//        List<ScrollableHitSource.SearchFailure> searchFailures =
//                bulkResponse.getSearchFailures();
//        List<BulkItemResponse.Failure> bulkFailures =
//                bulkResponse.getBulkFailures();
        //依次
        //获取总时间
        //
        //
        //检查请求是否超时
        //
        //
        //获取处理的文档总数
        //
        //
        //已更新的文档数
        //
        //
        //已删除的文档数
        //
        //
        //已执行的批次数
        //
        //
        //跳过的文档数
        //
        //
        //版本冲突的数量
        //
        //
        //请求必须重试批量索引操作的次数
        //
        //
        //请求必须重试搜索操作的次数
        //
        //
        //此请求限制的总时间不包括当前正在休眠的当前节流时间
        //
        //
        //任何当前油门睡眠的剩余延迟，如果不睡觉则为0
        //
        //
        //搜索阶段失败
        //
        //
        //批量索引操作期间出现故障


        /**
         * DeleteByQueryAPI
         */

//        DeleteByQueryRequest request1 =new DeleteByQueryRequest("source1", "source2");
//
//        //遇到版本冲突时继续执行
//        request.setConflicts("proceed");
//
//        //通过添加查询来限制文档
//        request.setQuery(new TermQueryBuilder("user", "kimchy"));//Only copy documents which have field user set to kimchy
//
//        //通过设置来限制处理文档的数量maxDocs
//        request.setMaxDocs(10);
//
//        //默认情况下，DeleteByQueryRequest使用批次1000.您可以使用更改批次大小setBatchSize
//        request.setBatchSize(100);
//
//
//        //还可以使用并行sliced-scroll使用setSlices
//        request.setSlices(2);//设置要使用的切片数
//
//
//        //使用该scroll参数来控制“搜索上下文”保持活动的时间。
//        request.setScroll(TimeValue.timeValueMinutes(10));//设置滚动时间
//
//        //如果提供路由，则路由将复制到滚动查询，从而将进程限制为与该路由值匹配的分片。
//        request.setRouting("=cat");


        //配置其他的参数
//        request.setTimeout(TimeValue.timeValueMinutes(2));
//        request.setRefresh(true);
//        request.setIndicesOptions(IndicesOptions.LENIENT_EXPAND_OPEN);
//
//
//        //同步运行
//        BulkByScrollResponse bulkResponse =
//                client.deleteByQuery(request, RequestOptions.DEFAULT);


        //异步运行
//        ActionListener<BulkByScrollResponse> listener = new ActionListener<BulkByScrollResponse>() {
//            @Override
//            public void onResponse(BulkByScrollResponse bulkResponse) {
//
//            }
//
//            @Override
//            public void onFailure(Exception e) {
//
//            }
//        };
//        client.deleteByQueryAsync(request, RequestOptions.DEFAULT, listener);



        //返回DeleteByQueryResponse包含有关已执行操作的信息，并允许迭代每个结果
//        TimeValue timeTaken = bulkResponse.getTook();
//        boolean timedOut = bulkResponse.isTimedOut();
//        long totalDocs = bulkResponse.getTotal();
//        long deletedDocs = bulkResponse.getDeleted();
//        long batches = bulkResponse.getBatches();
//        long noops = bulkResponse.getNoops();
//        long versionConflicts = bulkResponse.getVersionConflicts();
//        long bulkRetries = bulkResponse.getBulkRetries();
//        long searchRetries = bulkResponse.getSearchRetries();
//        TimeValue throttledMillis = bulkResponse.getStatus().getThrottled();
//        TimeValue throttledUntilMillis =
//                bulkResponse.getStatus().getThrottledUntil();
//        List<ScrollableHitSource.SearchFailure> searchFailures =
//                bulkResponse.getSearchFailures();
//        List<BulkItemResponse.Failure> bulkFailures =
//                bulkResponse.getBulkFailures();

        //参数依次是
        //获取总时间
        //
        //
        //检查请求是否超时
        //
        //
        //获取处理的文档总数
        //
        //
        //已删除的文档数
        //
        //
        //已执行的批次数
        //
        //
        //跳过的文档数
        //
        //
        //版本冲突的数量
        //
        //
        //请求必须重试批量索引操作的次数
        //
        //
        //请求必须重试搜索操作的次数
        //
        //
        //此请求限制的总时间不包括当前正在休眠的当前节流时间
        //
        //
        //任何当前油门睡眠的剩余延迟，如果不睡觉则为0
        //
        //
        //搜索阶段失败
        //
        //
        //批量索引操作期间出现故障






        /**
         * SearchAPI
         */



//        //构建SearchRequest没有添加参数，将会针对所有的索引
//        SearchRequest request = new SearchRequest("user");
//        //大多数搜索参数都会添加到SearchResourceBuilder,它为搜索请求正文中的所有内容提供了setter
//        SearchSourceBuilder builder =new SearchSourceBuilder();
//        //添加match_all查询到SearchSourceBuilder
//        builder.query(QueryBuilders.matchAllQuery());
//        //添加SearchSourceBuilder到SearchSourceBuilder到SeachReuqest
//        request.source(builder);
//
//
//        //可以选择添加指定的索引
//        SearchRequest searchRequest = new SearchRequest("posts");
//
//
//
//        //添加其他参数
//        searchRequest.routing("routing");
//        //设置IndicesOptions控制如何解析不可用的索引以及如何扩展通配符表达式
//        searchRequest.indicesOptions(IndicesOptions.lenientExpandOpen());
//        //使用首选项参数例如执行搜索以优先选择本地分片。默认是跨分片随机化。
//        searchRequest.preference("_local");
//
//
//        //SearchSourceBuilder配置
//        SearchSourceBuilder sourceBuilder = new SearchSourceBuilder();//创建默认的SearchSourceBuilder
//        sourceBuilder.query(QueryBuilders.termQuery("user", "kimchy"));//设置查询，可以是任何类型的QueryBuilders
//        sourceBuilder.from(0);//设置from确定结果索引的选项以开始搜索，默认为0
//        sourceBuilder.size(5);//设置size确定要返回的搜索匹配数的选项。默认为10。
//        sourceBuilder.timeout(new TimeValue(60, TimeUnit.SECONDS));//设置一个可选的超时，控制允许搜索的时间。
//        //在此之后，SearchSourceBuilder只需要添加到 SearchRequest：
//        SearchRequest request = new SearchRequest();
//        request.indices("posts");
//        request.source(sourceBuilder);
//
//        //构建查询
//        //搜索查询是使用QueryBuilder对象创建的，一个QueryBuilder存在通过elasticSearch支持的每一个搜索查询类型查询DSL
//
//        //一个QueryBuilder可以使用其构造函数创建
//        MatchQueryBuilder matchQueryBuilder = new MatchQueryBuilder("user","kimchy");//创建一个匹配文本“kimchy”匹配字段“user”的全文匹配查询。
//        //创建后，该QueryBUilder对象提供了匹配其创建的搜索查询选项的方法
//        matchQueryBuilder.fuzziness(Fuzziness.AUTO);//在匹配查询上启用模糊搜索
//        matchQueryBuilder.prefixLength(3);//在匹配查询上设置前缀长度选项
//        matchQueryBuilder.maxExpansions(10);//设置最大扩展选项以控制查询的模糊过程
//
//        //QueryBuilder也可以使用QueryBuilders使用程序类创建对象
//        //此类提供了可用于QueryBuilder使用流畅的编程样式创建对象的辅助方法
//        MatchQueryBuilder matchQueryBuilder = QueryBuilders.matchQuery("user", "kimchy")
//                .fuzziness(Fuzziness.AUTO)
//                .prefixLength(3)
//                .maxExpansions(10);
//
//        //无论用于创建它的方法是什么，QueryBuilder都必须将对象添加到SearchSOurceBUilder中
//        //searchSourceBuilder.query(matchQueryBuilder);
//
//
//
//
//        //指定排序
//        //在SearchSourceBuilder允许添加一个或多个SortBuilder实例，有四种实现
//        //四种实现(Field-,Source-,GeoDistance-,ScriptSortBuilder)
//        sourceBuilder.sort(new ScoreSortBuilder().order(SortOrder.DESC));//按降序排列（默认）
//        sourceBuilder.sort(new FieldSortBuilder("_id").order(SortOrder.ASC));//也可以按_id字段升序排列
//
//
////        源过滤
////        默认情况下，搜索请求会返回文档的内容，_source在RestAPI中，也可以覆盖此行行为，例如可以_source完全关闭检索
//        sourceBuilder.fetchSource(false);
//        //该方法还接受一个或多个通配符模式的数组，以控制以更精细的方式包含或排除哪些字段
//        String[] includeFields = new String[]{"title","innerObject.*"};
//        String[] excludeFields = new String[]{"user"};
//        sourceBuilder.fetchSource(includeFields,excludeFields);
//
//
//        //请求冲突显示
//        //通过设置打开HighlightBuilder，可以突出显示搜索结果SearchSOurceBUilder
//        //通过向一个HighlightBuilder.Field添加一个或多个实例，可以为每个字段定义不同的土突出显示行为
//
//        SearchSourceBuilder searchSourceBuilder = new SearchSourceBuilder();
//        //创建一个新的HighlightBuilder
//        HighlightBuilder highlightBuilder = new HighlightBuilder();
//        //为该title字段创建一个字段的高亮
//        HighlightBuilder.Field highlightTitle = new HighlightBuilder.Field("title");
//        //设置字段的高亮类型
//        highlightTitle.highlighterType("unified");
//        //将字段突出显示器添加到突出显示构建器中
//        highlightBuilder.field(highlightTitle);
//        HighlightBuilder.Field highlightUser = new HighlightBuilder.Field("user");
//        highlightBuilder.field(highlightTitle);
//        searchSourceBuilder.highlighter(highlightBuilder);
//
//        //RestAPI文档中有许多选项需要详细说明。RestAPI参数通常由具有相似名称的setter更改
//        //突出显示的文本片段可以 被以后来自SearchResponse检索
//
//
//
//        //请求聚合
//        //可以通过首先创建适当的AggregationBuilder然后在其上设置聚合来将聚合添加到搜索中SearchSourceBuilder
//        //在以下实例中，terms使用公司员工平均年龄的子聚合创建公司名称的聚合
//
//        SearchSourceBuilder searchSourceBuilder = new SearchSourceBuilder();
//        TermsAggregationBuilder aggregation = AggregationBuilders.terms("by_company").field("company.keyword");
//        aggregation.subAggregation(AggregationBuilders.avg("average_age").field("age"));
//        searchSourceBuilder.aggregation(aggregation);
//
//        //该构建聚合给出了所有可用的聚合与它们对应的列表AggregationBUilder对象和AggregationBuilders辅助方法
//
//
//
//        //Requesting Suggestions
//        //要向搜索请求添加建议，使用SuggestionBuilder可从SuggestBuilders工厂类轻松访问其中一个的实现，需要将建议构建器添加到顶层SuggestBUilder,它本身可以设置在顶层SearchSourceBuilder
//        SearchSourceBuilder searchSourceBuilder = new SearchSourceBuilder();
//        //TermSuggestionBuilder为user字段和文本创建新的kmichy
//        SuggestionBuilder termSuggestionBuilder = SuggestBuilders.termSuggestion("user").text("kmichy");
//        SuggestBuilder suggestBuilder = new SuggestBuilder();
//        //添加建议构建器并为其命名suggest_user
//        suggestBuilder.addSuggestion("suggest_user",termSuggestionBuilder);
//        searchSourceBuilder.suggest(suggestBuilder);
//
//
//
//        //分析查询和聚合
//        //该profileAPI可用于简档查询和聚合的执行针对特定的搜索请求
//        //为了使用它，在以下情况必须将profile标志设置为true SearchSourceBuilder
//        SearchSourceBuilder searchSourceBuilder = new SearchSourceBuilder();
//        searchSourceBuilder.profile(true);
//        //一旦SearchRequest执行，相应的SearchResponse将包含分析结果
//
//
//
//
//        //同步执行
//        SearchResponse searchResponse = client.search(request, RequestOptions.DEFAULT);
//
//
//
//        //异步执行
//        ActionListener<SearchResponse> listener = new ActionListener<SearchResponse>() {
//            @Override
//            public void onResponse(SearchResponse searchResponse) {
//
//            }
//
//            @Override
//            public void onFailure(Exception e) {
//
//            }
//        };
//        client.searchAsync(searchRequest, RequestOptions.DEFAULT, listener);
//
//
//        //SearchResponse
//
//        //在SearchResponse由执行搜索返回提供有关搜索执行本身以及访问返回文档的详细信息。
//        //有关请求执行本身的有用信息，例如HTTP状态代码，执行时间或请求是提前终止还是超时
//        RestStatus status = searchResponse.status();
//        TimeValue took = searchResponse.getTook();
//        Boolean terminatedEarly = searchResponse.isTerminatedEarly();
//        boolean timeOut = searchResponse.isTimedOut();
//
//        //Response还通过提供有关搜索影响的分片总数以及成功与不成功分片的统计信息
//        //提供有关分片级别执行的信息，可以通过迭代遍历数组来处理可能的失败
//        int totalShards = searchResponse.getTotalShards();
//        int successfulShards = searchResponse.getSuccessfulShards();
//        int faliedShards = searchResponse.getFailedShards();
//        for (ShardSearchFailure shardFailure : searchResponse.getShardFailures()) {
//            //故障应该在这里处理
//        }
//
//
//
//
        //Retrieving SearchHits
        //返回查询中所有文档的信息
        //要访问返回的文档，我们需要首先获取SearchHits响应中包含的内容
//        SearchHits hits = searchResponse.getHits();
//        //将SearchHits提供所有点击全局信息
//        TotalHits totalHits = hits.getTotalHits();
//        //点击总数，必须在totalHits.relation上下文中解释
//        long numHit = totalHits.value;
//        TotalHits.Relation relation = totalHits.relation;
//        float maxScore = hits.getMaxScore();
//
//
//        //嵌套在SearchHits可以迭代的单个搜索结果中：
//        SearchHit[] searchHits = hits.getHits();
//        for (SearchHit hit : searchHits) {
//            //可访问索引一样，文档ID和每个搜索命中的得分基本信息
//            String index = hit.getIndex();
//            String id = hit.getId();
//            float score = hit.getScore();
//
//
//            //此外，它还允许您以简单的JSON-String或键值对的映射形式返回文档源
//            //在此映射中，常规字段由字段名称键控并包含字段值。多值字段作为对象列表返回，嵌套对象作为另一个值映射返回
//            String sourceAsString = hit.getSourceAsString();
//            Map<String, Object> sourceAsMap = hit.getSourceAsMap();
//            String documentTitle = (String) sourceAsMap.get("title");
//            List<Object> users = (List<Object>) sourceAsMap.get("user");
//            Map<String, Object> innerObject = (Map<String, Object>) sourceAsMap.get("innerObject");

//
//
//
//
//            //Retrieving Highlighting
//            //如果需要，可以从SearchHit结果中的每个片段中检索突出显示的文本片段
//            //命中对象提供对HighlightField实例的字段名称映射的访问,每个实例包含一个或多个突出显示的文本片段
//        SearchHits hits = searchResponse.getHits();
//        for (SearchHit hit : hits) {
//            Map<String, HighlightField> highlightFields = hit.getHighlightFields();
//            //获得该title领域的突出显示
//            HighlightField highlight = highlightFields.get("title");
//            //获取包含突出显示的字段内容的一个或多个片段
//            Text[] fragments = highlight.fragments();
//            String fragmentString = fragments[0].string();
//        }
//
//
//
//        //Retrieving Aggregations
//        //SearchResponse可以获取aggregation tree的root，aggregation的对象，按名称获取聚合来检索聚合
//        Aggregations aggregations = searchResponse.getAggregations();
//        //获取名为by_company的聚合
//        Terms byCompanyAggregation = aggregations.get("by_company");
//        //获取key为elastic的bucket
//        Terms.Bucket elasticBucket = byCompanyAggregation.getBucketByKey("Elastic");
//        //从bucket获取名为average_age的sub-aggregation
//        Avg averageAge = elasticBucket.getAggregations().get("average_age");
//        double avg = averageAge.getValue();
//
//
//
//        //如果按名称访问聚合，则需要根据所请求的聚合类型指定聚合接口，否则将会抛出ClassCastException异常
//        Range range = aggregations.get("by_company");//这将引发异常，因为"by_company"是一个terms聚合，但我们尝试将其作为range聚合检索
//
//        //还可以将所有聚合作为由聚合名称键入的映射进行访问。
//        //这种情况下，需要显式地进行到正确聚合接口的转换
//        Map<String, Aggregation> aggregationMap = aggregations.getAsMap();
//        Terms companyAggregation = (Terms) aggregationMap.get("by_company");
//
//        //还有一些getter将所有顶级聚合作为list返回
//        List<Aggregation> aggregationList = aggregations.asList();
//
//        //可以迭代所有的聚合，然后根据类型决定如何进一步处理它们
//        for (Aggregation agg : aggregations) {
//            String type = agg.getType();
//            if (type.equals(TermsAggregationBuilder.NAME)){
//                Terms.Bucket bucket = ((Terms)agg).getBucketByKey("Elastic");
//                long numberOfDocs = bucket.getDocCount();
//            }
//
//        }
//
//
//        //Retrieving Suggestions
//        //要从SearchResponse获取suggestions ,使用Suggest对象，检索嵌套的suggestion 对象
//        //使用Suggest该类访问建议
//        Suggest suggest = searchResponse.getSuggest();
//        //可以通过名称检索suggestions,您需要将它们分配给正确类型的Suggestion类（此处TermSuggestion），否则ClassCastException抛出a
//        TermSuggestion termSuggestion = suggest.getSuggestion("suggest_user");
//        //迭代建议条目
//        for(TermSuggestion.Entry entry : termSuggestion.getEntries()){
//            //在一个条目中迭代选项
//            for (TermSuggestion.Entry.Option option : entry){
//                String suggestText = option.getText().string();
//            }
//        }
//
//
//        //Retrieving Profiling Results
//        //SearchResponse使用getProfileResults()方法检索分析结果
//        //此方法返回Map包含执行中ProfileShardResult涉及每个分片的对象SearchRequest
//        //ProfileShardResult存储在Map使用秘钥中，该密钥唯一的标识配置文件结果对应的分片
//
//        //通过SearchResponse从ProfileShardResult取出Map
//        Map<String, ProfileShardResult> profilingResults = searchResponse.getProfileResults();
//        //如果密钥已知，则可以通过分片的密钥检索分析结果，否则迭代所有分析结果可能更简单
//        for (Map.Entry<String, ProfileShardResult> profilingResult : profilingResults.entrySet()) {
//            //取出标识ProfileShardResult属于哪个分片的key
//            String key = profilingResult.getKey();
//            //取出ProfileShardResult给定的分片
//            ProfileShardResult value = profilingResult.getValue();
//        }
//
//        //所述ProfileShardResult对象本身包含一个或多个查询简档的结果
//        //一个用于抵靠底层Lucene索引执行的每个查询
//
//        //获取从QueryProfileShardResult获取list
//        List<QueryProfileShardResult> queryProfileShardResults =
//                profileShardResult.getQueryProfileResults();
//        //迭代每一个QuertProfileShardResult
//        for (QueryProfileShardResult queryProfileResult : queryProfileShardResults) {
//
//        }
//
//        //每个都QueryProfileShardResult提供对详细查询树执行的访问权限，作为ProfileResult对象列表返回 ：
//        //迭代配置文件结果
//        for (ProfileResult profileResult : queryProfileResult.getQueryResults()) {
//        //检索Lucene查询的名称
//            String queryName = profileResult.getQueryName();
//        //检索执行Lucene查询所花费的时间
//            long queryTimeInMillis = profileResult.getTime();
//        //检索子查询的配置文件结果（如果有）
//            List<ProfileResult> profiledChildren = profileResult.getProfiledChildren();
//        }
//
//
//        //Rest API文档包含有关分析查询的更多信息以及查询分析信息的描述。
//        //该QueryProfileShardResult还可以访问了Lucene的收藏家的分析信息：
//        CollectorResult collectorResult = queryProfileResult.getCollectorResult();//检索Lucene收集器的分析结果
//        String collectorName = collectorResult.getName();//检索Lucene收集器的名称
//        Long collectorTimeInMillis = collectorResult.getTime();//检索执行Lucene收集器所花费的时间
//        List<CollectorResult> profiledChildren = collectorResult.getProfiledChildren();//检索子收集器的配置文件结果（如果有）
//
//
//
//        //Rest API文档包含有关Lucene收集器的分析信息的更多信息。请参阅分析查询。
//        //以与查询树执行非常类似的方式，QueryProfileShardResult对象提供对详细聚合树执行的访问：
//
//        AggregationProfileShardResult aggsProfileResults =
//                profileShardResult.getAggregationProfileResults();
//        for (ProfileResult profileResult : aggsProfileResults.getProfileResults()) {
//            String aggName = profileResult.getQueryName();
//            long aggTimeInMillis = profileResult.getTime();
//            List<ProfileResult> profiledChildren = profileResult.getProfiledChildren();
//        }
//        //
//        //检索 AggregationProfileShardResult
//        //
//        //
//        //迭代聚合配置文件结果
//        //
//        //
//        //检索聚合的类型（对应于用于执行聚合的Java类）
//        //
//        //
//        //检索执行Lucene收集器所花费的时间
//        //
//        //
//        //检索子聚合的概要文件结果（如果有）




//        SearchSourceBuilder sourceBuilder = new SearchSourceBuilder();
//        //降序排序（默认）
//        sourceBuilder.sort(new ScoreSortBuilder().order(SortOrder.DESC));
//        //按ID字段升序排序
//        sourceBuilder.sort(new FieldSortBuilder("name").order(SortOrder.ASC));
//
//        TermsQueryBuilder termsQueryBuilder = QueryBuilders.termsQuery("", "");
//
//        BoolQueryBuilder boolQueryBuilder = QueryBuilders.boolQuery();





//        SearchRequest searchRequest = new SearchRequest();
//        SearchSourceBuilder sourceBuilder = new SearchSourceBuilder();
//        AbstractQueryBuilder queryBuilder = new ESConfigQuery(ESEnums.QueryType.TERM_QUERY, "name", "zhaoliu").configQuery(BuildQuery.class);
//
//        sourceBuilder.query(queryBuilder);
//
//        searchRequest.source(sourceBuilder);
//        SearchResponse searchResponse = client.search(searchRequest, RequestOptions.DEFAULT);
//
//        SearchHit[] hits = searchResponse.getHits().getHits();
//
//        for (SearchHit hit : hits) {
//            System.out.println("数据"+hit.getSourceAsMap());
//        }



        SearchRequest searchRequest = new SearchRequest();
        SearchSourceBuilder sourceBuilder = new SearchSourceBuilder();

        //按降序排序_score默认值
//        sourceBuilder.sort(new ScoreSortBuilder().order(SortOrder.ASC));
        //也按id字段升序排序
        sourceBuilder.sort(new FieldSortBuilder("id").order(SortOrder.DESC));
        searchRequest.source(sourceBuilder);

        SearchResponse searchResponse = client.search(searchRequest, RequestOptions.DEFAULT);

        SearchHit[] hits = searchResponse.getHits().getHits();

        for (SearchHit hit : hits) {
            System.out.println(hit.getSourceAsMap());
        }





        client.close();
    }
}
