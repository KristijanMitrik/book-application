package com.example.books_application.features.login.authenticator;

import android.os.Build;
import android.support.annotation.RequiresApi;
import com.example.books_application.exception.ValidationExceptions;
import io.reactivex.Single;
import io.reactivex.SingleSource;
import io.reactivex.functions.Function;
import java.util.concurrent.TimeUnit;

public class MockAuthenticator implements Authenticator {

  private static final String EMAIL = "kmitrikeski@hotmail.com";
  private static final String PASSWORD = "password1";

  public MockAuthenticator() {

  }

  @Override public Single<Boolean> login(final String email, final String pass) {

    return Single.timer(5, TimeUnit.SECONDS)
        .flatMap(new Function<Long, SingleSource<? extends Boolean>>() {
          @Override public SingleSource<? extends Boolean> apply(Long aLong) throws Exception {
            return authenticate(email, pass);
          }
        });
  }

  private Single<Boolean> authenticate(final String email, final String pass) {
    if (email.compareTo(EMAIL) == 0 && pass.compareTo(PASSWORD) == 0) {
      return Single.just(true);
    } else { return Single.just(false); }
  }

  @Override public boolean logout() {
    return false;
  }

  @RequiresApi(api = Build.VERSION_CODES.KITKAT) @Override
  public void validate(final String email, final String pass) throws ValidationExceptions {
    boolean valid = true;
    ValidationExceptions.Builder builder = new ValidationExceptions.Builder();
    ValidationExceptions exc = builder.build();

    if (email.isEmpty()) {
      builder.setKind(ValidationExceptions.Kind.EMAIL_ERROR);
      builder.setMessage("Email field cannot be empty");
      valid = false;
      exc.addSuppressed(builder.build());
    } else if (!android.util.Patterns.EMAIL_ADDRESS.matcher(email).matches()) {
      builder.setKind(ValidationExceptions.Kind.EMAIL_ERROR);
      builder.setMessage("enter a valid EMAIL address");
      valid = false;
      exc.addSuppressed(builder.build());
    }

    if (pass.isEmpty()) {
      builder.setKind(ValidationExceptions.Kind.PASSWORD_ERROR);
      builder.setMessage("PASSWORD field cannot be empty");
      valid = false;
      exc.addSuppressed(builder.build());
    } else if (pass.length() < 8 || pass.length() > 15) {
      builder.setKind(ValidationExceptions.Kind.PASSWORD_ERROR);
      builder.setMessage("PASSWORD must be between 8 and 15 characters ");
      valid = false;
      exc.addSuppressed(builder.build());
    }

    if (!valid) {
      throw exc;
    }
  }
}
