package com.example.books_application.data.local;

import android.support.annotation.NonNull;
import com.example.books_application.model.Book;
import com.example.books_application.data.BooksDataSource;
import io.reactivex.Single;
import java.util.ArrayList;
import java.util.List;

public class BooksLocalDataSource implements BooksDataSource {

  private static volatile BooksLocalDataSource INSTANCE;

  private List<Book> booksItems;

  // Prevent direct instantiation.
  private BooksLocalDataSource() {
    booksItems = new ArrayList<>();
  }

  public static BooksLocalDataSource getInstance() {
    if (INSTANCE == null) {
      synchronized (BooksLocalDataSource.class) {
        if (INSTANCE == null) {
          INSTANCE = new BooksLocalDataSource();
        }
      }
    }
    return INSTANCE;
  }

  @Override public Single<List<Book>> getTheInfoForTheBooks() {
   return Single.just(booksItems);
  }

  @Override public void saveBooks(@NonNull List<Book> books) {
        this.booksItems = books;
  }
}
