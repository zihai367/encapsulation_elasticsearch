package com.sinux.query;

import org.elasticsearch.index.query.AbstractQueryBuilder;

import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;

/**
 * @author kevin
 * @date 2019-09-20 11:45
 */
public class ConfigBoolQuery {

    private Map<String,List<AbstractQueryBuilder>> queryBuildersMap = new HashMap<String,List<AbstractQueryBuilder>>();

    private List<AbstractQueryBuilder> mustList = new LinkedList<AbstractQueryBuilder>();
    private List<AbstractQueryBuilder> shouldList = new LinkedList<AbstractQueryBuilder>();
    private List<AbstractQueryBuilder> mustNotList = new LinkedList<AbstractQueryBuilder>();

    public void addMust(AbstractQueryBuilder... queryBuilder){
        for (AbstractQueryBuilder abstractQueryBuilder : queryBuilder) {
            mustList.add(abstractQueryBuilder);
        }
        queryBuildersMap.put("must",mustList);
    }
    public void addShould(AbstractQueryBuilder... queryBuilder){
        for (AbstractQueryBuilder abstractQueryBuilder : queryBuilder) {
            shouldList.add(abstractQueryBuilder);
        }
        queryBuildersMap.put("should",shouldList);
    }
    public void addMustNot(AbstractQueryBuilder... queryBuilder){
        for (AbstractQueryBuilder abstractQueryBuilder : queryBuilder) {
            mustNotList.add(abstractQueryBuilder);
        }
        queryBuildersMap.put("mustNot",mustNotList);
    }


    public Map<String, List<AbstractQueryBuilder>> getQueryBuildersMap() {
        return queryBuildersMap;
    }

}
