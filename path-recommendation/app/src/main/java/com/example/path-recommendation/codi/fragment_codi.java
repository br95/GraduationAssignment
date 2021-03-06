package com.example.shareonfoot.codi;

import android.Manifest;
import android.app.Activity;
import android.app.DatePickerDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Color;
import android.location.Address;
import android.location.Geocoder;
import android.location.Location;
import android.location.LocationManager;
import android.os.AsyncTask;
import android.os.Bundle;
import android.os.Looper;
import android.os.Message;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.CycleInterpolator;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AlertDialog;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;
import androidx.core.view.GravityCompat;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.fragment.app.Fragment;
import androidx.viewpager.widget.ViewPager;


import com.example.shareonfoot.R;
import com.example.shareonfoot.home.activity_home;
import com.example.shareonfoot.util.OnBackPressedListener;
import com.github.clans.fab.FloatingActionMenu;
import com.github.clans.fab.FloatingActionButton;
import com.google.android.gms.location.FusedLocationProviderClient;
import com.google.android.gms.location.LocationCallback;
import com.google.android.gms.location.LocationRequest;
import com.google.android.gms.location.LocationResult;
import com.google.android.gms.location.LocationServices;
import com.google.android.gms.location.LocationSettingsRequest;
import com.google.android.gms.maps.CameraUpdate;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.MapView;
import com.google.android.gms.maps.MapsInitializer;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.model.BitmapDescriptorFactory;
import com.google.android.gms.maps.model.JointType;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.Marker;
import com.google.android.gms.maps.model.MarkerOptions;
import com.google.android.gms.maps.model.PolylineOptions;

import org.json.JSONArray;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.net.CookieHandler;
import java.net.CookieManager;
import java.net.CookiePolicy;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.Locale;
import java.util.concurrent.Callable;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;


import static android.app.Activity.RESULT_OK;

