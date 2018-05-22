package al.com.cacheable.network.data;

/**
 * Created by Al Noman on 8/17/2017.
 */

interface DBKeys {

    int DB_VER = 1;

    String DB_NAME = "network.cache.db";

    String TABLE_CACHE = "cache_data_tbl";

    String ID = "_id";
    String CACHED_DATA = "cached_data";
    String URL = "url";
    String REQUEST_PARAM = "req_param";
    String CREATED_AT = "created_at";

}
