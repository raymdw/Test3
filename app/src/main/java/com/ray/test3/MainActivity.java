package com.ray.test3;

import android.content.IntentFilter;
import android.net.Uri;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.bluetooth.BluetoothAdapter;
import android.bluetooth.BluetoothDevice;
import android.bluetooth.BluetoothSocket;
import android.content.BroadcastReceiver;
import android.content.Intent;
import android.content.Context;
import android.util.Log;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.appindexing.Action;
import com.google.android.gms.appindexing.AppIndex;
import com.google.android.gms.common.api.GoogleApiClient;

public class MainActivity extends AppCompatActivity {
    private BluetoothAdapter mBluetoothAdapter = null;
    private BluetoothDevice mBluetoothDevice = null;
    private static String TAG = "Bluetooth_State";
    //private TextView btDesc;
    /**
     * ATTENTION: This was auto-generated to implement the App Indexing API.
     * See https://g.co/AppIndexing/AndroidStudio for more information.
     */
    private GoogleApiClient client;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        mBluetoothAdapter = BluetoothAdapter.getDefaultAdapter(); // ��ñ���������������������
        if (mBluetoothAdapter == null) {
            toast("No BlueAdapter");
            return;
        }
        IntentFilter bluetoothFilter = new IntentFilter();
        bluetoothFilter.addAction(BluetoothAdapter.ACTION_STATE_CHANGED);
        bluetoothFilter.addAction(BluetoothAdapter.ACTION_SCAN_MODE_CHANGED);
        this.registerReceiver(BluetoothReciever, bluetoothFilter);

