package com.bod.yangondoortodoor.adapter;

import android.content.Context;
import android.content.res.Configuration;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.ListView;
import android.widget.TextView;

import com.bod.yangondoortodoor.R;
import com.bod.yangondoortodoor.fragment.DetailTaskFragment;
import com.bod.yangondoortodoor.model.Constants;
import com.bod.yangondoortodoor.model.Order;

import java.util.List;

/**
 * Created by ezdir on 8/28/14.
 */
public class OrderAdapter extends BaseAdapter {

    Context context;
    List<Order> orderList;
    String  txtph ;
    ListView listView;
    FragmentManager fg;

    public OrderAdapter(Context context, List<Order> bizList, ListView ll,FragmentManager fg) {
        this.context = context;
        this.orderList = bizList;
        this.fg = fg;
        listView = ll;
    }

    public View getView(int position, View convertView, ViewGroup paraViewGroup) {
        final ViewHolder holder;
        View view = convertView;
        if (view != null) {
            holder = (ViewHolder) view.getTag();
        } else {
            LayoutInflater inflater =
                    (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            view = inflater.inflate(R.layout.row_item, null, false);
            holder = new ViewHolder();
            view.setTag(holder);
        }

         holder.resName = (TextView) view.findViewById(R.id.resName);
         holder.orderDate = (TextView) view.findViewById(R.id.orderDate);
         holder.btnDetail = (Button)view.findViewById(R.id.btnOrderDetail);
         final Order orderItem = (Order) getItem(position);

         holder.resName.setText(orderItem.getOrderCode());
         holder.orderDate.setText(orderItem.getOrderDate());
         //holder.orderDate.setText(orderItem.getOrderDate());

       holder.btnDetail.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Log.e("click Call me", "Click call me");
                Log.e("ordID,hidID",orderItem.getOrderID()+" "+orderItem.getOrderhidID()+"");
                int selPos = listView.getPositionForView((View) v.getParent());
                Order orderItem = orderList.get(selPos);
                Constants.orderID = orderItem.getOrderID();
                FragmentTransaction lfragmentTransaction = fg.beginTransaction();
                lfragmentTransaction.replace(R.id.containerView,new DetailTaskFragment()).commit();
            }
        });

        return view;
    }

    @Override
    public int getCount() {
        try {
            return orderList.size();
        }catch (NullPointerException e){
            return 0;
        }
    }

    @Override
    public Object getItem(int position) {
        return orderList.get(position);
    }

    @Override
    public long getItemId(int position) {
        return orderList.indexOf(getItem(position));
    }

    public  class ViewHolder
    {
            TextView resName;
            TextView orderDate;
            Button btnDetail;
    }

    private boolean isSizeLarge() {
        if ((context.getResources().getConfiguration().screenLayout &
                Configuration.SCREENLAYOUT_SIZE_MASK) ==
                Configuration.SCREENLAYOUT_SIZE_LARGE) {
            return true;
        }

        else
        {
            return false;
        }
    }
}
