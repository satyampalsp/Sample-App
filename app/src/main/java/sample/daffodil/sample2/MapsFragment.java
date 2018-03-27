package sample.daffodil.sample2;

import android.app.FragmentManager;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Color;
import android.graphics.Path;
import android.location.Geocoder;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v4.app.ActivityCompat;
import android.support.v4.app.Fragment;
import android.support.v7.widget.CardView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.SearchView;
import android.widget.Toast;

import com.google.android.gms.common.GooglePlayServicesNotAvailableException;
import com.google.android.gms.common.GooglePlayServicesRepairableException;
import com.google.android.gms.common.api.Status;
import com.google.android.gms.location.places.Place;
import com.google.android.gms.location.places.ui.PlaceAutocomplete;
import com.google.android.gms.location.places.ui.PlaceAutocompleteFragment;
import com.google.android.gms.location.places.ui.PlaceSelectionListener;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.LocationSource;
import com.google.android.gms.maps.MapView;
import com.google.android.gms.maps.MapsInitializer;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.model.BitmapDescriptor;
import com.google.android.gms.maps.model.BitmapDescriptorFactory;
import com.google.android.gms.maps.model.CameraPosition;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MapStyleOptions;
import com.google.android.gms.maps.model.Marker;
import com.google.android.gms.maps.model.MarkerOptions;
import com.google.android.gms.maps.model.Polyline;
import com.google.android.gms.maps.model.PolylineOptions;

import org.json.JSONArray;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.Locale;
import java.util.concurrent.TimeUnit;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import pugman.com.simplelocationgetter.SimpleLocationGetter;

import static android.R.attr.addPrintersActivity;
import static android.R.attr.data;
import static android.app.Activity.RESULT_CANCELED;
import static android.app.Activity.RESULT_OK;

/**
 * Created by DAFFODIL-29 on 3/13/2018.
 */

public class MapsFragment extends Fragment {
    View view;
    MapView mMapView;
    private static final String API_KEY = "AIzaSyDdK10bxb18t3a4zVjV62GtQbY9TCk1PPs";
    final String TAG = "GPS";
    Marker m1=null,m2=null;
    int firstCameraMove=0;
    LocationManager locationManager;
    GoogleMap googleMap;
    String provider;
    String url;
    JSONArray directions;
    String loadUrl=null;
    Object datatransfer[];
    int PLACE_AUTOCOMPLETE_REQUEST_CODE = 1;
    Boolean MAP_MODE=true;
    Button changeMapView;
    ImageView searchView;
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
// Inflate the layout for this fragment
        view = inflater.inflate(R.layout.maps_fragment, container, false);
        mMapView = (MapView) view.findViewById(R.id.id_google_maps);
        mMapView.onCreate(savedInstanceState);
        mMapView.onResume();
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
                        m1=googleMap.addMarker(new MarkerOptions().position(loc).title("Your Location").snippet("You are here!").draggable(true).icon(BitmapDescriptorFactory.fromResource(R.drawable.ic_account_circle_black_24dp)));
                        return false;
                    }
                });
                googleMap.setOnMarkerDragListener(new GoogleMap.OnMarkerDragListener() {
                    boolean markerOne=false;

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
                        }
                        else if(m2.getId().equals(marker.getId())){
                            m2=marker;
                        }
                        m1=googleMap.addMarker(new MarkerOptions().position(m1.getPosition()).title("Drop Location").snippet(""+m1.getPosition().latitude+","+m1.getPosition().longitude).draggable(true));
                        if (m2!=null) {
                            m2 = googleMap.addMarker(new MarkerOptions().position(m2.getPosition()).title("Drop Location").snippet(""+m2.getPosition().latitude+","+m2.getPosition().longitude).draggable(true).icon(BitmapDescriptorFactory.fromResource(R.drawable.maps_icon)));
                        }
                        }
                });
                googleMap.setOnMyLocationChangeListener(new GoogleMap.OnMyLocationChangeListener() {
                    @Override
                    public void onMyLocationChange(Location location) {
                        LatLng loc=new LatLng(location.getLatitude(),location.getLongitude());
                        if(firstCameraMove==0) {
                            CameraPosition cameraPosition = new CameraPosition.Builder().target(loc).zoom(15).build();
                            googleMap.animateCamera(CameraUpdateFactory.newCameraPosition(cameraPosition));
                            firstCameraMove++;
                        }m1.remove();
                        m1=googleMap.addMarker(new MarkerOptions().position(loc).title("Your Location").snippet("You are here!").draggable(true).icon(BitmapDescriptorFactory.fromResource(R.drawable.ic_account_circle_black_24dp)));
                    }
                });
            }
        });
        final Button direction=(Button)view.findViewById(R.id.id_maps_view_directions);
        direction.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(m2==null){
                    Toast.makeText(getContext(), "Select Destination first!", Toast.LENGTH_SHORT).show();
                    search();
                }
                else {
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
        getActivity().getSupportFragmentManager().popBackStack(null, FragmentManager.POP_BACK_STACK_INCLUSIVE);
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
            directions = getDirectionsData.getDirections();
        }
    }
}