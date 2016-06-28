package com.bod.yangondoortodoor.fragment;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.graphics.Color;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import android.util.Log;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.ViewGroup.LayoutParams;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.bod.yangondoortodoor.R;
import com.bod.yangondoortodoor.config.ConfigLink;
import com.bod.yangondoortodoor.model.CollectTo;
import com.bod.yangondoortodoor.model.Constants;
import com.bod.yangondoortodoor.model.DataCarry;
import com.bod.yangondoortodoor.model.DeliverTo;
import com.bod.yangondoortodoor.model.Item;
import com.bod.yangondoortodoor.util.SharePref;
import com.google.android.gms.maps.model.LatLng;

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

public class DetailTaskFragment extends Fragment {

    ArrayList<Item> orderList = new ArrayList<Item>();
    ArrayList<DeliverTo> deliverDataList = new ArrayList<DeliverTo>();
    ArrayList<CollectTo> collectDataList = new ArrayList<CollectTo>();
    TextView collectName,collectAddress,collectPhone,deliverName,deliverAddress,deliverPhone,txtordercode,txtdelicharge,txttotalamount;
    LinearLayout layItem;
    Button btnRequest;
    String ordStatus="";
    String statusflag="";
    Button btnMap;
    Context context;

    public DetailTaskFragment()
    {
        // Required empty public constructor
    }

