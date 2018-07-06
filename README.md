# Cacheable-Network
A Cacheable Network Library For Android Application 

[![](https://jitpack.io/v/noman404/Cacheable-Network.svg)](https://jitpack.io/#noman404/Cacheable-Network)
[![Build Status](https://travis-ci.org/noman404/Cacheable-Network.svg?branch=master)](https://travis-ci.org/noman404/Cacheable-Network)
[![API](https://img.shields.io/badge/API-17%2B-brightgreen.svg?style=flat)](https://android-arsenal.com/api?level=17)
[![Android Arsenal](https://img.shields.io/badge/Android%20Arsenal-Cacheable--Network-brightgreen.svg?style=flat)](https://android-arsenal.com/details/1/7016)

## **Intro**

The total scenario of A network calling is to communicate with server then get back the result. So, to handle this two action using this library you need to instantiate `ApiAdapter` class to perform A network call and invoke `NetworkResponse` interface to receive the results (to receive both error and success message)
	
- Supports both (Standard):
	- XML / JSON RESTful Service
	- [JSON-RPC Service](https://en.wikipedia.org/wiki/JSON-RPC)

## **Usage**

- in your top level *build.gradle* file add the **jitpack** dependency 
    
    `maven { url 'https://jitpack.io' }`

- in application level *build.gradle* add the *Cacheable-Network* dependency
    
    `implementation 'com.github.noman404:Cacheable-Network:1.0.2'`

## **Make A Network Call**

To perform a network call use following methods as per you endpoint type after creating the `ApiAdapter` instance.

- **To make a json object request,**


    `public void jsonObjectRequest(
	                                   int requestMethod,
                                           String endpoint,
                                           HashMap<String, String> header,
                                           HashMap<String, String> params,
                                           JSONObject jsonObjectParams,
                                           Object reference,
                                           boolean useCache);`

- **To make a jsonArray request,**


    `public void jsonArrayRequest(
	                                  int requestMethod,
                                          String endpoint,
                                          HashMap<String, String> header,
                                          HashMap<String, String> params,
                                          JSONArray jsonArrayParams,
                                          Object reference,
                                          boolean useCache);`
										  
- **To make a Plain String request,**


    `public void stringRequest(
	                               int requestMethod,
                                       String endpoint,
                                       HashMap<String, String> header,
                                       HashMap<String, String> params,
                                       Object reference,
                                       boolean useCache);`
- **To make a Form data request,**


    `public void formDataRequest(String endpoint,
                                         HashMap<String, String> header,
                                         HashMap<String, String> params,
                                         Object reference,
                                         boolean useCache);`

- **To make a Multipart request (specially for files),**


   `public void multipartFormDataRequest(String endpoint,
                                                  HashMap<String, String> header,
                                                  HashMap<String, String> params,
                                                  Object reference);`


## **To Receive The Response**

To receive the response from server you can use the interface `NetworkResponse` as the way you want (as innerclass, method level, class level), Once you implement the class you'll get

- **To receive the message whose status code is 200. (`String response` the server response, 
 `Object reference` is a optional reference object if you need.)** 
 

    `void onSuccessResponse(
            String response,
            Object reference);`

- **To receive the error respones, if occours.**


    `void onErrorResponse(
            String endpoint,
            String error,
            Object reference);`

- **To receive the authentication error respones, if occours only for status code `401` & `403`.**


    `void onAuthError(
            NetworkStatus networkStatus,
            Object reference);`

## **Cache**

To use the cache, pass the last paramater `boolean useCache` as `true` in every network call. You'll get cached data if you have stored them erlier, meaning to say, this module automatically store response as cache if you pass `useCache = true`. So, if you don't hit the endpoint with these method with `useCache = true`, you won't get cache data later.

![Sample](https://github.com/noman404/Cacheable-Network/blob/master/graphics/uml.png?raw=true)
