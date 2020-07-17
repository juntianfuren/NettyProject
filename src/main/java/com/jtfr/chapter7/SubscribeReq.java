/*
 * frxs Inc.  湖南兴盛优选电子商务有限公司.
 * Copyright (c) 2017-2020. All Rights Reserved.
 */

package com.jtfr.chapter7;

import java.io.Serializable;

/**
 * <B>主类名称：</B>SubscribeReq<BR>
 * <B>概要说明：</B>订购请求POJO类<BR>
 * 
 * @author kangming.chen
 * @since 2020年07月17日 14:43
 */
public class SubscribeReq implements Serializable {

    private static final long serialVersionUID = -8567396068806282889L;
    private int subReqID;
    private String userName;
    private String productName;
    private String phoneNumber;
    private String address;

    public int getSubReqID() {
        return subReqID;
    }

    public void setSubReqID(int subReqID) {
        this.subReqID = subReqID;
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public String getProductName() {
        return productName;
    }

    public void setProductName(String productName) {
        this.productName = productName;
    }

    public String getPhoneNumber() {
        return phoneNumber;
    }

    public void setPhoneNumber(String phoneNumber) {
        this.phoneNumber = phoneNumber;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    @Override
    public String toString() {
        return "SubscribeReq [subReqID=" + subReqID + ", userName=" + userName + ", productName=" + productName
            + ", phoneNumber=" + phoneNumber + ", address=" + address + "]";
    }

}
