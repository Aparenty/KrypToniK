package com.adrienparenty.kryptonik.phone;

import android.content.ContentResolver;
import android.database.Cursor;
import android.provider.ContactsContract;
import android.util.Log;
import com.adrienparenty.kryptonik.data.Contact;
import java.util.Vector;


/**
 * Created by adrienparenty on 10/04/2015.
 */
public class ContactCollector {

    private Vector <Contact> contactList;
    public Vector ListContacts(ContentResolver contentResolver)
    {
        contactList = new Vector<>();
        Cursor cursor = contentResolver.query(ContactsContract.CommonDataKinds.Phone.CONTENT_URI, null,null,null, null);
        //verification to avoid NullPointer Exception
        if (cursor == null)
        {
            Log.e("Class - PhoneContacts", "Cannot retrieve the contacts");
            return null;
        }
        //if the cursor is not empty we move to the first item
        if (cursor.moveToFirst())
        {
            do
            {


                Contact contactTemp = new Contact();
                contactTemp.setContactName(cursor.getString(cursor.getColumnIndex(ContactsContract.CommonDataKinds.Phone.DISPLAY_NAME)));
                contactTemp.setContactNumber(cursor.getString(cursor.getColumnIndex(ContactsContract.CommonDataKinds.Phone.NUMBER)));
                contactTemp.setContactFormattedNumber(formatNumber(cursor.getString(cursor.getColumnIndex(ContactsContract.CommonDataKinds.Phone.NUMBER))));
                contactList.add(contactTemp);

            }
            while (cursor.moveToNext());
        }

        //when we reach the last one , we close the cursor
        if (!cursor.isClosed())
        {
            cursor.close();
        }

        return contactList;
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
