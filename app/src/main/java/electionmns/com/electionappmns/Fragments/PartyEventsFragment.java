package electionmns.com.electionappmns.Fragments;

import android.app.Dialog;
import android.app.Fragment;
import android.app.FragmentManager;
import android.app.ProgressDialog;
import android.content.Context;
import android.net.ConnectivityManager;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;
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

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.Date;

import electionmns.com.electionappmns.Adapters.PartyEventAdapter;
import electionmns.com.electionappmns.Models.DailyUpdatesModel;
import electionmns.com.electionappmns.Models.PartyModel;
import electionmns.com.electionappmns.R;
import electionmns.com.electionappmns.Utils.AppConstant;
import electionmns.com.electionappmns.Utils.TypeFaceHelper;

/**
 * Created by Neha on 6/21/2016.
 */
public class PartyEventsFragment extends Fragment {
    private View view;
    private Fragment[] objFragment = {null};
    private ProgressDialog progDailog;
    private Context mContext;
    private RecyclerView listame_event;
    private ArrayList<PartyModel> titles;
    private JSONArray parentarray;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.partyev,container,false);
        return view;
    }

    @Override
    public void onViewCreated(View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        mContext = getActivity().getApplicationContext();
        listame_event = (RecyclerView) view.findViewById(R.id.recycler_eve);
        TextView backarrow = (TextView)view.findViewById(R.id.backarrowpartyworkspeTV);
        backarrow.setTypeface(TypeFaceHelper.getInstance(getActivity().getApplicationContext()).getStyleTypeFace(TypeFaceHelper.FONT_AWESOME));
        backarrow.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                FragmentManager fm = getFragmentManager();
                objFragment[0] = new HomeFragment();
                fm.beginTransaction().replace(R.id.frame_main, objFragment[0]).commit();
            }
        });
        if (isNetworkConnected()==true){
            titles = new ArrayList<>();
            makevolleycall();
        }else{
            Toast.makeText(mContext,"Check Your connection",Toast.LENGTH_SHORT).show();
        }
    }

    public void loadprogressbar() {
        if (progDailog == null) {
            progDailog = new ProgressDialog(getActivity());
        }
        if (progDailog.isShowing() == false) {
            progDailog.setMessage("Loading, Please wait...");
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

    private boolean isNetworkConnected() {
        ConnectivityManager cm = (ConnectivityManager) mContext.getSystemService(Context.CONNECTIVITY_SERVICE);

        return cm.getActiveNetworkInfo() != null;
    }

    public void makevolleycall() {

        loadprogressbar();
        RequestQueue queue = Volley.newRequestQueue(mContext);
        JsonObjectRequest req22 = new JsonObjectRequest(Request.Method.GET, AppConstant.PARTYEVENTS,
                new Response.Listener<JSONObject>() {
                    @Override
                    public void onResponse(JSONObject response) {
                        stoploadprogress();

                        JSONArray jsonArray = response.optJSONArray("events");
                        if(jsonArray!=null && jsonArray.length()>0){
                            parentarray = jsonArray;
                            for(int i=0;i<jsonArray.length();i++){
                                JSONObject jsonObject = jsonArray.optJSONObject(i);
                                PartyModel partyModel = new PartyModel(jsonObject);
                                titles.add(partyModel);
                            }
                        }
                        if(titles!=null && titles.size()>0){
                            Collections.sort(titles, new Comparator<PartyModel>() {
                                public int compare(PartyModel m1, PartyModel m2) {
                                    return m2.date.compareTo(m1.date);
                                }
                            });
                        }
                        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(mContext);
                        PartyEventAdapter partyEventAdapter = new PartyEventAdapter(titles,mContext,getActivity());
                        listame_event.setLayoutManager(linearLayoutManager);
                        listame_event.setAdapter(partyEventAdapter);
                        System.out.println("----------responsePendingDataSuccess------" + response.toString());

                    }
                }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                System.out.println("----------errorPendingData------" + error.getMessage());
                Toast.makeText(mContext, "Failed to load Updates", Toast.LENGTH_SHORT).show();
                stoploadprogress();
            }
        });
        req22.setRetryPolicy(new DefaultRetryPolicy(
                45000,
                DefaultRetryPolicy.DEFAULT_MAX_RETRIES,
                DefaultRetryPolicy.DEFAULT_BACKOFF_MULT));
        queue.add(req22);

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

}
