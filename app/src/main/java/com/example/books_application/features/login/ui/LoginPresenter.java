package com.example.books_application.features.login.ui;

import android.os.Build;
import android.support.annotation.RequiresApi;
import android.util.Log;
import com.example.books_application.exception.ValidationExceptions;
import com.example.books_application.features.login.authenticator.Authenticator;
import com.example.books_application.features.login.authenticator.MockAuthenticator;
import com.example.books_application.user.UserManager;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.Disposable;
import io.reactivex.observers.DisposableSingleObserver;
import io.reactivex.schedulers.Schedulers;

public class LoginPresenter implements LoginContract.Presenter {

  private LoginContract.View loginView;
  private UserManager userManager;
  private Authenticator userAuthenticator;
  private Disposable userAuthenticatorDisposable;

  LoginPresenter(LoginContract.View loginView) {
    this.loginView = loginView;
    this.userManager = UserManager.getInstance();
    this.userAuthenticator = new MockAuthenticator();
  }

  @Override public void start() {

  }

  @Override public void stop() {
    if (userAuthenticatorDisposable != null && !userAuthenticatorDisposable.isDisposed()) {
      userAuthenticatorDisposable.dispose();
    }
  }

  @RequiresApi(api = Build.VERSION_CODES.KITKAT) @Override
  public void login(final String email, final String pass) {
    try {

      userAuthenticator.validate(email, pass);
      loginView.showProgressDialog();
      if (userAuthenticatorDisposable != null && !userAuthenticatorDisposable.isDisposed()) {
        userAuthenticatorDisposable.dispose();
      }
      userAuthenticatorDisposable = userAuthenticator.login(email, pass).
          subscribeOn(Schedulers.io())
          .observeOn(AndroidSchedulers.mainThread())
          .subscribeWith(new DisposableSingleObserver<Boolean>() {
            @Override public void onSuccess(Boolean aBoolean) {
              if (aBoolean) {
                Log.w("asd", "ASDSADSADSA");
                userManager.setUserCredentials(email, pass);
                loginView.goToHomePage();
              } else {
                loginView.loginFailed();
              }
            }

            @Override public void onError(Throwable e) {

            }
          });
    } catch (Exception e) {
      for (final Throwable throwable : e.getSuppressed()) {
        ValidationExceptions exception = (ValidationExceptions) throwable;
        if (exception.getKind().equals(ValidationExceptions.Kind.EMAIL_ERROR)) {
          loginView.showEmailAddressNotValidError(exception.getMessage());
        } else {
          loginView.showPasswordNotValidError(exception.getMessage());
        }
      }
    }
  }
}
