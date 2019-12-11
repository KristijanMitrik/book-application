package com.example.books_application.features.booksgallery;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import butterknife.BindView;
import butterknife.ButterKnife;
import com.example.books_application.R;
import com.example.books_application.model.Book;
import com.squareup.picasso.Picasso;
import java.util.List;
import org.jetbrains.annotations.NotNull;

public class RecyclerAdapterForGallery
    extends RecyclerView.Adapter<RecyclerAdapterForGallery.MyViewHolder> {

  private List<Book> bookList;
  private Context context;

  RecyclerAdapterForGallery(List<Book> listOfImages, Context context) {
    this.bookList = listOfImages;
    this.context = context;
  }

  @NonNull
  @Override
  public MyViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
    View view = LayoutInflater.from(viewGroup.getContext())
        .inflate(R.layout.books_gallery_item, viewGroup, false);
    return new MyViewHolder(view);
  }

  @Override
  public void onBindViewHolder(@NotNull MyViewHolder holder, int i) {
    final Book book = bookList.get(i);

    holder.titleTextView.setText(book.getTitle());
    Picasso.with(context).load(book.getImageLink()).into(holder.imageTextView);
  }

  @Override
  public int getItemCount() {
    return bookList.size();
  }

  public static class MyViewHolder extends RecyclerView.ViewHolder {

    @BindView(R.id.bookGalleryTitle) TextView titleTextView;
    @BindView(R.id.bookGalleryImage) ImageView imageTextView;

    MyViewHolder(@NonNull View itemView) {
      super(itemView);
      ButterKnife.bind(this, itemView);
    }

    public ImageView getImageTextView() {
      return imageTextView;
    }

    public TextView getTitleTextView() {
      return titleTextView;
    }
  }

  void addImages(final List<Book> books) {
    bookList.addAll(books);
    notifyDataSetChanged();
  }
}

