package com.example.books_application.features.booksgallery;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.NavigationView;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;
import com.example.books_application.R;
import com.example.books_application.data.BooksRepository;
import com.example.books_application.data.local.BooksLocalDataSource;
import com.example.books_application.data.remote.BooksRemoteDataSource;
import com.example.books_application.features.books.MainActivity;
import com.example.books_application.model.Book;
import com.example.books_application.user.UserManager;
import java.util.List;
import org.jetbrains.annotations.NotNull;

public class BooksGalleryActivity extends AppCompatActivity
    implements NavigationView.OnNavigationItemSelectedListener, BookGalleryContract.View {

  private RecyclerView recyclerView;
  private ProgressBar progressBar;
  private RecyclerAdapterForGallery adapter;
  private BookGalleryContract.Presenter presenter;
  private UserManager userManager = UserManager.getInstance();
  private TextView userInformation;
  private TextView userEmail;
  @Override
  protected void onCreate(Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);
    setContentView(R.layout.activity_books_gallery);
    Toolbar toolbar = findViewById(R.id.toolbar);
    setSupportActionBar(toolbar);
    DrawerLayout drawer = findViewById(R.id.drawer_layout);
    NavigationView navigationView = findViewById(R.id.nav_view_gallery);
    initializeRecyclerView();
    View hView = navigationView.getHeaderView(0);
    userInformation = hView.findViewById(R.id.userInformation);
    userEmail = hView.findViewById(R.id.userEmail);
    progressBar = findViewById(R.id.progressBarGallery);
    presenter =
        new BookGalleryPresenter(BooksRepository.getInstance(BooksRemoteDataSource.getInstance(),
            BooksLocalDataSource.getInstance()), this);

    ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
        this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
    drawer.addDrawerListener(toggle);
    toggle.syncState();
    navigationView.setNavigationItemSelectedListener(this);
    presenter.start();
  }

  public void initializeRecyclerView() {
    recyclerView = findViewById(R.id.books_gallery_recycler);
    final GridLayoutManager layoutManager = new GridLayoutManager(this, 2);
    recyclerView.setHasFixedSize(true);
    recyclerView.setLayoutManager(layoutManager);
  }

  @SuppressWarnings("StatementWithEmptyBody")
  @Override
  public boolean onNavigationItemSelected(@NotNull MenuItem item) {
    // Handle navigation view item clicks here.
    int id = item.getItemId();

    if (id == R.id.nav_home) {
      startActivity(new Intent(BooksGalleryActivity.this, MainActivity.class));
    } else if (id == R.id.nav_gallery) {
      presenter.start();
    } else if (id == R.id.settings) {

    }

    DrawerLayout drawer = findViewById(R.id.drawer_layout);
    drawer.closeDrawer(GravityCompat.START);
    return true;
  }

  @Override
  public void onBackPressed() {
    DrawerLayout drawer = findViewById(R.id.drawer_layout);
    if (drawer.isDrawerOpen(GravityCompat.START)) {
      drawer.closeDrawer(GravityCompat.START);
    } else {
      super.onBackPressed();
    }
  }

  @Override public void showPosts(final List<Book> data) {
    adapter = new RecyclerAdapterForGallery(data, BooksGalleryActivity.this);
    recyclerView.setAdapter(adapter);
    Toast.makeText(BooksGalleryActivity.this,
        "The information about the books are loaded " + data.size(),
        Toast.LENGTH_LONG).show();
  }

  @Override public void addPostsToTheRecycler(final List<Book> data) {
    adapter.addImages(data);
    Toast.makeText(BooksGalleryActivity.this, "more books are loaded...",
        Toast.LENGTH_LONG).show();
  }

  @Override public void showErrorMessage(String message) {
    Log.w("EROOR ", message);
  }

  @Override public void showProgressBar() {
    progressBar.setVisibility(View.VISIBLE);
  }

  @Override public void hideProgressBar() {
    progressBar.setVisibility(View.GONE);
  }

  @Override public void postsNotAvailable() {
    Toast.makeText(BooksGalleryActivity.this, "there are no books avilable right now try later..",
        Toast.LENGTH_LONG).show();
  }

  @SuppressLint("SetTextI18n") @Override public void addUserInformation() {
      userEmail.setText(userManager.getUserEmailAddress());
      userInformation.setText("Kristijan Mitrikeski");
  }
}
