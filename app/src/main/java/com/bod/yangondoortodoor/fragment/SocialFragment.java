package com.bod.yangondoortodoor.fragment;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;

import com.bod.yangondoortodoor.R;
import com.bod.yangondoortodoor.config.ConfigLink;
import com.bod.yangondoortodoor.util.SharePref;

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
public class SocialFragment extends Fragment {

    EditText edtFirstName,edtLastName,edtUserName,edtPhone,edtAddress,edtTownship;
    Button btnSave,btnCancel;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        //return inflater.inflate(R.layout.fragment_two,null);
        View inflaterview = inflater.inflate(R.layout.fragment_two, container, false);
        edtFirstName = (EditText)inflaterview.findViewById(R.id.edtFirstName);
        edtLastName = (EditText) inflaterview.findViewById(R.id.edtLastName);
        edtUserName = (EditText)inflaterview.findViewById(R.id.edtEmail);
        edtPhone = (EditText)inflaterview.findViewById(R.id.edtPhone);
        edtAddress = (EditText)inflaterview.findViewById(R.id.edtAddress);
        btnSave = (Button)inflaterview.findViewById(R.id.btnSave);
        btnCancel = (Button)inflaterview.findViewById(R.id.btnCancel);
        return inflaterview;
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        Log.e("coming....","coming....primary fragment");
        ConnectServerProfile connProfile = new ConnectServerProfile(getActivity().getApplicationContext());
        connProfile.execute();
        btnSave.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                ConnectServerEditProfile connEditProfile = new ConnectServerEditProfile(getActivity());
                connEditProfile.execute();
            }
        });
        btnCancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view)
            {

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
                Log.e("Post Data", jsonobj.toString());
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
            Log.e("user data", userdata);
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
                        edtUserName.setText(username);
                        edtPhone.setText(phoneno);
                        edtFirstName.setText(firstname);
                        edtLastName.setText(lastname);
                        edtAddress.setText(numbers+","+street+","+ward+","+city);

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

    public class ConnectServerEditProfile extends AsyncTask
    {
        Context c;

        public ConnectServerEditProfile(Context applicationContext)
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
            HttpPost request = new HttpPost(ConfigLink.editProfileURL);
            try
            {
                JSONObject jsonobj = new JSONObject();
                jsonobj.put("session_token", SharePref.getInstance(getActivity()).getUserSession());
                jsonobj.put("username",edtUserName.getText().toString());
                jsonobj.put("phoneno",edtPhone.getText().toString());
                jsonobj.put("address",edtAddress.getText().toString());
                jsonobj.put("first_name",edtFirstName.getText().toString());
                jsonobj.put("last_name",edtLastName.getText().toString());
                StringEntity se = new StringEntity(jsonobj.toString());
                Log.e("Post Data", jsonobj.toString());
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
            Log.e("Login", userdata + "aaa");
            if(userdata == null || userdata.equalsIgnoreCase(""))
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
                    AlertDialog myQuittingDialogBox = new AlertDialog.Builder(getActivity())
                            .setTitle("Message!")
                            .setMessage(Message)
                            .setPositiveButton("OK", new DialogInterface.OnClickListener()
                            {	public void onClick(DialogInterface dialog, int whichButton)
                                {
                                    ConnectServerProfile connProfile = new ConnectServerProfile(getActivity().getApplicationContext());
                                    connProfile.execute();
                                }
                            })
                            .create();
                    myQuittingDialogBox.show();
                }
                catch (JSONException e)
                {
                    e.printStackTrace();
                }
            }
        }
    }

}
