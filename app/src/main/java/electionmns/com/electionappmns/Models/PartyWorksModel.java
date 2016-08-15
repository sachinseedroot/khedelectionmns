package electionmns.com.electionappmns.Models;

import org.json.JSONObject;

/**
 * Created by Neha on 7/31/2016.
 */
public class PartyWorksModel {

    public String title;
    public String amount;
    public String nidhi;

    public PartyWorksModel(JSONObject  jsonObject) {

        title = jsonObject.optString("title");
        amount = jsonObject.optString("amount");
        nidhi = jsonObject.optString("nidhi");


    }
}
