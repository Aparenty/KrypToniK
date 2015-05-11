package com.adrienparenty.kryptonik.data;

import java.util.List;
import java.util.Vector;

/**
 * Created by adrienparenty on 10/04/2015.
 */
public class Data {
    //this is the static datas we will need to run the app
    //the information located in the phone
    public static Vector smsList;
    public static int smsCount = 0 ;
    public static Vector contactList;
    //then the configuration information tha password and the list of passphrase
    public static String password;
    public static List keyChains;

    //the getters metthods
    public static Vector getSmsList(){
        return smsList;
    }
    public static int getSmsCount(){return smsCount;}
    public static Vector getContactList(){
        return contactList;
    }
    public static String getPassword() {
        return password;
    }
    public static List getKeyChains() {
        return keyChains;
    }
    //the setters methods
    public static void setSmsList(Vector smsList) {
        Data.smsList = smsList;
    }
    public static void setSmsCount(int count){smsCount = count;}
    public static void setContactList(Vector contactList) {
        Data.contactList = contactList;
    }
    public static void setPassword(String password) {
        Data.password = password;
    }
    public static void setKeyChains(List keyChains) {
        Data.keyChains = keyChains;
    }
}
