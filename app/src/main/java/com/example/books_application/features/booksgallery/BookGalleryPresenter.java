package com.example.books_application.features.booksgallery;

import android.support.annotation.NonNull;
import com.example.books_application.data.BooksRepository;
import com.example.books_application.model.Book;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.Disposable;
import io.reactivex.observers.DisposableSingleObserver;
import io.reactivex.schedulers.Schedulers;
import java.util.List;

public class BookGalleryPresenter implements BookGalleryContract.Presenter {

  private final BookGalleryContract.View booksGallery;
  private final BooksRepository booksRepository;
  private Disposable booksGalleryObservable;

  public BookGalleryPresenter(@NonNull BooksRepository booksRepository,
      @NonNull BookGalleryContract.View booksView) {
    this.booksRepository = booksRepository;
    this.booksGallery = booksView;
  }

  @Override public void start() {

    booksGallery.addUserInformation();

    if (booksGalleryObservable != null && !booksGalleryObservable.isDisposed()) {
      booksGalleryObservable.dispose();
    }

    booksGallery.showProgressBar();
    booksGalleryObservable = booksRepository.getTheInfoForTheBooks()
        .subscribeOn(Schedulers.io())
        .observeOn(AndroidSchedulers.mainThread())
        .subscribeWith(new DisposableSingleObserver<List<Book>>() {
          @Override public void onSuccess(List<Book> bookItems) {
            booksGallery.showPosts(bookItems);
            booksGallery.hideProgressBar();
          }

          @Override public void onError(Throwable e) {
            booksGallery.showErrorMessage(e.getMessage());
          }
        });
  }

  @Override public void stop() {
    if (booksGalleryObservable != null && !booksGalleryObservable.isDisposed()) {
      booksGalleryObservable.dispose();
    }
  }
}
