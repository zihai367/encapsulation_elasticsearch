#ElasticSearch
##前言
ElasticSearch的javaAPI官方一共提供了有三种：TransPortClient,LowLevelRestClient,HighLevelRestClient。
目前TransPortClient已经被废弃了，并且官方极力推荐HighLevelRestClient，本次的项目使用的就是HighLevenRestClient，目前论坛上相关资料还比较少，所以大部分参考的是ES官方文档。
##在Windows系统安装配置ElasticSearch
在官网下载ElasticSearch：https://www.elastic.co/cn/start

下载完后解压（ES需要在本机安装JDK，这里默认已安装），进入到目录文件夹config，编辑elasticsearch.yml

打开注释

    cluster.name
    network.host
    http.port

在末尾加上

    http.cors.enabled: true
    http.cors.allow-origin: "*"

然后使用cmd进入到解压包目录下，执行命令elasticsearch，即可开启es服务，在浏览器中访问localhost:9200能看到一串js数据就说明安装成功了
##Head插件的使用
安装Head插件需要nodejs和grunt
###Node.js
Node可以从官网下载http://nodejs.cn/，下载后进行安装即可，安装完成后打开cmd，使用node -v命令查询Node是否安装成功
###grunt
然后进行grunt的安装，cd到Node.js根目录下，运行命令npm install -g grunt-cli
###Head插件
在https://github.com/mobz/elasticsearch-head下载head插件的压缩包，并解压到本地，修改Gruntfile.js文件中加上一句hostname:'*'
	
    connect: {
        server: {
            options: {
                port: 9100,
                hostname:'*',
                base: '.',
                keepalive: true
            }
        }
    }
在elasticsearch-head-master目录下执行npm install命令，然后执行grunt server命令即可打开elasticsearch-head服务，通过浏览器访问localhost:9100端口，即可进入head页面
##项目结构
###parse包
parse包里面主要包含了对配置文件的解析
CenterConfig类里会扫描当前项目目录resource路径下的所有文件，如果是.xml,.properties,.json为后缀的文件，则会解析出properties和json文件中的所有键值对，xml文件中的所有标签名和内容放到Map集合中
###query包
里面包含三个类和一个枚举类
枚举类ESEnums:里面有QueryType用来列举要创建的QueryBuilder类型
BuildQuery:初始化并创建对应的QueryBuilder
ConfigBoolQuery:用来配置boolQuery
ESConfigQuery:用来选择要创建的QueryBuilder类型并且传入参数并调用BuildQuery类初始化并创建QueryBuilder
###tools包
tools包里只包含一个类：EnumNameChangeToMethodNameTool
用来将枚举名转换为方法名
###CreateESClient类
用来创建并初始化HighLevelRestClient
###ESSingleton类
这是一个单例类，用来存放配置文件的配置信息和持久化存放HighLevelRestClient
###ESTools类
此类的构造函数执行对配置文件的解析和对Client的初始化
并且封装了对ES的大部分操作

createIndex():创建新的索引

addDocument():添加文档

deleteById():通过ID删除文档

deleteByField():通过匹配的字段值来删除文档

updateById():通过ID修改文档

searchAndGetString():搜索并返回json字符串

searchAndGetMap():搜索并返回jsonMap数据

groupByTermsAndGetStats():通过Terms聚合搜索并且返回指定key的stats对象

groupByTermsAndGetStates():通过Terms聚合搜索并返回stats对象集合

groupByDateRangeAndGetStats():通过DateRange聚合搜索一个时间段，并返回Stats

groupByDateHistogramAndGetStates():通过DateHistogram聚合按每个时间段聚合搜索分段如年，月，日并返回时间段的Stats集合

reIndex():把一个索引迁移到另一个索引，迁移的包括Settings,Mappings和数据

searchByPage():分页查询

##使用

###首先
**首先在xml配置文件中配置主机、端口和scheme**
    
    <host>127.0.0.1</host>
    <port>9200</port>
    <scheme>http</scheme>
**实例化ESTools类**

    ESTools esTools = new ESTools();

###获取Client
**获取client，调用getClient()方法可以获取当前配置的RestHighLevelClient**

    RestHighLevelClient client = esTools.getClient();
    
###创建Index
**创建新的index**
    
    esTools.createIndex("index1");
    
###添加
**添加文档，给一个指定的Id添加文档**

    HashMap<String,Object> map = new HashMap<String,Object>();
            map.put("date","2018-10-01");
            map.put("age","20");
            esTools.addDocument("user","12",map);
###删除
**删除文档，通过Id删除的文档**
    
    esTools.deleteById("index1","10");
    
**删除文档，通过查询条件删除文档**

            //配置并生成QueryBuilder
            AbstractQueryBuilder queryBuilder = new ESConfigQuery(ESEnums.QueryType.TERM_QUERY, "name", "lisi").configQuery();
            //添加QueryBuilder到EsTools
            esTools.setQueryBuilders(queryBuilder);
            //删除
            esTools.deleteByQuery("index1");
###修改
**修改文档，通过Id修改整个文档**

    HashMap<String,Object> map = new HashMap<String,Object>();
            map.put("date","2018-10-01");
            map.put("age","20");
            esTools.updateById("index1","",map);

**修改文档中的字段，通过查询条件来修改某个文档的字段值**

        //配置并生成QueryBuilder
        AbstractQueryBuilder queryBuilder = new ESConfigQuery(ESEnums.QueryType.TERM_QUERY, "name", "lisi").configQuery();
        //添加QueryBuilder到EsTools
        esTools.setQueryBuilders(queryBuilder);
        //修改
        esTools.updateByQuery("age","10","index1");
