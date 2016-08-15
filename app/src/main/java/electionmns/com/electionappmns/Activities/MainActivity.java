package electionmns.com.electionappmns.Activities;

import android.Manifest;
import android.app.Activity;
import android.app.Fragment;
import android.app.FragmentManager;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.os.Handler;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.Toast;

import electionmns.com.electionappmns.Fragments.HomeFragment;
import electionmns.com.electionappmns.R;

public class MainActivity extends Activity {
    private Fragment[] objFragment = {null};
    private Context mcontext;
    boolean doubleBackToExitPressedOnce = false;
    private static String TAG = "Permission";
    private static final int REQUEST_WRITE_STORAGE = 112;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        mcontext = this;
        boolean hasPermission = (ContextCompat.checkSelfPermission(mcontext,
                Manifest.permission.WRITE_EXTERNAL_STORAGE) == PackageManager.PERMISSION_GRANTED);
        if (!hasPermission) {
            ActivityCompat.requestPermissions(MainActivity.this,
                    new String[]{Manifest.permission.WRITE_EXTERNAL_STORAGE},
                    REQUEST_WRITE_STORAGE);
        }
        FragmentManager fm = getFragmentManager();
        objFragment[0] = new HomeFragment();
        fm.beginTransaction().add(R.id.frame_main, objFragment[0]).commit();

    }


//    @Override
//    public void onBackPressed() {
////        if (doubleBackToExitPressedOnce) {
//
//            super.onBackPressed();
//            return;
////        }
//
////        this.doubleBackToExitPressedOnce = true;
////        Toast.makeText(this, "बाहेर पडण्यासाठी परत क्लिक करा", Toast.LENGTH_SHORT).show();
////
////        new Handler().postDelayed(new Runnable() {
////
////            @Override
////            public void run() {
////                doubleBackToExitPressedOnce=false;
////            }
////        }, 2000);
////    }


//    @Override
//    public void onBackPressed() {
//
//
//
//
//        if (doubleBackToExitPressedOnce) {
//
//                super.onBackPressed();
//                return;
//
//
//        }
//
//        this.doubleBackToExitPressedOnce = true;
//        FragmentManager fm = getFragmentManager();
//        if(fm.getBackStackEntryCount()>0){
//            fm.popBackStack();
//        }else{
//            Toast.makeText(this, "Please click BACK again to exit", Toast.LENGTH_SHORT).show();
//        }
//
//
//        new Handler().postDelayed(new Runnable() {
//
//            @Override
//            public void run() {
//                doubleBackToExitPressedOnce=false;
//            }
//        }, 2000);
//    }


    @Override
    public void onBackPressed() {
        super.onBackPressed();
    }

    public void  shareinitent(String msg){
        if(msg.equals("")){

            Intent sharingIntent = new Intent(android.content.Intent.ACTION_SEND);
            sharingIntent.setType("text/plain");
            sharingIntent.putExtra(android.content.Intent.EXTRA_TEXT, msg);
            sharingIntent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
            startActivity(Intent.createChooser(sharingIntent, mcontext.getResources().getString(R.string.share_using)));
        }else{
            Toast.makeText(mcontext,"Failed to share",Toast.LENGTH_SHORT).show();
        }
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, String[] permissions, int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        switch (requestCode)
        {
            case REQUEST_WRITE_STORAGE: {
                if (grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED)
                {
                    finish();
                    startActivity(getIntent());
                    //reload my activity with permission granted or use the features what required the permission
                } else
                {
                    Toast.makeText(mcontext, "The app was not allowed to write to your storage. Hence, it cannot function properly. Please consider granting it this permission", Toast.LENGTH_LONG).show();
                }
            }
        }
    }
}
