package com.example.books_application.features.books;

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
import com.example.books_application.features.booksgallery.BooksGalleryActivity;
import com.example.books_application.model.Book;
import com.example.books_application.user.UserManager;
import java.util.List;
import org.jetbrains.annotations.NotNull;

public class MainActivity extends AppCompatActivity
    implements NavigationView.OnNavigationItemSelectedListener, MainContract.View {

  private RecyclerView recyclerView;
  private ProgressBar progressBar;
  private MainContract.Presenter presenter;
  TextView userEmail;
  TextView userInformation;
  private UserManager userManager = UserManager.getInstance();

  @Override
  protected void onCreate(Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);
    setContentView(R.layout.activity_main);
    Toolbar toolbar = findViewById(R.id.toolbar);
    setSupportActionBar(toolbar);
    DrawerLayout drawer = findViewById(R.id.drawer_layout);
    NavigationView navigationView = findViewById(R.id.nav_view);
    initializeRecyclerView();
    View hView = navigationView.getHeaderView(0);
    progressBar = findViewById(R.id.ProgressBar);
    userEmail = hView.findViewById(R.id.userEmail);
    userInformation = hView.findViewById(R.id.userInformation);
    presenter = new MainPresenter(BooksRepository.getInstance(BooksRemoteDataSource.getInstance(),

        BooksLocalDataSource.getInstance()), this);
    ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
        this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
    drawer.addDrawerListener(toggle);
    toggle.syncState();
    navigationView.setNavigationItemSelectedListener(this);
    presenter.start();
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

  @Override protected void onDestroy() {
    super.onDestroy();
    presenter.stop();
  }

  public void initializeRecyclerView() {
    recyclerView = findViewById(R.id.my_recycler_view);
    final GridLayoutManager layoutManager = new GridLayoutManager(this, 1);
    recyclerView.setHasFixedSize(true);
    recyclerView.setLayoutManager(layoutManager);
  }

  @SuppressWarnings("StatementWithEmptyBody")
  @Override
  public boolean onNavigationItemSelected(@NotNull MenuItem item) {
    // Handle navigation view item clicks here.
    int id = item.getItemId();

    if (id == R.id.nav_home) {
      presenter.start();
    } else if (id == R.id.nav_gallery) {
      startActivity(new Intent(MainActivity.this, BooksGalleryActivity.class));
    } else if (id == R.id.settings) {

    }

    DrawerLayout drawer = findViewById(R.id.drawer_layout);
    drawer.closeDrawer(GravityCompat.START);
    return true;
  }

  @Override public void showPosts(List<Book> data) {
    final RecyclerAdapter adapter = new RecyclerAdapter(data, MainActivity.this);
    recyclerView.setAdapter(adapter);
    Toast.makeText(MainActivity.this, "The information about the books are loaded " + data.size(),
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
    Toast.makeText(MainActivity.this, "there are no books avilable right now try later..",
        Toast.LENGTH_LONG).show();
  }

  @SuppressLint("SetTextI18n") @Override public void addUserInformation() {
    userEmail.setText(userManager.getUserEmailAddress());
    userInformation.setText("Kristijan Mitrikeski");
  }
}
