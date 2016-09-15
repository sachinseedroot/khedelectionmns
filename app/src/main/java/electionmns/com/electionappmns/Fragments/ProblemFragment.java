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
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.Spinner;
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
import java.util.List;

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

    private Context mcontext;
    private ProgressDialog progDailog;
    private SQLiteDatabase mydatabase;
    private Spinner spinr_problem_type;
    private JSONObject jsonProbleId;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.problem_main, container, false);
        return view;
    }

    @Override
    public void onViewCreated(View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        mcontext = getActivity().getApplicationContext();
        mydatabase = mcontext.openOrCreateDatabase(AppSettings.getdburl(mcontext), mcontext.MODE_PRIVATE, null);

        fontawesome = TypeFaceHelper.getInstance(mcontext).getStyleTypeFace(TypeFaceHelper.FONT_AWESOME);
        TextView textViewbackpress = (TextView) view.findViewById(R.id.backarrowpromblemTV);
        textViewbackpress.setTypeface(fontawesome);
        textViewbackpress.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                FragmentManager fm = getFragmentManager();
                objFragment[0] = new HomeFragment();
                fm.beginTransaction().replace(R.id.frame_main, objFragment[0]).commit();
            }
        });

        spinr_problem_type = (Spinner)view.findViewById(R.id.spinr_problem_type);
        final EditText probl_desc = (EditText)view.findViewById(R.id.probl_desc);
        final EditText fname = (EditText)view.findViewById(R.id.fname);
        final EditText addrs = (EditText)view.findViewById(R.id.addrs);
        final EditText mob_no = (EditText)view.findViewById(R.id.mob_no);
        TextView fileattach = (TextView)view.findViewById(R.id.fileattach);
        fileattach.setText(mcontext.getResources().getString(R.string.camera) + "    "+
                mcontext.getResources().getString(R.string.video)+"    "+mcontext.getResources().getString(R.string.file));
        fileattach.setTypeface(fontawesome);
        Button btnsbunit=(Button)view.findViewById(R.id.btn_submit_proble);
        btnsbunit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (isNetworkConnected() == true) {
                    if (!TextUtils.isEmpty(fname.getText().toString().trim())) {
                        if (!TextUtils.isEmpty(mob_no.getText().toString().trim())) {
                            if (!TextUtils.isEmpty(probl_desc.getText().toString().trim())) {
                                if (!spinr_problem_type.getSelectedItem().toString().equals("-- श्रेणी निवडा --")) {
                                    String id="";
                                    if(jsonProbleId!=null && jsonProbleId.length()>0){
                                        String selected = spinr_problem_type.getSelectedItem().toString();
                                        id = jsonProbleId.optString(selected);
                                    }

                                    System.out.println("----------success_all_data");
                                    postPartyProblems(AppConstant.PartyAddProblem,id,fname.getText().toString().trim(),
                                            addrs.getText().toString().trim(),mob_no.getText().toString().trim(),
                                            probl_desc.getText().toString().trim(),""
                                            );
                                } else {
                                    Toast.makeText(mcontext, "कृपया समस्या प्रकार निवडा", Toast.LENGTH_SHORT).show();
                                }
                            } else {
                                probl_desc.setError("Cannot be empty");
                            }
                        } else {
                            mob_no.setError("Cannot be empty");
                        }
                    } else {
                        fname.setError("Cannot be empty");
                    }
                } else {
                    Toast.makeText(mcontext, "Check nternet connection", Toast.LENGTH_SHORT).show();
                }

            }
        });
        if(isNetworkConnected()==true){
            jsonProbleId = new JSONObject();
            getParttyWorkds(AppConstant.PARTYPROBLEM_GETCATEGORY);
        }



    }

    public void loadprogressbar() {
        if (progDailog == null) {
            progDailog = new ProgressDialog(getActivity());
        }
        if (progDailog.isShowing() == false) {
            progDailog.setMessage("Loading, Please wait...");
            progDailog.setProgressStyle(ProgressDialog.STYLE_SPINNER);
            progDailog.setCancelable(false);
            progDailog.show();
        }
    }

    public void stoploadprogress() {

        if (progDailog!=null && progDailog.isShowing() == true) {
            progDailog.dismiss();
        }

    }



    private boolean isNetworkConnected() {
        ConnectivityManager cm = (ConnectivityManager) mcontext.getSystemService(Context.CONNECTIVITY_SERVICE);

        return cm.getActiveNetworkInfo() != null;
    }

    public void getParttyWorkds(String url) {

         loadprogressbar();
        RequestQueue queue = Volley.newRequestQueue(mcontext);
        JsonObjectRequest req22 = new JsonObjectRequest(Request.Method.GET, url,
                new Response.Listener<JSONObject>() {
                    @Override
                    public void onResponse(JSONObject response) {
                         stoploadprogress();
                        System.out.println("==========party problem=====" + response.toString());
                        JSONArray jsonArrayResposne = response.optJSONArray("problemtype");
                        if (jsonArrayResposne != null && jsonArrayResposne.length() > 0) {
                            List<String> categories = new ArrayList<String>();
                            categories.add("-- श्रेणी निवडा --");
                            for (int i = 0; i < jsonArrayResposne.length(); i++) {
                                JSONObject singleobect = jsonArrayResposne.optJSONObject(i);
                                if (singleobect != null && singleobect.length() > 0) {
                                    String id = singleobect.optString("id");
                                    String name = singleobect.optString("name");
                                    categories.add(name);
                                    try {
                                        jsonProbleId.put("name",id);
                                    } catch (JSONException e) {
                                        e.printStackTrace();
                                    }
                                }
                            }



                            if(categories!=null && categories.size()>0) {
                                ArrayAdapter<String> dataAdapter = new ArrayAdapter<String>(mcontext, R.layout.spinnertv, categories);
                                spinr_problem_type.setAdapter(dataAdapter);
                            }
                        }

                    }
                }

                , new Response.ErrorListener()

        {
            @Override
            public void onErrorResponse(VolleyError error) {
                System.out.println("----------errorproblem------" + error.getMessage());
                stoploadprogress();

            }
        }

        );
        queue.add(req22);

    }
    public void postPartyProblems(String url,String pid,String fullname, String fulladrs,String mobile,String desc,String file) {
        JSONObject jsonObject = new JSONObject();
        try {
            jsonObject.put("politicalpartyid",AppConstant.PARTY_KEY);
            jsonObject.put("problemtypeid",pid);
            jsonObject.put("fullname",fullname);
            jsonObject.put("fulladdress",fulladrs);
            jsonObject.put("mobile",mobile);
            jsonObject.put("description",desc);
            jsonObject.put("file",file);
        } catch (JSONException e) {
            e.printStackTrace();
        }

        loadprogressbar();
        RequestQueue queue = Volley.newRequestQueue(mcontext);
        JsonObjectRequest req22 = new JsonObjectRequest(Request.Method.POST, url,jsonObject,
                new Response.Listener<JSONObject>() {
                    @Override
                    public void onResponse(JSONObject response) {
                        stoploadprogress();
                        System.out.println("==========problem Saved REsponse=====" + response.toString());
                    }
                }

                , new Response.ErrorListener()

        {
            @Override
            public void onErrorResponse(VolleyError error) {
                System.out.println("----------errorproblem------" + error.getMessage());
                stoploadprogress();

            }
        }

        );
        queue.add(req22);

    }
}