    public static DetailTaskFragment newInstance(int sectionNumber) {
        DetailTaskFragment fragment = new DetailTaskFragment();
        Bundle args = new Bundle();
        args.putInt(Constants.ARG_SECTION_NUMBER, sectionNumber);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState)
    {
        // Inflate the layout for this fragment
        context = getActivity().getApplicationContext();
        View inflaterview = inflater.inflate(R.layout.fragment_order_detail, container, false);
        collectName = (TextView)inflaterview.findViewById(R.id.txtResName);
        collectAddress = (TextView)inflaterview.findViewById(R.id.txtResAddress);
        collectPhone = (TextView)inflaterview.findViewById(R.id.txtResPhone);
        deliverAddress = (TextView)inflaterview.findViewById(R.id.txtDeliverAddress);
        deliverPhone = (TextView)inflaterview.findViewById(R.id.txtDeliverPhone);
        layItem = (LinearLayout)inflaterview.findViewById(R.id.layoutItem);
        btnRequest = (Button)inflaterview.findViewById(R.id.btnRequest);
        btnMap = (Button)inflaterview.findViewById(R.id.btnMap);
        txtordercode = (TextView)inflaterview.findViewById(R.id.txtOrderCode);
        txtdelicharge = (TextView)inflaterview.findViewById(R.id.txtDelivcharge);
        txttotalamount = (TextView)inflaterview.findViewById(R.id.txtTotalAmount);
        return inflaterview;
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState)
    {
        super.onActivityCreated(savedInstanceState);
        Log.e("coming in detail","coming in detail");
        ConnectServerOrderDetailList connOrderDetail = new ConnectServerOrderDetailList(getActivity());
        connOrderDetail.execute();

        btnMap.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Log.e("Click me","Click Map");
                DataCarry.locflag="true";
               FragmentTransaction lfragmentTransaction =getFragmentManager().beginTransaction();
               lfragmentTransaction.replace(R.id.containerView,new HomeFragment()).commit();

               // Fragment mFragment = new HomeFragment().newInstance(0);
               // FragmentManager fragmentManager = getFragmentManager();
               // fragmentManager.beginTransaction()
                      //  .replace(R.id.container, mFragment)
                      //  .addToBackStack(null)
                      //  .commit();

            }
        });
        btnRequest.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view)
            {
                ConnectServerOrderRequest connRequest = new ConnectServerOrderRequest(getActivity());
                connRequest.execute();
            }

        });
    }


    public class ConnectServerOrderDetailList extends AsyncTask {
        Context c;

        public ConnectServerOrderDetailList(Context applicationContext) {
            this.c = applicationContext;
        }

        @Override
        protected Object doInBackground(Object... arg0) {
            String userdata = "";
            HttpParams httpParameters = new BasicHttpParams();
            HttpConnectionParams.setConnectionTimeout(httpParameters, 0);
            HttpConnectionParams.setSoTimeout(httpParameters, 0);
            HttpClient client = new DefaultHttpClient(httpParameters);
            HttpPost request = new HttpPost(ConfigLink.getOrderDetailURL);
            try {
                JSONObject jsonobj = new JSONObject();
                jsonobj.put("token", SharePref.getInstance(getActivity()).getUserSession());
                jsonobj.put("id", Constants.orderID);
                StringEntity se = new StringEntity(jsonobj.toString());
                Log.e("Detail Post Data", jsonobj.toString());
                se.setContentType("application/json;charset=UTF-8");
                se.setContentEncoding(new BasicHeader(HTTP.CONTENT_TYPE, "application/json;charset=UTF-8"));
                request.setEntity(se);
                HttpResponse httpresponse = client.execute(request);
                userdata = EntityUtils.toString(httpresponse.getEntity());
            } catch (Exception e) {
                e.printStackTrace();
            }
            return userdata;
        }

        protected void onPostExecute(Object result) {
            super.onPostExecute(result);
            {
                String userdata = (String) result;
              //  userdata = " 'order': 'order_code': 'ORDR-7052DNU7','address':'Shwe Yin Mar St, Yangon, Myanmar (Burma)','delivery_charge': null,'total': '28764','status':'accepted','order_details': [{'name':'Chicken Pizza','price': '5000.00','suboption': [ {'option_name': 'Crust','suboption_name': 'Thin','price': '0','link': [] },{'option_name': 'Sauce','suboption_name':'Tomatoe','price': '0','link': [] },{'option_name': 'Extra','suboption_name': 'Pork','price':'60','link': []}, {'option_name': 'Extra','suboption_name':'Prawn','price': '70','link': [] } ] }, {'name': 'Spaghetta','price': '4000.00', 'suboption': []  }  ]  }, 'user': { 'username': 'guest','latitude': '16.830837100','address':'Shwe Yin Mar St, Yangon, Myanmar (Burma)','longitude':'96.122650800', 'phone':'' }, 'restaurant': {'restaurant_name':'Palm','address': '9-13, 50th Street, Botatung Township,, Yangon', 'latitude': '16.770745155','longitude':'96.186394212','phone': '09435345435'  } }";
                Log.e("user data", userdata);

                if (userdata == null || userdata.equalsIgnoreCase("")) {
                } else {
                    JSONObject json, json_order;

                    try {
                        json = new JSONObject(userdata);
                        json_order = json.getJSONObject("order");
                        JSONArray jsonOrder, jsonCollect, jsonDeliver;
                        Log.e("order", json_order.toString());
                        String total = json_order.getString("total");
                        String ordercode = json_order.getString("order_code");
                        ordStatus = json_order.getString("status");
                        String deliverCharge = json_order.getString("delivery_charge");
                        JSONArray ordDetail = json_order.getJSONArray("order_details");
                        btnRequest.setEnabled(true);
                        if (ordStatus.equalsIgnoreCase("replied")) {
                            statusflag = "working";
                            btnRequest.setText("Work");
                        }
                        if (ordStatus.equalsIgnoreCase("working")) {
                            statusflag = "completed";
                            btnRequest.setText("Finish");
                        }
                        if (ordStatus.equalsIgnoreCase("accepted")) {
                            statusflag = "accepted";
                            btnRequest.setText("Request");
                        }
                        if (ordStatus.equalsIgnoreCase("completed")) {
                            btnRequest.setEnabled(false);
                        }

                        LinearLayout parent = new LinearLayout(context);
                        parent.setLayoutParams(new LinearLayout.LayoutParams(LayoutParams.MATCH_PARENT, LayoutParams.WRAP_CONTENT));
                        parent.setOrientation(LinearLayout.VERTICAL);
                        parent.setPadding(15,15,15,15);

                        for (int i = 0; i < ordDetail.length(); i++) {
                            JSONObject jsonobject = (JSONObject) ordDetail.get(i);
                            String name = jsonobject.getString("name");
                            String price = jsonobject.getString("price");
                            LinearLayout layout2 = new LinearLayout(context);
                            layout2.setLayoutParams(new LinearLayout.LayoutParams(LayoutParams.MATCH_PARENT, LayoutParams.WRAP_CONTENT));
                            layout2.setOrientation(LinearLayout.HORIZONTAL);
                            TextView tv1 = new TextView(context);
                            TextView tv2 = new TextView(context);

                            tv1.setLayoutParams(new LinearLayout.LayoutParams(0,
                                    LinearLayout.LayoutParams.WRAP_CONTENT, 1f));
                            tv2.setLayoutParams(new LinearLayout.LayoutParams(0,
                                    LinearLayout.LayoutParams.WRAP_CONTENT, 0.5f));
                            tv1.setTextColor(getResources().getColor(R.color.darkgreen));
                            tv2.setTextColor(getResources().getColor(R.color.darkgreen));
                            tv1.setTextSize(16);
                            tv2.setTextSize(16);
                            tv1.setText(name);
                            tv2.setText(price);
                            layout2.addView(tv1);
                            layout2.addView(tv2);
                            JSONArray ordSuboption = jsonobject.getJSONArray("suboption");
                            Log.e("option length", ordSuboption.length() + "");
                            parent.addView(layout2);

                            for (int j = 0; j < ordSuboption.length(); j++) {
                                JSONObject jsonSub = (JSONObject) ordSuboption.get(j);
                                String option_name = jsonSub.getString("option_name");
                                String suboption_name = jsonSub.getString("suboption_name");
                                String option_price = jsonSub.getString("price");
                                JSONArray jsonlinkArr = jsonSub.getJSONArray("link");
                                LinearLayout layout3 = new LinearLayout(context);
                                layout3.setLayoutParams(new LinearLayout.LayoutParams(LayoutParams.MATCH_PARENT, LayoutParams.WRAP_CONTENT));
                                layout3.setOrientation(LinearLayout.HORIZONTAL);
                                layout3.setPadding(10,10,10,10);
                                TextView tvsub_name = new TextView(context);
                                tvsub_name.setLayoutParams(new LinearLayout.LayoutParams(0,
                                        LinearLayout.LayoutParams.WRAP_CONTENT, 1f));
                                tvsub_name.setTextColor(getResources().getColor(R.color.black));
                                TextView tvsub_price = new TextView(context);
                                tvsub_price.setLayoutParams( new LinearLayout.LayoutParams(0,
                                        LinearLayout.LayoutParams.WRAP_CONTENT, 0.5f));
                                tvsub_price.setTextColor(getResources().getColor(R.color.black));
                                tvsub_name.setText(option_name + " - " + suboption_name);
                                tvsub_price.setText(option_price);
                                layout3.addView(tvsub_name);
                                layout3.addView(tvsub_price);
                                parent.addView(layout3);

                                Log.e("Length of link",jsonlinkArr.length()+"");
                                for (int k = 0; k < jsonlinkArr.length(); k++)
                                {
                                    JSONObject jsonlink = (JSONObject) jsonlinkArr.get(k);
                                    String link_opname = jsonlink.getString("option_name");
                                    String link_name = jsonlink.getString("link_option_name");
                                    String link_price = jsonlink.getString("price");
                                    LinearLayout layout4 = new LinearLayout(context);
                                    layout4.setLayoutParams(new LinearLayout.LayoutParams(LayoutParams.MATCH_PARENT, LayoutParams.WRAP_CONTENT));
                                    layout4.setOrientation(LinearLayout.HORIZONTAL);
                                    TextView tvlink_name = new TextView(context);
                                    tvlink_name.setLayoutParams(new LinearLayout.LayoutParams(0,
                                            LinearLayout.LayoutParams.WRAP_CONTENT, 1f));
                                    tvlink_name.setTextColor(getResources().getColor(R.color.black));
                                    TextView tvlink_price = new TextView(context);
                                    tvlink_price.setLayoutParams(new LinearLayout.LayoutParams(0,
                                            LinearLayout.LayoutParams.WRAP_CONTENT, 0.5f));
                                    tvlink_price.setTextColor(getResources().getColor(R.color.black));
                                    tvlink_name.setText(link_opname + " - " + link_name);
                                    tvlink_price.setText(link_price);
                                    layout4.addView(tvlink_name);
                                    layout4.addView(tvlink_price);
                                    parent.addView(layout4);
                                }
                            }
                        }

                        layItem.addView(parent);
                        txtdelicharge.setText(deliverCharge);
                        txtordercode.setText(ordercode);
                        txttotalamount.setText(total);
                        Log.e("Total ", total);
                        collectDataList.clear();
                        deliverDataList.clear();
                        JSONObject uniObject = json.getJSONObject("restaurant");
                        JSONObject devObject = json.getJSONObject("user");
                        String resName = uniObject.getString("restaurant_name");
                        String resphone = uniObject.getString("phone");
                        String reslatitude = uniObject.getString("latitude");
                        String reslongitude = uniObject.getString("longitude");
                        String resaddress = uniObject.getString("address");
                        CollectTo collectdata = new CollectTo();
                        collectdata.setCollectName(resName);
                        collectdata.setCollectPhone(resphone);
                        collectdata.setCollectLatitude(reslatitude);
                        collectdata.setCollectLongitude(reslongitude);
                        collectdata.setCollectAddress(resaddress);
                        collectDataList.add(collectdata);
                        String userName = devObject.getString("username");
                        String userPhone = devObject.getString("phone");
                        String userAddress = devObject.getString("address");
                        String userlatitud = devObject.getString("latitude");
                        String userlongitude = devObject.getString("longitude");
                        DeliverTo devData = new DeliverTo();
                        devData.setCusName(userName);
                        devData.setCusAddress(userAddress);
                        devData.setCusPhone(userPhone);
                        devData.setCusLatitude(userlatitud);
                        devData.setCusLongitude(userlongitude);
                        deliverDataList.add(devData);

                        //orderList.clear();
                       /* for(int i=0;i<jsonCollect.length();i++)
                        {
                            JSONObject cjson = new JSONObject();
                            cjson = jsonCollect.getJSONObject(i);

                            CollectTo collectdata = new CollectTo();
                            collectdata.setCollectName(cjson.getString("restaurant_name"));
                            collectdata.setCollectPhone(cjson.getString("phone"));
                            collectdata.setCollectLatitude(cjson.getString("latitude"));
                            collectdata.setCollectLongitude(cjson.getString("longitude"));
                            collectdata.setCollectAddress(cjson.getString("address"));
                            collectDataList.add(collectdata);
                        }  */

                       /* for(int j=0;j<jsonDeliver.length();j++)
                        {
                            JSONObject djson = new JSONObject();
                            djson = jsonDeliver.getJSONObject(j);
                            DeliverTo deliverdata = new DeliverTo();
                            deliverdata.setCusName(djson.getString("username"));
                            deliverdata.setCusAddress(djson.getString("address"));
                            deliverdata.setCusPhone(djson.getString("phone"));
                            deliverdata.setCusLatitude(djson.getString("latitude"));
                            deliverdata.setCusLongitude(djson.getString("longitude"));
                            deliverDataList.add(deliverdata);
                        }
                        */

                      /*  for(int k=0;k<jsonOrder.length();k++)
                        {
                            JSONObject ijson = new JSONObject();
                            ijson = jsonOrder.getJSONObject(k);
                            if(k==0)
                            {   Item itemdatatitle = new Item();
                                itemdatatitle.setItemName(" Order Details ");
                                orderList.add(itemdatatitle);
                                Item itemdata = new Item();
                                itemdata.setItemName(ijson.getString("item_name"));
                                itemdata.setItemQty(ijson.getString("quantity"));
                                orderList.add(itemdata);
                            }
                            else
                            {
                                Item itemdata = new Item();
                                itemdata.setItemName(ijson.getString("item_name"));
                                itemdata.setItemQty(ijson.getString("quantity"));
                                orderList.add(itemdata);
                            }
                        }  */

                        for (int i = 0; i < collectDataList.size(); i++) {
                            collectName.setText(collectDataList.get(i).getCollectName());
                            collectAddress.setText(collectDataList.get(i).getCollectAddress());
                            DataCarry.resLoc = new LatLng(Double.parseDouble(collectDataList.get(i).getCollectLatitude()), Double.parseDouble(collectDataList.get(i).getCollectLongitude()));

                           /* if(collectDataList.get(i).getCollectStatus().equalsIgnoreCase("Dispatched"))
                            {
                                btnRequest.setText("Order Request");
                                btnRequest.setVisibility(View.VISIBLE);
                                btnMap.setVisibility(View.VISIBLE);
                                DataCarry.resLoc = new LatLng(Double.parseDouble(collectDataList.get(i).getCollectLatitude()),Double.parseDouble(collectDataList.get(i).getCollectLongitude()));
                            } */
                        }

                        for (int i = 0; i < deliverDataList.size(); i++) {
                            deliverAddress.setText(deliverDataList.get(i).getCusAddress());
                            deliverPhone.setText(deliverDataList.get(i).getCusPhone());
                            DataCarry.cusLoc = new LatLng(Double.parseDouble(deliverDataList.get(i).getCusLatitude()), Double.parseDouble(deliverDataList.get(i).getCusLongitude()));
                        }

                        for (int i = 0; i < orderList.size(); i++) {
                            LinearLayout lay = new LinearLayout(getActivity());
                            LinearLayout.LayoutParams LLParams = new LinearLayout.LayoutParams(LinearLayout.LayoutParams.MATCH_PARENT, LinearLayout.LayoutParams.WRAP_CONTENT);
                            lay.setLayoutParams(LLParams);
                            lay.setWeightSum(2);
                            if (i == 0)
                            {
                                lay.setMinimumHeight(90);
                                lay.setGravity(Gravity.CENTER);

                            }
                            else
                            {
                                lay.setMinimumHeight(50);
                                lay.setGravity(Gravity.CENTER);
                            }
                            lay.setOrientation(LinearLayout.HORIZONTAL);
                            LinearLayout lay1 = new LinearLayout(getActivity());
                            LinearLayout.LayoutParams layout1Params = new LinearLayout.LayoutParams(0, LinearLayout.LayoutParams.WRAP_CONTENT, 1.0f);
                            lay1.setLayoutParams(layout1Params);
                            lay1.setGravity(Gravity.LEFT);
                            lay1.setPadding(10, 0, 0, 0);
                            TextView txtName = new TextView(getActivity());
                            lay1.addView(txtName);
                            if (i == 0) {
                                txtName.setTextColor(Color.parseColor("#9fc206"));
                                txtName.setTextSize(18);
                            }
                            LinearLayout lay2 = new LinearLayout(getActivity());
                            LinearLayout.LayoutParams layout2Params = new LinearLayout.LayoutParams(0, LinearLayout.LayoutParams.WRAP_CONTENT, 1.0f);
                            lay2.setLayoutParams(layout2Params);
                            lay2.setGravity(Gravity.RIGHT);
                            lay2.setPadding(0, 0, 10, 0);
                            TextView txtQty = new TextView(getActivity());
                            txtQty.setTextColor(Color.parseColor("#9fc206"));
                            lay2.addView(txtQty);
                            txtName.setText(orderList.get(i).getItemName());
                            txtQty.setText(orderList.get(i).getItemQty());
                            lay.addView(lay1);
                            lay.addView(lay2);
                            if (((i + 1) % 2) == 0) {
                                lay.setBackgroundColor(Color.parseColor("#eeeeee"));
                            }
                            layItem.addView(lay);
                        }
                    } catch (JSONException e) {
                        e.printStackTrace();
                    }
                }
            }
        }
    }
    public class ConnectServerOrderRequest extends AsyncTask
    {
        Context c;
        public ConnectServerOrderRequest(Context applicationContext)
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
            HttpPost request = new HttpPost(ConfigLink.acceptDispatch);
            try
            {
                JSONObject jsonobj = new JSONObject();
                jsonobj.put("token", SharePref.getInstance(getActivity()).getUserSession());
                jsonobj.put("id", Constants.orderID);
                jsonobj.put("status",statusflag);
                StringEntity se = new StringEntity(jsonobj.toString());
                Log.e("Request order  Data", jsonobj.toString());
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
           // Log.e("Dispatched Data", userdata + "aaa");
            if(userdata == null || userdata.equalsIgnoreCase(""))
            {

            }
            else
            {
                JSONObject json;
                Log.e("data", userdata + "");
                // btnEdit.setText("Edit");
                try
                {
                    json = new JSONObject(userdata);
                    String Message = json.getString("message");
                    AlertDialog myQuittingDialogBox = new AlertDialog.Builder(getActivity())
                            .setTitle("Message!")
                            .setMessage(Message)
                            .setPositiveButton("OK", new DialogInterface.OnClickListener()
                            {	public void onClick(DialogInterface dialog, int whichButton)
                                {
                                    ConnectServerOrderDetailList connOrderDetail = new ConnectServerOrderDetailList(getActivity());
                                    connOrderDetail.execute();
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
