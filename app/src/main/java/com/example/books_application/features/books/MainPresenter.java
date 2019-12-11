package com.example.books_application.features.books;

import android.support.annotation.NonNull;
import android.util.Log;
import com.example.books_application.data.BooksRepository;
import com.example.books_application.model.Book;
import io.reactivex.Single;
import io.reactivex.SingleSource;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.Disposable;
import io.reactivex.functions.Function;
import io.reactivex.observers.DisposableSingleObserver;
import io.reactivex.schedulers.Schedulers;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;

public class MainPresenter implements MainContract.Presenter {

  private final MainContract.View booksView;
  private final BooksRepository booksRepository;
  private Disposable booksDisposable;

  MainPresenter(@NonNull BooksRepository booksRepository,
      @NonNull MainContract.View booksView) {
    this.booksRepository = booksRepository;
    this.booksView = booksView;
  }

  @Override public void start() {

    booksView.addUserInformation();

    booksView.showProgressBar();
    if (booksDisposable != null && !booksDisposable.isDisposed()) {
      booksDisposable.dispose();
    }
    booksDisposable = booksRepository.getTheInfoForTheBooks().flatMap(
        new Function<List<Book>, SingleSource<List<Book>>>() {
          @Override public SingleSource<List<Book>> apply(List<Book> bookItems)
              throws Exception {

            for (int i = 0; i < bookItems.size(); i++) {
              HashMap<String, List<String>> anagrams = new HashMap<>();
              Log.w("BOOKS COUNTS", bookItems.get(i).getTitle());
              String[] titleItems = bookItems.get(i).getTitle().split("[ .,?]");
              for (String titleItem : titleItems) {
                char[] charArray = titleItem.toLowerCase().toCharArray();
                Arrays.sort(charArray);
                List<String> anagramList = new ArrayList<>();
                anagramList.add(titleItem);
                anagrams.put(new String(charArray), anagramList);
              }
              for (String descItem : bookItems.get(i).getDescription().split("[ .,?]")
              ) {
                char[] charArray = descItem.toLowerCase().toCharArray();
                Arrays.sort(charArray);
                String checkString = new String(charArray);
                if (anagrams.containsKey(checkString)) {
                  anagrams.get(checkString).add(descItem);
                }
              }
              bookItems.get(i).setAnagrams(anagrams);
            }
            return Single.just(bookItems);
          }
        })
        .subscribeOn(Schedulers.io())
        .observeOn(AndroidSchedulers.mainThread())
        .subscribeWith(new DisposableSingleObserver<List<Book>>() {
          @Override public void onSuccess(List<Book> bookItems) {
            if (bookItems.size() == 0) {
              booksView.postsNotAvailable();
            }
            booksView.showPosts(bookItems);
            booksView.hideProgressBar();
          }

          @Override public void onError(Throwable e) {
            booksView.showErrorMessage(e.getMessage());
          }
        });
  }



  @Override public void stop() {

  }
}
