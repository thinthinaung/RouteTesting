package com.bod.yangondoortodoor.model;

import com.google.android.gms.maps.model.LatLng;

/**
 * Created by Thin Thin Aung on 6/17/2016.
 */
public class LatLngObj
{
    public LatLng getFirstposition() {
        return firstposition;
    }

    public void setFirstposition(LatLng firstposition)
    {
        this.firstposition = firstposition;
    }

    public LatLng getSecondposition() {
        return secondposition;
    }

    public void setSecondposition(LatLng secondposition) {
        this.secondposition = secondposition;
    }

    public LatLng getFourposition() {
        return fourposition;
    }

    public void setFourposition(LatLng fourposition) {
        this.fourposition = fourposition;
    }

    public LatLng getThirdposition() {
        return thirdposition;
    }

    public void setThirdposition(LatLng thirdposition) {
        this.thirdposition = thirdposition;

    }



    public String getMeslatitude() {
        return meslatitude;
    }

    public void setMeslatitude(String meslatitude) {
        this.meslatitude = meslatitude;
    }

    public String getMeslongitue() {
        return meslongitue;
    }

    public void setMeslongitue(String meslongitue) {
        this.meslongitue = meslongitue;
    }

    public String getMesname() {
        return mesname;
    }

    public void setMesname(String mesname) {
        this.mesname = mesname;
    }

    public String getOrderno() {
        return orderno;
    }

    public void setOrderno(String orderno) {
        this.orderno = orderno;
    }

    String meslatitude;
   String meslongitue;
   String mesname;
   String orderno;
    LatLng firstposition;
    LatLng secondposition;
    LatLng thirdposition;
    LatLng fourposition;

}
