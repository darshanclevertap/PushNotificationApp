package com.app.darshan.pushnotificationapp;

import android.app.NotificationManager;
import android.content.Intent;
import android.content.SharedPreferences;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.amplitude.api.Amplitude;
import com.clevertap.android.sdk.CleverTapAPI;
import com.clevertap.android.sdk.exceptions.CleverTapMetaDataNotFoundException;
import com.clevertap.android.sdk.exceptions.CleverTapPermissionsNotSatisfied;
import com.facebook.AccessToken;
import com.facebook.CallbackManager;
import com.facebook.FacebookSdk;
import com.google.android.gms.auth.api.Auth;
import com.google.android.gms.auth.api.signin.GoogleSignInAccount;
import com.google.android.gms.auth.api.signin.GoogleSignInOptions;
import com.google.android.gms.auth.api.signin.GoogleSignInResult;
import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.SignInButton;
import com.google.android.gms.common.api.GoogleApiClient;
import com.leanplum.Leanplum;
import com.mixpanel.android.mpmetrics.MixpanelAPI;

import org.json.JSONException;
import org.json.JSONObject;

import java.net.MalformedURLException;
import java.net.URL;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;

import es.dmoral.toasty.Toasty;

public class MainActivity extends AppCompatActivity {

    public static CleverTapAPI cleverTapAPI;
    TextView title,body;
    Button button,button1,button2,button3,button4,button5;
    private static String TAG = "PushLog";
    ArrayList<Books> booksArrayList = new ArrayList<>();
    //RemoteViews contentView;
    CallbackManager callbackManager;
    AccessToken accessToken;
    SharedPreferences sharedPreferences;
    SharedPreferences.Editor editor;
    public static MixpanelAPI mixpanel;
    Intent intent;
    public static int RC_SIGN_IN=1234;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        GoogleSignInOptions gso = new GoogleSignInOptions.Builder(GoogleSignInOptions.DEFAULT_SIGN_IN)
                .requestEmail()
                .build();

        final GoogleApiClient mGoogleApiClient = new GoogleApiClient.Builder(this)
                .enableAutoManage(this /* FragmentActivity */, new GoogleApiClient.OnConnectionFailedListener() {
                    @Override
                    public void onConnectionFailed(@NonNull ConnectionResult connectionResult) {

                    }
                } /* OnConnectionFailedListener */)
                .addApi(Auth.GOOGLE_SIGN_IN_API, gso)
                .build();

        Bundle bundle = getIntent().getExtras();
        String projectToken = "aa669758893492ce0226414ff08e27ac"; // e.g.: "1ef7e30d2a58d27f4b90c42e31d6d7ad"
         mixpanel = MixpanelAPI.getInstance(this, projectToken);
        mixpanel.getPeople().identify("1234");

        //LeanPLum
        Leanplum.setUserId("1234");

        //Amplitude
        Amplitude.getInstance().initialize(this, "f44afc86d8fe90bb2f49e9e1fc18e399").enableForegroundTracking(getApplication());
        //mixpanel.getPeople().getPushRegistrationId();
        FacebookSdk.sdkInitialize(this.getApplicationContext());
        try {
            //CleverTapAPI.setDebugLevel(1277182231);
            CleverTapAPI.setDebugLevel(3);
            cleverTapAPI = CleverTapAPI.getInstance(getApplicationContext());
            cleverTapAPI.createNotificationChannel(getApplicationContext(),"Testing","Testing","Testing",NotificationManager.IMPORTANCE_MAX,true);
            //MyApplication.startTimer();
           // cleverTapAPI.data.pushFcmRegistrationId(FirebaseInstanceId.getInstance().getToken(),true);

        } catch (CleverTapMetaDataNotFoundException e) {
            // thrown if you haven't specified your CleverTap Account ID or Token in your AndroidManifest.xml
            Log.e(TAG,e.toString());
        } catch (CleverTapPermissionsNotSatisfied e) {
            // thrown if you haven’t requested the required permissions in your AndroidManifest.xml
            Log.e(TAG,e.toString());
        }

        Toasty.Config.getInstance()
                .setErrorColor(getResources().getColor(android.R.color.holo_red_light)) // optional
                .setInfoColor(getResources().getColor(android.R.color.holo_blue_bright)) // optional
                .setSuccessColor(getResources().getColor(android.R.color.holo_green_light)) // optional
                .setWarningColor(getResources().getColor(android.R.color.holo_orange_light)) // optional
                .setTextSize(18) // optional
                .apply(); // required

