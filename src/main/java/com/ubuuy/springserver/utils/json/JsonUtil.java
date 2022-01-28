package com.ubuuy.springserver.utils.json;


public interface JsonUtil {

  JsonString toJson(JsonPair... jsonPairs);

  JsonPair pair(String key, String value);

}
