package com.stupidbird.core.util;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

public class GsonUtil {
	private static Gson instance = new GsonBuilder().serializeNulls().setDateFormat("yyyy-MM-dd HH:mm:ss").create();;

	
	public static Gson getJsonInstance() {
		return instance;
	}

	public static <T> T fromJson(String json, Class<T> clazz) {
		return instance.fromJson(json, clazz);
	}

	public static String toJson(Object entity) {
		return instance.toJson(entity);
	}
}
