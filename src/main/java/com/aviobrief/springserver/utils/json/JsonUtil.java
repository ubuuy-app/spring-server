package com.aviobrief.springserver.utils.json;


import javax.json.JsonObject;

public interface JsonUtil {

  String toJsonGson(String... props);

  JsonObject toJsonJavaxJson(String... props);

}
