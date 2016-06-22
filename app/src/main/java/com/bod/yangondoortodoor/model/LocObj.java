package com.bod.yangondoortodoor.model;
import com.google.android.gms.maps.model.LatLng;

/**
 * Created by Thin Thin Aung on 1/5/2016.
 */
public  class LocObj
{
    public LatLng getFirstPosition() {
        return firstPosition;
    }

    public void setFirstPosition(LatLng firstPosition) {
        this.firstPosition = firstPosition;
    }

    public LatLng getSecondPosition() {
        return secondPosition;
    }

    public void setSecondPosition(LatLng secondPosition) {
        this.secondPosition = secondPosition;
    }

    public LatLng getThirdPosition() {
        return thirdPosition;
    }

    public void setThirdPosition(LatLng thirdPosition) {
        this.thirdPosition = thirdPosition;
    }

    public LatLng getFourPosition()
    {
        return fourPosition;
    }

    public void setFourPosition(LatLng fourPosition) {
        this.fourPosition = fourPosition;
    }

    public String getResLatitude() {
        return resLatitude;
    }

    public void setResLatitude(String resLatitude) {
        this.resLatitude = resLatitude;
    }

    public String getResLongitude() {
        return resLongitude;
    }

    public void setResLongitude(String resLongitude) {
        this.resLongitude = resLongitude;
    }

    public String getCusLatitude() {
        return cusLatitude;
    }

    public void setCusLatitude(String cusLatitude) {
        this.cusLatitude = cusLatitude;
    }

    public String getCusLongitude() {
        return cusLongitude;
    }

    public void setCusLongitude(String cusLongitude) {
        this.cusLongitude = cusLongitude;
    }

    public String getMesLatitude() {
        return mesLatitude;
    }

    public void setMesLatitude(String mesLatitude) {
        this.mesLatitude = mesLatitude;
    }

    public String getMesLongitude() {
        return mesLongitude;
    }

    public void setMesLongitude(String mesLongitude) {
        this.mesLongitude = mesLongitude;
    }

    public String getMesName() {
        return mesName;
    }

    public void setMesName(String mesName) {
        this.mesName = mesName;
    }

    public String getOrderNo() {
        return orderNo;
    }

    public void setOrderNo(String orderNo) {
        this.orderNo = orderNo;
    }

    public String getFlagIcon() {
        return flagIcon;
    }

    public void setFlagIcon(String flagIcon) {
        this.flagIcon = flagIcon;
    }



    public LatLng firstPosition ;
    public LatLng secondPosition;
    public LatLng thirdPosition;
    public LatLng fourPosition;
    public String resLatitude;
    public String resLongitude;
    public String cusLatitude;
    public String cusLongitude;
    public String mesLatitude;
    public String mesLongitude;
    public String mesName;
    public String orderNo;
    public String flagIcon;

    public double getDistance() {
        return distance;
    }

    public void setDistance(double distance) {
        this.distance = distance;
    }

    public double  distance;

  /*  @Override
    public int compareTo(LocObj dis)
    {
        int compare_distance=((LocObj)dis).getDistance();
        //For Ascending order
        return this.distance-compare_distance;
    } */

}
