package electionmns.com.electionappmns.Utils;

import android.content.Context;
import android.content.SharedPreferences;

import electionmns.com.electionappmns.Activities.SplashScreenActivity;

/**
 * Created by nitesh on 8/7/15.
 */
public class AppSettings {


    public static void setisLogin(Context context, boolean geofenceAdded) {
        SharedPreferences prefs = Prefs.get(context);
        SharedPreferences.Editor editor = prefs.edit();
        editor.putBoolean("isLogin", geofenceAdded);
        editor.commit();
    }

    public static boolean getislogin(Context context) {
        SharedPreferences prefs = Prefs.get(context);
        return prefs.getBoolean("isLogin", false);
    }


    public static void setusername(Context context, String home_ta) {
        SharedPreferences prefs = Prefs.get(context);
        SharedPreferences.Editor editor = prefs.edit();
        editor.putString("username", home_ta);
        editor.commit();
    }

    public static String getusername(Context context) {
        SharedPreferences prefs = Prefs.get(context);
        return prefs.getString("username", null);
    }

    public static void setuserid(Context context, String home_ta) {
        SharedPreferences prefs = Prefs.get(context);
        SharedPreferences.Editor editor = prefs.edit();
        editor.putString("userid", home_ta);
        editor.commit();
    }

    public static String getuserid(Context context) {
        SharedPreferences prefs = Prefs.get(context);
        return prefs.getString("userid", null);
    }


    public static void setdburl(Context splashScreenActivity, String urldb) {
        SharedPreferences prefs = Prefs.get(splashScreenActivity);
        SharedPreferences.Editor editor = prefs.edit();
        editor.putString("urldb", urldb);
        editor.commit();
    }
    public static String getdburl(Context context) {
        SharedPreferences prefs = Prefs.get(context);
        return prefs.getString("urldb", null);
    }

}