###查询
**查询，查询整个Index并返回JsonMap数据**

    List<Map<String, Object>> maps = esTools.searchAndGetMap("index1");
    
**查询，添加查询条件和升序排列查询查询并返回JsonMap数据**

            esTools.setSortBuilder(new FieldSortBuilder("age").order(SortOrder.ASC));
            //配置QueryBuilder
            ESConfigQuery esConfigQuery = new ESConfigQuery(ESEnums.QueryType.TERM_QUERY,"name","lisi");
            //获取QueryBuilder
            AbstractQueryBuilder queryBuilder = esConfigQuery.configQuery();
            esTools.setQueryBuilders(queryBuilder);
            List<Map<String, Object>> user = esTools.searchAndGetMap("user");
            
**查询，分页查询(每页大小，第x页，查询索引)**

    esTools.searchByPage(10,1,"index1");
###聚合查询
**聚合查询，以name字段为聚合点，计算age字段，并返回所有的桶的计算值(stats)**

        Map<String, Stats> statsMap = esTools.groupByTermsAndGetStates("name", "age", "user");
        Stats stats = statsMap.get("list");
        System.out.println(stats.getAvg());
    
**聚合查询，以name字段为聚合点，计算age字段，并返回指定桶lisi的计算值()**

        Stats stats1 = esTools.groupByTermsAndGetStats("name", "age", "lisi", "user");
                System.out.println(stats1.getAvg());

**时间范围聚合查询，以某个时间段为聚合，并计算age字段，返回age字段的计算值**

            Stats stats = esTools.groupByDateRangeAndGetStats("2000-01-01", "2020-01-01", "date", "age", "user");
            System.out.println(stats.getAvg());

**时间分段聚合查询，把所有文档的date字段以日历上的月为阶段划分到每一个桶里，并计算桶里的age字段的值，并返回每个月和计算值的map**

        Map<Object, Stats> statsMap = esTools.groupByDateHistogramAndGetStates("date", "age", DateHistogramInterval.MONTH, "user");
        for (Object o : statsMap.keySet()) {
            System.out.println(o);
            Stats stats = statsMap.get(o);
            System.out.println(stats.getCount());
        }
###数据迁移，Reindex
**数据迁移Reindex，将index1里的mappings,settings,和数据拷贝迁移到index2里**

    esTools.reIndex("index1","index2");


    
###配置QueryBuilder
**MatchAllQueryBuilder(查询所有)**
    
            ESConfigQuery esConfigQuery = new ESConfigQuery(ESEnums.QueryType.MATCH_ALL_QUERY);
            AbstractQueryBuilder queryBuilder = esConfigQuery.configQuery();
            
**MatchQueryBuilder(对搜索内容进行分词)**

            ESConfigQuery esConfigQuery = new ESConfigQuery(ESEnums.QueryType.MATCH_QUERY,"name","Max Smith");
            AbstractQueryBuilder queryBuilder = esConfigQuery.configQuery();
            
**TERM_QUERY(精准查询)**

            ESConfigQuery esConfigQuery = new ESConfigQuery(ESEnums.QueryType.TERM_QUERY,"name","lisi");
            AbstractQueryBuilder queryBuilder = esConfigQuery.configQuery();    
    
**TERMS_QUERY(精确查询多个匹配值)**
    
    ESConfigQuery esConfigQuery = new ESConfigQuery(ESEnums.QueryType.TERMS_QUERY,"name","lisi","wangwu","zhaoliu");
    
    
**RANGE_QUERY(范围查询)**
    
            ESConfigQuery esConfigQuery = new ESConfigQuery(ESEnums.QueryType.TERM_QUERY,"age","10","20");
            AbstractQueryBuilder queryBuilder = esConfigQuery.configQuery();
            
**FUZZY_QUERY(模糊查询,容错查询)**

    ESConfigQuery esConfigQuery = new ESConfigQuery(ESEnums.QueryType.FUZZY_QUERY,"name","lis");


**BOOL_QUERY(多重条件查询)**

            //配置并获取TERM_QUERY
            AbstractQueryBuilder termQuery = new ESConfigQuery(ESEnums.QueryType.TERM_QUERY, "name", "lisi").configQuery();
            //配置并获取MATCH_ALL_QUERY
            AbstractQueryBuilder matchAllQuery = new ESConfigQuery(ESEnums.QueryType.MATCH_ALL_QUERY).configQuery();
    
            ConfigBoolQuery configBoolQuery = new ConfigBoolQuery();
            configBoolQuery.addMust(matchAllQuery);
            configBoolQuery.addMustNot(termQuery);
    
            //配置并获取BOOL_QUERY
            AbstractQueryBuilder boolQuery = new ESConfigQuery(ESEnums.QueryType.BOOL_QUERY, configBoolQuery.getQueryBuildersMap()).configQuery();
            //将boolQuery放入esTool里
            esTools.setQueryBuilders(boolQuery);
            //执行查询
            List<Map<String, Object>> user = esTools.searchAndGetMap("user");

##缺陷及改进
目前ESTools里的方法每调一次都需要重新创建一个ESTools对象，不然会存在多线程安全问题，后期会采用加锁或其他更优方式进行改进

聚合查询也只包含term,dateRange,dateHistogram,以及state计算聚合四种，在将来了解和学习了更多的聚合知识后会进行一个聚合的封装




