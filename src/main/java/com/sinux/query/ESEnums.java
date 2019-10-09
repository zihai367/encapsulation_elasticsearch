package com.sinux.query;

/**
 * @author kevin
 */
public interface ESEnums {
    enum QueryType{
        MATCH_ALL_QUERY,
        MATCH_QUERY,
        TERM_QUERY,
        TERMS_QUERY,
        FUZZY_QUERY,
        RANGE_QUERY,
        BOOL_QUERY
    }
}
