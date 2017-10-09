package com.app.darshan.pushnotificationapp;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.widget.TextViewCompat;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import com.leanplum.Leanplum;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;

import static com.app.darshan.pushnotificationapp.MainActivity.cleverTapAPI;
import static com.app.darshan.pushnotificationapp.MainActivity.mixpanel;

/**
 * Created by darshanpania on 06/07/17.
 */

public class BookListActivity extends AppCompatActivity implements CartInterface {

    RecyclerView books_rv;
    ArrayList<Books> booksArrayList = new ArrayList<Books>();
    BookListAdapter bookListAdapter;
    ArrayList<Books> cartList = new ArrayList<>();

    TextView viewCart,clearCart;
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_booklist);

        Intent intent = getIntent();
        if(intent.getExtras()!=null){
            booksArrayList = intent.getParcelableArrayListExtra("BooksList");
        }
        viewCart = (TextView) findViewById(R.id.view_cart);
        clearCart = (TextView) findViewById(R.id.clear_cart);
        books_rv = (RecyclerView) findViewById(R.id.books_rv);
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(this);
        linearLayoutManager.setOrientation(LinearLayoutManager.VERTICAL);
        books_rv.setLayoutManager(linearLayoutManager);

        if(booksArrayList!=null && booksArrayList.size()>0){
            bookListAdapter = new BookListAdapter(this, booksArrayList,this);
            books_rv.setAdapter(bookListAdapter);
        }

        viewCart.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent1 = new Intent(BookListActivity.this, CartListActivity.class);
                intent1.putParcelableArrayListExtra("CartList",cartList);
                startActivity(intent1);
            }
        });

        clearCart.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                removeAllFromCart();
            }
        });
        //cleverTapAPI.event.push("Book List Viewed");
        HashMap<String, Object> prodViewedAction = new HashMap<String, Object>();
        Date date = new Date();
        Calendar c = Calendar.getInstance();
        //c.setTime(date);
        c.set(2017,9,4,17,0,0);
        c.add(Calendar.DAY_OF_YEAR,5);
        Date newDate = c.getTime();
        prodViewedAction.put("Date", newDate);

        cleverTapAPI.event.push("BookList Viewed", prodViewedAction);
        mixpanel.track("Book List Viewed");
        Leanplum.track("Book List Viewed");

    }

    @Override
    public void addToCart(Books book) {

        cartList.add(book);
        viewCart.setText("View Cart ("+cartList.size()+")");
    }

    @Override
    public void removeFromCart(Books book) {
        cartList.remove(book);
    }

    @Override
    public void removeAllFromCart() {

        cartList.clear();
        viewCart.setText("View Cart");
        cleverTapAPI.event.push("Cart Cleared");
    }
}
