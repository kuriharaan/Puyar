package com.yumnup.puyar;

import android.app.DialogFragment;
import android.content.Intent;
import android.net.Uri;
import android.support.v4.app.FragmentActivity;
import android.os.Bundle;
import android.support.v4.view.MenuItemCompat;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;

import com.google.android.gms.common.GoogleApiAvailability;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.Marker;
import com.google.android.gms.maps.model.MarkerOptions;

import java.util.HashMap;
import java.util.Map;

public class MapsActivity extends AppCompatActivity implements OnMapReadyCallback, LinkConfirmationFragment.NoticeDialogListener, LegalNoticeFragment.NoticeDialogListener{

    private GoogleMap mMap;

    class MarkerData
    {
        double lat;
        double lng;
        String videoId;

        public MarkerData(double lat, double lng, String videoId)
        {
            this.lat = lat;
            this.lng = lng;
            this.videoId = videoId;
        }
    }

    HashMap<String, MarkerData> markerHash = new HashMap<String, MarkerData>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_maps);
        // Obtain the SupportMapFragment and get notified when the map is ready to be used.
        SupportMapFragment mapFragment = (SupportMapFragment) getSupportFragmentManager()
                .findFragmentById(R.id.map);
        mapFragment.getMapAsync(this);

        markerHash.put("Verona Italy Livecam Webcam Live Cam Streaming Piazza Erbe Giulietta e Romeo", new MarkerData(45.44385, 10.9951455, "rSUwjLgZ8Lk"));
        markerHash.put("東京タワーライブカメラ / TOKYOTOWER LIVE CAMERA", new MarkerData(35.6585805, 139.7432442, "X67sgKwmOE0"));
        markerHash.put("Johnson Street Bridge Live Webcam", new MarkerData(48.4280775, -123.3741179, "GyYPSZMQ9Hg"));
        markerHash.put("Prague Dominants, Czech Republic", new MarkerData(50.0386744, 14.5063607, "al73Y4YZa-Y"));
        markerHash.put("Prague, Czech Republic", new MarkerData(50.0803479, 14.3836666, "IgU321JODEs"));
        markerHash.put("SUNTEC Fountain of Wealth Live - Using Axis P3364 and NetRex CamStreamer", new MarkerData(1.2944879, 103.8565505, "zME4DDgSpwA"));
        markerHash.put("Lysefjord, Norway - Live!", new MarkerData(58.9776189, 6.0832104, "efLzFNmrMLI"));
        markerHash.put("WebCam.NL - LIVE HD Pan Tilt Zoom camera haven Vollenhove (OV.)", new MarkerData(52.6709355, 5.9086714, "FSpb0J6jA8s"));
        markerHash.put("Venice Italy Webcam - Rialto Bridge in Live Streaming from Palazzo Bembo Venezia", new MarkerData(45.4379879, 12.3337093, "cObrnhmjfxU"));
        markerHash.put("LIVE de pe Calea Victoriei (Cavaleria.ro)", new MarkerData(44.4419753, 26.092052, "NsCD9TjZIFA"));
        markerHash.put("Oxford Martin School Webcam - Broad Street, Oxford", new MarkerData(51.754866, -1.2564027, "JzCsc4UeUfs"));
        markerHash.put("WebCam.NL | www.amsterdam-dam.com - Amsterdam Dam LIVE HD Pan Tilt Zoom camera.", new MarkerData(52.3727291, 4.8936489, "YH3Nhp0R9t8"));
        //markerHash.put("Jackson Hole Town Square", new MarkerData(43.4793352, -110.7642137, "MOsjbvTfahg"));
        markerHash.put("Live Camera Axis P1357", new MarkerData(43.9164058, -112.0158144, "RcrTPW7zRIs"));
        markerHash.put("Alpine, Wyoming PTZ", new MarkerData(43.1611114, -111.0365561, "Yyg1E4mpLw8"));
        markerHash.put("Real Club Náutico de Salinas - Playa de Salinas, Asturias, Spain.", new MarkerData(43.5766441, -5.9653928, "K09BKMx4STQ"));
        markerHash.put("Lausanne, place de la Palud", new MarkerData(46.5219489, 6.6306945, "_YVgbeNw8jo"));
//markerHash.put("", new MarkerData(0, 0, ""));
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {

        MenuItem item = menu.add(0, 0, 0, "Help")
                .setIcon(R.drawable.ic_action_about);
        MenuItemCompat.setShowAsAction(item, MenuItemCompat.SHOW_AS_ACTION_IF_ROOM);

        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        boolean ret = true;

        DialogFragment dialog = new LegalNoticeFragment();
        dialog.show(getFragmentManager(), GoogleApiAvailability.getInstance().getOpenSourceSoftwareLicenseInfo(getApplicationContext()));

        return ret;
    }

    /**
     * Manipulates the map once available.
     * This callback is triggered when the map is ready to be used.
     * This is where we can add markers or lines, add listeners or move the camera. In this case,
     * we just add a marker near Sydney, Australia.
     * If Google Play services is not installed on the device, the user will be prompted to install
     * it inside the SupportMapFragment. This method will only be triggered once the user has
     * installed Google Play services and returned to the app.
     */
    @Override
    public void onMapReady(GoogleMap googleMap) {
        mMap = googleMap;

        LatLng startLatLng = new LatLng(0,0);
        int startMarkerIndex = (int)(Math.random() * markerHash.size());
        int index = 0;
        for(Map.Entry<String, MarkerData> e : markerHash.entrySet() )
        {
            MarkerData d = e.getValue();
            LatLng latlng = new LatLng(d.lat, d.lng);
            mMap.addMarker(new MarkerOptions().position(latlng).title(e.getKey()));
            if( index == startMarkerIndex )
            {
                startLatLng = latlng;
            }
            ++index;
        }


        mMap.moveCamera(CameraUpdateFactory.newLatLng(startLatLng));

        mMap.setOnMarkerClickListener(new GoogleMap.OnMarkerClickListener() {
            @Override
            public boolean onMarkerClick(Marker marker) {
                //Toast.makeText(getApplicationContext(), "マーカータップ", Toast.LENGTH_LONG).show();

                DialogFragment dialog = new LinkConfirmationFragment();
                //dialog.show(getFragmentManager(), "test");
                dialog.show(getFragmentManager(), marker.getTitle());

                return true;
            }
        });
    }


    @Override
    public void onDialogPositiveClick(DialogFragment dialog) {
        // User touched the dialog's positive button
        //String videoId = "rSUwjLgZ8Lk";
        //String videoId = markerData.videoId;

        MarkerData markerData = markerHash.get(dialog.getTag());

        String videoId = markerData.videoId;
        Intent intent = new Intent(Intent.ACTION_VIEW);
        intent.setData(Uri.parse("vnd.youtube:" + videoId));
        intent.putExtra("VIDEO_ID", videoId);
        startActivity(intent);
    }

    @Override
    public void onDialogNegativeClick(DialogFragment dialog) {
        // User touched the dialog's negative button
    }

    @Override
    public void onDialogCloseClick(DialogFragment dialog){

    }
}
