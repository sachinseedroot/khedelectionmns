package electionmns.com.electionappmns.FCM;

import android.app.Service;
import android.content.Intent;
import android.content.IntentFilter;
import android.os.IBinder;
import android.support.annotation.Nullable;
import android.util.Log;

import com.android.volley.DefaultRetryPolicy;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;
import com.google.firebase.FirebaseApp;
import com.google.firebase.iid.FirebaseInstanceId;
import com.google.firebase.iid.FirebaseInstanceIdService;
import com.google.firebase.iid.zzd;

import org.json.JSONException;
import org.json.JSONObject;



/**
 * Created by Neha on 8/27/2016.
 */
public class PushInstance extends FirebaseInstanceIdService {
    private static final String TAG = "PushInstanceDService";

    @Override
    public void onTokenRefresh() {

        //Getting registration token
        String refreshedToken = FirebaseInstanceId.getInstance().getToken();
        System.out.println("----PushInstance--token---"+refreshedToken);
        //Displaying token on logcat
        Log.d(TAG, "Refreshed token: " + refreshedToken);

    }


}
