package com.example.books_application.features.books;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.CardView;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import butterknife.BindView;
import com.example.books_application.R;
import com.example.books_application.model.Book;
import com.squareup.picasso.Picasso;
import java.util.List;
import org.jetbrains.annotations.NotNull;

public class RecyclerAdapter extends
    RecyclerView.Adapter<com.example.books_application.features.books.RecyclerAdapter.MyViewHolder> {

  private List<Book> bookList;
  private Context context;

  RecyclerAdapter(List<Book> listOfImages, Context context) {
    this.bookList = listOfImages;
    this.context = context;
  }

  @NonNull
  @Override
  public MyViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
    View view = LayoutInflater.from(viewGroup.getContext())
        .inflate(R.layout.book_item, viewGroup, false);
    return new MyViewHolder(view);
  }

  @Override
  public void onBindViewHolder(@NotNull MyViewHolder holder, int i) {
    final Book book = bookList.get(i);

    holder.titleTextView.setText(book.getTitle());
    holder.descriptionTextView.setText(book.getDescription());
    Picasso.with(context).load(book.getImageLink()).into(holder.image);
    holder.anagramsTextView.setText(book.printAnagrams());
  }

  @Override
  public int getItemCount() {
    return bookList.size();
  }

  public static class MyViewHolder extends RecyclerView.ViewHolder {

    @BindView(R.id.BookImage) ImageView image;
    @BindView(R.id.BookTitle) TextView titleTextView;
    @BindView(R.id.CardViewId) CardView cardView;
    @BindView(R.id.BookDescription) TextView descriptionTextView;
    @BindView(R.id.BookAnagrams) TextView anagramsTextView;

    MyViewHolder(@NonNull View itemView) {
      super(itemView);
      titleTextView = itemView.findViewById(R.id.BookTitle);
      descriptionTextView = itemView.findViewById(R.id.BookDescription);
      anagramsTextView = itemView.findViewById(R.id.BookAnagrams);
      image = (ImageView) itemView.findViewById(R.id.BookImage);
      cardView = (CardView) itemView.findViewById(R.id.CardViewId);
    }

    public ImageView getImage() {
      return image;
    }

    public TextView getTitleTextView() {
      return titleTextView;
    }

    public CardView getCardView() {
      return cardView;
    }

    public TextView getDescriptionTextView() {
      return descriptionTextView;
    }

    public TextView getAnagramsTextView() {
      return anagramsTextView;
    }
  }

  void addImages(final List<Book> books) {
    bookList.addAll(books);
    notifyDataSetChanged();
  }
}

