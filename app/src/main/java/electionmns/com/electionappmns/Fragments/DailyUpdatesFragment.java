package electionmns.com.electionappmns.Fragments;

import android.app.Fragment;
import android.app.FragmentManager;
import android.app.ProgressDialog;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.graphics.Movie;
import android.net.ConnectivityManager;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
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
import org.json.JSONException;
import org.json.JSONObject;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.Date;

import electionmns.com.electionappmns.Adapters.DailyUpdateAdapters;
import electionmns.com.electionappmns.Models.DailyUpdatesModel;
import electionmns.com.electionappmns.R;
import electionmns.com.electionappmns.Utils.AppConstant;
import electionmns.com.electionappmns.Utils.AppSettings;
import electionmns.com.electionappmns.Utils.TypeFaceHelper;

/**
 * Created by Neha on 6/21/2016.
 */
public class DailyUpdatesFragment extends Fragment {
    private View view;
    private Fragment[] objFragment = {null};
    private Context mContext;
    private ProgressDialog progDailog;
    private SQLiteDatabase mydatabase;
    private ArrayList<DailyUpdatesModel> dailyUpdatesModels;
    private RecyclerView rec_recycler;
    private ProgressBar getProgDailog;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.dailyupdates, container, false);


        return view;
    }

    @Override
    public void onViewCreated(View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        mContext = getActivity().getApplicationContext();

        mydatabase = mContext.openOrCreateDatabase(AppSettings.getdburl(mContext), mContext.MODE_PRIVATE, null);
        LinearLayout partworks = (LinearLayout) view.findViewById(R.id.parent_lin_dailyupdates);
        rec_recycler = (RecyclerView) view.findViewById(R.id.rec_recycler);
        partworks.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                FragmentManager fm = getFragmentManager();
                objFragment[0] = new PartyWorksFragment();
                fm.beginTransaction().setCustomAnimations(R.anim.slideinright, R.anim.slideoutleft);
                fm.beginTransaction().replace(R.id.frame_main, objFragment[0]).commit();
            }
        });

        RelativeLayout relheader = (RelativeLayout)view.findViewById(R.id.relheader);
        relheader.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                System.out.println("--------------blank");
            }
        });

        if (isNetworkConnected() == true) {
            makelocaldbcall(null);
            Fetchinbackground fetchinbackground = new Fetchinbackground();
            fetchinbackground.execute();
        } else {
            makelocaldbcall(null);
        }

        TextView backarrow = (TextView)view.findViewById(R.id.backarrowpartyworksduTV);
        backarrow.setTypeface(TypeFaceHelper.getInstance(getActivity().getApplicationContext()).getStyleTypeFace(TypeFaceHelper.FONT_AWESOME));
        backarrow.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                FragmentManager fm = getFragmentManager();
                objFragment[0] = new HomeFragment();
                fm.beginTransaction().replace(R.id.frame_main, objFragment[0]).commit();
            }
        });

    }

    private void makelocaldbcall(JSONArray jsonArrayfromNetwork) {
        if (jsonArrayfromNetwork != null && jsonArrayfromNetwork.length() > 0) {
            String dele = "delete from dailyupdates";
            mydatabase.execSQL(dele);
            for (int i = 0; i < jsonArrayfromNetwork.length(); i++) {
                JSONObject jsonObject = jsonArrayfromNetwork.optJSONObject(i);
                if (jsonObject != null && jsonObject.length() > 0) {
                    String id = jsonObject.optString("id");
                    String title = jsonObject.optString("title");
                    String desc = jsonObject.optString("description");
                    String img1 = jsonObject.optString("image1url");
                    String img2 = jsonObject.optString("image2url");
                    String img1name = jsonObject.optString("img1name");
                    String img2name = jsonObject.optString("img2name");
                    String loc = jsonObject.optString("location");
                    String date = jsonObject.optString("datetime");
                    title = title.replace("'","");
                    desc = desc.replace("'","");
//                    String dailyUpdateQuery = "select * from dailyupdates where URLONE='"+img1+"'";
//                    Cursor cursor = mydatabase.rawQuery(dailyUpdateQuery, null);
//                    if (cursor.getCount() > 0) {
//                        if (cursor.moveToFirst()) {
//                            do {
//                                String dele = "delete from dailyupdates where URLONE='"+img1+"'";
//                                mydatabase.execSQL(dele);
//                            } while (cursor.moveToNext());
//
//                        }
//                    }

                    String insertquery = "insert into dailyupdates(ID,TITLE ,DESC,DATE,LOCATION,URLONE,URLTWO,IMGONENAME,IMGTWO) values('" + id + "','" + title + "','" + desc + "','" + date + "','" + loc + "','" + img1 + "','" + img2 + "','" + img1name + "','" + img2name + "')";
                    System.out.println("-------insertquery----" + insertquery);
                    mydatabase.execSQL(insertquery);


                }
            }
        }
        dailyUpdatesModels = new ArrayList<>();
        String dailyUpdateQuery = "select * from dailyupdates";
        Cursor cursor = mydatabase.rawQuery(dailyUpdateQuery, null);
        if (cursor.getCount() > 0) {
            if (cursor.moveToFirst()) {
                do {
                    String d_id = cursor.getString(cursor.getColumnIndex("ID"));
                    String d_date = cursor.getString(cursor.getColumnIndex("DATE"));
                    String d_title = cursor.getString(cursor.getColumnIndex("TITLE"));
                    String d_desc = cursor.getString(cursor.getColumnIndex("DESC"));
                    String d_img1 = cursor.getString(cursor.getColumnIndex("URLONE"));
                    String d_img2 = cursor.getString(cursor.getColumnIndex("URLTWO"));
                    String d_loc = cursor.getString(cursor.getColumnIndex("LOCATION"));

                    JSONObject jsonObjectDY = new JSONObject();
                    try {
                        jsonObjectDY.put("id", d_id);
                        jsonObjectDY.put("datetime", d_date);
                        jsonObjectDY.put("title", d_title);
                        jsonObjectDY.put("desc", d_desc);
                        jsonObjectDY.put("img1", d_img1);
                        jsonObjectDY.put("img2", d_img2);
                        jsonObjectDY.put("loc", d_loc);
                        DailyUpdatesModel dailyUpdatesModel = new DailyUpdatesModel(jsonObjectDY);
                        dailyUpdatesModels.add(dailyUpdatesModel);
                    } catch (JSONException e) {
                        e.printStackTrace();
                        dailyUpdatesModels = null;
                    }

                } while (cursor.moveToNext());

            }

            LinearLayoutManager linearLayoutManager = new LinearLayoutManager(mContext);
            rec_recycler.setLayoutManager(linearLayoutManager);
            rec_recycler.setHasFixedSize(true);
            if(dailyUpdatesModels!=null && dailyUpdatesModels.size()>0){
                Collections.sort(dailyUpdatesModels, new Comparator<DailyUpdatesModel>() {
                    public int compare(DailyUpdatesModel m1, DailyUpdatesModel m2) {
                        return m2.date.compareTo(m1.date);
                    }
                });
            }
            DailyUpdateAdapters mAdapter = new DailyUpdateAdapters(dailyUpdatesModels, mContext,getActivity());
            rec_recycler.setAdapter(mAdapter);
        }

    }

    public void makevolleycall() {


        RequestQueue queue = Volley.newRequestQueue(mContext);
        JsonObjectRequest req22 = new JsonObjectRequest(Request.Method.GET, AppConstant.DAILY_UPDATES_URL,
                new Response.Listener<JSONObject>() {
                    @Override
                    public void onResponse(JSONObject response) {


                        JSONArray jsonArray = response.optJSONArray("dailyupdates");
                        makelocaldbcall(jsonArray);
                        System.out.println("----------responsePendingDataSuccess------" + response.toString());

                    }
                }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                System.out.println("----------errorPendingData------" + error.getMessage());
                Toast.makeText(mContext, "Failed to load Updates", Toast.LENGTH_SHORT).show();

            }
        });
        req22.setRetryPolicy(new DefaultRetryPolicy(
                45000,
                DefaultRetryPolicy.DEFAULT_MAX_RETRIES,
                DefaultRetryPolicy.DEFAULT_BACKOFF_MULT));
        queue.add(req22);

    }

    private boolean isNetworkConnected() {
        ConnectivityManager cm = (ConnectivityManager) mContext.getSystemService(Context.CONNECTIVITY_SERVICE);

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
    public String getdate(String date){
        if(!TextUtils.isEmpty(date)){
            SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-mm-dd hh:mm:ss");
            SimpleDateFormat simpleDateFormat2 = new SimpleDateFormat("dd MMM yyyy");
            try {
                Date givendate = simpleDateFormat.parse(date);
                String dates = simpleDateFormat2.format(givendate);
                return dates;
            } catch (ParseException e) {
                e.printStackTrace();
                return "";
            }
        }
        return "";
    }
    public class Fetchinbackground extends AsyncTask<Void,Void,Void>{
        @Override
        protected Void doInBackground(Void... params) {
            makevolleycall();
            return null;
        }


    }
}
