package com.app.darshan.pushnotificationapp;

import android.app.Dialog;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.RadioButton;
import android.widget.TextView;
import android.widget.Toast;

import com.clevertap.android.sdk.CleverTapAPI;
import com.clevertap.android.sdk.exceptions.InvalidEventNameException;
import com.leanplum.Leanplum;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;

import es.dmoral.toasty.Toasty;

import static com.app.darshan.pushnotificationapp.MainActivity.cleverTapAPI;
import static com.app.darshan.pushnotificationapp.MainActivity.mixpanel;

/**
 * Created by darshanpania on 07/07/17.
 */

public class CartListActivity extends AppCompatActivity {

    RecyclerView cart_rv;

    CartListAdapter cartListAdapter;
    ArrayList<Books> cartList = new ArrayList<>();
    Button buy;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_cart);

        Intent intent = getIntent();
        if(intent.getExtras()!=null){
            cartList = intent.getParcelableArrayListExtra("CartList");
        }

        buy = (Button)findViewById(R.id.buy_btn);
        cart_rv = (RecyclerView) findViewById(R.id.cart_rv);
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(this);
        linearLayoutManager.setOrientation(LinearLayoutManager.VERTICAL);
        cart_rv.setLayoutManager(linearLayoutManager);

        if(cartList!=null && cartList.size()>0){
            cartListAdapter = new CartListAdapter(this, cartList);
            cart_rv.setAdapter(cartListAdapter);
        }

        if(cartList.isEmpty()){
            buy.setText("No Items");
            buy.setEnabled(false);
        }

        buy.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //Toast.makeText(CartListActivity.this,"Books have been ordered",Toast.LENGTH_SHORT).show();

                final Dialog dialog = new Dialog(CartListActivity.this);
                dialog.setContentView(R.layout.radio_dialog);
                dialog.setTitle("Payment Option");
                dialog.setCancelable(true);
                // there are a lot of settings, for dialog, check them all out!
                // set up radiobutton
                final RadioButton rd1 = (RadioButton) dialog.findViewById(R.id.rd_1);
                final RadioButton rd2 = (RadioButton) dialog.findViewById(R.id.rd_2);
                Button ok_btn = (Button) dialog.findViewById(R.id.ok_btn);
                ok_btn.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        if (rd1.isChecked()){
                            rd2.setChecked(false);
                            int totalAmount =0;

                            ArrayList<HashMap<String, Object>> items = new ArrayList<HashMap<String, Object>>();
                            for(int i=0;i < cartList.size();i++){
                                totalAmount += Integer.parseInt(cartList.get(i).getPrice());
                                HashMap<String, Object> item = new HashMap<String, Object>();
                                item.put("Amount", cartList.get(i).getPrice());
                                item.put("Payment Mode", "Credit card");
                                item.put("Charged ID", (int)(Math.random()*100));
                                item.put("Product category", "Books");
                                item.put("Book Name", cartList.get(i).getName());
                                item.put("Book Author", cartList.get(i).getAuthor());
                                item.put("Book Publisher",cartList.get(i).getPublisher());
                                item.put("Quantity", 1);
                                items.add(item);
                            }
                            HashMap<String, Object> chargeDetails = new HashMap<String, Object>();
                            chargeDetails.put("Amount", totalAmount);
                            chargeDetails.put("Payment Mode", "Credit card");
                            chargeDetails.put("Charged ID", (int)(Math.random()*100));

                            try {
                                cleverTapAPI.event.push(CleverTapAPI.CHARGED_EVENT, chargeDetails, items);
                            } catch (InvalidEventNameException e) {
                                // You have to specify the first parameter to push()
                                // as CleverTapAPI.CHARGED_EVENT
                            }

                            Toast.makeText(CartListActivity.this,"Book has been ordered. Paid by credit card.",Toast.LENGTH_SHORT).show();
                            dialog.dismiss();
                        }else if(rd2.isChecked()){

                            rd1.setChecked(false);


                            int totalAmount =0;

                            ArrayList<HashMap<String, Object>> items = new ArrayList<HashMap<String, Object>>();
                            for(int i=0;i < cartList.size();i++){
                                totalAmount += Integer.parseInt(cartList.get(i).getPrice());
                                HashMap<String, Object> item = new HashMap<String, Object>();
                                item.put("Amount", cartList.get(i).getPrice());
                                item.put("Payment Mode", "Cash");
                                item.put("Charged ID", (int)(Math.random()*100));
                                item.put("Product category", "Books");
                                item.put("Book Name", cartList.get(i).getName());
                                item.put("Book Author", cartList.get(i).getAuthor());
                                item.put("Book Publisher",cartList.get(i).getPublisher());
                                item.put("Quantity", 1);
                                items.add(item);
                            }
                            HashMap<String, Object> chargeDetails = new HashMap<String, Object>();
                            chargeDetails.put("Amount", totalAmount);
                            chargeDetails.put("Payment Mode", "Cash");
                            chargeDetails.put("Charged ID", (int)(Math.random()*100));

                            try {
                                cleverTapAPI.event.push(CleverTapAPI.CHARGED_EVENT, chargeDetails, items);
                            } catch (InvalidEventNameException e) {
                                // You have to specify the first parameter to push()
                                // as CleverTapAPI.CHARGED_EVENT
                            }

                            Toast.makeText(CartListActivity.this,"Book has been ordered. Cash on Delivery.",Toast.LENGTH_SHORT).show();
                            dialog.dismiss();
                        }else{
                            dialog.dismiss();
                        }
                    }
                });
                // now that the dialog is set up, it's time to show it
                dialog.show();
                Toasty.info(CartListActivity.this,"Books have been ordered",Toast.LENGTH_SHORT,true).show();
            }
        });

    }
}
