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
import android.widget.ListView;
import com.bod.yangondoortodoor.R;
import com.bod.yangondoortodoor.adapter.OrderAdapter;
import com.bod.yangondoortodoor.config.ConfigLink;
import com.bod.yangondoortodoor.model.Order;
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
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

public class PastTaskFragment extends Fragment {

    ListView listPastTask;
    OrderAdapter adapter;
    ArrayList<Order> orderList = new ArrayList<Order>();
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState)
    {
        // Inflate the layout for this fragment
        View  inflaterView = inflater.inflate(R.layout.fragment_past_task, container, false);
        listPastTask = (ListView)inflaterView.findViewById(R.id.listView);
        return inflaterView;
    }

    public class ConnectServerPastOrderList extends AsyncTask
    {
        Context c;
        public ConnectServerPastOrderList(Context applicationContext)
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
            HttpPost request = new HttpPost(ConfigLink.requestOrder);

            try
            {
                JSONObject jsonobj = new JSONObject();
                jsonobj.put("token", SharePref.getInstance(getActivity()).getUserSession());
                jsonobj.put("status","delivered");
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
            //userdata = "{'dispatch': [],'assign': [ {'id': 5,'order_code': 'ORDR-9MEROJT9 }, {'id': 6,'order_code': 'ORDR-7052DNU7' } ]}";
            Log.e("user data",userdata);
            // pd.dismiss();
            Log.e("History Order List", userdata + "aaa");
            if(userdata == null || userdata.equalsIgnoreCase(""))
            {

            }
            else
            {
                JSONObject json;
                Log.e("data", userdata + "");
                try
                {
                       json = new JSONObject(userdata);
                        JSONArray jsonOrder;
                        String orderData = json.getString("order");
                        jsonOrder = new JSONArray(orderData);
                        Log.e("order length",jsonOrder.length()+"");
                        orderList.clear();
                        for(int i=0;i<jsonOrder.length();i++)
                        {
                            JSONObject jsonObj = new JSONObject();
                            jsonObj = jsonOrder.getJSONObject(i);
                            Order item = new Order();
                            item.setOrderID(jsonObj.getString("id"));
                            item.setOrderCode(jsonObj.getString("order_code"));
                            item.setOrderDate(jsonObj.getString("order_date"));
                            orderList.add(item);
                        }

                        adapter = new OrderAdapter(getActivity(),orderList,listPastTask,getFragmentManager());
                        listPastTask.setAdapter(adapter);
                }
                catch (JSONException e)
                {
                    e.printStackTrace();
                }
            }
        }
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState)
    {
        super.onActivityCreated(savedInstanceState);
        ConnectServerPastOrderList connPastOrder = new ConnectServerPastOrderList(getActivity());
        connPastOrder.execute();
    }

}

