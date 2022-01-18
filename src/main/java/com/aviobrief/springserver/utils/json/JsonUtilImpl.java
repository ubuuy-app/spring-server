package com.aviobrief.springserver.utils.json;


import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import org.json.JSONObject;

import java.util.Arrays;


public class JsonUtilImpl implements JsonUtil {
    private final Gson gson = new GsonBuilder().create();

    @Override
    public String toJson(String... props) {

        JSONObject jsonObject = new JSONObject();
        Arrays.stream(props).forEach(prop -> {
            String[] values = prop.split(":");
            jsonObject.put(values[0], values[1]);
        });

        return jsonObject.toString();
    }
}

/* different problems with this implementations */
//        Map<String, Object> propsMap =
//                props.stream()
//                        .map(prop -> prop.split(":"))
//                        .collect(Collectors.toMap(prop -> prop[0], prop -> prop[1]));

//        JsonObjectBuilder builder = Json.createObjectBuilder();
//        props.forEach(prop-> {
//            String[] values = prop.split(":");
//            builder.add(values[0], values[1]);
//        });
