package com.sinux.query;

import org.elasticsearch.index.query.*;

import java.util.Collection;
import java.util.List;
import java.util.Map;

/**
 * @author kevin
 * @date 2019-09-19 17:40
 */
public class BuildQuery {


    public MatchAllQueryBuilder matchAllQuery(){
         return QueryBuilders.matchAllQuery();
    }
    public MatchQueryBuilder matchQuery(String fieldName, Object value){
        return QueryBuilders.matchQuery(fieldName,value);
    }
    public BoolQueryBuilder boolQuery(Map<String,List<AbstractQueryBuilder>> builders){

        BoolQueryBuilder boolQueryBuilder = QueryBuilders.boolQuery();

        List<AbstractQueryBuilder> mustList     = builders.get("must");
        List<AbstractQueryBuilder> shouldList   = builders.get("should");
        List<AbstractQueryBuilder> mustNotList  = builders.get("mustNot");
        if (mustList != null && mustList.size() >0){
            for (AbstractQueryBuilder queryBuilder : mustList) {
                boolQueryBuilder.must(queryBuilder);
            }
        }
        if (shouldList != null && shouldList.size() >0){
            for (AbstractQueryBuilder queryBuilder : shouldList) {
                boolQueryBuilder.should(queryBuilder);
            }
        }
        if (mustNotList != null && mustNotList.size() >0){
            for (AbstractQueryBuilder queryBuilder : mustNotList) {
                boolQueryBuilder.mustNot(queryBuilder);
            }
        }

        return boolQueryBuilder;

    }
    public TermQueryBuilder termQuery(String fieldName,Object value){
        return QueryBuilders.termQuery(fieldName,value);
    }
    public TermsQueryBuilder termsQuery(String fieldName,Object objects){
        TermsQueryBuilder termsQueryBuilder = null;
        if (objects instanceof Collection<?>){
            termsQueryBuilder = QueryBuilders.termsQuery(fieldName,(Collection<?>)objects);
        }else {
            termsQueryBuilder = QueryBuilders.termsQuery(fieldName,objects);
        }
        return termsQueryBuilder;
    }
    public FuzzyQueryBuilder fuzzyQuery(String fieldName,Object value){
        return QueryBuilders.fuzzyQuery(fieldName,value);
    }

    /**
     * 生成范围查找
     * @param fieldName         字段名
     * @param from              从哪来(null为无下限，做人除外)
     * @param to                到哪去(null为无上限)
     * @return                  返回范围查找对象
     */
    public RangeQueryBuilder rangeQuery(String fieldName,Object from,Object to){
        return  QueryBuilders.rangeQuery(fieldName).from(from).to(to);
    }

}
