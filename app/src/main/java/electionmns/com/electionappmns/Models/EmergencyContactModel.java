package electionmns.com.electionappmns.Models;

import org.json.JSONObject;

/**
 * Created by Neha on 7/31/2016.
 */
public class EmergencyContactModel {
    public String cat;
    public String title;
    public String con;

    public EmergencyContactModel(JSONObject  jsonObject) {

        cat = jsonObject.optString("catergory");
        title = jsonObject.optString("title");
        con = jsonObject.optString("contact");
    }
}