        //����ɨ������豸
        IntentFilter btDiscoveryFilter = new IntentFilter();
        btDiscoveryFilter.addAction(BluetoothAdapter.ACTION_DISCOVERY_STARTED);
        btDiscoveryFilter.addAction(BluetoothAdapter.ACTION_DISCOVERY_FINISHED);
        btDiscoveryFilter.addAction(BluetoothDevice.ACTION_FOUND);
        btDiscoveryFilter.addAction(BluetoothDevice.ACTION_BOND_STATE_CHANGED);
        this.registerReceiver(BTDiscoveryReceiver, btDiscoveryFilter);
        toast(mBluetoothAdapter.getName());
        int initialBTState = mBluetoothAdapter.getState();
        //btDesc = (TextView) findViewById(R.id.textView);
        Log.v(TAG," Name : " + mBluetoothAdapter.getName() + " Address : "
                + mBluetoothAdapter.getAddress() + " Scan Mode --" + mBluetoothAdapter.getScanMode());
        // ATTENTION: This was auto-generated to implement the App Indexing API.
        // See https://g.co/AppIndexing/AndroidStudio for more information.
        client = new GoogleApiClient.Builder(this).addApi(AppIndex.API).build();
    }

    public void OnButtonClick(View v) {
        Log.i("info", "OnButtonclick....");
        if (mBluetoothAdapter == null) {
            mBluetoothAdapter = BluetoothAdapter.getDefaultAdapter();
        }

        //--------------------------------------------------------------
        if (!mBluetoothAdapter.isEnabled())//判断蓝牙设备是否已开起
        {
            //开起蓝牙设备
            //Intent intent = new Intent(BluetoothAdapter.ACTION_REQUEST_ENABLE);
            //startActivity(intent);
            boolean result = mBluetoothAdapter.enable();
            Log.i("info", "open.........");
        }

        Log.i("info", "connectthread start.........");
    }

    public void OnButtonClose(View v) {
        Log.i("info", "OnButtonclose....");
        if (mBluetoothAdapter == null) {
            mBluetoothAdapter = BluetoothAdapter.getDefaultAdapter();
        }

        //--------------------------------------------------------------
        if (mBluetoothAdapter.isEnabled())//判断蓝牙设备是否已开起
        {
            //开起蓝牙设备
            //Intent intent = new Intent(BluetoothAdapter.ACTION_REQUEST_ENABLE);
            //startActivity(intent);
            boolean result = mBluetoothAdapter.disable();
            Log.i("info", "closing.........");
        }

        Log.i("info", "close bluetooth.........");
    }

    public void OnButtonScan(View v) {
        Log.i("info", "OnButtonScan....");
        if (!mBluetoothAdapter.isDiscovering()) {
            Log.i("info", "btCancelDiscovery ### the bluetooth dont't discovering");
            mBluetoothAdapter.startDiscovery();
        }
        //      else
        //         toast("Sacn ---- ");

        Log.i("info", "Scan bluetooth.........");
    }

    public void OnButtonScanStop(View v) {
        Log.i("info", "OnScanStopScan....");
        if (mBluetoothAdapter.isDiscovering()) {
            Log.i("info", "btCancelDiscovery ### the bluetooth is isDiscovering");
            mBluetoothAdapter.cancelDiscovery();
        }
        //       else
        //           toast("Stop ---- ");

        Log.i("info", "Stop Scan bluetooth.........");
    }

    @Override
    public void onStart() {
        super.onStart();

        // ATTENTION: This was auto-generated to implement the App Indexing API.
        // See https://g.co/AppIndexing/AndroidStudio for more information.
        client.connect();
        Action viewAction = Action.newAction(
                Action.TYPE_VIEW, // TODO: choose an action type.
                "Main Page", // TODO: Define a title for the content shown.
                // TODO: If you have web page content that matches this app activity's content,
                // make sure this auto-generated web page URL is correct.
                // Otherwise, set the URL to null.
                Uri.parse("http://host/path"),
                // TODO: Make sure this auto-generated app URL is correct.
                Uri.parse("android-app://com.ray.test3/http/host/path")
        );
        AppIndex.AppIndexApi.start(client, viewAction);
    }

    @Override
    public void onStop() {
        super.onStop();

        // ATTENTION: This was auto-generated to implement the App Indexing API.
        // See https://g.co/AppIndexing/AndroidStudio for more information.
        Action viewAction = Action.newAction(
                Action.TYPE_VIEW, // TODO: choose an action type.
                "Main Page", // TODO: Define a title for the content shown.
                // TODO: If you have web page content that matches this app activity's content,
                // make sure this auto-generated web page URL is correct.
                // Otherwise, set the URL to null.
                Uri.parse("http://host/path"),
                // TODO: Make sure this auto-generated app URL is correct.
                Uri.parse("android-app://com.ray.test3/http/host/path")
        );
        AppIndex.AppIndexApi.end(client, viewAction);
        client.disconnect();
    }
    private BroadcastReceiver BluetoothReciever = new BroadcastReceiver()
    {
        @Override
        public void onReceive(Context context, Intent intent)
        {
            // TODO Auto-generated method stub
            if (BluetoothAdapter.ACTION_STATE_CHANGED.equals(intent.getAction()))
            {
                Log.v(TAG, "### Bluetooth State has changed ##");

                int btState = intent.getIntExtra(BluetoothAdapter.EXTRA_STATE,
                        BluetoothAdapter.STATE_OFF);

                //printBTState(btState);
            }
            else if(BluetoothAdapter.ACTION_SCAN_MODE_CHANGED.equals(intent.getAction()))
            {
                Log.v(TAG, "### ACTION_SCAN_MODE_CHANGED##");
                int cur_mode_state = intent.getIntExtra(BluetoothAdapter.EXTRA_SCAN_MODE, BluetoothAdapter.SCAN_MODE_NONE);
                int previous_mode_state = intent.getIntExtra(BluetoothAdapter.EXTRA_PREVIOUS_SCAN_MODE, BluetoothAdapter.SCAN_MODE_NONE);

                Log.v(TAG, "### cur_mode_state ##" + cur_mode_state + " ~~ previous_mode_state" + previous_mode_state);

            }

        }

    };

    //����ɨ��ʱ�Ĺ㲥������
    private BroadcastReceiver BTDiscoveryReceiver = new BroadcastReceiver()
    {
        @Override
        public void onReceive(Context context, Intent intent)
        {
            // TODO Auto-generated method stub
            String action = intent.getAction();
            Log.v(TAG, action);
            if (BluetoothAdapter.ACTION_DISCOVERY_STARTED.equals(action))
            {
                Log.v(TAG, "### BT ACTION_DISCOVERY_STARTED ##");
            }
            else if(BluetoothAdapter.ACTION_DISCOVERY_FINISHED.equals(action))
            {
                Log.v(TAG, "### BT ACTION_DISCOVERY_FINISHED ##");
            }
            else if(BluetoothDevice.ACTION_FOUND.equals(action))
            {
                Log.v(TAG, "### BT BluetoothDevice.ACTION_FOUND ##");

                BluetoothDevice btDevice = intent.getParcelableExtra(BluetoothDevice.EXTRA_DEVICE);

                if(btDevice != null)
                    Log.v(TAG , "Name : " + btDevice.getName() + " Address: " + btDevice.getAddress());

            }
            else if(BluetoothDevice.ACTION_BOND_STATE_CHANGED.equals(action))
            {
                Log.v(TAG, "### BT ACTION_BOND_STATE_CHANGED ##");

                int cur_bond_state = intent.getIntExtra(BluetoothDevice.EXTRA_BOND_STATE, BluetoothDevice.BOND_NONE);
                int previous_bond_state = intent.getIntExtra(BluetoothDevice.EXTRA_PREVIOUS_BOND_STATE, BluetoothDevice.BOND_NONE);


                Log.v(TAG, "### cur_bond_state ##" + cur_bond_state + " ~~ previous_bond_state" + previous_bond_state);
            }
        }

    };
    protected void onDestroy() {
        super.onDestroy();
        if (mBluetoothAdapter != null) {
            mBluetoothAdapter.cancelDiscovery();
        }
        this.unregisterReceiver(BluetoothReciever);
        this.unregisterReceiver(BTDiscoveryReceiver);
    }
    private void doDiscovery() {

        if (mBluetoothAdapter.isDiscovering()) {
            mBluetoothAdapter.cancelDiscovery();
        }
        mBluetoothAdapter.startDiscovery();
    }

    private void toast(String str)
    {
        Toast.makeText(MainActivity.this, str, Toast.LENGTH_SHORT).show();
    }
}
