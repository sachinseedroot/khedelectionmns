package electionmns.com.electionappmns.Fragments;

import android.app.DatePickerDialog;
import android.app.Dialog;
import android.app.DialogFragment;
import android.app.Fragment;
import android.app.FragmentManager;
import android.app.ProgressDialog;
import android.content.ContentProviderOperation;
import android.content.Context;
import android.content.Intent;
import android.net.ConnectivityManager;
import android.net.Uri;
import android.os.Bundle;
import android.provider.ContactsContract;
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

import org.json.JSONObject;

import java.util.ArrayList;
import java.util.Calendar;

import electionmns.com.electionappmns.R;
import electionmns.com.electionappmns.Utils.AppConstant;
import electionmns.com.electionappmns.Utils.TypeFaceHelper;

/**
 * Created by Neha on 6/21/2016.
 */
public class ContactFragment extends Fragment {
    private View view;
    private Fragment[] objFragment = {null};
    private Context mcContext;
    String DisplayName = "Vaibhav Khedekar ,MNS Leader";



    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.contact,container,false);
        return view;
    }

    @Override
    public void onViewCreated(View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        mcContext = getActivity().getApplicationContext();
        TextView backarrow = (TextView)view.findViewById(R.id.backarrowpartyworksconTV);
        backarrow.setTypeface(TypeFaceHelper.getInstance(getActivity().getApplicationContext()).getStyleTypeFace(TypeFaceHelper.FONT_AWESOME));
        backarrow.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                FragmentManager fm = getFragmentManager();
                objFragment[0] = new HomeFragment();
                fm.beginTransaction().replace(R.id.frame_main, objFragment[0]).commit();
            }
        });

        RelativeLayout addtocontact = (RelativeLayout)view.findViewById(R.id.addtocontact);
        addtocontact.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(
                        ContactsContract.Intents.SHOW_OR_CREATE_CONTACT,
                        ContactsContract.Contacts.CONTENT_URI);
                intent.setData(Uri.parse("tel:9422595900"));
                intent.putExtra(ContactsContract.Intents.Insert.NAME




                        , DisplayName);
                startActivity(intent);
                Toast.makeText(getActivity().getApplicationContext(), "Add this Contact to your phone", Toast.LENGTH_SHORT).show();            }
        });
    }

}
