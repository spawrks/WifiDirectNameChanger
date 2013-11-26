package com.spawrks.wifidirecttest;

import android.app.Activity;
import android.content.Context;
import android.net.wifi.p2p.WifiP2pManager;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

public class MainActivity extends Activity {

    WifiP2pManager mWifiP2pManager;
    WifiP2pManager.Channel mChannel;
    String myNewDeviceName = "nothingNew";
    String TAG = "SPAWRKS";
    Context mContext;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

    }

    public void performMagic(String input){

        //inner class needs to have access to new name
        
        myNewDeviceName = input;

        //
        //  This will get the WifiDirect manager for use
        //



        mWifiP2pManager = (WifiP2pManager) getSystemService(Context.WIFI_P2P_SERVICE);
        if (mWifiP2pManager != null) {
            mChannel = mWifiP2pManager.initialize(this, getMainLooper(), null);
            if (mChannel == null) {
                //Failure to set up connection
                Log.e(TAG, "Failed to set up connection with wifi p2p service");
                mWifiP2pManager = null;
            }
        } else {
            Log.e(TAG, "mWifiP2pManager is null !");
        }

        //
        //  Setup for using the reflection API to actually call the methods we want
        //

        int numberOfParams = 3;
        Class[] methodParameters = new Class[numberOfParams];
        methodParameters[0] = WifiP2pManager.Channel.class;
        methodParameters[1] = String.class;
        methodParameters[2] = WifiP2pManager.ActionListener.class;

        Object arglist[] = new Object[numberOfParams];
        arglist[0] = mChannel;
        arglist[1] = myNewDeviceName;
        arglist[2] = new WifiP2pManager.ActionListener() {
            public void onSuccess() {
                String resultString = "Changed to " + myNewDeviceName;
                Log.e("SECRETAPI", resultString);
                Toast.makeText(getApplicationContext(), resultString, Toast.LENGTH_LONG).show();
            }

            public void onFailure(int reason) {
                String resultString = "Fail reason: " + String.valueOf(reason);
                Log.e("SECRETAPI",resultString);
                Toast.makeText(getApplicationContext(), resultString,Toast.LENGTH_LONG).show();
            }
        };

        //
        //  Now we use the reflection API to call a method we normally wouldnt have access to.
        //

        ReflectionUtils.executePrivateMethod(mWifiP2pManager,WifiP2pManager.class,"setDeviceName",methodParameters,arglist);

    }

    public void buttonClick(View v){
        EditText e = (EditText) findViewById(R.id.myInput);
        performMagic(e.getText().toString());
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        switch (item.getItemId()) {
            case R.id.action_settings:
                return true;
        }
        return super.onOptionsItemSelected(item);
    }


}
