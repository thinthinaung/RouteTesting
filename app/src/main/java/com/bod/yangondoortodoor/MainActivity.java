package com.bod.yangondoortodoor;

import android.content.Context;
import android.os.Bundle;
import android.support.design.widget.NavigationView;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.Toast;

import com.bod.yangondoortodoor.fragment.HomeFragment;
import com.bod.yangondoortodoor.fragment.LogOutFragment;
import com.bod.yangondoortodoor.fragment.NewOrderListFragment;
import com.bod.yangondoortodoor.fragment.PastTaskFragment;
import com.bod.yangondoortodoor.fragment.PresentTaskFragment;
import com.bod.yangondoortodoor.fragment.QueueTaskFragment;
import com.bod.yangondoortodoor.fragment.TabFragment;
import com.bod.yangondoortodoor.model.DataCarry;
import com.bod.yangondoortodoor.model.LatLngObj;
import com.bod.yangondoortodoor.util.SharePref;
import com.pusher.client.Pusher;
import com.pusher.client.channel.Channel;
import com.pusher.client.channel.SubscriptionEventListener;

import java.util.ArrayList;

public class MainActivity extends AppCompatActivity{
    DrawerLayout mDrawerLayout;
    public static NavigationView mNavigationView;
    FragmentManager mFragmentManager;
    FragmentTransaction mFragmentTransaction;
    public static ArrayList<LatLngObj> LatLngList = new ArrayList<LatLngObj>();
    Context context;
    Menu menu;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        context= this;
        /**
         *Setup the DrawerLayout and NavigationView
         */
            mPusher();
            mDrawerLayout = (DrawerLayout) findViewById(R.id.drawerLayout);
            mNavigationView = (NavigationView) findViewById(R.id.shitstuff) ;

        /**
         *
         * Lets inflate the very first fragment
         * Here , we are inflating the TabFragment as the first Fragment
         */
             mFragmentManager = getSupportFragmentManager();
             mFragmentTransaction = mFragmentManager.beginTransaction();
             mFragmentTransaction.replace(R.id.containerView,new HomeFragment()).commit();
        /**
         * Setup click events on the Navigation View Items.
         */
      mNavigationView.setNavigationItemSelectedListener(new NavigationView.OnNavigationItemSelectedListener() {
             @Override
             public boolean onNavigationItemSelected(MenuItem menuItem) {

                mDrawerLayout.closeDrawers();

                if (menuItem.getItemId() == R.id.nav_item_home) {


                    FragmentTransaction xfragmentTransaction = mFragmentManager.beginTransaction();
                    xfragmentTransaction.replace(R.id.containerView,new HomeFragment()).commit();
                 }
                 if (menuItem.getItemId() == R.id.nav_item_editprofile) {
                     FragmentTransaction efragmentTransaction = mFragmentManager.beginTransaction();
                     efragmentTransaction.replace(R.id.containerView,new TabFragment()).commit();
                 }
                 if (menuItem.getItemId() == R.id.nav_item_currentorders) {
                     FragmentTransaction cfragmentTransaction = mFragmentManager.beginTransaction();
                     cfragmentTransaction.replace(R.id.containerView,new PresentTaskFragment()).commit();
                 }
                 if (menuItem.getItemId() == R.id.nav_item_pendingorder) {
                     FragmentTransaction pfragmentTransaction = mFragmentManager.beginTransaction();
                     pfragmentTransaction.replace(R.id.containerView,new QueueTaskFragment()).commit();
                 }
                 if (menuItem.getItemId() == R.id.nav_item_order_history) {
                     FragmentTransaction hfragmentTransaction = mFragmentManager.beginTransaction();
                     hfragmentTransaction.replace(R.id.containerView,new PastTaskFragment()).commit();
                 }
                 if (menuItem.getItemId() == R.id.nav_item_neworders) {
                     FragmentTransaction hfragmentTransaction = mFragmentManager.beginTransaction();
                     hfragmentTransaction.replace(R.id.containerView,new NewOrderListFragment()).commit();
                 }
                 if (menuItem.getItemId() == R.id.nav_item_logout) {
                     FragmentTransaction lfragmentTransaction = mFragmentManager.beginTransaction();
                     lfragmentTransaction.replace(R.id.containerView,new LogOutFragment()).commit();
                 }
                 return false;
             }
        });

        /**
         * Setup Drawer Toggle of the Toolbar
         */
                android.support.v7.widget.Toolbar toolbar = (android.support.v7.widget.Toolbar) findViewById(R.id.toolbar);
                ActionBarDrawerToggle mDrawerToggle = new ActionBarDrawerToggle(this,mDrawerLayout, toolbar,R.string.app_name,
                R.string.app_name);
                mDrawerLayout.setDrawerListener(mDrawerToggle);
                mDrawerToggle.syncState();
    }

    @Override
    public void onBackPressed()
    {
        super.onBackPressed();
        Log.e("Coming in back","coming in back");
        FragmentTransaction xfragmentTransaction = mFragmentManager.beginTransaction();
        xfragmentTransaction.replace(R.id.containerView,new HomeFragment()).commit();

    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        super.onCreateOptionsMenu(menu);
        this.menu = menu;
        MenuItem mm =menu.findItem(R.id.nav_item_newflag);
        mm.setTitle(DataCarry.count_order+"");
        return true;
    }

    public void mPusher() {
        Pusher pusher = new Pusher("50a0fa81d0deb1ee2601");
        Channel channel = pusher.subscribe("test_channel");
        channel.bind("my_event", new SubscriptionEventListener() {
            @Override
            public void onEvent(String channelName, String eventName, final String data) {
                System.out.println(data);
                Log.e("pusher data", data);
                DataCarry.count_order = 1;
                Toast.makeText(context, data.toString(), Toast.LENGTH_SHORT).show();
            }
        });

        pusher.connect();
        String ID = SharePref.getInstance(context).getUSERID();
        Log.e("userID", ID);
        Pusher pusher1 = new Pusher("50a0fa81d0deb1ee2601");
        Channel channel1 = pusher1.subscribe(ID);
        channel1.bind("my_event", new SubscriptionEventListener() {
            @Override
            public void onEvent(String channelName, String eventName, final String data) {
                System.out.println(data);
                Log.e("pusher data", data);
                Toast.makeText(context, data.toString(), Toast.LENGTH_SHORT).show();
            }
        });
        pusher1.connect();

    }

   /* public class ConnectServerGetAllLocation extends AsyncTask
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
                jsonobj.put("token", SharePref.getInstance(context).getUserSession());
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
        }}  */

}