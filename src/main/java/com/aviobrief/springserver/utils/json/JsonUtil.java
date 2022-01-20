package com.aviobrief.springserver.utils.json;


public interface JsonUtil {

  JsonString toJson(JsonPair... jsonPairs);

  JsonPair pair(String key, String value);

}
