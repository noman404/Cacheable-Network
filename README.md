# Cacheable-Network
A Cacheable Network Library For Android Application 

[![](https://jitpack.io/v/noman404/Cacheable-Network.svg)](https://jitpack.io/#noman404/Cacheable-Network)
[![Build Status](https://travis-ci.org/noman404/Cacheable-Network.svg?branch=master)](https://travis-ci.org/noman404/Cacheable-Network)

- in your top level *build.gradle* file add the **jitpack** dependency 
    
    `maven { url 'https://jitpack.io' }`

- in application level *build.gradle* add the *Cacheable-Network* dependency
    
    `implementation 'com.github.noman404:Cacheable-Network:1.0.2'`
	
	Supports both:
	- XML / JSON REST
	- [JSON-RPC](https://en.wikipedia.org/wiki/JSON-RPC)


The total scenario of network calling is to communicate with server and get back the result. So, for this two action using this library you need to instantiate `ApiAdapter` class to perform network call and invoke `NetworkResponse` interface to receive the results (to receive both error and success message)

To perform a network call use following methods as per you endpoint type after creating the `ApiAdapter` instance.

*(to use the cache, pass the last paramater `boolean useCache` as `true`, you'll get cached data if you have them erlier i.e. this module automatically store cache if you pass true to use later as cache, that means if you don't hit the endpoint with these method with `useCache = true`, you won't get cache data later.)*

- To make a json object request,


    `public void JsonObjectRequest(
	                                       int requestMethod,
                                           String endpoint,
                                           HashMap<String, String> header,
                                           HashMap<String, String> params,
                                           JSONObject jsonObjectParams,
                                           Object reference,
                                           boolean useCache);`

- To make a jsonArray request,


    `public void JsonArrayRequest(
	                                      int requestMethod,
                                          String endpoint,
                                          HashMap<String, String> header,
                                          HashMap<String, String> params,
                                          JSONArray jsonArrayParams,
                                          Object reference,
                                          boolean useCache);`
										  
- To make a Plain String request,


    `public void StringRequest(
	                                   int requestMethod,
                                       String endpoint,
                                       HashMap<String, String> header,
                                       HashMap<String, String> params,
                                       Object reference,
                                       boolean useCache);`
- To make a Form data request,


    `public void formDataRequest(String endpoint,
                                         HashMap<String, String> header,
                                         HashMap<String, String> params,
                                         Object reference,
                                         boolean useCache);`

- To make a Multipart request (specially for files),


   `public void MultipartFormDataRequest(String endpoint,
                                                  HashMap<String, String> header,
                                                  HashMap<String, String> params,
                                                  Object reference);`

To receive the response from server you can use the interface `NetworkResponse` as the way you want (as innerclass, method level, class level), Once you implement the class you'll get

- To receive the message whose status code 200. `String response` the server response, 
 `Object reference` is a optional reference object if you need. 
 

    `void onSuccessResponse(
            String response,
            Object reference);`

- To receive the error respones, if occours.


    `void onErrorResponse(
            String endpoint,
            String error,
            Object reference);`

- To receive the authentication error respones, if occours only for status code `401` & `403`.


    `void onAuthError(
            NetworkStatus networkStatus,
            Object reference);`


***Fork is welcome, but make sure you've unit test your code before make pull request.*
**
