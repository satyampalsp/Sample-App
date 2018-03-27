package sample.daffodil.sample2;

import android.util.Log;

import com.google.android.gms.maps.model.Polyline;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;

/**
 * Created by DAFFODIL-29 on 3/23/2018.
 */

public class DataParser {
    private JSONArray route;

    public JSONArray parseDirections(String jsonData) {
        JSONArray jsonArray = null;
        JSONObject jsonObject;
        try {
            jsonObject = new JSONObject(jsonData);
            jsonArray = jsonObject.getJSONArray("routes");
            route = jsonArray;
        } catch (JSONException e) {
            e.printStackTrace();
        }
        return route;
    }

    public String getPath(JSONObject googlePathJson) {
        String polyline = null;
        try {
            polyline = googlePathJson.getJSONObject("polyline").getString("points");
        } catch (JSONException e) {
            e.printStackTrace();
        }
        return polyline;
    }
}
