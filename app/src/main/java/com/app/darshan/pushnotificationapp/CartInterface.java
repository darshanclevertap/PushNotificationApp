package com.app.darshan.pushnotificationapp;

/**
 * Created by darshanpania on 07/07/17.
 */

public interface CartInterface {

    public void addToCart(Books book);
    public void removeFromCart(Books book);
    public void removeAllFromCart();
}
