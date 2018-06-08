package al.com.cacheable.network.adapter;

import android.content.Context;

import com.android.volley.RequestQueue;

import org.json.JSONArray;
import org.json.JSONObject;

import java.util.HashMap;

import al.com.cacheable.network.data.DBAdapter;
import al.com.cacheable.network.listener.NetworkResponse;
import al.com.cacheable.network.network.VolleySingleton;


/**
 * Created by Al Noman on 10/18/2016.
 */

public abstract class BaseApiAdapter {

    final RequestQueue requestQueue;
    final NetworkResponse networkResponse;
    final DBAdapter db;

    final int APP_SOCKET_TIMEOUT_MS = 15 * 1000;
    final int DEFAULT_MAX_RETRIES = 2;

    BaseApiAdapter(Context context, NetworkResponse networkResponse) {
        requestQueue = VolleySingleton.getInstance(context).getRequestQueue();
        db = DBAdapter.getInstance(context);

        this.networkResponse = networkResponse;
    }

    public abstract void jsonObjectRequest(int requestMethod,
                                           String endpoint,
                                           HashMap<String, String> header,
                                           HashMap<String, String> params,
                                           JSONObject jsonObjectParams,
                                           Object reference,
                                           boolean useCache);

    public abstract void jsonArrayRequest(int requestMethod,
                                          String endpoint,
                                          HashMap<String, String> header,
                                          HashMap<String, String> params,
                                          JSONArray jsonArrayParams,
                                          Object reference,
                                          boolean useCache);

    public abstract void stringRequest(int requestMethod,
                                       String endpoint,
                                       HashMap<String, String> header,
                                       HashMap<String, String> params,
                                       Object reference,
                                       boolean useCache);

    public abstract void formDataRequest(String endpoint,
                                         HashMap<String, String> header,
                                         HashMap<String, String> params,
                                         Object reference,
                                         boolean useCache);

    public abstract void multipartFormDataRequest(String endpoint,
                                                  HashMap<String, String> header,
                                                  HashMap<String, String> params,
                                                  Object reference);

}
