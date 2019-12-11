package com.example.books_application.features.login.ui;

import android.app.AlertDialog;
import android.app.Dialog;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import com.example.books_application.R;
import com.example.books_application.features.books.MainActivity;
import com.example.books_application.user.UserManager;

public class LoginActivity extends AppCompatActivity implements LoginContract.View {

  @BindView(R.id.btn_login) Button loginButton;
  private LoginContract.Presenter loginPresenter;
  @BindView(R.id.input_email) EditText emailEditText;
  @BindView(R.id.input_password) EditText passwordEditText;
  @BindView(R.id.loginErrorMessage) TextView authenticationFailedErrorMessage;
  private Dialog dialog;

  @Override
  protected void onCreate(Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);
    setContentView(R.layout.activity_login);
    Toolbar toolbar = findViewById(R.id.toolbar);
    setSupportActionBar(toolbar);
    createProgressDialog();
    loginPresenter = new LoginPresenter(this);
    loginPresenter.start();
    ButterKnife.bind(this);
  }

  @OnClick(R.id.btn_login)
  public void onLoginButtonClicked() {
    authenticationFailedErrorMessage.setVisibility(View.GONE);
    loginPresenter.login(emailEditText.getText().toString(),
        passwordEditText.getText().toString());
  }

  public void createProgressDialog() {
    AlertDialog.Builder builder = new AlertDialog.Builder(this);
    LayoutInflater inflater = getLayoutInflater();
    builder.setView(inflater.inflate(R.layout.progress_dialog, null));
    this.dialog = builder.create();
  }

  @Override public void goToHomePage() {
    startActivity(new Intent(LoginActivity.this, MainActivity.class));
    hideProgressDialog();
    finish();
  }

  @Override public void loginFailed() {
    hideProgressDialog();
    authenticationFailedErrorMessage.setVisibility(View.VISIBLE);
    authenticationFailedErrorMessage.requestLayout();
    loginButton.setEnabled(true);
  }

  @Override public void showProgressDialog() {

    dialog.show();
  }

  @Override public void hideProgressDialog() {
    dialog.cancel();
  }

  @Override protected void onDestroy() {
    loginPresenter.stop();
    super.onDestroy();
  }

  @Override public void showEmailAddressNotValidError(final String error) {
    emailEditText.setError(error);
  }

  @Override public void showPasswordNotValidError(final String error) {
    passwordEditText.setError(error);
  }
}
