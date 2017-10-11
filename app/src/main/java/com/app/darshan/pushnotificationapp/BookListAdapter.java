package com.app.darshan.pushnotificationapp;

import android.content.Context;
import android.content.Intent;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.leanplum.Leanplum;

import java.util.ArrayList;
import java.util.HashMap;

import static com.app.darshan.pushnotificationapp.MainActivity.cleverTapAPI;

/**
 * Created by darshanpania on 06/07/17.
 */

public class BookListAdapter extends RecyclerView.Adapter {

    Context context;
    ArrayList<Books> booksArrayList;
    CartInterface cartInterface;
    public BookListAdapter(Context context, ArrayList booksArrayList, CartInterface cartInterface){
        this.context = context;
        this.booksArrayList = booksArrayList;
        this.cartInterface = cartInterface;
    }

    @Override
    public long getItemId(int position) {
        return super.getItemId(position);
    }

    @Override
    public BooksHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        return new BooksHolder(LayoutInflater.from(parent.getContext()).inflate(R.layout.book_item,parent,false));
    }

    public Books getItem(int position){
        return booksArrayList.get(position);
    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, final int position) {
        String name = booksArrayList.get(position).getName();
        ((BooksHolder)holder).name.setText(name);
        String author = booksArrayList.get(position).getAuthor();
        ((BooksHolder)holder).author.setText("By : " + author);
        String publisher = booksArrayList.get(position).getPublisher();
        ((BooksHolder)holder).publisher.setText(publisher);
        String price = booksArrayList.get(position).getPrice();
        ((BooksHolder)holder).price.setText("Price : "+price);
        String imgUrl = booksArrayList.get(position).getImageURL();
        Glide.with(context)
                .load(imgUrl)
                .into(((BooksHolder)holder).image);
        ((BooksHolder)holder).addtoCart.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Books selected = booksArrayList.get(position);
                cartInterface.addToCart(selected);

                HashMap<String, Object> addToCart = new HashMap<String, Object>();
                addToCart.put("Book Name", selected.getName());
                addToCart.put("Book Category", "Books");
                addToCart.put("Book Author",selected.getAuthor());
                addToCart.put("Book Publisher",selected.getPublisher());
                addToCart.put("Book Price", Integer.parseInt(selected.getPrice()));
                addToCart.put("Date", new java.util.Date());

                cleverTapAPI.event.push("Added to Cart", addToCart);
                Leanplum.track("Add to Cart",addToCart);
            }
        });
        ((BooksHolder)holder).relativeLayout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Books selected = booksArrayList.get(position);

                Intent intent = new Intent(context,ViewBookActivity.class);
                intent.putExtra("name",selected.getName());
                intent.putExtra("author",selected.getAuthor());
                intent.putExtra("publisher",selected.getPublisher());
                intent.putExtra("price",selected.getPrice());
                intent.putExtra("imageUrl",selected.getImageURL());
                context.startActivity(intent);
                Toast.makeText(context,selected.getName()+" clicked",Toast.LENGTH_SHORT).show();
            }
        });
    }

    @Override
    public int getItemCount() {
        return booksArrayList.size();
    }

    public static class BooksHolder extends RecyclerView.ViewHolder{

        TextView name, author, publisher, price, addtoCart;
        ImageView image;
        RelativeLayout relativeLayout;
        public BooksHolder(View itemView){
            super(itemView);
            name = (TextView)itemView.findViewById(R.id.book_name);
            author = (TextView)itemView.findViewById(R.id.book_author);
            publisher = (TextView)itemView.findViewById(R.id.book_publisher);
            price = (TextView)itemView.findViewById(R.id.book_price);
            image = (ImageView) itemView.findViewById(R.id.book_img);
            addtoCart = (TextView) itemView.findViewById(R.id.addtoCart);
            relativeLayout = (RelativeLayout)itemView.findViewById(R.id.item_lyt);

        }
    }

}
