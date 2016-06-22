package com.bod.yangondoortodoor.ui;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.Window;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.bod.yangondoortodoor.MainActivity;
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

import java.util.Timer;
import java.util.TimerTask;

/**
 * Created by Thin Thin Aung on 11/3/2015.
 */
public class Splash extends Activity
{
    Context context;
    EditText edtUserName;
    EditText edtPassword;
    LinearLayout layBox;
    ImageView imgLogo;
    Button btnLogin;
    TextView txtForgotPassword;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        this.requestWindowFeature(Window.FEATURE_NO_TITLE);
        setContentView(R.layout.splash);
        getRegisterID();
        context = this;

        //dotProgressBar.setAnimationTime(time);
        if(SharePref.getInstance(context).isFirstTime())
        {
            SharePref.getInstance(context).saveGCM_REGID("NoRegister");
            SharePref.getInstance(context).saveUserSession("NoSession");
            SharePref.getInstance(context).saveGCMFlag("NoSend");
            SharePref.getInstance(context).saveUSERID("NoUser");
            SharePref.getInstance(context).noLongerFirstTime();
        }
        Log.e("User Session", SharePref.getInstance(context).getUserSession()+"");
        if(SharePref.getInstance(context).getUserSession().equalsIgnoreCase("NoSession"))
        {
             layBox.setVisibility(View.VISIBLE);
             imgLogo.setVisibility(View.GONE);
        }
        else
        {
            imgLogo.setVisibility(View.VISIBLE);
            layBox.setVisibility(View.GONE);
            Timer timer = new Timer();
            timer.schedule(new TimerTask()
            {
                public void run()
                {
                   // findViewById(R.id.loadingPanel).setVisibility(View.GONE);
                    Intent intent = new Intent(Splash.this,MainActivity.class);
                    startActivity(intent);
                }
            }, 3000);
        }
    }

    public void Login(View v)
    {
        Log.e("coming in Login","Login");
        ConnectServerUserLogin connlogin = new ConnectServerUserLogin(context);
        connlogin.execute();
      // Intent intent = new Intent(Splash.this,MainActivity.class);
      // startActivity(intent);
    }

    public void getRegisterID()
    {
        edtUserName = (EditText)findViewById(R.id.edtUserName);
        edtPassword = (EditText)findViewById(R.id.edtPassword);
        layBox = (LinearLayout)findViewById(R.id.lyBox);
        btnLogin = (Button)findViewById(R.id.btnLogin);
       // txtForgotPassword = (TextView)findViewById(R.id.txtForgotPassword);
        imgLogo = (ImageView)findViewById(R.id.imgLogo);
    }

    public class ConnectServerUserLogin extends AsyncTask
    {
        Context c;
        public ConnectServerUserLogin(Context applicationContext)
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
            HttpPost request = new HttpPost(ConfigLink.loginURL);

            try{
                JSONObject jsonobj = new JSONObject();
                jsonobj.put("email",edtUserName.getText().toString());
                jsonobj.put("password",edtPassword.getText().toString());
                StringEntity se = new StringEntity(jsonobj.toString());
                Log.e("Post Data",jsonobj.toString());
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
            Log.e("user login data",userdata);
            //pd.dismiss();
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
                try
                {
                    json = new JSONObject(userdata);
                    String Status = json.getString("status");
                    if(Status.equalsIgnoreCase("1"))
                    {
                        String Message = json.getString("message");
                        String session_token = json.getString("token");
                        String user_id = json.getString("id");
                        //String msg = getResources().getString(R.string.msgTitle);
                        SharePref.getInstance(context).saveUserSession(session_token);
                        SharePref.getInstance(context).saveUSERID(user_id);
                        AlertDialog myQuittingDialogBox = new AlertDialog.Builder(Splash.this)
                                .setTitle("Message!")
                                .setMessage(Message)
                                .setPositiveButton("OK", new DialogInterface.OnClickListener()
                                {	public void onClick(DialogInterface dialog, int whichButton)
                                    {
                                        Intent intent = new Intent(c,MainActivity.class);
                                        startActivity(intent);
                                    }
                                })
                                .create();
                        myQuittingDialogBox.show();
                    }

                    else
                    {
                        String Message = json.getString("message");
                        AlertDialog myQuittingDialogBox = new AlertDialog.Builder(Splash.this)
                                .setTitle("Alert Message!")
                                .setMessage(Message)
                                .setPositiveButton("OK", new DialogInterface.OnClickListener()
                                {	public void onClick(DialogInterface dialog, int whichButton)
                                    {

                                    }
                                })
                                .create();
                        myQuittingDialogBox.show();
                    }

                    // AlertDialogGenerator.showSimpleInfoDialog1(MainActivity.this,msg,Message);
                }

                catch (JSONException e)
                {
                    e.printStackTrace();
                }
            }
        }
    }
}
