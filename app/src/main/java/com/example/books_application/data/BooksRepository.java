package com.example.books_application.data;

import android.support.annotation.NonNull;
import com.example.books_application.model.Book;
import io.reactivex.Single;
import io.reactivex.functions.Consumer;
import java.util.List;
import java.util.Calendar;

import static com.google.common.base.Preconditions.checkNotNull;

public class BooksRepository implements BooksDataSource {

  private static BooksRepository INSTANCE = null;

  private final BooksDataSource booksLocalDataSource;

  private final BooksDataSource booksRemoteDataSource;

  private long currentTime = 0;

  private BooksRepository(@NonNull BooksDataSource booksRemoteDataSource,
      @NonNull BooksDataSource booksLocalDataSource) {
    this.booksRemoteDataSource = checkNotNull(booksRemoteDataSource);
    this.booksLocalDataSource = checkNotNull(booksLocalDataSource);
  }

  public static BooksRepository getInstance(BooksDataSource booksRemoteDataSource,
      BooksDataSource booksLocalDataSource) {
    if (INSTANCE == null) {
      INSTANCE = new BooksRepository(booksRemoteDataSource, booksLocalDataSource);
    }
    return INSTANCE;
  }

  public static BooksRepository getInstance() {return INSTANCE;}

  public static void destroyInstance() {
    INSTANCE = null;
  }

  @Override public Single<List<Book>> getTheInfoForTheBooks() {
    if (currentTime == 0 || currentTime + 120000 < Calendar.getInstance().getTimeInMillis()) {
      currentTime = Calendar.getInstance().getTimeInMillis();
      return booksRemoteDataSource.getTheInfoForTheBooks().doAfterSuccess(
          new Consumer<List<Book>>() {
            @Override public void accept(List<Book> bookItems) throws Exception {
              booksLocalDataSource.saveBooks(bookItems);
            }
          });
    } else {
      return booksLocalDataSource.getTheInfoForTheBooks();
    }
  }

  @Override public void saveBooks(@NonNull List<Book> books) {

  }
}
