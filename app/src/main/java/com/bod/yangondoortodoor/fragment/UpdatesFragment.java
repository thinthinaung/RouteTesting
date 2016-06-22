package com.bod.yangondoortodoor.fragment;

import android.content.Context;
import android.content.Intent;
import android.net.Uri;
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
public class UpdatesFragment extends Fragment {

    Button btnforgot;
    EditText edtOldPass,edtNewPass,edtreNewPass;
    Button btnSave,btnCancel;
    View inflaterView;
    @Nullable
    @Override

    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        inflaterView = inflater.inflate(R.layout.fragment_three,null);
        btnforgot = (Button) inflaterView.findViewById(R.id.btnForgotPassword);
        edtOldPass = (EditText) inflaterView.findViewById(R.id.edtOldPassword);
        edtNewPass = (EditText) inflaterView.findViewById(R.id.edtNewPassword);
        edtreNewPass = (EditText)inflaterView.findViewById(R.id.edtReNewPassword);
        btnSave = (Button)inflaterView.findViewById(R.id.btnChange);
        btnCancel = (Button)inflaterView.findViewById(R.id.btnCancel);
        return inflaterView;
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);

        btnSave.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                ConnectServerChangePassword connChangePass = new ConnectServerChangePassword(getActivity());
                connChangePass.execute();
            }
        });

        btnCancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

            }
        });

        btnforgot.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                  Log.e("coming","coming");
                Thread thread = new Thread(new Runnable()
                {
                    @Override
                    public void run()
                    {
                        try
                        {
                            startActivity(new Intent(Intent.ACTION_VIEW, Uri.parse(ConfigLink.urlLink)));
                        }
                        catch (Exception e)
                        {
                            e.printStackTrace();
                        }
                    }
                });
                thread.start();
            }
        });
    }

    public class ConnectServerChangePassword extends AsyncTask
    {
        Context c;
        public ConnectServerChangePassword(Context applicationContext)
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
            HttpPost request = new HttpPost(ConfigLink.changpassUrl);
            try
            {
                JSONObject jsonobj = new JSONObject();
                jsonobj.put("token", SharePref.getInstance(getActivity()).getUserSession());
                jsonobj.put("old_password",edtOldPass.getText().toString());
                jsonobj.put("new_password",edtNewPass.getText().toString());
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

        protected void onPostExecute(Object result) {
            super.onPostExecute(result);
            String userdata = (String) result;
            Log.e("user data", userdata);
            Log.e("change password", userdata + "aaa");
            if (userdata == null || userdata.equalsIgnoreCase(""))
            {

            }
            else {
                JSONObject json;
                Log.e("data", userdata + "");
                try {
                      json = new JSONObject(userdata);

                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }
        }
    }
}
