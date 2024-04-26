package com.edavalos.mtx.util.db.query;

import java.util.HashMap;
import java.util.List;

public class MtxSelectQuery extends MtxQuery {
    private final String SELECT;
    private final String FROM;
    private final HashMap<String, String> INNER_JOIN;
    private final HashMap<String, String> OUTER_JOIN;
    private final List<String> WHERE;
    private final List<String> ORDERBY;
}
