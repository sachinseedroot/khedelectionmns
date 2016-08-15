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
import android.support.v7.widget.StaggeredGridLayoutManager;
import android.view.View;
import android.widget.GridView;
import android.widget.TextView;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

import electionmns.com.electionappmns.Adapters.EndevourAdapter;
import electionmns.com.electionappmns.Adapters.PartyEventGridAdapter;
import electionmns.com.electionappmns.Models.PartyWorksModel;
import electionmns.com.electionappmns.R;
import electionmns.com.electionappmns.Utils.TypeFaceHelper;

/**
 * Created by Neha on 8/5/2016.
 */
public class PartyEventGridActivity extends Activity {
    private Context mcontext;
    private Typeface fontawesome;
    private RecyclerView recycler_end;
    private ArrayList<String> urls;
    private ArrayList<String> urlL;
    private ProgressDialog progDailog;
    private SQLiteDatabase mydatabase;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.partyrventdetails);
        mcontext = this;
        recycler_end = (RecyclerView) findViewById(R.id.gridview);
        fontawesome = TypeFaceHelper.getInstance(mcontext).getStyleTypeFace(TypeFaceHelper.FONT_AWESOME);
        TextView backarrowpartyworksDetailsTV = (TextView) findViewById(R.id.backarrowpartyworksDetailsprTV);
        backarrowpartyworksDetailsTV.setTypeface(fontawesome);
        backarrowpartyworksDetailsTV.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });

        TextView hed = (TextView) findViewById(R.id.hedpr);

        urls = new ArrayList<>();
        urlL = new ArrayList<>();

        Intent intent = getIntent();
        if (intent != null) {
            hed.setText(intent.getStringExtra("title"));
            String urlss = intent.getStringExtra("urls");
            try {
                JSONArray jsonArray = new JSONArray(urlss);
                if(jsonArray!=null && jsonArray.length()>0) {
                    for (int i = 0; i < jsonArray.length(); i++) {
                        JSONObject jsonObject = jsonArray.optJSONObject(i);
                        urls.add(jsonObject.optString("smallurl"));
                        urlL.add(jsonObject.optString("url"));
                    }
                    StaggeredGridLayoutManager staggeredGridLayoutManager = new StaggeredGridLayoutManager(3,1);
                    recycler_end.setLayoutManager(staggeredGridLayoutManager);
                    PartyEventGridAdapter partyEventGridAdapter = new PartyEventGridAdapter(mcontext,urls,urlL,PartyEventGridActivity.this);
                    recycler_end.setAdapter(partyEventGridAdapter);
                }
            } catch (JSONException e) {
                e.printStackTrace();
            }
        }

    }



}
