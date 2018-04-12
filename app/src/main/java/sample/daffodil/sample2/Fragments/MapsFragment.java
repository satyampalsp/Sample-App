package sample.daffodil.sample2.Fragments;

import android.content.Intent;
import android.content.pm.PackageManager;
import android.location.Location;
import android.os.Bundle;
import android.support.v4.app.ActivityCompat;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.Toast;

import com.google.android.gms.ads.AdRequest;
import com.google.android.gms.ads.AdView;
import com.google.android.gms.ads.InterstitialAd;
import com.google.android.gms.common.GooglePlayServicesNotAvailableException;
import com.google.android.gms.common.GooglePlayServicesRepairableException;
import com.google.android.gms.common.api.Status;
import com.google.android.gms.location.places.Place;
import com.google.android.gms.location.places.ui.PlaceAutocomplete;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.MapView;
import com.google.android.gms.maps.MapsInitializer;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.model.BitmapDescriptorFactory;
import com.google.android.gms.maps.model.CameraPosition;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.Marker;
import com.google.android.gms.maps.model.MarkerOptions;

import org.json.JSONArray;

import sample.daffodil.sample2.Model.GetDirectionsData;
import sample.daffodil.sample2.R;

import static android.app.Activity.RESULT_CANCELED;
import static android.app.Activity.RESULT_OK;

/**
 * Created by DAFFODIL-29 on 3/13/2018.
 */

