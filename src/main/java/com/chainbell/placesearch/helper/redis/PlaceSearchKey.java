package com.chainbell.placesearch.helper.redis;

public class PlaceSearchKey {

    /**
     * TYPE : list
     * COMMAND : rpush, lpop
     * */
    public static final String keywordQueue = "place.search";

    /**
     * TYPE : sorted set
     * COMMAND : ZINCRBY, ZRANGE
     * */
    public static final String keywordRank = "place.keyword.rank";

}
