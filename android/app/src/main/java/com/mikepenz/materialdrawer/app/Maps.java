package com.mikepenz.materialdrawer.app;

import android.graphics.Bitmap;
import android.os.Bundle;
import android.support.annotation.IntegerRes;
import android.support.v4.graphics.drawable.RoundedBitmapDrawable;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.Toast;

import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonArrayRequest;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.MapFragment;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.model.BitmapDescriptorFactory;
import com.google.android.gms.maps.model.CameraPosition;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.Marker;
import com.google.android.gms.maps.model.MarkerOptions;
import com.mikepenz.materialdrawer.AccountHeader;
import com.mikepenz.materialdrawer.Drawer;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;


public class Maps extends AppCompatActivity implements OnMapReadyCallback {
    private static final int PROFILE_SETTING = 100000;

    private AccountHeader headerResult = null;
    private Drawer result = null;
    private RoundedBitmapDrawable BitmapDrawable;
    private Bitmap bitmap = null;
    MapFragment mapFragment;
    GoogleMap gMap;
    MarkerOptions markerOptions = new MarkerOptions();
    CameraPosition cameraPosition;
    LatLng center, latLng;
    String title;
//    String sumPolling1;
//    String kandidat2;
//    String kandidat3;


//    private List<String> title = new ArrayList<>();
//    private List<String> lat = new ArrayList<>();
//    private List<String> lng = new ArrayList<>();
//

    public static final String ID = "id";
    public static final String TITLE = "nama";
    public static final String kandidat1 = "sumPolling1";
    public static final String kandidat2 = "sumPolling2";
    public static final String kandidat3 = "sumPolling3";
    public static final String LAT = "lat";
    public static final String LNG = "lng";


    // private String url = "http://192.168.43.222/api/maps.php";
    //  public static final String URL_MAPS= ROOT_URL+"maps.php";
    //public static final String ROOT_URL ="http://192.168.43.222/api/maps.php";
    //private String url = "http://wisatademak.dedykuncoro.com/Main/json_wisata";

    String tag_json_obj = "json_obj_req";


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.maps);

        mapFragment = (MapFragment) getFragmentManager().findFragmentById(R.id.maps);
        mapFragment.getMapAsync(this);

    }

    @Override
    public void onMapReady(GoogleMap googleMap) {
        gMap = googleMap;

       /* gMap.setInfoWindowAdapter(new GoogleMap.InfoWindowAdapter() {

            public View getInfoWindow(Marker arg0) {
                View v = getLayoutInflater().inflate(R.layout.custom_infowindow, null);
                return v;
            }

            public View getInfoContents(Marker arg0) {

                //View v = getLayoutInflater().inflate(R.layout.custom_infowindow, null);

                return null;

            }
        });*/

        // Mengarahkan ke alun-alun Demak
        //    center = new LatLng(-6.894796, 110.638413);
        center = new LatLng(-7.09091091156006, 107.668884277344);
        cameraPosition = new CameraPosition.Builder().target(center).zoom(8).build();
        googleMap.animateCamera(CameraUpdateFactory.newCameraPosition(cameraPosition));

        getMarkers();
    }

    private void addMarker(LatLng latlng, final String title, final int kandidat1, final int kandidat2, final int kandidat3) {
        markerOptions.position(latlng);
        markerOptions.title(title);
        markerOptions.snippet( "kandidat 1 = " +  kandidat1 + " " + "kandidat 2 = " + kandidat2 + " " +  "kandidat 3 =" + kandidat3);

        //     markerOptions.snippet("Kandidat 1 : \n "+ "kandidat 2 = \n");
        gMap.addMarker(markerOptions);


        gMap.setOnInfoWindowClickListener(new GoogleMap.OnInfoWindowClickListener() {
            @Override


            public void onInfoWindowClick(Marker marker) {
                Toast.makeText(getApplicationContext(), marker.getTitle(), Toast.LENGTH_LONG).show();
            }
        });
    }


    // Fungsi get JSON marker
    private void getMarkers() {
        // StringRequest strReq = new StringRequest(Request.Method.POST, url, new Response.Listener<String>() {
        JsonArrayRequest jArr = new JsonArrayRequest(Constant.URL_MAPS,
                new Response.Listener<JSONArray>() {
                    @Override
                    public void onResponse(JSONArray response) {
                        Log.e("Response: ", response.toString());


                        try {
                            Log.d("JsonArray", response.toString());

                            for (int i = 0; i < response.length(); i++) {

                                JSONObject jsonObject = response.getJSONObject(i);

                                String title = jsonObject.getString("nama");
                                Log.i("nama", title);

                                latLng = new LatLng(Double.parseDouble(jsonObject.getString(LAT)), Double.parseDouble(jsonObject.getString(LNG)));
                                Log.i("latlng", String.valueOf(latLng));


                                int kandidat1 = jsonObject.getInt("sumPolling1");
                                Log.i("sumPolling1", "" + kandidat1 + "\n");

                                int kandidat2 = jsonObject.getInt("sumPolling2");
                                Log.i("sumPolling2", ""+kandidat2 + "\n");

                                int kandidat3 = jsonObject.getInt("sumPolling3");
                                Log.i("sumPolling3", ""+kandidat3 + "\n");

                                addMarker(latLng, title, kandidat1, kandidat2, kandidat3);

                            }

                        } catch (JSONException e) {
                            // JSON error
                            e.printStackTrace();
                        }

                    }
                }, new Response.ErrorListener() {

            @Override
            public void onErrorResponse(VolleyError error) {
                Log.e("Error: ", error.getMessage());
                Toast.makeText(Maps.this, error.getMessage(), Toast.LENGTH_LONG).show();
            }
        });
        CustomApplication.getInstance().addToRequestQueue(jArr, tag_json_obj);
    }

}