public class fragment_codi extends Fragment implements OnBackPressedListener, OnMapReadyCallback, GoogleMap.OnMapClickListener, GoogleMap.OnMapLongClickListener,
        ActivityCompat.OnRequestPermissionsResultCallback {

    ViewGroup viewGroup;
    Toast toast;
    long backKeyPressedTime;
    private GoogleMap mMap;

    int MAKE_CODI = 120;
    int WEATHER_CODI = 191;
    int RECO_CODI = 255;

    Activity activity;
    private PolylineOptions polylineOptions;
    private ArrayList<LatLng> arrayPoints;



    DrawerLayout drawer;
    public RelativeLayout Cloth_Info;
    public RelativeLayout Cloth_Info_edit;
    public ImageView iv_image;
    public ImageView iv_edit_image;
    public TextView theme;
    public TextView tv_category;
    public TextView tv_detailcategory;
    public TextView tv_season;
    public TextView tv_brand;
    public TextView tv_date;
    public String weekDay;
    public ImageView iv_heart;
    public ImageView iv_modify;
    public ImageView iv_delete;
    public ImageView iv_save;
    public TextView tv_cloNo;
    public TextView tv_cloFavorite;
    public TextView tv_edit_category;
    public TextView tv_edit_season;
    public TextView tv_edit_date;
    public TextView tv_edit_name;
    public TextView tv_edit_detailcategory;
    public TextView tv_edit_brand;
    public TextView weekday;
    private MapView mapView = null;
    public static String ErrMag = "ErrMag";
    public String err;
    //tring[] REQUIRED_PERMISSIONS = {Manifest.permission.ACCESS_FINE_LOCATION, Manifest.permission.ACCESS_COARSE_LOCATION};
    private FloatingActionMenu fam;
    private FloatingActionButton fabMake, fabRecommend;

    private static final int UPDATE_INTERVAL_MS = 300000;  // 1???
    private static final int FASTEST_UPDATE_INTERVAL_MS = 300000; // 0.5???
    private Marker currentMarker = null;


    // onRequestPermissionsResult?????? ????????? ???????????? ActivityCompat.requestPermissions??? ????????? ????????? ????????? ???????????? ?????? ???????????????.


    // ?????? ???????????? ?????? ????????? ???????????? ???????????????.


    Location mCurrentLocatiion;


    private LocationRequest locationRequest;
    private Location location;


    // The entry point to the Fused Location Provider.
    private FusedLocationProviderClient mFusedLocationProviderClient; // Deprecated??? FusedLocationApi??? ??????

    private LatLng mDefaultLocation = new LatLng(37.375280717973304, 126.63289979777781);
    private static final int PERMISSIONS_REQUEST_ACCESS_FINE_LOCATION = 1;
    private boolean mLocationPermissionGranted;

    LatLng latLng=null;



    public fragment_codi() {
    }

    public static fragment_codi newInstance() {

        Bundle args = new Bundle();

        fragment_codi fragment = new fragment_codi();
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        viewGroup = (ViewGroup) inflater.inflate(R.layout.frag_codi, container, false);
        toast = Toast.makeText(getContext(), "?????? ??? ????????? ???????????????.", Toast.LENGTH_SHORT);
        mapView = (MapView) viewGroup.findViewById(R.id.map);
        mapView.getMapAsync(this);
        return viewGroup;
    }

    @Override
    public void onAttach(@NonNull Context context) {
        super.onAttach(context);

        if (context instanceof Activity) {
            activity = (Activity) context;
            ((activity_home) activity).setOnBackPressedListener(this);
        }

    }


    @Override
    public void onStart() {
        super.onStart();

        drawer = getView().findViewById(R.id.final_drawer_layout);

        Cloth_Info = (RelativeLayout) getView().findViewById(R.id.cloth_info);
        Cloth_Info.setVisibility(View.GONE);
        Cloth_Info_edit = (RelativeLayout) getView().findViewById(R.id.cloth_info_edit);
        Cloth_Info_edit.setVisibility(View.GONE);

        iv_image = (ImageView) getView().findViewById(R.id.iv_codi_image);
        iv_edit_image = (ImageView) getView().findViewById(R.id.iv_edit_image);
        tv_category = (TextView) getView().findViewById(R.id.tv_info_catergory);
        tv_detailcategory = (TextView) getView().findViewById(R.id.tv_info_detailcategory);
        tv_season = (TextView) getView().findViewById(R.id.tv_info_season);
        tv_brand = (TextView) getView().findViewById(R.id.tv_info_brand);
        tv_date = (TextView) getView().findViewById(R.id.tv_info_date);

        iv_heart = (ImageView) getView().findViewById(R.id.iv_heart);
        iv_modify = (ImageView) getView().findViewById(R.id.iv_modify);
        iv_delete = (ImageView) getView().findViewById(R.id.iv_delete);
        iv_save = (ImageView) getView().findViewById(R.id.iv_save);

        tv_cloNo = (TextView) getView().findViewById(R.id.tv_clothes_no);
        tv_cloFavorite = (TextView) getView().findViewById(R.id.tv_clothes_favorite);
        tv_edit_name = (TextView) getView().findViewById(R.id.tv_info_color);
        tv_edit_detailcategory = (TextView) getView().findViewById(R.id.tv_edit_detailcategory);
        tv_edit_brand = (TextView) getView().findViewById(R.id.tv_edit_brand);
        weekday = (TextView) getView().findViewById(R.id.tabLayout);

        tv_edit_category = (TextView) getView().findViewById(R.id.tv_edit_catergory);
        tv_edit_season = (TextView) getView().findViewById(R.id.tv_edit_season);
        tv_edit_date = (TextView) getView().findViewById(R.id.tv_edit_date);
        theme = (TextView) getView().findViewById(R.id.day_theme);

        final String[] Category = {""};


        // BitmapDescriptorFactory ???????????? ?????? ??????

        MapsInitializer.initialize(requireActivity());
        mapView = (MapView) getView().findViewById(R.id.map);
        mapView.getMapAsync(this);
        arrayPoints = new ArrayList<LatLng>();
        //??? ?????? ??????


        SimpleDateFormat dayFormat = new SimpleDateFormat("EEEE", Locale.getDefault());

        Calendar calendar = Calendar.getInstance();
        weekDay = dayFormat.format(calendar.getTime());


        weekday.setText(weekDay);
        switch (day_return(weekDay)) {
            case 1:
                theme.setText("????????? ????????? ?????????!!");

                break;
            case 2:
                theme.setText("????????? ????????????!!");

                break;
            case 3:
                theme.setText("????????? ???????????? ??????!!");

                break;
            case 4:
                theme.setText("????????? ????????? ??????!!");

                break;
            case 5:
                theme.setText("????????? ??????!!");

                break;
            case 6:
                theme.setText("????????? ?????? ??????!!");

                break;
            case 7:
                theme.setText("????????? ?????? ???!!");

                break;
        }


        //????????? ?????? ?????? ??????

        //fabAdd = (FloatingActionButton) getView().findViewById(R.id.fab_add_photo);
        fabMake = (FloatingActionButton) getView().findViewById(R.id.fab_make_codi);
        fabRecommend = (FloatingActionButton) getView().findViewById(R.id.fab_recommend_codi);
        fam = (FloatingActionMenu) getView().findViewById(R.id.fab_menu);

        //handling menu status (open or close)
        fam.setOnMenuToggleListener(new FloatingActionMenu.OnMenuToggleListener() {
            @Override
            public void onMenuToggle(boolean opened) {
                if (opened) {
                    //Toast.makeText(getContext(), "Menu is opened", Toast.LENGTH_SHORT).show();
                } else {
                    //Toast.makeText(getContext(), "Menu is closed", Toast.LENGTH_SHORT).show();
                }
            }
        });

        //handling each floating action button clicked
        //fabAdd.setOnClickListener(onClickListener);


        BtnOnClickListener onClickListener = new BtnOnClickListener();

        fabMake.setOnClickListener(onClickListener);
        fabRecommend.setOnClickListener(onClickListener);

        fam.setOnMenuButtonClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (fam.isOpened()) {
                    fam.close(true);
                } else {
                    fam.open(true);
                }
            }
        });

        fam.setIconAnimationOpenInterpolator(new CycleInterpolator(-0.5f));
        fam.setIconAnimationCloseInterpolator(new CycleInterpolator(-0.75f));
        fam.setClosedOnTouchOutside(true);
        fam.getMenuIconView().setColorFilter(Color.parseColor("#000000"));

    }




    public int day_return(String day) {
        if (day.equals("?????????")) return 1;
        else if (day.equals("?????????")) return 2;
        else if (day.equals("?????????")) return 3;
        else if (day.equals("?????????")) return 4;
        else if (day.equals("?????????")) return 5;
        else if (day.equals("?????????")) return 6;
        else return 7;
    }

    private DatePickerDialog.OnDateSetListener listener = new DatePickerDialog.OnDateSetListener() {
        @Override
        public void onDateSet(DatePicker view, int year, int monthOfYear, int dayOfMonth) {
            tv_edit_date.setText(year + "???" + monthOfYear + "???" + dayOfMonth + "???");
            Toast.makeText(getContext(), year + "???" + monthOfYear + "???" + dayOfMonth + "???", Toast.LENGTH_SHORT).show();
        }
    };


    @Override
    public void onResume() {
        super.onResume();
        mapView.onResume();
        if (mLocationPermissionGranted) {
            Log.d("TAG", "onResume : requestLocationUpdates");
            if (ActivityCompat.checkSelfPermission(getContext(), Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(getContext(), Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
                // TODO: Consider calling
                //    ActivityCompat#requestPermissions
                // here to request the missing permissions, and then overriding
                //   public void onRequestPermissionsResult(int requestCode, String[] permissions,
                //                                          int[] grantResults)
                // to handle the case where the user grants the permission. See the documentation
                // for ActivityCompat#requestPermissions for more details.
                return;
            }
            mFusedLocationProviderClient.requestLocationUpdates(locationRequest, locationCallback, null);
            if (mMap!=null)
                mMap.setMyLocationEnabled(true);
        }
    }

    //?????? ?????? ????????? ????????? ?????? ?????????(??????)??? ?????????.
    @Override
    public void onBackPressed() {

        if (drawer.isDrawerOpen(GravityCompat.START)) {
            drawer.closeDrawer(GravityCompat.START);
        } else if(fam.isOpened()){
            fam.close(true);
        } else if (Cloth_Info_edit.getVisibility() == View.VISIBLE) {
            Cloth_Info_edit.setVisibility(View.GONE);
        } else if (Cloth_Info.getVisibility() == View.VISIBLE) {
            Cloth_Info.setVisibility(View.GONE);
        } else if(System.currentTimeMillis() > backKeyPressedTime + 2000){
            backKeyPressedTime = System.currentTimeMillis();
            toast.show();
            return;
        } else if(System.currentTimeMillis() <= backKeyPressedTime + 2000){
            activity.finish();
            toast.cancel();
        }
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);

//??????????????? ?????? ????????? ??? ???????????? ??????

        locationRequest = new LocationRequest()
                .setPriority(LocationRequest.PRIORITY_HIGH_ACCURACY) // ???????????? ?????????????????? ??????
                .setInterval(UPDATE_INTERVAL_MS) // ????????? Update ?????? ??????
                .setFastestInterval(FASTEST_UPDATE_INTERVAL_MS); // ?????? ????????? ?????????????????? ??????

        LocationSettingsRequest.Builder builder =
                new LocationSettingsRequest.Builder();

        builder.addLocationRequest(locationRequest);

        // FusedLocationProviderClient ?????? ??????
        mFusedLocationProviderClient = LocationServices.getFusedLocationProviderClient(getActivity());

        if(mapView != null)
        {
            mapView.onCreate(savedInstanceState);
        }
    }


    @Override
    public void onMapClick(LatLng latLng) {
      /*  MarkerOptions markerOptions = new MarkerOptions();
        //add marker
        markerOptions.position(latLng);
        markerOptions.icon(BitmapDescriptorFactory.fromResource(R.drawable.marker2));
        mMap.addMarker(markerOptions);
        polylineOptions = new PolylineOptions();
        polylineOptions.color(Color.YELLOW);
        polylineOptions.width(8);
        // ?????????
        arrayPoints.add(latLng);
        polylineOptions.addAll(arrayPoints);
        mMap.addPolyline(polylineOptions);*/
    }

    //?????? ?????????
    class BtnOnClickListener implements Button.OnClickListener {
        String res="";

        @Override
        public void onClick(View view) {
            Intent intent;
        }
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if(requestCode == MAKE_CODI && resultCode == RESULT_OK)
            ((activity_home)activity).refresh_codi(fragment_codi.this);
        else if(requestCode == WEATHER_CODI && resultCode == RESULT_OK)
            ((activity_home)activity).refresh_codi(fragment_codi.this);
        else if(requestCode == RECO_CODI && resultCode == RESULT_OK)
            ((activity_home)activity).refresh_codi(fragment_codi.this);

    }

    //????????? ??????
    //????????? ??????
    @Override
    public void onMapReady(final GoogleMap map) {

        mMap = map;

        setDefaultLocation(); // GPS??? ?????? ????????? ????????? ?????? ?????? ????????? ?????? ????????? ?????????.
        getLocationPermission();
        updateLocationUI();
        getDeviceLocation();

    }

    @Override
    public void onStop() {
        super.onStop();
        mapView.onStop();
        if (mFusedLocationProviderClient != null) {
            Log.d("TAG", "onStop : removeLocationUpdates");
            mFusedLocationProviderClient.removeLocationUpdates(locationCallback);
        }
    }

    @Override
    public void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
        mapView.onSaveInstanceState(outState);
    }

    @Override
    public void onPause() {
        super.onPause();
        mapView.onPause();
    }

    @Override
    public void onLowMemory() {
        super.onLowMemory();
        mapView.onLowMemory();
    }



    @Override
    public void onDestroy() {
        super.onDestroy();
        mapView.onLowMemory();
    }

    @Override
    public void onMapLongClick(LatLng latLng) {

      /*  mMap.clear();
        arrayPoints.clear();*/


    }

    class NearestTask extends AsyncTask<String, String, String> {
        String sendMsg, receiveMsg;
        StringBuffer Buffer = new StringBuffer();
        URL url;

        @Override
        protected void onPreExecute() {
            super.onPreExecute();
        }

        @Override
        protected String doInBackground(String[] strings) {
            CookieHandler.setDefault( new CookieManager( null, CookiePolicy.ACCEPT_ALL ) );
            String get_json = "",tmp;
            URL url;

            try {
                url = new URL("http://49.50.172.215:8080/shareonfoot/data.jsp");
                HttpURLConnection conn = null;
                try {
                    conn = (HttpURLConnection) url.openConnection();
                } catch (IOException ioException) {
                    ioException.printStackTrace();
                    err = ioException.toString();
                    Log.i(ErrMag, "1");
                }
                conn.setRequestMethod("POST"); // URL ????????? ?????? ????????? ?????? : POST.
                conn.setRequestProperty("Accept-Charset", "UTF-8"); // Accept-Charset ??????.
                conn.setRequestProperty("Context_Type", "application/x-www-form-urlencoded;cahrset=UTF-8");

                // ???????????? ???????????? ?????? ????????? ??????
                OutputStreamWriter wr = new OutputStreamWriter(conn.getOutputStream());
                sendMsg = "lat=" + strings[0]+ "&lng=" + strings[1] + "&day=" + strings[2];
                Log.i("day",strings[2]);
                wr.write(sendMsg);
                wr.flush();
                wr.close();

                if (conn.getResponseCode() == conn.HTTP_OK) {

                    // ???????????? ???????????? ?????? ????????? ??????
                    InputStreamReader isr = new InputStreamReader(
                            conn.getInputStream());
                    // ???????????? ???????????? ?????? BufferReader??? ?????????.
                    BufferedReader br = new BufferedReader(isr);
                    // ????????? ?????????????????????
                    while (true) {
                        String line = br.readLine();
                        if (line == null) {

                            break;
                        }
                        Buffer.append(line);
                    }
                    br.close();
                    conn.disconnect();
                }
                get_json = Buffer.toString();
                Log.i("msg", "get_json: " + get_json);

            } catch (MalformedURLException e) {
                e.printStackTrace();
                err = e.toString();
                Log.i(ErrMag, "5");
            } catch (IOException e) {
                e.printStackTrace();
                err = e.toString();
                Log.i(ErrMag, err);
            }
            return get_json;
        }


        public void onPostExecute(String result) {
            super.onPostExecute(result);
            List<Location> list = new ArrayList<>();
            int i=0;
            Log.d("onPostExecute:  ", " <<<<<onPostExecute>>>> ");
            try {
                JSONArray jarray = new JSONObject(result).getJSONArray("store_data");
                if(jarray!=null){
                    while (jarray != null) {
                        JSONObject jsonObject = jarray.getJSONObject(i);
                        String name = jsonObject.getString("store_name");
                        double lng =  Double.parseDouble(jsonObject.getString("store_lng"));
                        double lat = Double.parseDouble(jsonObject.getString("store_lat"));
                        double dst = Double.parseDouble(jsonObject.getString("store_dst"));
                        //Toast.makeText(getContext(), String.valueOf(dst), Toast.LENGTH_SHORT).show();
                        LatLng position=new LatLng(lat,lng);
                        MarkerOptions markerOptions = new MarkerOptions();
                        markerOptions.position(position)
                                .title(name)
                                .snippet(String.format("????????? ?????? %.2fkm", dst));
                        switch (i){
                            case 0:
                                markerOptions.icon(BitmapDescriptorFactory.fromResource(R.drawable.m1));
                                break;
                            case 1:
                                markerOptions.icon(BitmapDescriptorFactory.fromResource(R.drawable.m2));
                                break;
                            case 2:
                                markerOptions.icon(BitmapDescriptorFactory.fromResource(R.drawable.m3));
                                break;
                            case 3:
                                markerOptions.icon(BitmapDescriptorFactory.fromResource(R.drawable.m4));
                                break;
                            case 4:
                                markerOptions.icon(BitmapDescriptorFactory.fromResource(R.drawable.m5));
                                break;
                            default:
                                markerOptions.icon(BitmapDescriptorFactory.fromResource(R.drawable.marker2));
                                break;
                        }
                        mMap.addMarker(markerOptions);
                        polylineOptions = new PolylineOptions();
                        polylineOptions.color(Color.rgb(135,206,235))
                                .width(10).
                                jointType(JointType.ROUND);;
                        // ?????????
                        arrayPoints.add(position);
                        polylineOptions.addAll(arrayPoints);

                        mMap.addPolyline(polylineOptions);
                        // null??? ?????? ??? ???????????? ?????? ????????? ????????? ??????
                        //String Start = jsonObject.optString("START_TIME", "text on no value");
                        //String Stop = jsonObject.optString("STOP_TIME", "text on no value");
                        //String REG = jsonObject.optString("REG_TIME", "text on no value");
                        Log.i("qw", name + "/" + lng+ "/" + lat);

                        i++;
                    }
                } else {
                    Toast.makeText(getContext(), "????????? ??? ????????????.", Toast.LENGTH_SHORT).show();
                }
            } catch (Exception e) {
                Log.e(ErrMag, "7");
            }
        }
    }

    private void updateLocationUI() {
        if (mMap == null) {
            return;
        }
        try {
            if (mLocationPermissionGranted) {
                mMap.setMyLocationEnabled(true);
                mMap.getUiSettings().setMyLocationButtonEnabled(true);
            } else {
                mMap.setMyLocationEnabled(false);
                mMap.getUiSettings().setMyLocationButtonEnabled(false);
                mCurrentLocatiion = null;
                getLocationPermission();
            }
        } catch (SecurityException e)  {
            Log.e("Exception: %s", e.getMessage());
        }
    }

    private void setDefaultLocation() {
        if (currentMarker != null) currentMarker.remove();

        MarkerOptions markerOptions = new MarkerOptions();
        markerOptions.position(mDefaultLocation);
        markerOptions.title("???????????? ????????? ??? ??????");
        markerOptions.snippet("?????? ???????????? GPS ?????? ?????? ???????????????");
        markerOptions.draggable(true);
        markerOptions.icon(BitmapDescriptorFactory.defaultMarker(BitmapDescriptorFactory.HUE_RED));
        currentMarker = mMap.addMarker(markerOptions);

        CameraUpdate cameraUpdate = CameraUpdateFactory.newLatLngZoom(mDefaultLocation, (float) 15);
        mMap.moveCamera(cameraUpdate);
    }

    String getCurrentAddress(LatLng latlng) {
        // ?????? ????????? ?????????????????? ?????? ???????????? ?????????.
        List<Address> addressList = null ;
        Geocoder geocoder = new Geocoder( getActivity(), Locale.getDefault());

        // ??????????????? ???????????? ?????? ???????????? ?????????.
        try {
            addressList = geocoder.getFromLocation(latlng.latitude,latlng.longitude,1);
        } catch (IOException e) {
            Toast. makeText( getActivity(), "??????????????? ????????? ????????? ??? ????????????. ??????????????? ???????????? ????????? ????????? ?????????.", Toast.LENGTH_SHORT ).show();
            e.printStackTrace();
            return "?????? ?????? ??????" ;
        }

        if (addressList.size() < 1) { // ?????? ???????????? ??????????????? ?????? ?????????
            return "?????? ????????? ?????? ??????" ;
        }

        // ????????? ?????? ???????????? ???????????? ??????
        Address address = addressList.get(0);
        StringBuilder addressStringBuilder = new StringBuilder();
        for (int i = 0; i <= address.getMaxAddressLineIndex(); i++) {
            addressStringBuilder.append(address.getAddressLine(i));
            if (i < address.getMaxAddressLineIndex())
                addressStringBuilder.append("\n");
        }

        return addressStringBuilder.toString();
    }

    LocationCallback locationCallback = new LocationCallback() {
        @Override
        public void onLocationResult(LocationResult locationResult) {
            super.onLocationResult(locationResult);

            List<Location> locationList = locationResult.getLocations();

            if (locationList.size() > 0) {
                Location location = locationList.get(locationList.size() - 1);

                LatLng currentPosition
                        = new LatLng(location.getLatitude(), location.getLongitude());

                String markerTitle = getCurrentAddress(currentPosition);
                String markerSnippet = "??????:" + String.valueOf(location.getLatitude())
                        + " ??????:" + String.valueOf(location.getLongitude());

                Log.d("TAG", "Time :" + CurrentTime() + " onLocationResult : " + markerSnippet);

                //?????? ????????? ?????? ???????????? ??????
                setCurrentLocation(location, markerTitle, markerSnippet);
                mCurrentLocatiion = location;

            }
        }

    };

    private String CurrentTime(){
        Date today = new Date();
        SimpleDateFormat date = new SimpleDateFormat("yyyy/MM/dd");
        SimpleDateFormat time = new SimpleDateFormat("hh:mm:ss a");
        return time.format(today);
    }

    public void setCurrentLocation(Location location, String markerTitle, String markerSnippet) {
        if (currentMarker != null) currentMarker.remove();

        //LatLng currentLatLng = new LatLng(location.getLatitude(), location.getLongitude());
        LatLng currentLatLng = new LatLng(37.341561, 126.7328088);
        latLng =currentLatLng;

        MarkerOptions markerOptions = new MarkerOptions();
        markerOptions.position(currentLatLng).icon(BitmapDescriptorFactory.fromResource(R.drawable.marker));

        Log.i("lng",String.valueOf(latLng.latitude));

        currentMarker = mMap.addMarker(markerOptions);
        mMap.moveCamera(CameraUpdateFactory.newLatLngZoom(currentLatLng, (float) 15));
        //CameraUpdate cameraUpdate = CameraUpdateFactory.newLatLng(currentLatLng);
        //mMap.moveCamera(cameraUpdate);

        mMap.setOnMapClickListener((GoogleMap.OnMapClickListener) this);
        mMap.setOnMapLongClickListener((GoogleMap.OnMapLongClickListener) this);

        SimpleDateFormat dayFormat = new SimpleDateFormat("EEEE", Locale.getDefault());

        Calendar calendar = Calendar.getInstance();
        weekDay = dayFormat.format(calendar.getTime());
        int day=day_return(weekDay);

        String coordinates[] = {String.valueOf(latLng.latitude), String.valueOf(latLng.longitude),String.valueOf(day)};
        //String coordinates[] = {"126.7328088", "37.341561`",String.valueOf(day)};
        //  mMap.setOnInfoWindowClickListener((GoogleMap.OnInfoWindowClickListener) this); //????????? ?????? ?????????(?????? ?????? ?????????)
        mMap.clear();
        arrayPoints.clear();
        new NearestTask().execute(coordinates);
    }

    private void getDeviceLocation() {
        try {
            if (mLocationPermissionGranted) {
                mFusedLocationProviderClient.requestLocationUpdates(locationRequest, locationCallback, Looper.myLooper());
            }
        } catch (SecurityException e)  {
            Log.e("Exception: %s", e.getMessage());
        }
    }

    private void getLocationPermission() {
        if (ContextCompat.checkSelfPermission(getActivity(),
                android.Manifest.permission.ACCESS_FINE_LOCATION)
                == PackageManager.PERMISSION_GRANTED) {
            mLocationPermissionGranted = true;
        } else {
            ActivityCompat.requestPermissions(getActivity(),
                    new String[]{android.Manifest.permission.ACCESS_FINE_LOCATION},
                    PERMISSIONS_REQUEST_ACCESS_FINE_LOCATION);
        }
    }

    @Override
    public void onRequestPermissionsResult(int requestCode,
                                           @NonNull String permissions[],
                                           @NonNull int[] grantResults) {
        mLocationPermissionGranted = false;
        switch (requestCode) {
            case PERMISSIONS_REQUEST_ACCESS_FINE_LOCATION: {
                if (grantResults.length > 0
                        && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                    mLocationPermissionGranted = true;
                }
            }
        }
        updateLocationUI();
    }

    public boolean checkLocationServicesStatus() {
        LocationManager locationManager = (LocationManager) getActivity().getSystemService(Context.LOCATION_SERVICE);

        return locationManager.isProviderEnabled(LocationManager.GPS_PROVIDER) ||
                locationManager.isProviderEnabled(LocationManager.NETWORK_PROVIDER);
    }
}