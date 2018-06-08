package al.com.cacheable.network.adapter;

import android.content.Context;

import com.android.volley.AuthFailureError;
import com.android.volley.DefaultRetryPolicy;
import com.android.volley.NoConnectionError;
import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.TimeoutError;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;

import org.json.JSONArray;
import org.json.JSONObject;

import java.util.HashMap;
import java.util.Map;

import al.com.cacheable.network.enums.NetworkStatus;
import al.com.cacheable.network.listener.NetworkResponse;
import al.com.cacheable.network.request.JSONRequest;
import al.com.cacheable.network.request.MultipartFileRequest;
import al.com.cacheable.network.util.Datetime.DateTime;
import al.com.cacheable.network.util.connectivity.Connectivity;

/**
 * Created by User on 10/18/2016.
 */

public class ApiAdapter extends BaseApiAdapter {

    private Context context;

    public ApiAdapter(Context context, NetworkResponse networkResponse) {
        super(context, networkResponse);
        this.context = context;
    }

    @Override
    public void jsonObjectRequest(
            int requestMethod,
            String endpoint,
            final HashMap<String, String> header,
            final HashMap<String, String> params,
            JSONObject jsonObjectParams,
            Object reference,
            boolean useCache) {

        if (Connectivity.isNetworkAvailable(context)) {

            JSONRequest jsonRequest = new JSONRequest(
                    requestMethod,
                    endpoint,
                    jsonObjectParams,
                    new JsonResponseListener(reference, endpoint, useCache, jsonObjectParams == null ? null : jsonObjectParams.toString()),
                    new ErrorResponse(endpoint, reference)) {

                @Override
                public Map<String, String> getHeaders() throws AuthFailureError {
                    return header == null ? new HashMap<String, String>() : header;
                }

                @Override
                protected Map<String, String> getParams() throws AuthFailureError {
                    return params == null ? new HashMap<String, String>() : params;
                }

            };

            jsonRequest.setRetryPolicy(new DefaultRetryPolicy(
                    APP_SOCKET_TIMEOUT_MS,
                    DEFAULT_MAX_RETRIES,
                    DefaultRetryPolicy.DEFAULT_BACKOFF_MULT));

            requestQueue.add(jsonRequest);

        } else {
            cachedResponse(useCache, endpoint, jsonObjectParams, reference);
        }
    }

    @Override
    public void jsonArrayRequest(
            int requestMethod,
            String endpoint,
            final HashMap<String, String> header,
            final HashMap<String, String> params,
            JSONArray jsonArrayParams,
            Object reference,
            boolean useCache) {

        if (Connectivity.isNetworkAvailable(context)) {

            JSONRequest jsonRequest = new JSONRequest(
                    requestMethod,
                    endpoint,
                    jsonArrayParams,
                    new JsonResponseListener(reference, endpoint, useCache, jsonArrayParams == null ? null : jsonArrayParams.toString()),
                    new ErrorResponse(endpoint, reference)) {


                @Override
                public Map<String, String> getHeaders() throws AuthFailureError {
                    return header == null ? new HashMap<String, String>() : header;
                }

                @Override
                protected Map<String, String> getParams() throws AuthFailureError {
                    return params == null ? new HashMap<String, String>() : params;
                }

            };

            jsonRequest.setRetryPolicy(new DefaultRetryPolicy(
                    APP_SOCKET_TIMEOUT_MS,
                    DEFAULT_MAX_RETRIES,
                    DefaultRetryPolicy.DEFAULT_BACKOFF_MULT));

            requestQueue.add(jsonRequest);

        } else {
            cachedResponse(useCache, endpoint, jsonArrayParams, reference);
        }

    }

    @Override
    public void stringRequest(int requestMethod,
                              String endpoint,
                              final HashMap<String, String> header,
                              final HashMap<String, String> params,
                              Object reference,
                              boolean useCache) {

        if (Connectivity.isNetworkAvailable(context)) {

            StringRequest jsonRequest = new StringRequest(
                    Request.Method.POST,
                    endpoint,
                    new StringResponseListener(reference, endpoint, useCache, params == null ? null : params.toString()),
                    new ErrorResponse(endpoint, reference)) {

                @Override
                public Map<String, String> getHeaders() throws AuthFailureError {
                    return header == null ? new HashMap<String, String>() : header;
                }

                @Override
                protected Map<String, String> getParams() throws AuthFailureError {
                    return params == null ? new HashMap<String, String>() : params;
                }
            };

            jsonRequest.setRetryPolicy(new DefaultRetryPolicy(
                    APP_SOCKET_TIMEOUT_MS,
                    DEFAULT_MAX_RETRIES,
                    DefaultRetryPolicy.DEFAULT_BACKOFF_MULT));

            requestQueue.add(jsonRequest);

        } else {
            cachedResponse(useCache, endpoint, params, reference);
        }
    }

    @Override
    public void formDataRequest(
            String endpoint,
            final HashMap<String, String> header,
            final HashMap<String, String> params,
            Object reference,
            boolean useCache) {

        if (Connectivity.isNetworkAvailable(context)) {

            StringRequest jsonRequest = new StringRequest(
                    Request.Method.POST,
                    endpoint,
                    new StringResponseListener(reference, endpoint, useCache, params == null ? null : params.toString()),
                    new ErrorResponse(endpoint, reference)) {

                @Override
                public Map<String, String> getHeaders() throws AuthFailureError {
                    return header == null ? new HashMap<String, String>() : header;
                }

                @Override
                protected Map<String, String> getParams() throws AuthFailureError {
                    return params == null ? new HashMap<String, String>() : params;
                }
            };

            jsonRequest.setRetryPolicy(new DefaultRetryPolicy(
                    APP_SOCKET_TIMEOUT_MS,
                    DEFAULT_MAX_RETRIES,
                    DefaultRetryPolicy.DEFAULT_BACKOFF_MULT));

            requestQueue.add(jsonRequest);

        } else {
            cachedResponse(useCache, endpoint, params, reference);
        }
    }

