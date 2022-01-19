package com.aviobrief.springserver.utils.json;


import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.node.ObjectNode;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import java.util.Arrays;
import java.util.Map;
import java.util.stream.Collectors;


public class JsonUtilImpl implements JsonUtil {
    private final Gson gson = new GsonBuilder().create();

    @Override
    public String toJsonJackson(String... props) {

        ObjectMapper mapper = new ObjectMapper();
        ObjectNode objectNode = mapper.createObjectNode();

        Arrays.stream(props).forEach(prop -> {
            String[] values = prop.split(":");
            objectNode.put(values[0], values[1]);
        });


        return objectNode.toString();
    }

    @Override
    public String toJsonGson(String... props) {

        Map<String, String> propsMap = Arrays
                .stream(props)
                .map(prop -> prop.split(":"))
                .collect(Collectors.toMap(prop -> prop[0], prop -> prop[1]));


        return gson.toJson(propsMap);
    }




//    @Override
//    public JsonObject toJsonJavaxJson(String... props) {
//        JsonObjectBuilder objectBuilder = Json.createObjectBuilder();
//
//        Arrays.stream(props).forEach(prop -> {
//            String[] values = prop.split(":");
//            objectBuilder.add(values[0], values[1]);
//        });
//
//        JsonObject jsonObject = objectBuilder.build();
//
////        Writer writer = new StringWriter();
////        Json.createWriter(writer).write(jsonObject);
////        String jsonString = writer.toString();
//
//        return jsonObject;
//    }

}