        setContentView(R.layout.activity_main);
        title = (TextView) findViewById(R.id.title);
        body = (TextView)findViewById(R.id.body);
        button = (Button) findViewById(R.id.button);
        button.setVisibility(View.VISIBLE);
        button1 = (Button) findViewById(R.id.button1);
        button2 = (Button) findViewById(R.id.button2);
        button3 = (Button) findViewById(R.id.button3);
        button4 = (Button) findViewById(R.id.button4);
        button5 = (Button) findViewById(R.id.button5);
        SignInButton signInButton = (SignInButton) findViewById(R.id.sign_in_button);
        signInButton.setSize(SignInButton.SIZE_STANDARD);
        signInButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent signInIntent = Auth.GoogleSignInApi.getSignInIntent(mGoogleApiClient);
                startActivityForResult(signInIntent, RC_SIGN_IN);
            }
        });
        button1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Intent webIntent = new Intent(MainActivity.this, WebViewActivity.class);
                startActivity(webIntent);

            }
        });

        button2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                 intent = new Intent(MainActivity.this,MyService.class);
                startService(intent);
//                Intent intent = new Intent(Settings.ACTION_CHANNEL_NOTIFICATION_SETTINGS);
//                intent.putExtra(Settings.EXTRA_CHANNEL_ID, "Testing");
//                intent.putExtra(Settings.EXTRA_APP_PACKAGE, getPackageName());
//                startActivity(intent);
                HashMap<String, Object> profileUpdate = new HashMap<String, Object>();
                profileUpdate.put("Name", "Darshan");                  // String
                profileUpdate.put("Identity", 12345);                    // String or number
                profileUpdate.put("Email", "darshanpania@gmail.com");               // Email address of the user
                profileUpdate.put("Phone", "9167605320");                 // Phone (with the country code, starting with +)
                profileUpdate.put("Gender", "M");                           // Can be either M or F
                profileUpdate.put("Employed", "Y");                         // Can be either Y or N
                profileUpdate.put("Education", "Graduate");                 // Can be either Graduate, College or School
                profileUpdate.put("Married", "N");                          // Can be either Y or N
                profileUpdate.put("DOB", new Date());                       // Date of Birth. Set the Date object to the appropriate value first
                profileUpdate.put("Age", 28);                               // Not required if DOB is set
                profileUpdate.put("Tz", "Asia/Kolkata");                    //an abbreviation such as "PST", a full name such as "America/Los_Angeles",
                //or a custom ID such as "GMT-8:00"
                //profileUpdate.put("Photo", "www.foobar.com/image.jpeg");    // URL to the Image

