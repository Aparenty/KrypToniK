package com.adrienparenty.kryptonik.phone;

import android.content.ContentResolver;
import android.database.Cursor;
import android.net.Uri;
import android.util.Log;
import com.adrienparenty.kryptonik.data.Sms;
import java.util.Vector;


/**
 * Created by adrienparenty on 10/04/2015.
 */
public class SmsCollector {
    ContentResolver contentR ;
    //the basic URI ALL SMS
    private static final Uri SMS_URI_ALL = Uri.parse("content://sms/");
    //the amount of text presents in the phone
    public int messageCount = 0;

    //this method is used to get the list of the messages presents in INBOX
    public Vector listMessages(ContentResolver contentResolver)
    {
        contentR = contentResolver;
        //the  final list containing all the sms in the phone
        Vector messages = new Vector();
        //the cursor wich sill temporary stock the messages
        Cursor cursor = contentResolver.query(SMS_URI_ALL, null, null, null, null);
        //verification to avoid NullPointer Exception
        if (cursor == null)
        {
            Log.e("Class - listMessages", "Cannot retrieve the messages");
            return null;
        }
        //if the cursor is not empty we move to the first item
        if (cursor.moveToFirst() == true)
        {
            do
            {
                //parsing informations from the cursor
                //the message ID
                int id = cursor.getInt(cursor.getColumnIndexOrThrow("_id"));
                //message adresse
                String address = cursor.getString(cursor.getColumnIndexOrThrow("address"));
                //message body
                String body = cursor.getString(cursor.getColumnIndexOrThrow("body"));
                //adding the message to the list
                if(body.length() > 20){body = body.substring(0,20)+" ...";}
                //creation of the objet SMS
                Sms tempMessage = new Sms();
                tempMessage.setSmsBody(body);
                tempMessage.setSmsId(id);
                tempMessage.setSmsNumber(address);
                tempMessage.setSmsFormatedNumber(formatNumber(address));
                messages.add(tempMessage);
                //increment message count
                messageCount ++;

            }
            while (cursor.moveToNext() == true);
            //then we set the vector to the data class wich allow acces from any class

        }
        //when we reach the last one , we close the cursor
        if (cursor.isClosed() == false)
        {
            cursor.close();
        }

        return messages;
    }
    //this method will search in the contact list if the contact is already registred in the phone
    //if it is the case, the phone number will be changed by the name of the contact


    public int getMessageCount(){
        return this.messageCount;
    }

    private String formatNumber(String baseNumber){
        String number = baseNumber.replaceAll(" ","");

        if (number.startsWith("+")){
            number = number.substring(3);
        }
        if(number.startsWith("0")){
            number =number.substring(1);
        }

        return number;

    }
}



