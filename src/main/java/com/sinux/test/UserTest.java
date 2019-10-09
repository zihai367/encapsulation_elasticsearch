package com.sinux.test;

import com.sinux.ESTools;
import com.sinux.query.ConfigBoolQuery;
import com.sinux.query.ESConfigQuery;
import com.sinux.query.ESEnums;
import org.elasticsearch.index.query.AbstractQueryBuilder;

import java.io.IOException;
import java.util.List;
import java.util.Map;

/**
 * @author kevin
 * @date 2019-09-10 16:04
 */
public class UserTest {
    public static void main(String[] args) throws IOException {
        ESTools esTools = new ESTools();


//        HashMap<String,Object> map = new HashMap<String,Object>();
//        map.put("date","2018-10-01");
//        map.put("age","20");
//        esTools.addDocument("user","12",map);


//        查询


        //配置排序
//        esTools.setSortBuilder(new FieldSortBuilder("age").order(SortOrder.ASC));
//        esTools.setQueryBuilders(queryBuilder);

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

        System.out.println(user);







//        普通聚合查询分别返回stats 和stats集合

        //返回集合
//        Map<String, Stats> statsMap = esTools.groupByTermsAndGetStates("name", "age", "user");
//        Stats stats = statsMap.get("lisi");
//        System.out.println(stats.getAvg());

//        返回states
//        Stats stats1 = esTools.groupByTermsAndGetStats("name", "age", "lisi", "user");
//        System.out.println(stats1.getAvg());

//        时间范围聚合查询
//        Stats stats = esTools.groupByDateRangeAndGetStats("2000-01-01", "2020-01-01", "date", "age", "user");
//        System.out.println(stats.getAvg());

//        时间分段聚合查询
//        Map<Object, Stats> statsMap = esTools.groupByDateHistogramAndGetStates("date", "age", DateHistogramInterval.MONTH, "user");
//        for (Object o : statsMap.keySet()) {
//            System.out.println(o);
//            Stats stats = statsMap.get(o);
//            System.out.println(stats.getCount());
//        }
//        esTools.searchByPage(10,1,"index1");


        //通过fieldName聚合查询
//        Stats stats = esTools.groupByTermsAndGetStats("name", "age", "lisi", "user");
//        System.out.println(stats.getAvg());








//
//        ConfigBoolQuery configBoolQuery = new ConfigBoolQuery();
//
//        AbstractQueryBuilder queryBuilder = new ESConfigQuery(ESEnums.QueryType.MATCH_ALL_QUERY).configQuery();
//        configBoolQuery.addMust(queryBuilder);
////        configBoolQuery.addMustNot();
////        configBoolQuery.addShould();
//        Map<String, List<AbstractQueryBuilder>> queryBuildersMap = configBoolQuery.getQueryBuildersMap();
//
//        AbstractQueryBuilder queryBuilder1 = new ESConfigQuery(ESEnums.QueryType.BOOL_QUERY, queryBuildersMap).configQuery();
//
//        //配置QueryBuilder
//
//        //获取QueryBuilder
//        esTools.setQueryBuilders(queryBuilder1);
//        List<Map<String, Object>> user1 = esTools.searchAndGetMap("user");
//        System.out.println(user1);



    }
}
