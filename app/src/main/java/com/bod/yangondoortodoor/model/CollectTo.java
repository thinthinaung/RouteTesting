package com.bod.yangondoortodoor.model;

/**
 * Created by Thin Thin Aung on 12/15/2015.
 */
public class CollectTo
{
    public String getCollectID() {
        return collectID;
    }

    public void setCollectID(String collectID) {
        this.collectID = collectID;
    }

    public String getCollectName() {
        return collectName;
    }

    public void setCollectName(String collectName) {
        this.collectName = collectName;
    }

    public String getCollectPhone() {
        return collectPhone;
    }

    public void setCollectPhone(String collectPhone) {
        this.collectPhone = collectPhone;
    }

    public String getCollectLatitude() {
        return collectLatitude;
    }

    public void setCollectLatitude(String collectLatitude) {
        this.collectLatitude = collectLatitude;
    }

    public String getCollectLongitude() {
        return collectLongitude;
    }

    public void setCollectLongitude(String collectLongitude) {
        this.collectLongitude = collectLongitude;
    }

    public String getCollectAddress() {
        return collectAddress;
    }

    public void setCollectAddress(String collectAddress) {
        this.collectAddress = collectAddress;
    }

    public String getCollectStatus() {
        return collectStatus;
    }

    public void setCollectStatus(String collectStatus) {
        this.collectStatus = collectStatus;
    }

    String collectStatus;
    String collectAddress;
    String collectID;
    String collectName;
    String collectPhone;
    String collectLatitude;
    String collectLongitude;
}
