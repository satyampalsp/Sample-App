package sample.daffodil.sample2;

import android.graphics.Color;
import android.os.AsyncTask;
import android.util.Log;

import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.internal.StreetViewLifecycleDelegate;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.Polyline;
import com.google.android.gms.maps.model.PolylineOptions;
import com.google.maps.android.PolyUtil;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.util.HashMap;
import java.util.List;

import static android.content.ContentValues.TAG;

/**
 * Created by DAFFODIL-29 on 3/23/2018.
 */
public class GetDirectionsData extends AsyncTask<Object,String,String> {
    GoogleMap mMap;
    String url;
    String googleDirectionsData;
    JSONArray directions;
    @Override
    protected String doInBackground(Object[] params) {
        mMap=(GoogleMap)params[0];
        url=(String)params[1];
        DownloadUrl downloadUrl=new DownloadUrl();
        try {
            googleDirectionsData=downloadUrl.readUrl(url);
        } catch (IOException e) {
            e.printStackTrace();
        }
        return googleDirectionsData;
    }
    @Override
    protected void onPostExecute(String s){
        JSONArray directionList=null;
        DataParser parser=new DataParser();
        directionList=parser.parseDirections(s);
        Log.e("Directions:", directionList.toString());
        directions=directionList;
        List<LatLng> decoded = null;
        try {
            String points=directionList.getJSONObject(0).getJSONObject("overview_polyline").get("points").toString();
            decoded = PolyUtil.decode(points);
            mMap.clear();
            mMap.addPolyline(new PolylineOptions().addAll(decoded).width(15).color(Color.BLUE));
        } catch (JSONException e) {
            e.printStackTrace();
        }

    }

    public JSONArray getDirections() {
        return directions;
    }

}
