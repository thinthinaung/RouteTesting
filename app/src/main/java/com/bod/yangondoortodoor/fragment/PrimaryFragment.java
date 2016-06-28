package com.bod.yangondoortodoor.fragment;

import android.content.Context;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import com.bod.yangondoortodoor.R;
import com.bod.yangondoortodoor.config.ConfigLink;
import com.bod.yangondoortodoor.model.DataCarry;
import com.bod.yangondoortodoor.util.SharePref;
import com.pusher.client.Pusher;
import com.pusher.client.channel.Channel;
import com.pusher.client.channel.SubscriptionEventListener;

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
import org.json.JSONException;
import org.json.JSONObject;


/**
 * Created by Ratan on 7/29/2015.
 */
public class PrimaryFragment extends Fragment {
    TextView txtFirstName,txtLastName,txtEmail,txtPhone,txtAddress,txtTownship,txtUserName;

    Thread readthread;
    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View inflaterview = inflater.inflate(R.layout.fragment_one, container, false);
        txtFirstName = (TextView)inflaterview.findViewById(R.id.txtFirstName);
        txtLastName = (TextView)inflaterview.findViewById(R.id.txtLastName);
        txtEmail = (TextView)inflaterview.findViewById(R.id.txtEmail);
        txtPhone = (TextView) inflaterview.findViewById(R.id.txtPhone);
        txtAddress = (TextView)inflaterview.findViewById(R.id.txtAddress);
        txtTownship = (TextView)inflaterview.findViewById(R.id.txtTownship);
        txtUserName = (TextView)inflaterview.findViewById(R.id.txtUserName);
        Log.e(" View one "," View one Create View");
        return inflaterview;
        //return inflater.inflate(R.layout.fragment_one,null);
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        setHasOptionsMenu(true);
        Log.e("coming....","coming....primary fragment");
        ConnectServerProfile connProfile = new ConnectServerProfile(getActivity().getApplicationContext());
        connProfile.execute();



            AsyncTask.execute(new Runnable() {
            @Override
            public void run()
            {
                Pusher pusher = new Pusher("50a0fa81d0deb1ee2601");
                Channel channel = pusher.subscribe("test_channel");
                channel.bind("my_event", new SubscriptionEventListener() {
                    @Override
                    public void onEvent(String channelName, String eventName, final String data) {
                        System.out.println(data);
                        Log.e("pusher data", data);

                        getActivity().runOnUiThread(new Runnable() {
                            public void run() {
                                DataCarry.count_order= 1;
                                Toast.makeText(getActivity(), " You have  New Orders ", Toast.LENGTH_SHORT).show();

                            }
                        });
                    }
                });

                pusher.connect();
                String ID = SharePref.getInstance(getActivity()).getUSERID();
                Log.e("userID",ID);
                Pusher pusher1 = new Pusher("50a0fa81d0deb1ee2601");
                Channel channel1 = pusher1.subscribe(ID);
                channel1.bind("my_event", new SubscriptionEventListener() {
                    @Override
                    public void onEvent(String channelName, String eventName, final String data) {
                        System.out.println(data);
                        Log.e("pusher data",data);
                        getActivity().runOnUiThread(new Runnable() {
                            public void run() {
                                getActivity().invalidateOptionsMenu();
                                Toast.makeText(getActivity(), "You have  New Orders", Toast.LENGTH_SHORT).show();
                            }
                        });
                    }
                });
                pusher1.connect();
            }
        });
    }

    public class ConnectServerProfile extends AsyncTask
    {
        Context c;
        public ConnectServerProfile(Context applicationContext)
        {
            this.c=applicationContext;
        }

        @Override
        protected Object doInBackground(Object... arg0) {
            String userdata = "";
            Log.e("coming in do in","coming in do in");
            HttpParams httpParameters = new BasicHttpParams();
            HttpConnectionParams.setConnectionTimeout(httpParameters, 0);
            HttpConnectionParams.setSoTimeout(httpParameters, 0);
            HttpClient client = new DefaultHttpClient(httpParameters);
            HttpPost request = new HttpPost(ConfigLink.getProfileURL);
            try
            {
                JSONObject jsonobj = new JSONObject();
                jsonobj.put("session_token", SharePref.getInstance(getActivity()).getUserSession());
                StringEntity se = new StringEntity(jsonobj.toString());
                Log.e("Post Data?????????????", jsonobj.toString());
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
            Log.e("user data in one frag", userdata);
            // pd.dismiss();
            Log.e("Login", userdata + "aaa");
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
                //btnEdit.setText("Edit");
                try
                {
                    json = new JSONObject(userdata);
                    String Status = json.getString("status");
                    if(Status.equalsIgnoreCase("1"))
                    {
                        String username = json.getString("username");
                        String email = json.getString("email");
                        String phoneno = json.getString("phone");
                        String firstname = json.getString("first_name");
                        String lastname = json.getString("last_name");
                        String ward = json.getString("ward");
                        String city = json.getString("city");
                        String street = json.getString("street");
                        String numbers = json.getString("number");
                        String country = json.getString("country");
                        txtEmail.setText(email);
                        txtFirstName.setText(firstname);
                        txtLastName.setText(lastname);
                        txtPhone.setText(phoneno);
                        txtUserName.setText(username);
                        txtAddress.setText(numbers+","+ward+","+street+","+city+","+country );
                    }
                    //String msg = getResources().getString(R.string.msgTitle);
                }
                catch (JSONException e)
                {
                    e.printStackTrace();
                }
            }
        }
    }

}