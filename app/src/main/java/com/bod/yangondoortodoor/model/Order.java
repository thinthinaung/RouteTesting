package com.bod.yangondoortodoor.model;

/**
 * Created by Thin Thin Aung on 12/11/2015.
 */
public class Order
{
    String orderID;
    String orderResName;
    String orderDate;
    String orderhidID;
    String orderCode;

    public String getOrderCode() {
        return orderCode;
    }

    public void setOrderCode(String orderCode) {
        this.orderCode = orderCode;
    }
    public String getOrderhidID() {
        return orderhidID;
    }

    public void setOrderhidID(String orderhidID) {
        this.orderhidID = orderhidID;
    }

    public String getOrderID() {
        return orderID;
    }

    public void setOrderID(String orderID) {
        this.orderID = orderID;
    }

    public String getOrderResName() {
        return orderResName;
    }

    public void setOrderResName(String orderResName) {
        this.orderResName = orderResName;
    }

    public String getOrderDate() {
        return orderDate;
    }

    public void setOrderDate(String orderDate) {
        this.orderDate = orderDate;
    }
}
