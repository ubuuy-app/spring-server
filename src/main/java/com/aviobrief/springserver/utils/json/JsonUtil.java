package com.aviobrief.springserver.utils.json;


import org.json.JSONObject;

public interface JsonUtil {

  JSONObject toJsonJackson(String... props);

  String toJsonGson(String... props);

//  JsonObject toJsonJavaxJson(String... props);

//  JsonObject toJsonJavaxJson(String... props);

}
