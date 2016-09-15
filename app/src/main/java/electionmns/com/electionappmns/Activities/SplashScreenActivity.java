package electionmns.com.electionappmns.Activities;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.graphics.Typeface;
import android.net.ConnectivityManager;
import android.os.Bundle;
import android.os.Handler;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.DefaultRetryPolicy;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;
import com.crashlytics.android.Crashlytics;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.File;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

import electionmns.com.electionappmns.R;
import electionmns.com.electionappmns.Utils.AppSettings;
import io.fabric.sdk.android.Fabric;


/**
 * Created by sachin on 21/1/16.
 */
public class SplashScreenActivity extends Activity {

    private static int SPLASH_TIME_OUT = 3000;

    Context mContext;
    SQLiteDatabase mydatabase;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
//        Fabric.with(this, new Crashlytics());
        setContentView(R.layout.splashscreen);
        mContext = this;
        new Handler().postDelayed(new Runnable() {

            /*
             * Showing splash screen with a timer. This will be useful when you
             * want to show case your app logo / company
             */

            @Override
            public void run() {
                // This method will be executed once the timer is over
                // Start your app main activity
                loaddata();
                if(AppSettings.getislogin(mContext)==true){
                    Intent i = new Intent(SplashScreenActivity.this, MainActivity.class);
                    startActivity(i);
                    overridePendingTransition(R.anim.slideinright, R.anim.slideoutleft);
                }else{
                    Intent i = new Intent(SplashScreenActivity.this, RegistrationActivity.class);
                    startActivity(i);
                    overridePendingTransition(R.anim.slideinright, R.anim.slideoutleft);
                }

                // close this activity
                finish();
            }
        }, SPLASH_TIME_OUT);


    }


    public void loaddata() {


        File folder = new File("/data/data/electionmns.com.electionappmns/databases/mnskhed.db");

        //File dbfile = new File(folder.toString() + "/extras.db");

        if (folder.exists()) {
            String urldb = folder.toString() + "/mnskhed.db";
            AppSettings.setdburl(SplashScreenActivity.this, urldb);
            Intent i = new Intent(SplashScreenActivity.this, MainActivity.class);
            startActivity(i);
            overridePendingTransition(R.anim.slideoutleft, R.anim.slideinright);
            finish();
        } else {
            folder.mkdirs();
            String urldb = folder.toString() + "/mnskhed.db";
            AppSettings.setdburl(SplashScreenActivity.this, urldb);
            mydatabase = openOrCreateDatabase(urldb, MODE_PRIVATE, null);
            mydatabase.execSQL("CREATE TABLE IF NOT EXISTS dailyupdates(ID VARCHAR,TITLE VARCHAR,DESC VARCHAR,DATE VARCHAR,LOCATION VARCHAR,URLONE VARCHAR,URLTWO VARCHAR,IMGONENAME VARCHAR,IMGTWO VARCHAR);");
            mydatabase.execSQL("CREATE TABLE IF NOT EXISTS gallery(ID VARCHAR,TITLE VARCHAR,DESC VARCHAR,DATE VARCHAR,URL VARCHAR);");
            mydatabase.execSQL("CREATE TABLE IF NOT EXISTS events(ID VARCHAR,TITLE VARCHAR,DESC VARCHAR,DATE VARCHAR,YEAR VARCHAR,URL VARCHAR);");
            mydatabase.execSQL("CREATE TABLE IF NOT EXISTS partyworks(ID VARCHAR,YEAR VARCHAR,TITLE VARCHAR,NIDHI VARCHAR,AMT VARCHAR,RAWDATA VARCHAR);");
        }
    }
    private boolean isNetworkConnected() {
        ConnectivityManager cm = (ConnectivityManager) getSystemService(Context.CONNECTIVITY_SERVICE);
        return cm.getActiveNetworkInfo() != null;
    }
}
