package com.example.books_application.network;

import android.util.Log;
import com.example.books_application.model.Book;
import com.google.gson.JsonArray;
import com.google.gson.JsonDeserializationContext;
import com.google.gson.JsonDeserializer;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonParseException;
import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.List;

public class BookAdapter implements JsonDeserializer<List<Book>> {

  @Override
  public List<Book> deserialize(JsonElement json, Type typeOfT, JsonDeserializationContext context)
      throws JsonParseException {
    List<Book> books = new ArrayList<>();

    final JsonObject jsonObject = json.getAsJsonObject();
    final int totalItems = jsonObject.get("totalItems").getAsInt();
    final JsonArray booksJsonArray = jsonObject.get("items").getAsJsonArray();

    Log.w("total items",Integer.toString(totalItems));
    for(JsonElement booksJsonElement : booksJsonArray)
    {
      final JsonObject bookJsonObject = booksJsonElement.getAsJsonObject();
      final JsonObject volumeInfo = bookJsonObject.get("volumeInfo").getAsJsonObject();
      final JsonObject imageLinks = volumeInfo.get("imageLinks").getAsJsonObject();
      final String title = volumeInfo.get("title").getAsString();
      final String description = volumeInfo.get("description").getAsString();
      final String imageLink = imageLinks.get("thumbnail").getAsString();
      books.add(new Book(title,description,imageLink));
    }
    return books;
  }
}
