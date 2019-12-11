package com.example.books_application.features.booksgallery;

import com.example.books_application.model.Book;
import java.util.List;

public interface BookGalleryContract {

  interface View {

    // called when the recycler needs to be created with new posts
    void showPosts(List<Book> data);

    // called when more posts are added to the recycler
    void addPostsToTheRecycler(List<Book> data);

    void showErrorMessage(String message);

    void showProgressBar();

    void hideProgressBar();

    void postsNotAvailable();

    void addUserInformation();
  }

  interface Presenter {

    void start();

    void stop();




  }
}
