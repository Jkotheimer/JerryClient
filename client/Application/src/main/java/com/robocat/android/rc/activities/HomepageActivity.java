package com.robocat.android.rc.activities;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.os.PersistableBundle;

import com.robocat.android.rc.persistence.entities.RemoteDevice;
import com.robocat.android.rc.services.BluetoothService;

import java.util.List;

public class HomepageActivity extends Activity {

    public static final String ACTION_GETTING_STARTED = "GETTING_STARTED";
    public static final String ACTION_PLAYGROUND = "PLAYGROUND";
    public static final String ACTION_ERROR_STATE = "ERROR_STATE";

    public static final String EXTRA_REMOTE_DEVICES = "REMOTE_DEVICES";

    private BluetoothService bluetoothService;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Intent intent = this.getIntent();
        String action = intent.getAction();
        if (ACTION_GETTING_STARTED.equals(action)) {
            // Display Get Started button
        } else if (ACTION_PLAYGROUND.equals(action)) {
            // Display available devices & attempt to connect to the default one
            List<RemoteDevice> devices = intent.getParcelableExtra(EXTRA_REMOTE_DEVICES);
        } else if (ACTION_ERROR_STATE.equals(action)) {

        }
    }
}