    @Override
    public void multipartFormDataRequest(
            String endpoint,
            final HashMap<String, String> header,
            final HashMap<String, String> params,
            Object reference) {

        if (Connectivity.isNetworkAvailable(context)) {

            MultipartFileRequest multipartFileRequest = new MultipartFileRequest(
                    endpoint,
                    new FileResponseListener(reference),
                    new ErrorResponse(endpoint, reference)) {

                @Override
                public Map<String, String> getHeaders() throws AuthFailureError {
                    return header == null ? new HashMap<String, String>() : header;
                }

                @Override
                protected Map<String, String> getParams() throws AuthFailureError {
                    return params == null ? new HashMap<String, String>() : params;
                }

            };

            multipartFileRequest.setRetryPolicy(new DefaultRetryPolicy(
                    APP_SOCKET_TIMEOUT_MS,
                    DEFAULT_MAX_RETRIES,
                    DefaultRetryPolicy.DEFAULT_BACKOFF_MULT));

            requestQueue.add(multipartFileRequest);

        } else {
            new ErrorResponse(endpoint, reference).onErrorResponse(new NoConnectionError());
        }

    }

    private void cachedResponse(boolean useCache, String endpoint, Object reqParams, Object reference) {

        if (useCache) {
            if (db.isCacheAvailable(endpoint)) {
                try {
                    new JsonResponseListener(reference, endpoint, true, reqParams == null ? null : reqParams.toString()).onResponse(new JSONObject(db.getCachedDataByUrl(endpoint)));

                } catch (Exception e) {
                    new ErrorResponse(endpoint, reference);
                }
            } else {
                new JsonResponseListener(reference, endpoint, true, reqParams == null ? null : reqParams.toString()).onResponse(new JSONObject());
            }
        } else {
            new ErrorResponse(endpoint, reference).onErrorResponse(new NoConnectionError());
        }
    }

    private class FileResponseListener implements Response.Listener<com.android.volley.NetworkResponse> {

        private Object reference;

        FileResponseListener(Object reference) {
            this.reference = reference;
        }

        @Override
        public void onResponse(com.android.volley.NetworkResponse response) {
            networkResponse.onSuccessResponse(new String(response.data), reference);
        }
    }

    private class JsonResponseListener implements Response.Listener<JSONObject> {

        private boolean useCache;
        private String endpoint;
        private String reqParams;
        private Object reference;

        JsonResponseListener(Object reference, String endpoint, boolean useCache, String reqParams) {

            this.reference = reference;
            this.endpoint = endpoint;
            this.useCache = useCache;
            this.reqParams = reqParams;
        }

        @Override
        public void onResponse(JSONObject response) {

            networkResponse.onSuccessResponse(response.toString(), reference);

            if (useCache) {

                if (db.isCacheAvailable(endpoint))
                    db.updateCacheByUrl(endpoint, response.toString(), reqParams);
                else
                    db.insertIntoCachedData(
                            endpoint,
                            response.toString(),
                            reqParams,
                            DateTime.getNow());
            }
        }

    }

    private class StringResponseListener implements Response.Listener<String> {

        private Object reference;
        private String endpoint;
        private boolean useCache;
        private String reqParams;

        StringResponseListener(Object reference, String endpoint, boolean useCache, String reqParams) {

            this.reference = reference;
            this.endpoint = endpoint;
            this.useCache = useCache;
            this.reqParams = reqParams;
        }

        @Override
        public void onResponse(String response) {

            networkResponse.onSuccessResponse(response.toString(), reference);

            if (useCache) {
                if (db.isCacheAvailable(endpoint))
                    db.updateCacheByUrl(endpoint, response.toString(), reqParams);
                else
                    db.insertIntoCachedData(
                            endpoint,
                            response.toString(),
                            reqParams,
                            DateTime.getNow());
            }
        }
    }

    private class ErrorResponse implements Response.ErrorListener {

        private String endpoint;
        private Object reference;

        ErrorResponse(String endpoint, Object reference) {
            this.endpoint = endpoint;
            this.reference = reference;
        }

        @Override
        public void onErrorResponse(VolleyError error) {

            if (null == error.networkResponse) {
                if (error instanceof TimeoutError) {
                    networkResponse.onErrorResponse(endpoint, "Request has Timeout", reference);
                } else if (error instanceof NoConnectionError) {
                    networkResponse.onErrorResponse(endpoint, "Failed to Connect Server..! Internet Problem", reference);
                }
            } else {
                if (error.networkResponse.statusCode == 404) {
                    networkResponse.onErrorResponse(endpoint, "Not Found", reference);
                } else if (error.networkResponse.statusCode == 401) {
                    networkResponse.onAuthError(NetworkStatus.UNAUTHORIZED, reference);
                } else if (error.networkResponse.statusCode == 403) {
                    networkResponse.onAuthError(NetworkStatus.FORBIDDEN, reference);
                } else if (error.networkResponse.statusCode == 400) {
                    networkResponse.onErrorResponse(endpoint, "Check your Inputs", reference);
                } else if (error.networkResponse.statusCode == 500) {
                    networkResponse.onErrorResponse(endpoint, "Internal Server Error", reference);
                } else {
                    networkResponse.onErrorResponse(endpoint, "Unknown Error Status Code: " + error.networkResponse.statusCode, reference);
                }

            }
        }
    }
}