public class MapsFragment extends Fragment {
    View view=null;
    MapView mMapView;
    private static final String API_KEY = "AIzaSyDdK10bxb18t3a4zVjV62GtQbY9TCk1PPs";
    final String TAG = "GPS";
    Marker m1=null,m2=null,selfLocation=null;
    int firstCameraMove=0;
    GoogleMap googleMap;
    String url;
    JSONArray directions;
    String loadUrl=null;
    Object datatransfer[];
    int PLACE_AUTOCOMPLETE_REQUEST_CODE = 1;
    Boolean MAP_MODE=true;
    Boolean markerOneDrag=false;
    Button changeMapView;
    ImageView searchView;
    private AdView mAdView;
    private InterstitialAd mInterstitialAd;
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
// Inflate the layout for this fragment
        if(view==null)
        view = inflater.inflate(R.layout.maps_fragment, container, false);
        mMapView = (MapView) view.findViewById(R.id.id_google_maps);
        mMapView.onCreate(savedInstanceState);
        mMapView.onResume();
        mInterstitialAd = new InterstitialAd(getContext());
        mInterstitialAd.setAdUnitId("ca-app-pub-5654044967858938/7431275144");
        mInterstitialAd.loadAd(new AdRequest.Builder().build());
        changeMapView=(Button)view.findViewById(R.id.id_maps_view_change);
        changeMapView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(MAP_MODE==true){
                    googleMap.setMapType(GoogleMap.MAP_TYPE_SATELLITE);
                    MAP_MODE=false;
                    changeMapView.setText("Normal View");
                }
                else{
                    googleMap.setMapType(GoogleMap.MAP_TYPE_NORMAL);
                    MAP_MODE=true;
                    changeMapView.setText("Satellite View");
                }
            }
        });
        searchView=(ImageView)view.findViewById(R.id.id_Search_view_maps);
        searchView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                search();
            }
        });
        try {
            MapsInitializer.initialize(getActivity().getApplicationContext());
        } catch (Exception e) {
            e.printStackTrace();
        }

        mMapView.getMapAsync(new OnMapReadyCallback() {
            @Override
            public void onMapReady(GoogleMap mMap) {
                googleMap = mMap;

                // For showing a move to my location button
                if (ActivityCompat.checkSelfPermission(getContext(), android.Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(getContext(), android.Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
                    // TODO: Consider calling

                    return;
                }
                googleMap.setMyLocationEnabled(true);

                // For dropping a marker at a point on the Map
                LatLng sydney = new LatLng(-34, 151);
                m1=googleMap.addMarker(new MarkerOptions().position(sydney).title("Marker Title").snippet("Marker Description"));

                // For zooming automatically to the location of the marker
                googleMap.setOnMyLocationButtonClickListener(new GoogleMap.OnMyLocationButtonClickListener() {
                    @Override
                    public boolean onMyLocationButtonClick() {
                        Location l=googleMap.getMyLocation();
                        LatLng loc=new LatLng(l.getLatitude(),l.getLongitude());
                        CameraPosition cameraPosition = new CameraPosition.Builder().target(loc).zoom(15).build();
                        googleMap.animateCamera(CameraUpdateFactory.newCameraPosition(cameraPosition));
                        m1.remove();
                        markerOneDrag=false;
                        m1=googleMap.addMarker(new MarkerOptions().position(loc).title("Your Location").snippet("You are here!").draggable(true).icon(BitmapDescriptorFactory.fromResource(R.drawable.ic_account_circle_black_24dp)));
                        return false;
                    }
                });
                googleMap.setOnMarkerDragListener(new GoogleMap.OnMarkerDragListener() {

                    @Override
                    public void onMarkerDragStart(Marker marker) {

                         }

                    @Override
                    public void onMarkerDrag(Marker marker) {

                    }

                    @Override
                    public void onMarkerDragEnd(Marker marker) {
                        if(m1.getId().equals(marker.getId())){
                            m1=marker;
                            markerOneDrag=true;
                        }
                        else if(m2.getId().equals(marker.getId())){
                            m2.remove();
                            m2=marker;
                        }m1.remove();
                        googleMap.clear();
                        m1=googleMap.addMarker(new MarkerOptions().position(m1.getPosition()).title("Drop Location").snippet(""+m1.getPosition().latitude+","+m1.getPosition().longitude).draggable(true));
                        if (m2!=null) {
                            m2 = googleMap.addMarker(new MarkerOptions().position(m2.getPosition()).title("Drop Location").snippet(""+m2.getPosition().latitude+","+m2.getPosition().longitude).draggable(true));
                        }
                    }
                });
                googleMap.setOnMyLocationChangeListener(new GoogleMap.OnMyLocationChangeListener() {
                    @Override
                    public void onMyLocationChange(Location location) {
                        if(markerOneDrag==false) {
                            LatLng loc = new LatLng(location.getLatitude(), location.getLongitude());
                                if(m2==null) {
                                    CameraPosition cameraPosition = new CameraPosition.Builder().target(loc).zoom(15).build();
                                    googleMap.animateCamera(CameraUpdateFactory.newCameraPosition(cameraPosition));
                                }m1.remove();
                                m1=googleMap.addMarker(new MarkerOptions().position(loc).title("Drop Location").snippet(""+loc.latitude+","+loc.longitude).draggable(true).icon(BitmapDescriptorFactory.fromResource(R.drawable.ic_account_circle_black_24dp)));
                            }
                           }
                });
            }
        });
        final Button direction=(Button)view.findViewById(R.id.id_maps_view_directions);
        direction.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v){
            if (m2 == null) {
                Toast.makeText(getContext(), "Select Destination first!", Toast.LENGTH_SHORT).show();
                search();
            } else {
                markerOneDrag=true;
                getDirections();
            }
               }
        });
        return view;
    }
    @Override
    public void onResume() {
        super.onResume();
        mMapView.onResume();
    }

    @Override
    public void onPause() {
        super.onPause();
        mMapView.onPause();
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        mMapView.onDestroy();
    }

    @Override
    public void onLowMemory() {
        super.onLowMemory();
        mMapView.onLowMemory();
    }

    @Override
     public void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (requestCode == PLACE_AUTOCOMPLETE_REQUEST_CODE) {
            if (resultCode == RESULT_OK) {
                Place place = PlaceAutocomplete.getPlace(getActivity(), data);
                Log.i("Searched", "Place: " + place.getLatLng());
                if(m2!=null)
                m2.remove();
                m2=googleMap.addMarker(new MarkerOptions().position(place.getLatLng()).title(place.getName().toString()).snippet(place.getAddress().toString()).draggable(true));

                googleMap.setMapType(GoogleMap.MAP_TYPE_NORMAL);
                // For zooming automatically to the location of the marker
                CameraPosition cameraPosition = new CameraPosition.Builder().target(place.getLatLng()).zoom(15).build();
                googleMap.animateCamera(CameraUpdateFactory.newCameraPosition(cameraPosition));
                float results[]=new float[10];
                Location.distanceBetween(m1.getPosition().latitude,m1.getPosition().longitude,m2.getPosition().latitude,m2.getPosition().longitude,results);
                m2.setSnippet("Distance:"+results[0]);


            } else if (resultCode == PlaceAutocomplete.RESULT_ERROR) {
                Status status = PlaceAutocomplete.getStatus(getActivity(), data);
                // TODO: Handle the error.
                Log.i(TAG, status.getStatusMessage());

            } else if (resultCode == RESULT_CANCELED) {
                // The user canceled the operation.
            }
        }
    }
    void search(){
        try {
            Intent intent =
                    new PlaceAutocomplete.IntentBuilder(PlaceAutocomplete.MODE_OVERLAY)
                            .build(getActivity());
            startActivityForResult(intent, PLACE_AUTOCOMPLETE_REQUEST_CODE);
        } catch (GooglePlayServicesRepairableException e) {
            // TODO: Handle the error.
        } catch (GooglePlayServicesNotAvailableException e) {
            // TODO: Handle the error.
        }
    }
    private String getDirectionUrl(){
        if(m1!=null && m2!=null) {
            StringBuilder googleDirectionUrl = new StringBuilder("https://maps.googleapis.com/maps/api/directions/json?");
            googleDirectionUrl.append("origin=" + m1.getPosition().latitude + "," + m1.getPosition().longitude);
            googleDirectionUrl.append("&destination=" + m2.getPosition().latitude + "," + m2.getPosition().longitude);
            googleDirectionUrl.append("&key=" + API_KEY);
            loadUrl = googleDirectionUrl.toString();
        }
            return loadUrl;
    }
    @Override
    public void onDetach() {
        super.onDetach();
    }
    void getDirections(){

            datatransfer = new Object[2];
            datatransfer[0] = googleMap;
            url = getDirectionUrl();
        if(loadUrl!=null) {
            datatransfer[1] = url;
            GetDirectionsData getDirectionsData = new GetDirectionsData();
            getDirectionsData.execute(datatransfer);
            googleMap.clear();
            directions = getDirectionsData.getDirections();
            m1=googleMap.addMarker(new MarkerOptions().position(m1.getPosition()).title("Drop Location").snippet(""+m1.getPosition().latitude+","+m1.getPosition().longitude).draggable(true));
            m2=googleMap.addMarker(new MarkerOptions().position(m2.getPosition()).title("Drop Location").snippet(""+m2.getPosition().latitude+","+m2.getPosition().longitude).draggable(true));
        }
    }

}