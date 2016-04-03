package com.spark.util;

import com.google.gson.Gson;
import spark.ResponseTransformer;

/**
 * Created by vignesh on 1/3/2016.
 */
public class JsonUtil {

    public static String toJson(Object object){
        return new Gson().toJson(object);

    }


    public static <T>T fromJson(String jsonString,Class<T> object){
        return new Gson().fromJson(jsonString,object);
    }

public static ResponseTransformer json(){
    return JsonUtil::toJson;
}


}
