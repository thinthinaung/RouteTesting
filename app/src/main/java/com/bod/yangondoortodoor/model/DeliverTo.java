package com.bod.yangondoortodoor.model;

/**
 * Created by Thin Thin Aung on 12/15/2015.
 */
public class DeliverTo
{
    public String getCusID() {
        return cusID;
    }

    public void setCusID(String cusID) {
        this.cusID = cusID;
    }

    public String getCusName() {
        return cusName;
    }

    public void setCusName(String cusName) {
        this.cusName = cusName;
    }

    public String getCusAddress() {
        return cusAddress;
    }

    public void setCusAddress(String cusAddress) {
        this.cusAddress = cusAddress;
    }

    public String getCusPhone() {
        return cusPhone;
    }

    public void setCusPhone(String cusPhone) {
        this.cusPhone = cusPhone;
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

    String cusID;
    String cusName;
    String cusAddress;
    String cusPhone;
    String cusLatitude;
    String cusLongitude;

}
