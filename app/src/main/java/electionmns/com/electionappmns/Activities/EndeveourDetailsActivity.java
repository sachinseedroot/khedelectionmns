package electionmns.com.electionappmns.Activities;

import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.graphics.Typeface;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

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

import electionmns.com.electionappmns.Adapters.DailyUpdateAdapters;
import electionmns.com.electionappmns.Adapters.EndevourAdapter;
import electionmns.com.electionappmns.Models.PartyWorksModel;
import electionmns.com.electionappmns.R;
import electionmns.com.electionappmns.Utils.AppSettings;
import electionmns.com.electionappmns.Utils.TypeFaceHelper;

/**
 * Created by Neha on 8/5/2016.
 */
public class EndeveourDetailsActivity extends Activity {
    private Context mcontext;
    private Typeface fontawesome;
    private RecyclerView recycler_end;
    private ArrayList<PartyWorksModel> partyWorksModels;
    private ProgressDialog progDailog;
    private SQLiteDatabase mydatabase;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.enddetails);
        mcontext = this;
        recycler_end = (RecyclerView) findViewById(R.id.recycler_end);
        TextView backarrowpartyworksDetailsTV = (TextView) findViewById(R.id.backarrowpartyworksDetailsTV);
        backarrowpartyworksDetailsTV.setTypeface(fontawesome);
        backarrowpartyworksDetailsTV.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
        fontawesome = TypeFaceHelper.getInstance(mcontext).getStyleTypeFace(TypeFaceHelper.FONT_AWESOME);
        TextView hed = (TextView) findViewById(R.id.hed);

        partyWorksModels = new ArrayList<>();


        Intent intent = getIntent();
        if (intent != null) {
            hed.setText(intent.getStringExtra("title"));
        }
    }


    public void loaddata(String year) {
        String selectquery = "select * from partyworks where YEAR='" + year + "'";
        Cursor cursor = mydatabase.rawQuery(selectquery, null);
        if (cursor.getCount() > 0) {
            if (cursor.moveToFirst()) {
                do {
                    String ids = cursor.getString(cursor.getColumnIndex("NEWS"));
                    try {
                        JSONArray jsonArray = new JSONArray(ids);
                        if(jsonArray!=null && jsonArray.length()>0){
                            for (int i =0;i<jsonArray.length();i++){
                                JSONObject singlenews = jsonArray.optJSONObject(i);
                                PartyWorksModel partyWorksModel = new PartyWorksModel(singlenews);
                                partyWorksModels.add(partyWorksModel);
                            }
                        }
                    } catch (JSONException e) {
                        e.printStackTrace();
                    }
                } while (cursor.moveToNext());
            }
        }

        if(partyWorksModels!=null && partyWorksModels.size()>0){
            LinearLayoutManager linearLayoutManager = new LinearLayoutManager(mcontext);
            recycler_end.setLayoutManager(linearLayoutManager);
            recycler_end.setHasFixedSize(true);
            EndevourAdapter mAdapter = new EndevourAdapter(partyWorksModels, mcontext);
            recycler_end.setAdapter(mAdapter);
        }
    }


}
