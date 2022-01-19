package com.aviobrief.springserver.utils.json;


import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.node.ObjectNode;

import java.util.Arrays;

import static com.aviobrief.springserver.utils.json.JsonString.JSON_STRING;

public class JsonUtilImpl implements JsonUtil {

    @Override
    public JsonString toJsonString(JsonPair... jsonPairs) {

        ObjectMapper mapper = new ObjectMapper();
        ObjectNode objectNode = mapper.createObjectNode();

        Arrays.stream(jsonPairs).forEach(pair -> {
            String key = pair.getKey();
            String value = pair.getValue();
            objectNode.put(key, value);
        });

        String on = objectNode.toString();


        return JSON_STRING.setValue(on);
    }

    @Override
    public JsonPair fromStringPair(String key, String value){
        JsonPair jsonPair = new JsonPair();
        jsonPair.setKey(key);
        jsonPair.setValue(value);
        return jsonPair;
    }
}


//    @Override
//    public String toJsonGson(String... props) {
//
//        Map<String, String> propsMap = Arrays
//                .stream(props)
//                .map(prop -> prop.split(":"))
//                .collect(Collectors.toMap(prop -> prop[0], prop -> prop[1]));
//
//
//        return gson.toJson(propsMap);
//    }

//    @Override
//    public String toJsonJackson(String... props) {
//
//        ObjectMapper mapper = new ObjectMapper();
//        ObjectNode objectNode = mapper.createObjectNode();
//
//        Arrays.stream(props).forEach(prop -> {
//            String[] values = prop.split(":");
//            objectNode.put(values[0], values[1]);
//        });
//
//
//        return objectNode.toString();
//    }


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

