package com.adrienparenty.kryptonik.data;

/**
 * Created by adrienparenty on 17/04/2015.
 */
//this class is the Object SMS

public class Sms {
    //the different type of SMS we can have
    public static int SMS_TYPE_SENT     =1;
    public static int SMS_TYPE_DRAFT    =2;
    public static int SMS_TYPE_RECIEVED =3;
    //variables
    private int smsType;
    private int smsId;
    private String smsBody           = "";
    private String smsNumber         = "";
    private String smsFormatedNumber = "";
    //setters
    public void setSmsType(int smsType) {
        this.smsType = smsType;
    }
    public void setSmsId(int smsId) {
        this.smsId = smsId;
    }
    public void setSmsBody(String smsBody) {
        this.smsBody = smsBody;
    }
    public void setSmsNumber(String smsNumber) {
        this.smsNumber = smsNumber;
    }
    public void setSmsFormatedNumber(String smsFormatedNumber) {
        this.smsFormatedNumber = smsFormatedNumber;
    }
    //getters
    public int getSmsType() {
        return smsType;
    }
    public int getSmsId() {
        return smsId;
    }
    public String getSmsBody() {
        return smsBody;
    }
    public String getSmsNumber() {
        return smsNumber;
    }
    public String getSmsFormatedNumber() {
        return smsFormatedNumber;
    }
}
