package com.bod.yangondoortodoor.model;

import com.google.android.gms.maps.model.LatLng;

import java.util.ArrayList;

/**
 * Created by Thin Thin Aung on 11/19/2015.
 */
public class DataCarry
{
    public  static  int flagNoti=0;
    public static String message = "";
    public static String cusAddress = "";
    public static String resAddress = "";
    public static String orderID = "";
    public static ArrayList<Order> orderList = new ArrayList<Order>();
    public static ArrayList<Item> itemList = new ArrayList<Item>();
   // public static ArrayList<LocObj> locitemList = new ArrayList<LocObj>();
    public static String locflag = "false";
    public static LatLng resLoc ;
    public static LatLng cusLoc ;
    public static int count_order = 0;


}
