package com.benny.blog.utils;

import java.lang.reflect.ParameterizedType;

import com.alibaba.fastjson.JSON;

public class JsonHelper<T> {

    public Class<T> getTClass() {
        return (Class<T>) ((ParameterizedType) getClass().getGenericSuperclass()).getActualTypeArguments()[0];
    }

    public static String fromObject(Object object) {
        return JSON.toJSON(object).toString();
    }

    public T parseObject(String json) {
        return JSON.parseObject(json, getTClass());
    }

}