package com.sinux.query;

import com.sinux.tools.EnumNameChangeToMethodNameTool;
import org.elasticsearch.index.query.AbstractQueryBuilder;

import java.lang.reflect.Constructor;
import java.lang.reflect.Method;
import java.util.Collection;
import java.util.List;
import java.util.Map;

/**
 * @author kevin
 * @date 2019-09-20 09:04
 */
public class ESConfigQuery implements ESEnums{
    private QueryType queryType;
    private String                                      fieldName           = null;
    private Object                                      value               = null;
    private Object                                      from                = null;
    private Object                                      to                  = null;
    private Map<String,List<AbstractQueryBuilder>>      builders            = null;
    private Object[]                                    objects             = null;
    //MATCH_ALL_QUERY
    public ESConfigQuery(QueryType queryType){
        this.queryType  = queryType;
    }
    //MATCH_QUERY or TERM_QUERY or FUZZY_QUERY
    public ESConfigQuery(QueryType queryType,String fieldName,Object value){
        this.queryType  = queryType;
        this.fieldName  = fieldName;
        this.value      = value;
    }
    //RANGE_QUERY
    public ESConfigQuery(QueryType queryType,String fieldName,Object from,Object to){
        this.queryType  = queryType;
        this.fieldName  = fieldName;
        this.from       = from;
        this.to         = to;
    }
    //BOOL_QUERY
    public ESConfigQuery(QueryType queryType, Map<String, List<AbstractQueryBuilder>> builders){
        this.queryType  = queryType;
        this.builders   = builders;
    }
    //TERMS_QUERY
    public ESConfigQuery(QueryType queryType, String fieldName, Collection<?> values){
        this.queryType  = queryType;
        this.fieldName  = fieldName;
        this.value      = values;
    }
    //TERMS_QUERY
    public ESConfigQuery(QueryType queryType,String fieldName,Object... objects){
        this.fieldName      = fieldName;
        this.queryType      = queryType;
        this.objects        = objects;
    }



    public AbstractQueryBuilder configQuery(){



        //通过工具类把枚举名转化为方法名
        String methodName = EnumNameChangeToMethodNameTool.
                changeName(queryType.toString());
        Object queryBuilder = null;
        try {
            //传入BuilderQuery类
            Class<BuildQuery> cla = BuildQuery.class;
            //获取构造函数
            Constructor<?> constructor = cla.getDeclaredConstructor();
            //实例化构造函数
            Object o = constructor.newInstance();
            Method method = null;
            //判断queryType
            switch (queryType){
                case MATCH_ALL_QUERY:
                    method = cla.getMethod(methodName);
                    queryBuilder = method.invoke(o);
                    break;
                case MATCH_QUERY:
                    method = cla.getMethod(methodName,String.class,Object.class);
                    queryBuilder = method.invoke(o,fieldName,value);
                    break;
                case TERMS_QUERY:
                    method = cla.getMethod(methodName,String.class,Object[].class);
                    queryBuilder = method.invoke(o,fieldName,objects);
                    break;
                case TERM_QUERY:
                    method = cla.getMethod(methodName,String.class,Object.class);
                    queryBuilder = method.invoke(o,fieldName,value);
                    break;
                case RANGE_QUERY:
                    method = cla.getMethod(methodName,String.class,Object.class,Object.class);
                    queryBuilder = method.invoke(o,fieldName,from,to);
                    break;
                case FUZZY_QUERY:
                    method = cla.getMethod(methodName,String.class,Object.class);
                    queryBuilder = method.invoke(o,fieldName,value);
                    break;
                case BOOL_QUERY:
                    method = cla.getMethod(methodName,Map.class);
                    queryBuilder = method.invoke(o,builders);
                    break;

            }

        } catch (Exception e) {
            e.printStackTrace();
        }

        return (AbstractQueryBuilder) queryBuilder;
    }


    public ESEnums.QueryType getQueryType() {
        return queryType;
    }

    public void setQueryType(ESEnums.QueryType queryType) {
        this.queryType = queryType;
    }

    public String getFieldName() {
        return fieldName;
    }

    public void setFieldName(String fieldName) {
        this.fieldName = fieldName;
    }

    public Object getValue() {
        return value;
    }

    public void setValue(Object value) {
        this.value = value;
    }

    public Object getFrom() {
        return from;
    }

    public void setFrom(Object from) {
        this.from = from;
    }

    public Object getTo() {
        return to;
    }

    public void setTo(Object to) {
        this.to = to;
    }

    public Map<String, List<AbstractQueryBuilder>> getBuilders() {
        return builders;
    }

    public void setBuilders(Map<String, List<AbstractQueryBuilder>> builders) {
        this.builders = builders;
    }

    public Object[] getObjects() {
        return objects;
    }

    public void setObjects(Object[] objects) {
        this.objects = objects;
    }
}
