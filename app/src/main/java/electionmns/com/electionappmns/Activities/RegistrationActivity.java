package electionmns.com.electionappmns.Activities;

import android.app.Activity;
import android.app.DatePickerDialog;
import android.app.Dialog;
import android.app.DialogFragment;
import android.app.FragmentTransaction;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.net.ConnectivityManager;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.DefaultRetryPolicy;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONObject;

import java.util.Calendar;

import electionmns.com.electionappmns.R;
import electionmns.com.electionappmns.Utils.AppConstant;
import electionmns.com.electionappmns.Utils.AppSettings;
import electionmns.com.electionappmns.Utils.TypeFaceHelper;

/**
 * Created by Neha on 7/23/2016.
 */
public class RegistrationActivity extends Activity {
    String partyid;
    String fname;
    String mname;
    String lname;
    String dob;
    String email;
    String mob;
    String add1;
    String add2;
    String area;
    String city;
    String state = "maharashtra";
    String pincode;
    private ProgressDialog progDailog;
    private Context mContext;
    private TextView dobtxt;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.register);
        mContext = this;
        final EditText fnametxt = (EditText) findViewById(R.id.regg_ed_fname);
        final EditText mnametxt = (EditText) findViewById(R.id.regg_ed_mname);
        final EditText lnametxt = (EditText) findViewById(R.id.regg_ed_lname);
        dobtxt = (TextView) findViewById(R.id.regg_dob_fname);
        final EditText emailtxt = (EditText) findViewById(R.id.regg_email_fname);
        final EditText mobtxt = (EditText) findViewById(R.id.regg_mobile);
        final EditText add1txt = (EditText) findViewById(R.id.regg_add1);
        final EditText add2txt = (EditText) findViewById(R.id.regg_add2);

        final EditText areatxt = (EditText) findViewById(R.id.regg_area);
        final EditText citytxt = (EditText) findViewById(R.id.regg_city);
        final EditText pincodetxt = (EditText) findViewById(R.id.regg_pincode);
