package electionmns.com.electionappmns.Models;

import android.text.TextUtils;

import org.json.JSONObject;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * Created by Neha on 7/31/2016.
 */
public class DailyUpdatesModel {
    public String id;
    public String title;
    public String date;
    public String desc;
    public String imgUrl1;
    public String imgUrl2;
    public String location;
    public DailyUpdatesModel(JSONObject  jsonObject) {

        id = jsonObject.optString("id");
        date = getdate(jsonObject.optString("datetime"));
        title = jsonObject.optString("title");
        desc = jsonObject.optString("desc");
        imgUrl1 = jsonObject.optString("img1");
        imgUrl2 = jsonObject.optString("img2");
        location = jsonObject.optString("loc");


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
