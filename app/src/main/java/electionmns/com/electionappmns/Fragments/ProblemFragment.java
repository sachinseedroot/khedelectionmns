package electionmns.com.electionappmns.Fragments;

import android.app.Fragment;
import android.app.FragmentManager;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.graphics.Color;
import android.graphics.Typeface;
import android.net.ConnectivityManager;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import electionmns.com.electionappmns.Activities.EndeveourDetailsActivity;
import electionmns.com.electionappmns.R;
import electionmns.com.electionappmns.Utils.AppConstant;
import electionmns.com.electionappmns.Utils.AppSettings;
import electionmns.com.electionappmns.Utils.TypeFaceHelper;

/**
 * Created by Neha on 6/22/2016.
 */
public class ProblemFragment extends Fragment {

    private View view;
    private Typeface fontawesome;
    private Fragment[] objFragment = {null};
    private LinearLayout list_topics_lin;
    private Context mcontext;
    private ProgressDialog progDailog;
    private SQLiteDatabase mydatabase;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.partyend, container, false);
        return view;
    }

    @Override
    public void onViewCreated(View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        mcontext = getActivity().getApplicationContext();
        mydatabase = mcontext.openOrCreateDatabase(AppSettings.getdburl(mcontext), mcontext.MODE_PRIVATE, null);

        fontawesome = TypeFaceHelper.getInstance(mcontext).getStyleTypeFace(TypeFaceHelper.FONT_AWESOME);
        TextView textViewbackpress = (TextView) view.findViewById(R.id.backarrowpartyworksTV);
        textViewbackpress.setTypeface(fontawesome);
        textViewbackpress.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                FragmentManager fm = getFragmentManager();
                objFragment[0] = new HomeFragment();
                fm.beginTransaction().replace(R.id.frame_main, objFragment[0]).commit();
            }
        });
        list_topics_lin = (LinearLayout) view.findViewById(R.id.list_topics_lin);


        loadlist();
        if (isNetworkConnected() == true) {
            getParttyWorkds(AppConstant.PARTYENDEVOUR);
        }


    }

    public void getParttyWorkds(String url) {

        // loadprogressbar();
        RequestQueue queue = Volley.newRequestQueue(mcontext);
        JsonObjectRequest req22 = new JsonObjectRequest(Request.Method.GET, url,
                new Response.Listener<JSONObject>() {
                    @Override
                    public void onResponse(JSONObject response) {
                        // stoploadprogress();
                        System.out.println("==========party works=====" + response.toString());
                        JSONArray jsonArrayResposne = response.optJSONArray("endeavour");
                        if (jsonArrayResposne != null && jsonArrayResposne.length() > 0) {
                            for (int i = 0; i < jsonArrayResposne.length(); i++) {
                                JSONObject singleobect = jsonArrayResposne.optJSONObject(i);
                                if (singleobect != null && singleobect.length() > 0) {
                                    String id = singleobect.optString("id");
                                    String year = singleobect.optString("projectstartyear");
                                    String nidhi = singleobect.optString("nidhi");
                                    String amount = singleobect.optString("amount");
                                    String title = singleobect.optString("title");
                                    String news = singleobect.toString();

                                    makeinsertcall(id, year, title, nidhi, amount, news);
                                }
                            }

                            if (ProblemFragment.this.isVisible()) {
                                loadlist();
                            } else {
                                System.out.println("--------saved-||-list not loaded as page not visible-----");
                            }
                        }

                    }
                }

                , new Response.ErrorListener()

        {
            @Override
            public void onErrorResponse(VolleyError error) {
                System.out.println("----------errorPartyWorks------" + error.getMessage());
                stoploadprogress();

            }
        }

        );
        queue.add(req22);

    }

    private void loadlist() {
        list_topics_lin.removeAllViews();
        String partyworksQuery = "select * from partyworks";
        Cursor cursor = mydatabase.rawQuery(partyworksQuery, null);
        JSONArray jsonArrayPartyEnd = new JSONArray();
        if (cursor.getCount() > 0) {
            if (cursor.moveToFirst()) {
                do {
                    String id = cursor.getString(cursor.getColumnIndex("ID"));
                    String year = cursor.getString(cursor.getColumnIndex("YEAR"));
                    String title = cursor.getString(cursor.getColumnIndex("TITLE"));
                    String nidhi = cursor.getString(cursor.getColumnIndex("NIDHI"));
                    String amt = cursor.getString(cursor.getColumnIndex("AMT"));
                    //String year = cursor.getString(cursor.getColumnIndex("YEAR"));
                    try {
                        JSONObject jsonObjectPE = new JSONObject();
                        jsonObjectPE.put("year", year);
                        jsonObjectPE.put("title", title);
                        jsonObjectPE.put("nidhi", nidhi);
                        jsonObjectPE.put("amt", amt);
                        jsonObjectPE.put("id", id);
                        jsonArrayPartyEnd.put(jsonObjectPE);

                    } catch (JSONException e) {
                        e.printStackTrace();
                    }

                } while (cursor.moveToNext());

            }
        }
        cursor.close();
        if (jsonArrayPartyEnd != null && jsonArrayPartyEnd.length() > 0) {
            RelativeLayout.LayoutParams layoutParamsRel = new RelativeLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, 80);
            layoutParamsRel.setMargins(15, 15, 15, 15);

            RelativeLayout.LayoutParams layoutParamsTV = new RelativeLayout.LayoutParams(ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.WRAP_CONTENT);
            layoutParamsTV.addRule(RelativeLayout.ALIGN_PARENT_LEFT);
            layoutParamsTV.addRule(RelativeLayout.CENTER_VERTICAL);
            layoutParamsTV.setMargins(20, 0, 0, 0);

            RelativeLayout.LayoutParams layoutParamsArrowTV = new RelativeLayout.LayoutParams(ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.WRAP_CONTENT);
            layoutParamsArrowTV.addRule(RelativeLayout.ALIGN_PARENT_RIGHT);
            layoutParamsArrowTV.addRule(RelativeLayout.CENTER_VERTICAL);
            layoutParamsArrowTV.setMargins(0, 0, 25, 0);
            for (int i = 0; i < jsonArrayPartyEnd.length(); i++) {
                JSONObject singlenews = jsonArrayPartyEnd.optJSONObject(i);
                if(singlenews!=null && singlenews.length()>0) {
                    String yearText = singlenews.optString("year");
                    RelativeLayout listrel = new RelativeLayout(mcontext);
                    listrel.setLayoutParams(layoutParamsRel);
                    listrel.setBackgroundColor(mcontext.getResources().getColor(R.color.deepsaffron));

                    TextView valueTV = new TextView(mcontext);
                    valueTV.setTextColor(mcontext.getResources().getColor(R.color.white));
                    valueTV.setText(yearText);
                    valueTV.setTypeface(fontawesome);
                    valueTV.setTextSize(18f);

                    TextView rightbackarow = new TextView(mcontext);
                    rightbackarow.setTextColor(mcontext.getResources().getColor(R.color.white));
                    rightbackarow.setText(mcontext.getResources().getString(R.string.fa_right_arrow));
                    rightbackarow.setTypeface(fontawesome);
                    rightbackarow.setTextSize(20f);

                    valueTV.setLayoutParams(layoutParamsTV);
                    rightbackarow.setLayoutParams(layoutParamsArrowTV);
                    listrel.addView(valueTV);
                    listrel.addView(rightbackarow);
                    list_topics_lin.addView(listrel);
                    View v = new View(mcontext);
                    v.setLayoutParams(new LinearLayout.LayoutParams(
                            ViewGroup.LayoutParams.MATCH_PARENT,
                            5
                    ));
                    v.setBackgroundColor(Color.parseColor("#FFFFFF"));

                    list_topics_lin.addView(v);

                    listrel.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {

                            RelativeLayout relativeLayout = (RelativeLayout) v;
                            TextView textView = (TextView) relativeLayout.getChildAt(0);
                            String year = textView.getText().toString();
                            System.out.println("------------year-----------" + year);
                            Intent newintent = new Intent(mcontext, EndeveourDetailsActivity.class);
                            newintent.putExtra("title", year);
                            startActivity(newintent);
                        }
                    });
                }

            }
        }
    }

    public void loadprogressbar() {
        if (progDailog == null) {
            progDailog = new ProgressDialog(mcontext);
        }
        if (progDailog.isShowing() == false) {
            progDailog.setMessage("Saving, Please wait...");
            progDailog.setProgressStyle(ProgressDialog.STYLE_SPINNER);
            progDailog.setCancelable(true);
            progDailog.show();
        }
    }

    public void stoploadprogress() {

        if (progDailog!=null && progDailog.isShowing() == true) {
            progDailog.dismiss();
        }

    }


    public void makeinsertcall(String id, String year, String title, String nidhi, String amt, String rawdata) {
        String partyworksQuery = "select * from partyworks where ID='"+id+"'";
        Cursor cursor = mydatabase.rawQuery(partyworksQuery, null);
        if (cursor.getCount() > 0) {
            System.out.println("----id exist updated----"+id);
            String insertquery = "update partyworks set YEAR='"+year+"',TITLE='"+title+"',NIDHI='"+nidhi+"',AMT='"+amt+"',RAWDATA='"+rawdata+"' where ID='"+id+"'";
            mydatabase.execSQL(insertquery);
        }else{
            System.out.println("----id new insert----"+id);
            String insertquery = "insert into partyworks(ID,YEAR,TITLE,NIDHI,AMT,RAWDATA) values('" + id + "','" + year + "','" + title + "','" + nidhi + "','" + amt + "','" + rawdata + "')";
            mydatabase.execSQL(insertquery);
        }
    }

    private boolean isNetworkConnected() {
        ConnectivityManager cm = (ConnectivityManager) mcontext.getSystemService(Context.CONNECTIVITY_SERVICE);

        return cm.getActiveNetworkInfo() != null;
    }

}