// optional fields. controls whether the user will be sent email, push etc.
                profileUpdate.put("MSG-email", true);                      // Disable email notifications
                profileUpdate.put("MSG-push", true);                        // Enable push notifications
                profileUpdate.put("MSG-sms", true);                        // Disable SMS notifications
                String[] usage = {"Mobile","Web","Watch"};
                ArrayList<String> usages = new ArrayList<String>();
                usages.add("Mobile");
                usages.add("Web");
                profileUpdate.put("Usage",usages);
                cleverTapAPI.profile.push(profileUpdate);

                ArrayList<String> stuff = new ArrayList<String>();
                stuff.add("bag");
                stuff.add("shoes");
                cleverTapAPI.profile.setMultiValuesForKey("MyStuff", stuff);


            }
        });

        button3.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                stopService(intent);
            }
        });
        MyOnClickListener myOnClickListener = new MyOnClickListener("Click Button", MainActivity.this){
            @Override
            public void onClick(View v) {
                super.onClick(v);
                Intent webIntent = new Intent(MainActivity.this, ButtonActivity.class);
                startActivity(webIntent);
            }
        };
        button4.setOnClickListener(myOnClickListener);



        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                if(booksArrayList.size()>0)
                    booksArrayList.clear();
                Books book1 = new Books();
                book1.setName("1984");
                book1.setAuthor("George Orwell");
                book1.setPublisher("CT Publications");
                book1.setPrice("200");
                book1.setImageURL("http://forreadingaddicts.co.uk/wp-content/uploads/2015/06/1984-e1458599761325.jpg");
                booksArrayList.add(book1);
                Books book2 = new Books();
                book2.setName("Animal Farm");
                book2.setAuthor("George Orwell");
                book2.setPublisher("CT Publications");
                book2.setPrice("200");
                book2.setImageURL("http://forreadingaddicts.co.uk/wp-content/uploads/2015/04/animal-farm.jpg");
                booksArrayList.add(book2);
                Books book3 = new Books();
                book3.setName("Fahrenheit 451");
                book3.setAuthor("Ray Bradbury");
                book3.setPublisher("CT Publications");
                book3.setPrice("200");
                book3.setImageURL("http://forreadingaddicts.co.uk/wp-content/uploads/2015/09/Fahrenheit-451-small.jpg");
                booksArrayList.add(book3);
                Books book4 = new Books();
                book4.setName("The Hunger Games");
                book4.setAuthor("Suzanne Collins");
                book4.setPublisher("CT Publications");
                book4.setPrice("300");
                book4.setImageURL("http://forreadingaddicts.co.uk/wp-content/uploads/2015/11/suzanne-collins-hunger-games-small.jpg");
                booksArrayList.add(book4);
                Books book5 = new Books();
                book5.setName("Prisoner of Azkaban");
                book5.setAuthor("JK Rowling");
                book5.setPublisher("CT Publications");
                book5.setPrice("200");
                book5.setImageURL("https://i1.wp.com/brocsbookcase.com/wp-content/uploads/2013/08/harry-potter-prisoner-of-azkaban.jpg?fit=200%2C300");
                booksArrayList.add(book5);
                Books book6 = new Books();
                book6.setName("Deathly Hallows");
                book6.setAuthor("JK Rowling");
                book6.setPublisher("CT Publications");
                book6.setPrice("400");
                book6.setImageURL("http://thebooksmugglers.com/wp-content/uploads/2009/07/hp7-200x300.jpg");
                booksArrayList.add(book6);
                Books book7 = new Books();
                book7.setName("Lord Of The Rings");
                book7.setAuthor("JRR Tolkien");
                book7.setPublisher("CT Publications");
                book7.setPrice("600");
                book7.setImageURL("https://nicolaalter.files.wordpress.com/2015/10/lotr.jpg?w=200&h=300");
                booksArrayList.add(book7);
                Books book8 = new Books();
                book8.setName("1Q84");
                book8.setAuthor("Haruki Murakami");
                book8.setPublisher("CT Publications");
                book8.setPrice("600");
                book8.setImageURL("https://nyoobserver.files.wordpress.com/2011/10/1q84uk1.jpg?quality=80&w=635");
                booksArrayList.add(book8);
                Books book9 = new Books();
                book9.setName("Women");
                book9.setAuthor("Charles Bukowski");
                book9.setPublisher("CT Publications");
                book9.setPrice("200");
                book9.setImageURL("https://s-media-cache-ak0.pinimg.com/564x/c8/12/d7/c812d7eaf174066afaed5e655e358dbb.jpg");
                booksArrayList.add(book9);
                Books book10 = new Books();
                book10.setName("Slaughter House V");
                book10.setAuthor("Kurt Vonnegut");
                book10.setPublisher("CT Publications");
                book10.setPrice("400");
                book10.setImageURL("http://forreadingaddicts.co.uk/wp-content/uploads/2016/03/MAIN_Tom_McQuaid_slaughterhouse_five_cover_1.png");
                booksArrayList.add(book10);
                Books book11 = new Books();
                book11.setName("The Catcher In The Rye");
                book11.setAuthor("JD Salinger");
                book11.setPublisher("CT Publications");
                book11.setPrice("200");
                book11.setImageURL("https://render.fineartamerica.com/images/rendered/search/print/images/artworkimages/medium/1/the-catcher-in-the-rye-book-cover-movie-poster-art-3-nishanth-gopinathan.jpg");
                booksArrayList.add(book11);
                Books book12 = new Books();
                book12.setName("Room On The Roof");
                book12.setAuthor("Ruskin Bond");
                book12.setPublisher("CT Publications");
                book12.setPrice("200");
                book12.setImageURL("http://forreadingaddicts.co.uk/wp-content/uploads/2016/03/Ruskin-Bond-Room-on-the-Roof-small-200x300.jpg");
                booksArrayList.add(book12);

                Intent intent1 = new Intent(MainActivity.this,BookListActivity.class);
                intent1.putParcelableArrayListExtra("BooksList",booksArrayList);
                startActivity(intent1);
            }
        });

    }


  @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        // Result returned from launching the Intent from GoogleSignInApi.getSignInIntent(...);
        if (requestCode == RC_SIGN_IN) {
            GoogleSignInResult result = Auth.GoogleSignInApi.getSignInResultFromIntent(data);
            if (result.isSuccess()) {
                // Signed in successfully, show authenticated UI.
                GoogleSignInAccount acct = result.getSignInAccount();
                //mStatusTextView.setText(getString(R.string.signed_in_fmt, acct.getDisplayName()));
                //updateUI(true);
            } else {
                // Signed out, show unauthenticated UI.
               // updateUI(false);
            }
        }
    }


    private void getFacebookData(JSONObject object) {

        try {
            Bundle bundle = new Bundle();
            String id = object.getString("id");

            try {
                URL profile_pic = new URL("https://graph.facebook.com/" + id + "/picture?width=200&height=150");
                Log.e("profile_pic", profile_pic + "");
                bundle.putString("profile_pic", profile_pic.toString());

            } catch (MalformedURLException e) {
                e.printStackTrace();
                //return null;
            }

            bundle.putString("idFacebook", id);
            if (object.has("first_name"))
                bundle.putString("first_name", object.getString("first_name"));
            if (object.has("last_name"))
                bundle.putString("last_name", object.getString("last_name"));
            if (object.has("email"))
                bundle.putString("email", object.getString("email"));
            if (object.has("gender"))
                bundle.putString("gender", object.getString("gender"));
            if (object.has("birthday"))
                bundle.putString("birthday", object.getString("birthday"));
            if (object.has("location"))
                bundle.putString("location", object.getJSONObject("location").getString("name"));
            if(object.has("relationship_status"))
                bundle.putString("relationship_status",object.getString("relationship_status"));
            pushProfileEvent(object);

            //return bundle;
        }
        catch(JSONException e) {
            Log.d(TAG,"Error parsing JSON");
        }
        //return null;
    }

    public void pushProfileEvent(JSONObject object){
        try{
            HashMap<String, Object> profileUpdate = new HashMap<String, Object>();
            if (object.has("first_name"))
                profileUpdate.put("Name", object.getString("first_name"));                  // String

            profileUpdate.put("Identity", object.getString("id"));                    // String or number

            if (object.has("email"))
                profileUpdate.put("Email", object.getString("email"));               // Email address of the user

            //profileUpdate.put("Phone", "+14155551234");                 // Phone (with the country code, starting with +)
            if (object.has("gender"))
                profileUpdate.put("Gender", object.getString("gender"));                           // Can be either M or F
            //profileUpdate.put("Employed", "Y");                         // Can be either Y or N
            //profileUpdate.put("Education", "Graduate");                 // Can be either Graduate, College or School
            //profileUpdate.put("Married", "Y");                          // Can be either Y or N
            if (object.has("birthday"))
                profileUpdate.put("DOB", new Date(object.getString("birthday")));                       // Date of Birth. Set the Date object to the appropriate value first
            //profileUpdate.put("Age", 28);                               // Not required if DOB is set
            //profileUpdate.put("Tz", "Asia/Kolkata");                    //an abbreviation such as "PST", a full name such as "America/Los_Angeles",
            //or a custom ID such as "GMT-8:00"

            profileUpdate.put("Photo", "https://graph.facebook.com/" + object.getString("id") + "/picture?width=300&height=300");    // URL to the Image

            // optional fields. controls whether the user will be sent email, push etc.
            //profileUpdate.put("MSG-email", false);                      // Disable email notifications
            profileUpdate.put("MSG-push", true);                        // Enable push notifications
            //profileUpdate.put("MSG-sms", false);                        // Disable SMS notifications

            cleverTapAPI.profile.push(profileUpdate);
            mixpanel.getPeople().setMap(profileUpdate);
            //mixpanel.getPeople().set("$email",object.getString("email"));
            Leanplum.setUserAttributes(profileUpdate);

        }
        catch (Exception e){

        }
    }


    @Override
    protected void onDestroy() {
        super.onDestroy();
       // MyApplication.stopTimer();
    }
}
