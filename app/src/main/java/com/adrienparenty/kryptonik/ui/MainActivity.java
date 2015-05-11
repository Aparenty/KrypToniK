package com.adrienparenty.kryptonik.ui;

import android.app.ProgressDialog;
import android.content.ContentResolver;
import android.content.Context;
import android.content.Intent;
import android.os.Handler;
import android.os.Message;
import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.adrienparenty.kryptonik.R;
import com.adrienparenty.kryptonik.data.Data;
import com.adrienparenty.kryptonik.data.Sms;
import com.adrienparenty.kryptonik.phone.ContactCollector;
import com.adrienparenty.kryptonik.phone.SmsCollector;


public class MainActivity extends ActionBarActivity implements Runnable{
    //the compnenents
    Button buttonLogin;
    EditText passwordField ;
    //this class will contains all the data we need to run the app
    public Data data;
    //variables need for the progress bar
    private ProgressDialog loadingDialog;
    private ContentResolver contentResolver;
    private int loadingDialogProgress = 0;
    private int messageAmount = 0;
    private int currentMessage = 0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        //loading datas
        data = new Data();
        //TODO : modifie that part after test
        data.setPassword("1234");
        //initialisation of the components
        initComponents();
        //getting the content resolver
        contentResolver = getContentResolver();
    }
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }
    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }
    //initialisation of the components
    private void initComponents(){
        buttonLogin = (Button)findViewById(R.id.button2);
        passwordField = (EditText)findViewById(R.id.editText2);

        buttonLogin.setOnClickListener(new Button.OnClickListener() {
            public void onClick(View v)
            {
                login(passwordField.getText().toString());
            }
        });
    }
    //verifying the credentials
    private void login(String password){
        String passStocked = data.getPassword();
        //if password is correct
        if(passStocked.equalsIgnoreCase(password)){
            loadingDialog = new ProgressDialog(this);
            // progress bar message
            loadingDialog.setMessage("Loading ...");
            // progess bar title
            loadingDialog.setTitle("KrypTonik is loading  ...");
            // progressbar style
            loadingDialog.setProgressStyle(ProgressDialog.STYLE_HORIZONTAL);
            // showing progress bar
            loadingDialog.show();

            Thread thread = new Thread(this);
            thread.start();
        }
        //else we show a toast message to advice the user
        else{
            Context context = getApplicationContext();
            CharSequence text = "Incorrect Password";
            int duration = Toast.LENGTH_SHORT;

            Toast toast = Toast.makeText(context, text, duration);
            toast.show();
        }

    }
    private Handler handler = new Handler() {
        @Override
        public void handleMessage(Message msg) {
            int i = msg.what;
            switch (i)
            {
                case 1: loadingDialog.setMessage("Loading contact list");
                        loadingDialog.setProgress(loadingDialogProgress);
                    break;
                case 2: loadingDialog.setMessage("Loading sms list");
                    loadingDialog.setProgress(loadingDialogProgress);
                    break;
                case 3: loadingDialog.setMessage("Loading sms "+ currentMessage+ " / "+messageAmount);
                    loadingDialog.setProgress(loadingDialogProgress);
                    break;

                default:
                    // close dial
                    loadingDialog.dismiss();
            }
        }
    };
    //loading all the datas needed
    //as this operation can take a little time ,i created a waiting dialog with a thread
    @Override
    public void run() {
        try{

                            //getting the contact list
                            data.setContactList(new ContactCollector().ListContacts(contentResolver));
                            loadingDialogProgress = 10;
                            handler.sendEmptyMessage(1);
                            Thread.sleep(10);
                            //getting the message
                            data.setSmsList(new SmsCollector().listMessages(contentResolver));
                            loadingDialogProgress = 20;
                            handler.sendEmptyMessage(2);
                            messageAmount = data.getSmsList().size();
                            Thread.sleep(10);

                            //to make sure the progress bar will show the real progress ,
                            double progressBarRate = 80.00 / (double)data.getSmsList().size();
                            double increseadBarValue = 20;
                            //try to match sms address with sender number
                            com.adrienparenty.kryptonik.phone.SmsSorter sorter = new com.adrienparenty.kryptonik.phone.SmsSorter();
                            for (int i= 0 ; i < data.getSmsList().size(); i ++){

                                //matching number and contact
                                sorter.findContactName((Sms) data.getSmsList().get(i));
                                //showing the progress
                                currentMessage =i;
                                increseadBarValue += (progressBarRate);
                                loadingDialogProgress = (int)increseadBarValue;
                                Log.e(""+loadingDialogProgress,"||"+(int)increseadBarValue);
                                handler.sendEmptyMessage(3);
                                Thread.sleep(1);


                            }
            loadingDialog.dismiss();
            Intent intent = new Intent(MainActivity.this, WelcomeScreen.class);
            startActivity(intent);



        }
        catch(Exception e){
                e.printStackTrace();
        }



    }


}
