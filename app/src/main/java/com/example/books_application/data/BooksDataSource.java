package com.example.books_application.data;

import android.support.annotation.NonNull;
import com.example.books_application.model.Book;
import io.reactivex.Single;
import java.util.List;

public interface BooksDataSource {

  Single<List<Book>> getTheInfoForTheBooks();

  void saveBooks(@NonNull List<Book> books);
}
