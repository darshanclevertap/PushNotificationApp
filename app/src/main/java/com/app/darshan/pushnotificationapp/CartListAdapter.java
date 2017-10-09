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

import java.util.ArrayList;

/**
 * Created by darshanpania on 07/07/17.
 */

public class CartListAdapter extends RecyclerView.Adapter {

    Context context;
    ArrayList<Books> cartList;

    public CartListAdapter(Context context, ArrayList cartList){
        this.context = context;
        this.cartList = cartList;

    }

    @Override
    public long getItemId(int position) {
        return super.getItemId(position);
    }

    @Override
    public CartHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        return new CartHolder(LayoutInflater.from(parent.getContext()).inflate(R.layout.cart_item,parent,false));
    }

    public Books getItem(int position){
        return cartList.get(position);
    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, final int position) {
        String name = cartList.get(position).getName();
        ((CartHolder)holder).name.setText(name);
        String author = cartList.get(position).getAuthor();
        ((CartHolder)holder).author.setText("By : " + author);
        String publisher = cartList.get(position).getPublisher();
        ((CartHolder)holder).publisher.setText(publisher);
        String price = cartList.get(position).getPrice();
        ((CartHolder)holder).price.setText("Price : "+price);
        String imgUrl = cartList.get(position).getImageURL();
        Glide.with(context)
                .load(imgUrl)
                .into(((CartHolder)holder).image);

        ((CartHolder)holder).relativeLayout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Books selected = cartList.get(position);

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
        return cartList.size();
    }

    public static class CartHolder extends RecyclerView.ViewHolder{

        TextView name, author, publisher, price;
        ImageView image;
        RelativeLayout relativeLayout;
        public CartHolder(View itemView){
            super(itemView);
            name = (TextView)itemView.findViewById(R.id.book_name);
            author = (TextView)itemView.findViewById(R.id.book_author);
            publisher = (TextView)itemView.findViewById(R.id.book_publisher);
            price = (TextView)itemView.findViewById(R.id.book_price);
            image = (ImageView) itemView.findViewById(R.id.book_img);
            //addtoCart = (TextView) itemView.findViewById(R.id.addtoCart);
            relativeLayout = (RelativeLayout)itemView.findViewById(R.id.item_lyt);

        }
    }

}
