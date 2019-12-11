package com.example.books_application.user;

public class UserManager {

  private String userEmailAddress;
  private String userPassword;
  private static UserManager userManager;

  private UserManager() {

  }

  public static UserManager getInstance() {
    if (userManager == null) {
      userManager = new UserManager();
    }
    return userManager;
  }

  public void setUserCredentials(String email, String pass) {
    this.userEmailAddress = email;
    this.userPassword = pass;
  }

  public String getUserEmailAddress() {
    return userEmailAddress;
  }

  public String getUserPassword() {
    return userPassword;
  }
}
