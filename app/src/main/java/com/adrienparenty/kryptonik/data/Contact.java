package com.adrienparenty.kryptonik.data;

/**
 * Created by adrienparenty on 17/04/2015.
 */
//this class is the Object Contact
public class Contact {

    //information about the contact
    private String contactName            = "";
    private String contactNumber          = "";
    private String contactFormattedNumber = "";
    //setters
    public void setContactFormattedNumber(String contactFormattedNumber) {
        this.contactFormattedNumber = contactFormattedNumber;
    }

    public void setContactName(String contactName) {
        this.contactName = contactName;
    }

    public void setContactNumber(String contactNumber) {
        this.contactNumber = contactNumber;
    }
    //getters
    public String getContactName() {
        return contactName;
    }

    public String getContactNumber() {
        return contactNumber;
    }

    public String getContactFormattedNumber() {
        return contactFormattedNumber;
    }
}
