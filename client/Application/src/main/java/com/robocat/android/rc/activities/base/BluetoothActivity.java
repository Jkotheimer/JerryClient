package com.robocat.android.rc.activities.base;

import android.app.Activity;
import android.app.Service;
import android.content.ComponentName;
import android.content.Intent;
import android.content.ServiceConnection;
import android.os.Bundle;
import android.os.IBinder;
import android.support.annotation.Nullable;

import com.robocat.android.rc.services.BluetoothClassicService;
import com.robocat.android.rc.services.BluetoothService;

public abstract class BluetoothActivity extends Activity {

    protected BluetoothService bluetoothService;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    protected void onBluetoothServiceStart()

    private void attachService() {
        Intent serviceIntent = new Intent(this, BluetoothClassicService.class);
        bindService(serviceIntent, connection, Service.BIND_AUTO_CREATE);
    }

    private void detachService() {
        unbindService(this.connection);
    }

    private ServiceConnection connection = new ServiceConnection() {
        private final BluetoothActivity self = BluetoothActivity.this;

        @Override
        public void onServiceConnected(ComponentName name, IBinder service) {
            self.bluetoothService = BluetoothService.getDefaultInstance();
        }

        @Override
        public void onServiceDisconnected(ComponentName name) {

        }
    }
}
