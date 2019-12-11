package com.example.books_application.network;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.jakewharton.retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory;
import java.lang.reflect.Type;
import retrofit2.Converter;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class ApiClient {

  private static Retrofit retrofit = null;

  private static Converter.Factory createGsonConverter(Type type, Object typeAdapter) {
    GsonBuilder gsonBuilder = new GsonBuilder();
    gsonBuilder.registerTypeAdapter(type, typeAdapter);
    Gson gson = gsonBuilder.create();

    return GsonConverterFactory.create(gson);
  }

  public static Retrofit getApiClient(Type type , Object typeAdapter) {
    if (retrofit == null) {
      retrofit = new Retrofit.Builder().baseUrl("http://webfactory.mk/courseapi/")
          .addConverterFactory(createGsonConverter(type,typeAdapter))
          .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
          .build();
    }
    return retrofit;
  }
}
