package com.aviobrief.springserver.utils.json;


public interface JsonUtil {

  JsonString toJsonString(JsonPair... jsonPairs);

  JsonPair fromStringPair(String key, String value);

}
