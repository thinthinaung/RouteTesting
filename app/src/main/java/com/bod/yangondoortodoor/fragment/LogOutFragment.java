package com.bod.yangondoortodoor.fragment;


import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import com.bod.yangondoortodoor.R;
import com.bod.yangondoortodoor.config.ConfigLink;
import com.bod.yangondoortodoor.ui.Splash;
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


public class LogOutFragment extends Fragment {

    Button btnLogOut;
       @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState)
    {
        // Inflate the layout for this fragment
        View inflaterView =inflater.inflate(R.layout.logout, container, false);
         btnLogOut = (Button)inflaterView.findViewById(R.id.btnLogOut);
        return inflaterView;
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        btnLogOut.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                ConnectServerLogout connLogOut = new ConnectServerLogout(getActivity());
                connLogOut.execute();
               // FragmentTransaction lfragmentTransaction =getFragmentManager().beginTransaction();
               // lfragmentTransaction.replace(R.id.containerView,new DetailTaskFragment()).commit();
            }
        });
    }

    public class ConnectServerLogout extends AsyncTask
    {
        Context c;
        public ConnectServerLogout(Context applicationContext)
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
            HttpPost request = new HttpPost(ConfigLink.logoutURL);
            try{
                JSONObject jsonobj = new JSONObject();
                jsonobj.put("session_token", SharePref.getInstance(getActivity()).getUserSession());
                StringEntity se = new StringEntity(jsonobj.toString());
                Log.e("Post logout Data", jsonobj.toString());
                se.setContentType("application/json;charset=UTF-8");
                se.setContentEncoding(new BasicHeader(HTTP.CONTENT_TYPE,"application/json;charset=UTF-8"));
                request.setEntity(se);
                HttpResponse httpresponse = client.execute(request);
                userdata = EntityUtils.toString(httpresponse.getEntity());

            }catch (Exception e)
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
            Log.e("Logout", userdata + "aaa");
            if(userdata == null ||  userdata.equalsIgnoreCase(""))
            {
                // String msg = getResources().getString(R.string.msgTitle);
                // String msg_word = getResources().getString(R.string.noInternet);
                // AlertDialogGenerator.showSimpleInfoDialog1(MainActivity.this, msg, msg_word);
            }
            else
            {
                JSONObject json;
                Log.e("logout data", userdata + "");
                try
                {
                    json = new JSONObject(userdata);
                    String Status = json.getString("status");
                    String Message = json.getString("message");
                    //String msg = getResources().getString(R.string.msgTitle);
                    AlertDialog myQuittingDialogBox = new AlertDialog.Builder(getActivity())
                            .setTitle("Alert Message!")
                            .setMessage(Message)
                            .setPositiveButton("OK", new DialogInterface.OnClickListener()
                            {	public void onClick(DialogInterface dialog, int whichButton)
                                {
                                    SharePref.getInstance(getActivity()).saveUserSession("NoSession");
                                    Intent intent = new Intent(getActivity(),Splash.class);
                                    startActivity(intent);
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
