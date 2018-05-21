package com.example.user.sample;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;
import java.util.Map;

import al.com.cacheable.network.adapter.ApiAdapter;
import al.com.cacheable.network.enums.NetworkStatus;
import al.com.cacheable.network.listener.NetworkResponse;

public class MainActivity extends AppCompatActivity implements NetworkResponse {

    private ApiAdapter apiAdapter;
    private final String sampleEndpoint = "https://jsonplaceholder.typicode.com/posts/1";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        apiAdapter = new ApiAdapter(this, this);
    }


    public void onCLick(View view) throws Exception {
        doRequest();
    }


    void doRequest() throws JSONException {

        apiAdapter.JsonObjectRequest(
                Request.Method.GET,
                sampleEndpoint,
                null,
                null,
                null,
                null,
                true);
    }

    @Override
    public void onSuccessResponse(String response, Object reference) {
        Log.d(getClass().getName(), response);
    }

    @Override
    public void onErrorResponse(String url, String error, Object reference) {

    }

    @Override
    public void onAuthError(NetworkStatus networkStatus, Object reference) {

    }
}