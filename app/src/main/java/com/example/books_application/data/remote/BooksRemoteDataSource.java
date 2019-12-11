package com.example.books_application.data.remote;

import android.support.annotation.NonNull;
import com.example.books_application.model.Book;
import com.example.books_application.network.ApiClient;
import com.example.books_application.network.ApiInterface;
import com.example.books_application.data.BooksDataSource;
import com.example.books_application.network.BookAdapter;
import com.google.gson.reflect.TypeToken;
import io.reactivex.Single;
import java.util.List;

public class BooksRemoteDataSource implements BooksDataSource {

  private static BooksRemoteDataSource INSTANCE;



  private final ApiInterface apiCalls;

  public static BooksRemoteDataSource getInstance() {
    if (INSTANCE == null) {
      INSTANCE = new BooksRemoteDataSource();
    }
    return INSTANCE;
  }

  // Prevent direct instantiation.
  private BooksRemoteDataSource() {

    this.apiCalls = ApiClient.getApiClient(new TypeToken<List<Book>>() {}.getType(),new BookAdapter()).create(ApiInterface.class);
  }

  @Override public Single<List<Book>> getTheInfoForTheBooks() {




    return apiCalls.getBooksInfoFromApi();
  }

  @Override public void saveBooks(@NonNull List<Book> books) {

  }
}
