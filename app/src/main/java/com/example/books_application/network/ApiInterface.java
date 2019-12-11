package com.example.books_application.network;

import com.example.books_application.model.Book;
import io.reactivex.Single;
import java.util.List;
import retrofit2.Call;
import retrofit2.http.GET;

public interface ApiInterface {

  @GET("books.json")
  Single<List<Book>> getBooksInfoFromApi();
}
