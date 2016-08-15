package electionmns.com.electionappmns.Fragments;

import android.app.DatePickerDialog;
import android.app.Dialog;
import android.app.DialogFragment;
import android.app.Fragment;
import android.app.FragmentManager;
import android.app.ProgressDialog;
import android.content.Context;
import android.net.ConnectivityManager;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
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

import org.json.JSONArray;
import org.json.JSONObject;

import java.util.Calendar;

import electionmns.com.electionappmns.R;
import electionmns.com.electionappmns.Utils.AppConstant;
import electionmns.com.electionappmns.Utils.TypeFaceHelper;

/**
 * Created by Neha on 6/21/2016.
 */
public class JoinPartyFragment extends Fragment {
    private View view;
    private Fragment[] objFragment = {null};
    TextView dobtxt;

    private ProgressDialog progDailog;
    private Context mcContext;
    private String dob;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.joinparty,container,false);
        return view;
    }

    @Override
    public void onViewCreated(View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        mcContext = getActivity().getApplicationContext();
//        RelativeLayout partworks = (RelativeLayout)view.findViewById(R.id.partworks_rel);
//        partworks.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                FragmentManager fm = getFragmentManager();
//                objFragment[0] = new PartyWorksFragment();
//                fm.beginTransaction().setCustomAnimations(R.anim.slideinright,R.anim.slideoutleft);
//                fm.beginTransaction().replace(R.id.frame_main, objFragment[0]).commit();
//            }
//        });

        final EditText fnametxt = (EditText)view.findViewById(R.id.reg_ed_fname);
        final EditText mnametxt = (EditText)view.findViewById(R.id.reg_ed_mname);
        final EditText lnametxt = (EditText)view.findViewById(R.id.reg_ed_lname);
        dobtxt = (TextView) view.findViewById(R.id.reg_dob_fname);
        final EditText emailtxt = (EditText)view.findViewById(R.id.reg_email_fname);
        final EditText mobtxt = (EditText)view.findViewById(R.id.reg_mobile);
        final EditText add1txt = (EditText)view.findViewById(R.id.reg_add1);
        final EditText add2txt = (EditText)view.findViewById(R.id.reg_add2);

        final EditText areatxt = (EditText)view.findViewById(R.id.reg_area);
        final EditText citytxt = (EditText)view.findViewById(R.id.reg_city);
        final EditText pincodetxt = (EditText)view.findViewById(R.id.reg_pincode);
        final EditText qualtxt = (EditText)view.findViewById(R.id.reg_qual);
        final EditText occtxt = (EditText)view.findViewById(R.id.reg_occ);

        dobtxt.setText("जन्म तारीख निवडा"+"   "+mcContext.getResources().getString(R.string.calendarFA));
        dobtxt.setTypeface(TypeFaceHelper.getInstance(getActivity().getApplicationContext()).getStyleTypeFace(TypeFaceHelper.FONT_AWESOME));
        TextView backarrow = (TextView)view.findViewById(R.id.backarrowpartyworksjpTV);
        backarrow.setTypeface(TypeFaceHelper.getInstance(getActivity().getApplicationContext()).getStyleTypeFace(TypeFaceHelper.FONT_AWESOME));
        backarrow.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                FragmentManager fm = getFragmentManager();
                objFragment[0] = new HomeFragment();
                fm.beginTransaction().replace(R.id.frame_main, objFragment[0]).commit();
            }
        });

        dobtxt.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                DialogFragment newFragment = new SelectDateFragment();
                newFragment.show(getFragmentManager(), "DatePicker");
            }
        });

        Button btnsubmit = (Button)view.findViewById(R.id.btnr_submit);
        btnsubmit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(isNetworkConnected()==true){
                    final String url="http://mylocalguru.in/politicalparty/api/json/joinparty?politicalpartyid="
                            + AppConstant.PARTY_KEY+"&firstname="
                            +fnametxt.getText().toString().trim()+"&middlename="
                            +mnametxt.getText().toString().trim()+"&lastname="+
                            lnametxt.getText().toString().trim()+"&dateofbirth="
                            +dob+"&emailid="
                            +emailtxt.getText().toString().trim()+"&mobile="
                            +mobtxt.getText().toString().trim()+"&address1="
                            +add1txt.getText().toString().trim()+"&address2="
                            +add2txt.getText().toString()+"&area="
                            +areatxt.getText().toString().trim()+"&city="
                            +citytxt.getText().toString().trim()+"&state=maharashtra&pincode="
                            +pincodetxt.getText().toString().trim()+"&qualification="
                            +qualtxt.getText().toString().trim()+"&occupation="
                            +occtxt.getText().toString().trim()+"";
                    if(fnametxt.getText().toString().trim().equals("") ||
                            mnametxt.getText().toString().trim().equals("") ||
                            lnametxt.getText().toString().trim().equals("") ||
                            dob.equals("") ||
                            emailtxt.getText().toString().trim().equals("") ||
                            mobtxt.getText().toString().trim().equals("") ||
                            add1txt.getText().toString().trim().equals("") ||
                            add2txt.getText().toString().equals("") ||
                            areatxt.getText().toString().trim().equals("") ||
                            citytxt.getText().toString().trim().equals("") ||
                            pincodetxt.getText().toString().trim().equals("") ||
                            qualtxt.getText().toString().trim().equals("") ||
                            occtxt.getText().toString().trim().equals("")){
                        Toast.makeText(mcContext,"All fields are mandatory",Toast.LENGTH_SHORT).show();
                    }else{
                        System.out.println("-------------url---"+url);
                        makevolleycall(url);
                    }

                }else{
                    Toast.makeText(mcContext,"Check internet connection",Toast.LENGTH_SHORT).show();
                }
            }
        });


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
            String monthString="";
            String dayString="";
            if (month < 10) {
                monthString = "0" + Integer.toString(month);
            }else{
                monthString = Integer.toString(month);
            }

            if (day < 10) {
                dayString = "0" + Integer.toString(day);
            }else{
                dayString = Integer.toString(day);
            }
            dob=dayString + "/" + monthString + "/" + year;
            dobtxt.setText("Date of birth: " + dayString + "/" + monthString + "/" + year);
        }

    }
