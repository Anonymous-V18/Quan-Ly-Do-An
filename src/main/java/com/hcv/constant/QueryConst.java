package com.hcv.constant;

public class QueryConst {

    public static final String LIKE_FORMAT = "%%%s%%";
    public static final String SEARCH_OPERATION = "(\\w+.?)(:|>|<|!|~|')(.+)";
    public static final String SORT_OPERATION = "(\\w+.?)(:)(.+)";

    private QueryConst() {
        throw new UnsupportedOperationException("Cannot be instantiated !");
    }

}
