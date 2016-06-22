package com.bod.yangondoortodoor.fragment;


import android.app.Dialog;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.res.Configuration;
import android.graphics.Color;
import android.location.Location;
import android.location.LocationManager;
import android.os.AsyncTask;
import android.os.Bundle;
import android.os.Handler;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.bod.yangondoortodoor.R;
import com.bod.yangondoortodoor.config.ConfigLink;
import com.bod.yangondoortodoor.model.CompDistance;
import com.bod.yangondoortodoor.model.DataCarry;
import com.bod.yangondoortodoor.model.LatLngObj;
import com.bod.yangondoortodoor.model.LocObj;
import com.bod.yangondoortodoor.model.MapData;
import com.bod.yangondoortodoor.ui.DirectionsJSONParser;
import com.bod.yangondoortodoor.ui.GCMClientManager;
import com.bod.yangondoortodoor.util.SharePref;
import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.GooglePlayServicesUtil;
import com.google.android.gms.maps.CameraUpdate;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.MapView;
import com.google.android.gms.maps.MapsInitializer;
import com.google.android.gms.maps.model.BitmapDescriptorFactory;
import com.google.android.gms.maps.model.CameraPosition;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;
import com.google.android.gms.maps.model.Polyline;
import com.google.android.gms.maps.model.PolylineOptions;

import org.apache.http.HttpResponse;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.message.BasicHeader;
import org.apache.http.params.BasicHttpParams;
import org.apache.http.params.HttpConnectionParams;
import org.apache.http.params.HttpParams;
import org.apache.http.protocol.HTTP;
import org.apache.http.util.EntityUtils;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Locale;


public  class HomeFragment extends Fragment {


    com.google.android.gms.maps.model.LatLng currentLatLng;
    private MapView mMapView;
    private GoogleMap mMap;
    protected com.google.android.gms.maps.model.LatLng start;
    protected com.google.android.gms.maps.model.LatLng end;
    String lineColor="";
    TextView btnNoti;
    LocationManager locationManager1;
    Location location1;
    CameraUpdate cameraUpdate;
    View inflatedView;
    Location location;
    private Bundle mBundle;
    double latitude=16.83692208009675 ;
    double longitude=96.12100012600422;
    String PROJECT_NUMBER="720495169328";
    LocationManager locationManager;
    String flag = "f";
    String locationProvider;
    private ImageView chatHead;
    String randomAndroidColor;
    private ProgressDialog progressDialog;
    private ArrayList<Polyline> polylines;

    ArrayList<com.google.android.gms.maps.model.LatLng> markerPoints;
    ArrayList<LocObj> locList = new ArrayList<LocObj>();
    ArrayList<LocObj> locOrdList= new ArrayList<LocObj>();
   ArrayList<LatLngObj> LatLngList = new ArrayList<LatLngObj>();
    String fposLat = "16.83692208009675";
    String fposLng = "96.12100012600422";
    String sposLat = "16.8037653";
    String sposLng = "96.1408736";
   LatLng firstPosition = new LatLng(16.83692208009675,96.12100012600422);
   LatLng secondPosition = new LatLng(16.83729176098213,96.1258066445589);
   LatLng thirdPosition = new  LatLng(16.83729176098213,96.1258066445589);
   LatLng fourPosition = new  LatLng(16.831912062761212,96.12792626023293);
    LatLng firstPosition1 = new  LatLng(16.8037653,96.1408736);
    LatLng secondPosition1 = new LatLng(16.822481, 96.160329);
   LatLng thirdPosition1 = new LatLng(16.822481, 96.160329);
    LatLng fourPosition1 = new LatLng(16.813362, 96.169928);
    LatLng point = new LatLng(16.83692208009675,96.12100012600422);
    ProgressDialog pd;


    //String[] url = new String[2];
   // private int[] colors = new int[]{R.color.primary_dark,R.color.primary,R.color.primary_light,R.color.accent,R.color.primary_dark_material_light};

    Handler mHandler = new Handler();


    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mBundle = savedInstanceState;
        setHasOptionsMenu(true);
        Log.e("Home ...","Oncreate...");
        markerPoints = new ArrayList<LatLng>();