public void makevolleycall(String url){
    loadprogressbar();
    RequestQueue queue = Volley.newRequestQueue(mcContext);
    final JsonObjectRequest req22 = new JsonObjectRequest(Request.Method.GET, url,
            new Response.Listener<JSONObject>() {
                @Override
                public void onResponse(JSONObject response) {
                    stoploadprogress();
                    if(response!=null && response.length()>0 && response.has("success")){
                        String success = response.optString("success");
                        String msg = response.optString("msg");
                        Toast.makeText(mcContext,msg.toUpperCase(),Toast.LENGTH_SHORT).show();
                        if(success.equals("true")){
                            FragmentManager fm = getFragmentManager();
                            objFragment[0] = new HomeFragment();
                            fm.beginTransaction().setCustomAnimations(R.anim.slideinright,R.anim.slideoutleft);
                            fm.beginTransaction().replace(R.id.frame_main, objFragment[0]).commit();
                        }
                    }


                    System.out.println("----------responsejoinpartySuccess------" + response.toString());

                }
            }, new Response.ErrorListener() {
        @Override
        public void onErrorResponse(VolleyError error) {
            System.out.println("----------errorPendingData------" + error.getMessage());
            Toast.makeText(mcContext, "Failed to register", Toast.LENGTH_SHORT).show();
            stoploadprogress();
        }
    });
    req22.setRetryPolicy(new DefaultRetryPolicy(
            45000,
            DefaultRetryPolicy.DEFAULT_MAX_RETRIES,
            DefaultRetryPolicy.DEFAULT_BACKOFF_MULT));
    queue.add(req22);

}

    private boolean isNetworkConnected() {
        ConnectivityManager cm = (ConnectivityManager) mcContext.getSystemService(Context.CONNECTIVITY_SERVICE);

        return cm.getActiveNetworkInfo() != null;
    }

    public void loadprogressbar() {
        if (progDailog == null) {
            progDailog = new ProgressDialog(getActivity());
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


}