//        final EditText qualtxt = (EditText)findViewById(R.id.regg_qual);
//        final EditText occtxt = (EditText)findViewById(R.id.regg_occ);
        dobtxt.setText("जन्म तारीख निवडा"+"   "+mContext.getResources().getString(R.string.calendarFA));
        dobtxt.setTypeface(TypeFaceHelper.getInstance(mContext).getStyleTypeFace(TypeFaceHelper.FONT_AWESOME));
        dobtxt.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                DialogFragment newFragment = new SelectDateFragment();
                newFragment.show(getFragmentManager(), "DatePicker");
            }
        });
        Button btnsubmit = (Button) findViewById(R.id.btng_submit);
        btnsubmit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                fname = fnametxt.getText().toString().trim();
                mname = mnametxt.getText().toString().trim();
                lname = lnametxt.getText().toString().trim();

                email = emailtxt.getText().toString().trim();
                mob = mobtxt.getText().toString().trim();

                add1 = add1txt.getText().toString().trim();
                add2 = add2txt.getText().toString();
                area = areatxt.getText().toString().trim();
                city = citytxt.getText().toString().trim();
                pincode = pincodetxt.getText().toString().trim();
                if (isNetworkConnected() == true) {
                    if (fnametxt.getText().toString().trim().equals("") ||
                            mnametxt.getText().toString().trim().equals("") ||
                            lnametxt.getText().toString().trim().equals("") ||
                            dobtxt.getText().toString().trim().equals("") ||
                            emailtxt.getText().toString().trim().equals("") ||
                            mobtxt.getText().toString().trim().equals("") ||
                            add1txt.getText().toString().trim().equals("") ||
                            add2txt.getText().toString().equals("") ||
                            areatxt.getText().toString().trim().equals("") ||
                            citytxt.getText().toString().trim().equals("") ||
                            pincodetxt.getText().toString().trim().equals("")) {
                        Toast.makeText(mContext, "All fields are mandatory", Toast.LENGTH_SHORT).show();
                    } else {
                        if (isValidMobile(mob) == true) {
                            String url = "http://mylocalguru.in/politicalparty/api/json/registration?politicalpartyid=" + AppConstant.PARTY_KEY + "&firstname=" + fname + "&middlename=" + mname + "&lastname=" + lname + "&dateofbirth=" + dob + "&emailid=" + email + "&mobile=" + mob + "&address1=" + add1 + "&address2=" + add2 + "&area=" + area + "&city=" + city + "&state=" + state + "&pincode=" + pincode;
                            System.out.println("-------------urlReg---" + url);
                            registeruser(url);
                        } else {
                            Toast.makeText(mContext, "Enter a valid mobile number", Toast.LENGTH_SHORT).show();
                        }

                    }
                } else {

                    Toast.makeText(mContext, "Please Connect to Internet", Toast.LENGTH_SHORT).show();
                }
            }
        });

    }

    public void registeruser(String url) {

        loadprogressbar();
        RequestQueue queue = Volley.newRequestQueue(mContext);
        JsonObjectRequest req22 = new JsonObjectRequest(Request.Method.POST, url,
                new Response.Listener<JSONObject>() {
                    @Override
                    public void onResponse(JSONObject response) {
                        stoploadprogress();
                        if (response != null && response.length() > 0 && response.has("success")) {
                            String success = response.optString("success");
                            String msg = response.optString("msg");
                            Toast.makeText(mContext, msg.toUpperCase(), Toast.LENGTH_SHORT).show();
                            if (success.equals("true")) {
                                AppSettings.setisLogin(mContext,true);
                                moveandfinish();
                            }else{
                                AppSettings.setisLogin(mContext,false);
                            }
                        }


                    System.out.println("----------success------"+response.toString());

                }
    }

    ,new Response.ErrorListener()

    {
        @Override
        public void onErrorResponse (VolleyError error){
        System.out.println("----------error------" + error.getMessage());
        stoploadprogress();

    }
    }

    );
    queue.add(req22);

}

    public void loadprogressbar() {
        if (progDailog == null) {
            progDailog = new ProgressDialog(RegistrationActivity.this);
        }
        if (progDailog.isShowing() == false) {
            progDailog.setMessage("Saving, Please wait...");
            progDailog.setProgressStyle(ProgressDialog.STYLE_SPINNER);
            progDailog.setCancelable(true);
            progDailog.show();
        }
    }

    public void stoploadprogress() {

        if (progDailog.isShowing() == true) {
            progDailog.dismiss();
        }

    }

    public void moveandfinish() {
        Intent i = new Intent(RegistrationActivity.this, MainActivity.class);
        startActivity(i);
        overridePendingTransition(R.anim.slideoutleft, R.anim.slideinright);
        finish();
    }

    private boolean isNetworkConnected() {
        ConnectivityManager cm = (ConnectivityManager) mContext.getSystemService(Context.CONNECTIVITY_SERVICE);

        return cm.getActiveNetworkInfo() != null;
    }

public class SelectDateFragment extends DialogFragment implements DatePickerDialog.OnDateSetListener {

    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {
        final Calendar calendar = Calendar.getInstance();
        int yy = calendar.get(Calendar.YEAR);
        int mm = calendar.get(Calendar.MONTH);
        int dd = calendar.get(Calendar.DAY_OF_MONTH);
        return new DatePickerDialog(getActivity(), this, yy, mm, dd);
    }

    public void onDateSet(DatePicker view, int yy, int mm, int dd) {
        populateSetDate(yy, mm + 1, dd);
    }

    public void populateSetDate(int year, int month, int day) {
        String monthString = "";
        String dayString = "";
        if (month < 10) {
            monthString = "0" + Integer.toString(month);
        } else {
            monthString = Integer.toString(month);
        }

        if (day < 10) {
            dayString = "0" + Integer.toString(day);
        } else {
            dayString = Integer.toString(day);
        }
        dob= dayString + "/" + monthString + "/" + year;
        dobtxt.setText("Date of birth: " + dayString + "/" + monthString + "/" + year);
    }

}

    private boolean isValidMobile(String phone) {
        return android.util.Patterns.PHONE.matcher(phone).matches();
    }
}