      // ConnectServerGetAllLocation connAllLoc = new ConnectServerGetAllLocation(getActivity());
      // connAllLoc.execute();
      //  connAllLoc.executeOnExecutor(AsyncTask.THREAD_POOL_EXECUTOR);
    }

    private void setUpMapIfNeeded(View inflatedView)
    {
        if (mMap == null) {
            mMap = ((MapView) inflatedView.findViewById(R.id.map)).getMap();
            if (mMap != null)
            {
                setUpMap();
                setLatLngMap();
            }
        }
    }

    private void setLatLngMap()
    {
        Log.e("length of  list ",locList.size()+"");
        for(int j=0;j<locList.size();j++)
        {
            randomAndroidColor = locList.get(j).getFlagIcon();
            Log.e("Color Value",randomAndroidColor);
            for (int i = 0; i < 4; i++)
            {
                Log.e("value of j",j+"");
                if (i == 0)
                {
                    flag  = "0";
                    point = locList.get(j).firstPosition;
                }
                else if (i == 1)
                {
                    flag = "1";
                    point = locList.get(j).secondPosition;

                } else if (i == 2)
                {
                    flag = "2";
                    point = locList.get(j).thirdPosition;
                }
                else if (i == 3)
                {
                    flag = "3";
                    point = locList.get(j).fourPosition;
                }
                markerPoints.add(point);
                // Creating MarkerOptions
                MarkerOptions options = new MarkerOptions();
                // Setting the position of the marker
                options.position(point);
                /**
                 * For the start location, the color of marker is GREEN and
                 * for the end location, the color of marker is RED.
                 */
                if (markerPoints.size() % 2 == 1)
                {
                    if(flag.equalsIgnoreCase("0"))
                    {
                       if(randomAndroidColor.equalsIgnoreCase("yellow"))
                        {
                            options.icon(BitmapDescriptorFactory.fromResource(R.drawable.byellow));
                        }

                        if(randomAndroidColor.equalsIgnoreCase("orange"))
                        {
                            options.icon(BitmapDescriptorFactory.fromResource(R.drawable.borange));
                        }

                        if(randomAndroidColor.equalsIgnoreCase("green"))
                        {
                            options.icon(BitmapDescriptorFactory.fromResource(R.drawable.bgreen));
                        }
                        //options.icon(BitmapDescriptorFactory.fromResource(R.drawable.d2dmaker));
                        options.title(locList.get(j).getMesName());
                    }

                    if(flag.equalsIgnoreCase("2"))
                    {
                        if(randomAndroidColor.equalsIgnoreCase("yellow"))
                        {
                            options.icon(BitmapDescriptorFactory.fromResource(R.drawable.resyellow));
                        }
                        if(randomAndroidColor.equalsIgnoreCase("orange"))
                        {
                            options.icon(BitmapDescriptorFactory.fromResource(R.drawable.resoragne));
                        }
                        if(randomAndroidColor.equalsIgnoreCase("green"))
                        {
                            options.icon(BitmapDescriptorFactory.fromResource(R.drawable.resgreen));
                        }
                        options.title(locList.get(j).getOrderNo());
                        //options.icon(BitmapDescriptorFactory.fromResource(R.drawable.resmaker));
                    }
                }

                if( markerPoints.size() %2 == 0)
                {
                    if(flag.equalsIgnoreCase("1"))
                    {
                        if(randomAndroidColor.equalsIgnoreCase("yellow"))
                        {
                            options.icon(BitmapDescriptorFactory.fromResource(R.drawable.resyellow));
                        }
                        if(randomAndroidColor.equalsIgnoreCase("orange"))
                        {
                            options.icon(BitmapDescriptorFactory.fromResource(R.drawable.resoragne));
                        }
                        if(randomAndroidColor.equalsIgnoreCase("green"))
                        {
                            options.icon(BitmapDescriptorFactory.fromResource(R.drawable.resgreen));
                        }
                        options.title(locList.get(j).getOrderNo());
                       // options.icon(BitmapDescriptorFactory.fromResource(R.drawable.resmaker));
                    }

                    if(flag.equalsIgnoreCase("3"))
                    {
                        if(randomAndroidColor.equalsIgnoreCase("yellow"))
                        {
                            options.icon(BitmapDescriptorFactory.fromResource(R.drawable.cusyellow));
                        }
                        if(randomAndroidColor.equalsIgnoreCase("orange"))
                        {
                            options.icon(BitmapDescriptorFactory.fromResource(R.drawable.cusorange));
                        }
                        if(randomAndroidColor.equalsIgnoreCase("green"))
                        {
                            options.icon(BitmapDescriptorFactory.fromResource(R.drawable.cusgreen));
                        }
                        options.title(locList.get(j).getOrderNo());
                       // options.icon(BitmapDescriptorFactory.fromResource(R.drawable.customermaker));
                    }
                }
                // Add new marker to the Google Map Android API V2
                mMap.addMarker(options);
                if (markerPoints.size() == 2)
                //for(int i=0;i<)
                {
                    LatLng origin = markerPoints.get(0);
                    LatLng dest = markerPoints.get(1);
                    //LatLng ldest = markerPoints.get(2);
                    Log.e("first LatLng", origin + "");
                    Log.e("second LatLng", dest + "");
                    markerPoints.clear();
                    MapData mData = new MapData();
                    mData.setUrl( getDirectionsUrl(origin, dest));
                    mData.setLink_colour(randomAndroidColor);
                    //url[0] = getDirectionsUrl(origin, dest);
                   // url[1] = "orange";
                    Log.e("url=0",mData.getUrl());
                    Log.e("url=1",mData.getLink_colour());
                    DownloadTask downloadTask = new DownloadTask();
                    // Start downloading json data from Google Directions API
                    downloadTask.execute(mData);
                }
            }
        }
    }

    private String downloadUrl(String strUrl) throws IOException {
        String data = "";
        InputStream iStream = null;
        HttpURLConnection urlConnection = null;
        try{

            URL url = new URL(strUrl);
            // Creating an http connection to communicate with url
            urlConnection = (HttpURLConnection) url.openConnection();
            // Connecting to url
            urlConnection.connect();
            // Reading data from url
            iStream = urlConnection.getInputStream();
            BufferedReader br = new BufferedReader(new InputStreamReader(iStream));
            StringBuffer sb = new StringBuffer();
            String line = "";

            while(( line = br.readLine()) != null)
            {
                sb.append(line);
            }

            data = sb.toString();
            br.close();

        }
        catch(Exception e)
        {
            //Log.d("Exception while downloading url", e.toString());
        }

        finally
        {
            iStream.close();
            urlConnection.disconnect();
        }
        Log.e("when data",data);
        return data;
    }

    private class DownloadTask extends AsyncTask<MapData, MapData,MapData >{
        // Downloading data in non-ui thread
        @Override
        protected MapData doInBackground(MapData... url)
        {
            MapData data = new MapData();
            Log.e("coming in DownloadTask","coming in DownloadTask");
            Log.e("Mdata of value ",url[0].getUrl().toString());
            try
            {
                data.setDirection_Data(downloadUrl(url[0].getUrl().toString()));
                data.setLink_colour(url[0].getLink_colour().toString());
                Log.e("value of data ",data.getDirection_Data());
            }
            catch(Exception e)
            {
                Log.d("Background Task",e.toString());
            }
            return data;
        }
        // Executes in UI thread, after the execution of
        // doInBackground()
        @Override
        protected void onPostExecute(MapData result)
        {
           super.onPostExecute(result);
            ParserTask parserTask = new ParserTask();
            parserTask.execute(result);
        }
       }

    /** A class to parse the Google Places in JSON format */
    private class ParserTask extends AsyncTask<MapData, Integer, List<List<HashMap<String,String>>> >{

        // Parsing the data in non-ui thread
        @Override
        protected List<List<HashMap<String, String>>> doInBackground(MapData... jsonData) {
            JSONObject jObject,jObject1;
            List<List<HashMap<String, String>>> routes = null;
            try{
                jObject = new JSONObject(jsonData[0].getDirection_Data().toString());
                Log.e("Json object",jObject+"");
                Log.e("Color",jsonData[0].getLink_colour().toString());
                //jObject1 = new JSONObject(jsonData[1].toString());
                DirectionsJSONParser parser = new DirectionsJSONParser();
                // Starts parsing data
                routes = parser.parse(jObject,jsonData[0].getLink_colour().toString());
                //routes = parser.parse(jObject1);
            }
            catch(Exception e)
            {
                e.printStackTrace();
            }

            return routes;
        }
        // Executes in UI thread, after the parsing process
        @Override
        protected void onPostExecute(List<List<HashMap<String, String>>> result) {
            ArrayList<com.google.android.gms.maps.model.LatLng> points = null;
            PolylineOptions lineOptions = null;
            MarkerOptions markerOptions = new MarkerOptions();
            // Traversing through all the routes
            Log.e("length of result size",result.size()+"");
            for(int i=0;i<result.size();i++)
            {
                points = new ArrayList<com.google.android.gms.maps.model.LatLng>();
                lineOptions = new PolylineOptions();
                Log.e("length of route",i+"" + randomAndroidColor +"");
                // Fetching i-th route
                List<HashMap<String, String>> path = result.get(i);
                // Fetching all the points in i-th route
                for(int j=0;j<path.size();j++){
                    HashMap<String,String> point = path.get(j);
                    double lat = Double.parseDouble(point.get("lat"));
                    double lng = Double.parseDouble(point.get("lng"));
                    Log.e("Line Color",point.get("line"));
                    lineColor = point.get("line");
                    com.google.android.gms.maps.model.LatLng position = new com.google.android.gms.maps.model.LatLng(lat, lng);
                    points.add(position);
                }

                // Adding all the points in the route to LineOptions
                lineOptions.addAll(points);
                lineOptions.width(3);
                if(lineColor.equalsIgnoreCase("yellow"))
                {
                    lineOptions.color(Color.parseColor("#FFFF00"));
                }
                if(lineColor.equalsIgnoreCase("orange"))
                    lineOptions.color(Color.parseColor("#ff6700"));
            }

            //Color.parseColor("#ff6700")
            // Drawing polyline in the Google Map for the i-th route
           mMap.addPolyline(lineOptions);
        }
    }

    private String getDirectionsUrl(com.google.android.gms.maps.model.LatLng origin, com.google.android.gms.maps.model.LatLng dest){

        // Origin of route
        String str_origin = "origin="+origin.latitude+","+origin.longitude;
        // Destination of route
        String str_dest = "destination="+dest.latitude+","+dest.longitude;
        // Sensor enabled
        String sensor = "sensor=false";
        //String mode = "mode=Bicycling";
        String mode = "mode=Bicycling";
        String avoid = "highways";
        // Building the parameters to the web service
        String parameters = str_origin+"&"+str_dest+"&"+sensor+"&"+avoid+"&"+mode;
        // Output format
        String output = "json";
        // Building the url to the web service
        String url = "https://maps.googleapis.com/maps/api/directions/"+output+"?"+parameters;
        return url;
    }

    private void setUpMap() {
        Log.e("coming ","coming in ");
        mMap = mMapView.getMap();
        mMap.setMyLocationEnabled(true);
        mMap.getUiSettings().setCompassEnabled(true);
        mMap.getUiSettings().setZoomControlsEnabled(true);
        location = mMap.getMyLocation();
        locationManager = (LocationManager) getActivity().getSystemService(Context.LOCATION_SERVICE);
        location = locationManager.getLastKnownLocation(LocationManager.NETWORK_PROVIDER);
        Log.e("map","map");
        locList.clear();
        if (location != null)
        {
            // latitude and longitude
            LatLng lat = new LatLng(location.getLatitude(),location.getLongitude());
            latitude = location.getLatitude();
            longitude = location.getLongitude();
            Log.e("latitude",latitude+"");
            Log.e("logigute",longitude+"");

          /*  for(int i=0;i<MainActivity.LatLngList.size();i++)
            {
                LocObj obj = new LocObj();
                obj.setFirstPosition(MainActivity.LatLngList.get(i).getFirstposition());
                obj.setSecondPosition(MainActivity.LatLngList.get(i).getSecondposition());
                obj.setThirdPosition(MainActivity.LatLngList.get(i).getThirdposition());
                obj.setFourPosition(MainActivity.LatLngList.get(i).getFourposition());
                obj.setMesLatitude(MainActivity.LatLngList.get(i).getMeslatitude());
                obj.setMesLongitude(MainActivity.LatLngList.get(i).getMeslongitue());
                obj.setMesName(MainActivity.LatLngList.get(i).getMesname());
                obj.setOrderNo(MainActivity.LatLngList.get(i).getOrderno());
                double flat = Double.parseDouble(MainActivity.LatLngList.get(i).getMeslatitude());
                double flng = Double.parseDouble(MainActivity.LatLngList.get(i).getMeslongitue());
                double dis = GetDistance(latitude,longitude,flat,flng);
                Log.e("Distance for first ",dis+"");
                obj.setDistance(dis);
                locList.add(obj);

            }  */
            LocObj obj1 = new LocObj();
            obj1.setFirstPosition(firstPosition1);
            obj1.setSecondPosition(secondPosition1);
            obj1.setThirdPosition(thirdPosition1);
            obj1.setFourPosition(fourPosition1);
            obj1.setMesLatitude(fposLat);
            obj1.setMesLongitude(fposLng);
            obj1.setMesName("Messenger4");
            obj1.setOrderNo("ORDNO-04");
            double flat = Double.parseDouble(fposLat);
            double flng = Double.parseDouble(fposLng);
            double fdis = GetDistance(latitude,longitude,flat,flng);
            Log.e("Distance for second ",fdis+"");
            obj1.setDistance(fdis);
            locList.add(obj1);

          LocObj obj2 = new LocObj();
            obj2.setFirstPosition(firstPosition);
            obj2.setSecondPosition(secondPosition);
            obj2.setThirdPosition(thirdPosition);
            obj2.setFourPosition(fourPosition);
            obj2.setMesLatitude(sposLat);
            obj2.setMesLongitude(sposLng);
            obj2.setMesName("Messenger3");
            obj2.setOrderNo("ORDNO-03");
            double slat = Double.parseDouble(sposLat);
            double slng = Double.parseDouble(sposLng);
            double sdis = GetDistance(latitude,longitude,slat,slng);
            Log.e("Distance for second ",sdis+"");
            obj2.setDistance(sdis);
            locList.add(obj2);

            Collections.sort(locList, new CompDistance());
            for(int i=0;i<locList.size();i++)
            {
                Log.e("coming in loop","coming in loop"+i+"");
                if(i==0)
                {
                    locList.get(0).setFlagIcon("yellow");
                    Log.e("value of loop",locList.get(0).getFlagIcon());
                }
                if(i==1){ locList.get(1).setFlagIcon("orange");}
            }

            if(DataCarry.locflag.equalsIgnoreCase("true"))
            {
                Log.e("coming","coming in new order");
              LatLng current = new LatLng(latitude,longitude);
                LocObj obj3 = new LocObj();
                obj3.setFirstPosition(current);
                obj3.setSecondPosition(DataCarry.resLoc);
                obj3.setThirdPosition(DataCarry.resLoc);
                obj3.setFourPosition(DataCarry.cusLoc);
                obj3.setFlagIcon("green");
                locList.add(obj3);
                DataCarry.locflag="false";
            }

           // MarkerOptions marker = new MarkerOptions().position(
                 //   lat).title("D2D");
            // Changing marker icon
           // marker.icon(BitmapDescriptorFactory.fromResource(R.drawable.d2d));
            // adding marker
           // mMap.addMarker(marker);
            CameraPosition cameraPosition = new CameraPosition.Builder()
                    .target(lat).zoom(15).build();
            mMap.animateCamera(CameraUpdateFactory
                    .newCameraPosition(cameraPosition));
            //mMapView.animate();
        }
    }

    @Override
    public void onResume()
    {
        super.onResume();
        Log.e("coming ","coming in resume");

      //  ConnectServerGetAllLocation connAllLoc = new ConnectServerGetAllLocation(getActivity());
       // connAllLoc.execute();
        mMapView.onResume();
        locList.clear();
        setUpMap();
        setLatLngMap();
    }

    @Override
    public void onPause() {
        super.onPause();
        DownloadTask task = new DownloadTask();
        Log.e("check ",task.getStatus()+"");
        ParserTask pTask = new ParserTask();
        Log.e("check1",pTask.getStatus()+"");
        //  if(task != null && task.getStatus() == DownloadTask.Status.RUNNING)
        task.cancel(true);
        pTask.cancel(true);
        mMapView.onPause();
    }

    @Override
    public void onDestroy() {
        mMapView.onDestroy();
        super.onDestroy();
    }

    @Override
    public void onStop() {
        super.onStop();
        //check the state of the task
        Log.e("coming on stop","coming on stop");
        DownloadTask task = new DownloadTask();
        Log.e("check ",task.getStatus()+"");
        ParserTask pTask = new ParserTask();
        Log.e("check1",pTask.getStatus()+"");
            task.cancel(true);
            pTask.cancel(true);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState)
    {
        String languageToLoad = "my_";
        Locale locale = new Locale(languageToLoad);
        Locale.setDefault(locale);
        Configuration config = new Configuration();
        config.locale = locale;
        getActivity().getBaseContext().getResources().updateConfiguration(config,
                getActivity().getBaseContext().getResources().getDisplayMetrics());
        inflatedView = inflater.inflate(R.layout.homefragment, container, false);
        MapsInitializer.initialize(getActivity());
        mMapView = (MapView) inflatedView.findViewById(R.id.map);
        btnNoti = (TextView)inflatedView.findViewById(R.id.btnnoti);
        mMapView.onCreate(mBundle);
        setUpMapIfNeeded(inflatedView);
        return inflatedView;
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        if(SharePref.getInstance(getActivity()).getGCM_REGID().equalsIgnoreCase("NoRegister"))
        {
            Integer resultCode = GooglePlayServicesUtil.isGooglePlayServicesAvailable(getActivity());
            if (resultCode == ConnectionResult.SUCCESS) {
                GCMClientManager pushClientManager = new GCMClientManager(getActivity(), PROJECT_NUMBER);
                pushClientManager.registerIfNeeded(new GCMClientManager.RegistrationCompletedHandler() {
                    @Override
                    public void onSuccess(String registrationId, boolean isNewRegistration) {

                        Log.e("Registration id", registrationId);
                        if(!(registrationId.equalsIgnoreCase("")))
                        {
                            SharePref.getInstance(getActivity()).saveGCM_REGID(registrationId);
                           // ConnectServerSendRegID connRegID = new ConnectServerSendRegID (getActivity());
                           // connRegID.execute();
                        }
                        //send this registrationId to your server
                    }
                    @Override
                    public void onFailure(String ex) {
                        super.onFailure(ex);
                    }
                });

            } else {
                Dialog dialog = GooglePlayServicesUtil.getErrorDialog(resultCode,getActivity(), 0);
                if (dialog != null) {
                    //This dialog will help the user update to the latest GooglePlayServices
                    dialog.show();
                }
            }
        }

        else
        {
            Log.e("NoSend","NoSend");
            if(SharePref.getInstance(getActivity()).getGCMFlag().equalsIgnoreCase("NoSend"))
            {
                Log.e("Finish","Finish");
              //  ConnectServerSendRegID connRegID = new ConnectServerSendRegID (getActivity());
               // connRegID.execute();
            }
        }
        if(DataCarry.flagNoti==1)
        {
            btnNoti.setVisibility(View.VISIBLE);
            Log.e("length of list",DataCarry.orderList.size()+"");
            btnNoti.setText(DataCarry.orderList.size()+"");
        }
        else
        {
            btnNoti.setVisibility(View.INVISIBLE);
        }


        btnNoti.setOnClickListener( new View.OnClickListener() {
            @Override
            public void onClick(View view)
            {
               /* Fragment mFragment = new NewOrderListFragment().newInstance(7);
                FragmentManager fragmentManager = getFragmentManager();
                fragmentManager.beginTransaction()
                        .replace(R.id.container, mFragment)
                        .addToBackStack(null)
                        .commit(); */
            }
        });

        new Thread(new Runnable() {
            @Override
            public void run() {
                // TODO Auto-generated method stub
                while (true)
                {
                    try {
                        Thread.sleep(30000);
                        mHandler.post(new Runnable() {
                            @Override
                            public void run()
                            {
                                // TODO Auto-generated method stub
                                Log.e("Hello","Hello EveryBody");
                                ConnectServerSendLocation connlocation = new ConnectServerSendLocation(getActivity());
                                connlocation.execute();
                            }
                        });
                    } catch (Exception e)
                    {
                        // TODO: handle exception
                    }
                }
            }
        }).start();
    }


    /*public void onLocationChanged(Location location) {
        Log.i("called", "onLocationChanged");
        //when the location changes, update the map by zooming to the location
        CameraUpdate center = CameraUpdateFactory.newLatLng(new LatLng(location.getLatitude(),location.getLongitude()));
        mMap.moveCamera(center);
        CameraUpdate zoom=CameraUpdateFactory.zoomTo(15);
        mMap.animateCamera(zoom);

    }   */

    public class ConnectServerSendLocation extends AsyncTask
    {
        Context c;
        public ConnectServerSendLocation(Context applicationContext)
        {
            this.c=applicationContext;
        }

        @Override
        protected Object doInBackground(Object... arg0) {
            String userdata = "";
            HttpParams httpParameters = new BasicHttpParams();
            HttpConnectionParams.setConnectionTimeout(httpParameters, 0);
            HttpConnectionParams.setSoTimeout(httpParameters, 0);
            HttpClient client = new DefaultHttpClient(httpParameters);
            HttpPost request = new HttpPost(ConfigLink.sendCurrentlatlongURL);
            try
            {
                JSONObject jsonobj = new JSONObject();
                jsonobj.put("token", SharePref.getInstance(getActivity()).getUserSession());
                jsonobj.put("latitude",latitude);
                jsonobj.put("longitude",longitude);
                StringEntity se = new StringEntity(jsonobj.toString());
                Log.e("Post disptcher Data",jsonobj.toString());
                se.setContentType("application/json;charset=UTF-8");
                se.setContentEncoding(new BasicHeader(HTTP.CONTENT_TYPE,"application/json;charset=UTF-8"));
                request.setEntity(se);
                HttpResponse httpresponse = client.execute(request);
                userdata = EntityUtils.toString(httpresponse.getEntity());
            }
            catch (Exception e)
            {
                e.printStackTrace();
            }
            return userdata;
        }
        protected void onPostExecute(Object result)
        {
            super.onPostExecute(result);
            String userdata = (String) result;
            Log.e("user data",userdata);
            // pd.dismiss();
            Log.e("lat and long", userdata + "aaa");
            if(userdata == null ||  userdata.equalsIgnoreCase(""))
            {
                // String msg = getResources().getString(R.string.msgTitle);
                // String msg_word = getResources().getString(R.string.noInternet);
                // AlertDialogGenerator.showSimpleInfoDialog1(MainActivity.this, msg, msg_word);
            }
            else
            {
                JSONObject json;
                Log.e("data", userdata + "");
               // btnEdit.setText("Edit");
                try
                {
                    json = new JSONObject(userdata);
                    String Status = json.getString("status");
                    String Message = json.getString("message");
                    //String msg = getResources().getString(R.string.msgTitle);
                /*    AlertDialog myQuittingDialogBox = new AlertDialog.Builder(getActivity())
                            .setTitle("Alert Message!")
                            .setMessage(Message)
                            .setPositiveButton("OK", new DialogInterface.OnClickListener()
                            {	public void onClick(DialogInterface dialog, int whichButton)
                                {

                                }
                            })
                            .create();
                    myQuittingDialogBox.show(); */
                }
                catch (JSONException e)
                {
                    e.printStackTrace();
                }
            }
        }
    }

    public double GetDistance(double latA,double lngA,double latB,double lngB)
    {
        Location locationA = new Location("point A");
        locationA.setLatitude(latA);
        locationA.setLongitude(lngA);
        Location locationB = new Location("point B");
        locationB.setLatitude(latB);
        locationB.setLongitude(lngB);
        double distance = locationA.distanceTo(locationB) ;
        return  distance;
    }


  public class ConnectServerGetAllLocation extends AsyncTask
    {
        Context c;
        public ConnectServerGetAllLocation(Context applicationContext)
        {
            this.c=applicationContext;
        }

        @Override
        protected Object doInBackground(Object... arg0) {
            String userdata = "";
            HttpParams httpParameters = new BasicHttpParams();
            HttpConnectionParams.setConnectionTimeout(httpParameters, 0);
            HttpConnectionParams.setSoTimeout(httpParameters, 0);
            HttpClient client = new DefaultHttpClient(httpParameters);
            HttpPost request = new HttpPost(ConfigLink.getAllMessengerLoc);
            try
            {

                JSONObject jsonobj = new JSONObject();
                jsonobj.put("token", SharePref.getInstance(getActivity()).getUserSession());
                StringEntity se = new StringEntity(jsonobj.toString());
                Log.e("Post for alldisptchData",jsonobj.toString());
                se.setContentType("application/json;charset=UTF-8");
                se.setContentEncoding(new BasicHeader(HTTP.CONTENT_TYPE,"application/json;charset=UTF-8"));
                request.setEntity(se);
                HttpResponse httpresponse = client.execute(request);
                userdata = EntityUtils.toString(httpresponse.getEntity());
            }
            catch (Exception e)
            {
                e.printStackTrace();
            }
            return userdata;
        }
        protected void onPostExecute(Object result)
        {
            super.onPostExecute(result);
            pd.dismiss();
           // String userdata = (String) result;
            String   userdata = "{'current_task': [  {'msgLat': '16.831596600','msgLong': '96.121543400','resLat':'16.770745155','resLong':'96.186394212','cusLat': '16.830837100','cusLong': '16.830837100','mesName': 'Messenger1','orderNo':'ORDR-7052DNU7' }  ] }";
            Log.e("user data",userdata);
            Log.e("get all lat and long", userdata + "aaa");
            if(userdata == null ||  userdata.equalsIgnoreCase(""))
            {

            }
            else
            {
                JSONObject json;
                JSONArray jsonLocation;
                Log.e(" all location data", userdata + "");
                try
                {
                    json = new JSONObject(userdata);
                    String location = json.getString("current_task");
                    jsonLocation = new JSONArray(location);
                    Log.e(" length",jsonLocation.length()+"");
                    LatLngList.clear();
                    for(int j=0;j<jsonLocation.length();j++)
                    {
                        JSONObject jsonObj = new JSONObject();
                        jsonObj = jsonLocation.getJSONObject(j);
                        LatLngObj loc = new LatLngObj();
                        LatLng locfirstobj = new LatLng(Double.parseDouble(jsonObj.getString("msgLat")),Double.parseDouble(jsonObj.getString("msgLong")));
                        LatLng locsecondobj = new LatLng(Double.parseDouble(jsonObj.getString("resLat")),Double.parseDouble(jsonObj.getString("resLong")));
                        LatLng locthirdobj = new LatLng(Double.parseDouble(jsonObj.getString("resLat")),Double.parseDouble(jsonObj.getString("resLong")));
                        LatLng locfourobj = new LatLng(Double.parseDouble(jsonObj.getString("cusLat")),Double.parseDouble(jsonObj.getString("cusLong")));
                        String ordno = jsonObj.getString("orderNo");
                        String mesname = jsonObj.getString("mesName");
                        String meslat = jsonObj.getString("msgLat");
                        String meslng = jsonObj.getString("msgLong");
                        loc.setFirstposition(locfirstobj);
                        loc.setSecondposition(locsecondobj);
                        loc.setThirdposition(locthirdobj);
                        loc.setFourposition(locfourobj);
                        loc.setMeslatitude(meslat);
                        loc.setMeslongitue(meslng);
                        loc.setOrderno(ordno);
                        loc.setMesname(mesname);
                        LatLngList.add(loc);
                    }
                }
                catch (JSONException e)
                {
                    e.printStackTrace();
                }
            }
        }}

    private void makeProgressDiag() // TTA
    {
        // TODO Auto-generated method stub
        pd = ProgressDialog.show(getActivity(), null, "Downloading Data ...");
    }


}
