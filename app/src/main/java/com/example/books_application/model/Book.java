package com.example.books_application.model;

import com.google.gson.annotations.SerializedName;
import java.util.HashMap;
import java.util.List;

public class Book {

  @SerializedName("title")
  private final String title;
  @SerializedName("description")
  private final String description;
  @SerializedName("imageLink")
  private final String imageLink;
  private transient HashMap<String, List<String>> anagrams;
  private transient String anagramString;

  public Book(String title, String description, String imageLink) {
    this.title = title;
    this.description = description;
    this.imageLink = imageLink;
  }

  public String getTitle() { return title; }

  public String getDescription() { return description; }

  public String getImageLink() { return imageLink; }

  public HashMap<String, List<String>> getAnagrams() {
    return anagrams;
  }

  public void setAnagrams(HashMap<String, List<String>> anagrams) {
    this.anagrams = anagrams;
  }

  public String printAnagrams() {

    if (anagramString == null) {
      StringBuilder str = new StringBuilder();
      for (List<String> anagramInfo : anagrams.values()
      ) {
        String title = anagramInfo.get(0);
        anagramInfo.remove(0);
        str.append(title).append(" has ");
        str.append(anagramInfo.size());
        str.append(" anagram/s : ");
        for (String anagram : anagramInfo
        ) {
          str.append("'").append(anagram).append("' ");
        }
        str.append("\n");
      }
      anagramString = str.toString();
      return anagramString;
    } else { return anagramString; }
  }
}
