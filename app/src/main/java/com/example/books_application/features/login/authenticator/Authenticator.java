package com.example.books_application.features.login.authenticator;

import io.reactivex.Single;

public interface Authenticator {

  Single<Boolean> login(final String email, final String pass);

  boolean logout();

  void validate(final String email, final String pass);
}
