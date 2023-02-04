/*
 * Copyright (C) 2013 The Android Open Source Project
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package com.robocat.android.rc;

import android.Manifest;
import android.app.Service;
import android.bluetooth.BluetoothAdapter;
import android.bluetooth.BluetoothClass;
import android.bluetooth.BluetoothDevice;
import android.bluetooth.BluetoothManager;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.content.res.Resources;
import android.os.Binder;
import android.os.Build;
import android.os.Handler;
import android.os.IBinder;
import android.support.annotation.Nullable;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.UUID;

public class BluetoothService extends Service {
    private final static String TAG = BluetoothService.class.getCanonicalName();

    public final static UUID deviceUUID = UUID.fromString(TAG);

    // BLUETOOTH ADAPTER STATUSES
    private static final int STATUS_NOT_SUPPORTED = -1;
    private static final int STATUS_NOT_AVAILABLE = 0;
    private static final int STATUS_NOT_ENABLED = 1;
    private static final int STATUS_ENABLED = 2;
    private static final int STATUS_SCANNING = 3;

    // BLUETOOTH INTENT REQUESTS
    private static final int REQUEST_ENABLE_BT = 1;
    private static final int REQUEST_SCAN_MODE = 2;

    private static final int STATE_DISCONNECTED = 0;
    private static final int STATE_SEARCHING = 1;
    private static final int STATE_CONNECTING = 2;
    private static final int STATE_CONNECTED = 3;

    private BluetoothAdapter bluetoothAdapter;
    private int state = STATE_DISCONNECTED;
    private String[] permissions;

    @Override
    public void onCreate() {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            // Explicitly request bluetooth permissions
            Resources resources = this.getResources();
            ArrayList<String> permissionsToCheck = new ArrayList<>(Arrays.asList(
                Manifest.permission.BLUETOOTH,
                Manifest.permission.BLUETOOTH_ADMIN,
                resources.getString(R.string.permission_bluetooth_scan),
                resources.getString(R.string.permission_bluetooth_connect)
            ));
            for (String permission : permissionsToCheck) {
                if (PackageManager.PERMISSION_GRANTED == checkCallingPermission(permission)) {
                    permissionsToCheck.remove(permission);
                    continue;
                }
                if (shouldShowRequestPermissionRationale(permission)) {
                    System.out.print("Should show request permission rationale for ");
                    System.out.println(permission);
                }
            }
            this.requestPermissions(this.permissions, REQUEST_SCAN_MODE);
        }
        int adapterStatus = this.initializeBluetoothAdapter();
        if (adapterStatus != STATUS_ENABLED) {
            return;
        }

    }

    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {
        return Service.START_STICKY_COMPATIBILITY;
    }

    @Nullable
    @Override
    public IBinder onBind(Intent intent) {
        return this.api;
    }

    private final BluetoothService.Api api = new BluetoothService.Api(this);
    public class Api extends Binder {
        private BluetoothService service;
        public Api(BluetoothService service) {
            this.service = service;
        }
        BluetoothService getService() {
            return this.service;
        }

        BluetoothDevice[] scan(Handler handler) {

        }
    }

    private final BroadcastReceiver bluetoothReceiver = new BroadcastReceiver() {
        @Override
        public void onReceive(Context context, Intent intent) {
            DeviceScanActivity self = (DeviceScanActivity) context;
            String action = intent.getAction();
            if (BluetoothAdapter.ACTION_STATE_CHANGED.equals(action)) {
                // State changed
                int previousState = intent.getIntExtra(BluetoothAdapter.EXTRA_PREVIOUS_STATE, BluetoothAdapter.STATE_OFF);
                int state = intent.getIntExtra(BluetoothAdapter.EXTRA_STATE, BluetoothAdapter.STATE_OFF);
            } else if (BluetoothAdapter.ACTION_DISCOVERY_STARTED.equals(action)) {
                // Discovery started
            } else if (BluetoothAdapter.ACTION_SCAN_MODE_CHANGED.equals(action)) {
                // Scan mode changed
            } else if (BluetoothAdapter.ACTION_DISCOVERY_FINISHED.equals(action)) {
                // Discovery finished
            } else if (BluetoothDevice.ACTION_FOUND.equals(action)) {
                BluetoothDevice foundDevice = (BluetoothDevice) intent.getParcelableExtra(BluetoothDevice.EXTRA_DEVICE);
                if (foundDevice != null) {
                    self.bluetoothDevices.add(foundDevice);
                }
                BluetoothClass foundClass = (BluetoothClass) intent.getParcelableExtra(BluetoothDevice.EXTRA_CLASS);

            } else if (BluetoothAdapter.ACTION_STATE_CHANGED.equals(action)) {

            }
        }
    };

    private int initializeBluetoothAdapter() {
        // If bluetooth is not available on this device, notify the user
        if (!getPackageManager().hasSystemFeature(PackageManager.FEATURE_BLUETOOTH)) {
            Toast.makeText(this, R.string.error_bluetooth_not_supported, Toast.LENGTH_SHORT).show();
            return STATUS_NOT_SUPPORTED;
        }

        final BluetoothManager bluetoothManager = (BluetoothManager)getSystemService(Context.BLUETOOTH_SERVICE);
        this.bluetoothAdapter = bluetoothManager.getAdapter();

        // Checks if Bluetooth is supported on the device.
        if (this.bluetoothAdapter == null) {
            // TODO find a way to reschedule this method
            Toast.makeText(this, R.string.error_bluetooth_adapter_failed, Toast.LENGTH_SHORT).show();
            return STATUS_NOT_AVAILABLE;
        }

        if (!this.bluetoothAdapter.isEnabled()) {
            startActivityForResult(new Intent(BluetoothAdapter.ACTION_REQUEST_ENABLE), REQUEST_ENABLE_BT);
            return STATUS_NOT_ENABLED;
        }
        return STATUS_ENABLED;
    }
}
