package com.example.books_application.features.login.ui;

public interface LoginContract {

  interface View {

    void goToHomePage();

    void loginFailed();

    void showProgressDialog();

    void hideProgressDialog();

    void showEmailAddressNotValidError(String error);

    void showPasswordNotValidError(String error);
  }

  interface Presenter {

    void start();

    void stop();

    void login(String email, String pass);
  }
}
