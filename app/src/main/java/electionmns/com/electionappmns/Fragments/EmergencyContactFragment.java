package electionmns.com.electionappmns.Fragments;

import android.app.Fragment;
import android.app.FragmentManager;
import android.app.ProgressDialog;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.net.ConnectivityManager;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.LinearLayout;
import android.widget.Spinner;
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

import java.util.ArrayList;
import java.util.List;

import electionmns.com.electionappmns.Adapters.DailyUpdateAdapters;
import electionmns.com.electionappmns.Adapters.EmergencyAdapters;
import electionmns.com.electionappmns.Models.DailyUpdatesModel;
import electionmns.com.electionappmns.Models.EmergencyContactModel;
import electionmns.com.electionappmns.R;
import electionmns.com.electionappmns.Utils.AppConstant;
import electionmns.com.electionappmns.Utils.AppSettings;
import electionmns.com.electionappmns.Utils.TypeFaceHelper;

/**
 * Created by Neha on 6/21/2016.
 */
public class EmergencyContactFragment extends Fragment {
    private View view;
    private Fragment[] objFragment = {null};
    private Context mContext;
    private ArrayList<EmergencyContactModel> emergencyContactModels;
    private RecyclerView rec_recycler_em;
    private JSONArray jsonArray;
    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.emergency, container, false);
        return view;
    }

    @Override
    public void onViewCreated(View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        mContext = getActivity().getApplicationContext();
        TextView backarrow = (TextView)view.findViewById(R.id.backarrowpartyworksemerTV);
        Spinner spinner = (Spinner) view.findViewById(R.id.spinner);
        rec_recycler_em = (RecyclerView)view.findViewById(R.id.contact_rec);

        List<String> categories = new ArrayList<String>();
        backarrow.setTypeface(TypeFaceHelper.getInstance(getActivity().getApplicationContext()).getStyleTypeFace(TypeFaceHelper.FONT_AWESOME));
        backarrow.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                FragmentManager fm = getFragmentManager();
                objFragment[0] = new HomeFragment();
                fm.beginTransaction().replace(R.id.frame_main, objFragment[0]).commit();
            }
        });
        try {
            String title="";int o=0;
            categories.add("-- श्रेणी निवडा --");
            String data= AppConstant.EMERGENCY_CONTACT;
            jsonArray = new JSONArray(data);
            if(jsonArray!=null && jsonArray.length()>0){
                System.out.println("------titlecount----"+jsonArray.length());

                for (int i=0;i<jsonArray.length();i++){
                    JSONObject jsonObject = jsonArray.optJSONObject(i);
                    if(!title.equals(jsonObject.getString("catergory"))){
                        title = jsonObject.getString("catergory");
                        categories.add(title);
                        System.out.println("------title----"+title+"---"+(o=o+1));
                    }
                }
            }
        } catch (JSONException e) {
            e.printStackTrace();
        }

        if(categories!=null && categories.size()>0) {
            ArrayAdapter<String> dataAdapter = new ArrayAdapter<String>(mContext, R.layout.spinnertv, categories);
            spinner.setAdapter(dataAdapter);

        }
        spinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                String item = parent.getItemAtPosition(position).toString();
                System.out.println("----item---"+item);
                getlist(item);
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });



    }
    public void getlist(String category){
        if(jsonArray!=null && jsonArray.length()>0) {
            emergencyContactModels = new ArrayList<>();
            for (int i=0;i<jsonArray.length();i++){
                JSONObject jsonObject = jsonArray.optJSONObject(i);
                try {
                    if(category.equals(jsonObject.getString("catergory"))){
                        EmergencyContactModel emergencyContactModel = new EmergencyContactModel(jsonObject);
                        emergencyContactModels.add(emergencyContactModel);
                    }
                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }
            if(emergencyContactModels!=null && emergencyContactModels.size()>0) {
                rec_recycler_em.setVisibility(View.VISIBLE);
                LinearLayoutManager linearLayoutManager = new LinearLayoutManager(mContext);
                rec_recycler_em.setLayoutManager(linearLayoutManager);
                rec_recycler_em.setHasFixedSize(true);
                EmergencyAdapters emergencyAdapters = new EmergencyAdapters(emergencyContactModels, mContext);
                rec_recycler_em.setAdapter(emergencyAdapters);
            }else{
                rec_recycler_em.setVisibility(View.GONE);
            }
        }
    }
}
