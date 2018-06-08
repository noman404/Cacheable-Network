package com.example.user.sample;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;

import com.android.volley.Request;

import org.json.JSONException;

import al.com.cacheable.network.adapter.ApiAdapter;
import al.com.cacheable.network.enums.NetworkStatus;
import al.com.cacheable.network.listener.NetworkResponse;


public class MainActivity extends AppCompatActivity implements NetworkResponse {

    private ApiAdapter apiAdapter;
    private final String SAMPLE_ENDPOINT = "https://jsonplaceholder.typicode.com/posts/1";

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

        apiAdapter.jsonObjectRequest(
                Request.Method.GET,
                SAMPLE_ENDPOINT,
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
        Log.d(getClass().getName(), error);
    }

    @Override
    public void onAuthError(NetworkStatus networkStatus, Object reference) {
        Log.d(getClass().getName(), networkStatus.name());
    }
}
