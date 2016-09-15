package electionmns.com.electionappmns.Models;

import android.text.TextUtils;

import org.json.JSONArray;
import org.json.JSONObject;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * Created by Neha on 7/31/2016.
 */
public class PartyModel {
    public String id;
    public String title;
    public String date;
    public String desc;
    public String location;
    public JSONArray imagesarray;
    public PartyModel(JSONObject  jsonObject) {

        id = jsonObject.optString("id");
        date = jsonObject.optString("datetime");
        title = jsonObject.optString("name");
        desc = jsonObject.optString("description");
        location = jsonObject.optString("location");
        imagesarray = jsonObject.optJSONArray("eventimages");

    }
    public String getdate(String date){
        if(!TextUtils.isEmpty(date)){
            SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
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
