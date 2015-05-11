package com.adrienparenty.kryptonik.phone;

import com.adrienparenty.kryptonik.data.Data;
import com.adrienparenty.kryptonik.data.Sms;
import com.adrienparenty.kryptonik.data.Contact;

import java.util.Vector;

/**
 * Created by adrienparenty on 11/04/2015.
 * This class will make the link between the sms sender and his name in contacts.
 * I created it in a new class because this operation can take time up to 6 seconds
 */
public class SmsSorter {
        Vector contactList;

    public void findContactName(Sms sms){

        contactList = Data.getContactList();

        for(int i = 0 ; i < contactList.size() ; i++){
            //getting the object Contact
                Contact contact = (Contact)contactList.get(i);
            //loop to search is the formated numbers are matching
            if (sms.getSmsFormatedNumber().equalsIgnoreCase(
                    contact.getContactFormattedNumber()
            ))
            {
                sms.setSmsNumber(contact.getContactName());

                break;
            }

        }


    }


}


