package com.app.darshan.pushnotificationapp;

import android.app.Dialog;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.RadioButton;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.clevertap.android.sdk.CleverTapAPI;
import com.clevertap.android.sdk.exceptions.InvalidEventNameException;
import com.leanplum.Leanplum;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;

import static com.app.darshan.pushnotificationapp.MainActivity.cleverTapAPI;
import static com.app.darshan.pushnotificationapp.MainActivity.mixpanel;

/**
 * Created by darshanpania on 07/07/17.
 */

public class ViewBookActivity extends AppCompatActivity {

    ImageView cover;
    TextView name,author,publisher,price;
    Button buyNow;
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_viewbook);


        cover = (ImageView)findViewById(R.id.cover_img);
        name = (TextView)findViewById(R.id.book_name);
        author = (TextView)findViewById(R.id.book_author);
        publisher = (TextView)findViewById(R.id.book_publisher);
        price = (TextView)findViewById(R.id.book_price);
        buyNow = (Button)findViewById(R.id.buynow_btn);
        Intent intent = getIntent();
        if(intent.getExtras()!=null){
            name.setText(intent.getStringExtra("name"));
            author.setText("By : "+intent.getStringExtra("author"));
            publisher.setText(intent.getStringExtra("publisher"));
            price.setText("Price : "+intent.getStringExtra("price"));
            String url = intent.getStringExtra("imageUrl");
            Glide.with(ViewBookActivity.this).load(url).into(cover);
            HashMap<String, Object> prodViewedAction = new HashMap<String, Object>();
            prodViewedAction.put("Book Name", intent.getStringExtra("name"));
            prodViewedAction.put("Book Category", "Books");
            prodViewedAction.put("Book Author",intent.getStringExtra("author"));
            prodViewedAction.put("Book Publisher",intent.getStringExtra("publisher"));
            prodViewedAction.put("Book Price", Integer.parseInt(intent.getStringExtra("price")));
            prodViewedAction.put("Date", new java.util.Date());

            cleverTapAPI.event.push("Product viewed", prodViewedAction);

        }
        buyNow.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //Toast.makeText(ViewBookActivity.this,"Book have been ordered",Toast.LENGTH_SHORT).show();
                final Dialog dialog = new Dialog(ViewBookActivity.this);
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
                            HashMap<String, Object> chargeDetails = new HashMap<String, Object>();
                            chargeDetails.put("Amount", price.getText().toString().replace("Price : ",""));
                            chargeDetails.put("Payment Mode", "Credit card");
                            chargeDetails.put("Charged ID", (int)(Math.random()*100));

                            HashMap<String, Object> item = new HashMap<String, Object>();
                            item.put("Amount", price.getText().toString().replace("Price : ",""));
                            item.put("Payment Mode", "Credit card");
                            item.put("Charged ID", (int)(Math.random()*100));
                            item.put("Product category", "Books");
                            item.put("Book Name", name.getText().toString());
                            item.put("Book Author", author.getText().toString());
                            item.put("Book Publisher", publisher.getText().toString());
                            item.put("Quantity", 1);

                            ArrayList<HashMap<String, Object>> items = new ArrayList<HashMap<String, Object>>();
                            items.add(item);

                            try {
                                cleverTapAPI.event.push(CleverTapAPI.CHARGED_EVENT, chargeDetails, items);
                            } catch (InvalidEventNameException e) {
                                // You have to specify the first parameter to push()
                                // as CleverTapAPI.CHARGED_EVENT
                            }

                            //MixPanel
                            try {
                                JSONObject props = new JSONObject();
                                props.put("Amount", price.getText().toString());
                                props.put("Payment Mode", "Credit Card");
                                props.put("Product category", "Books");
                                props.put("Book Name", name.getText().toString());
                                props.put("Book Author", author.getText().toString());
                                props.put("Book Publisher", publisher.getText().toString());
                                props.put("Quantity", 1);
                                mixpanel.track("Charged Event - Buy", props);
                            } catch (JSONException e) {
                                Log.e("MYAPP", "Unable to add properties to JSONObject", e);
                            }
                            //LeanPLum
                            Leanplum.track(Leanplum.PURCHASE_EVENT_NAME,item);
                            Toast.makeText(ViewBookActivity.this,"Book has been ordered. Paid by credit card.",Toast.LENGTH_SHORT).show();
                            dialog.dismiss();
                        }else if(rd2.isChecked()){

                            rd1.setChecked(false);


                            HashMap<String, Object> chargeDetails = new HashMap<String, Object>();
                            chargeDetails.put("Amount", price.getText().toString().replace("Price : ",""));
                            chargeDetails.put("Payment Mode", "Cash");
                            chargeDetails.put("Charged ID", (int)(Math.random()*100));

                            HashMap<String, Object> item = new HashMap<String, Object>();
                            item.put("Amount", price.getText().toString().replace("Price : ",""));
                            item.put("Payment Mode", "Cash");
                            item.put("Charged ID", (int)(Math.random()*100));
                            item.put("Product category", "Books");
                            item.put("Book Name", name.getText().toString());
                            item.put("Book Author", author.getText().toString());
                            item.put("Book Publisher", publisher.getText().toString());
                            item.put("Quantity", 1);

                            ArrayList<HashMap<String, Object>> items = new ArrayList<HashMap<String, Object>>();
                            items.add(item);

                            try {
                                cleverTapAPI.event.push(CleverTapAPI.CHARGED_EVENT, chargeDetails, items);
                            } catch (InvalidEventNameException e) {
                                // You have to specify the first parameter to push()
                                // as CleverTapAPI.CHARGED_EVENT
                            }

                            //MixPanel
                            try {
                                JSONObject props = new JSONObject();
                                props.put("Amount", price.getText().toString());
                                props.put("Payment Mode", "Cash");
                                props.put("Product category", "Books");
                                props.put("Book Name", name.getText().toString());
                                props.put("Book Author", author.getText().toString());
                                props.put("Book Publisher", publisher.getText().toString());
                                props.put("Quantity", 1);
                                mixpanel.track("Charged Event - Buy", props);
                            } catch (JSONException e) {
                                Log.e("MYAPP", "Unable to add properties to JSONObject", e);
                            }
                            //LeanPlum
                            Leanplum.track(Leanplum.PURCHASE_EVENT_NAME,item);
                            Toast.makeText(ViewBookActivity.this,"Book has been ordered. Cash on Delivery.",Toast.LENGTH_SHORT).show();
                            dialog.dismiss();
                        }else{
                            dialog.dismiss();
                        }
                    }
                });
                // now that the dialog is set up, it's time to show it
                dialog.show();
            }
        });
    }
}
