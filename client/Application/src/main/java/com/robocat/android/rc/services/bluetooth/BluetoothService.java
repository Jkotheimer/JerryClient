/*
 * MIT License
 *
 * Copyright (c) 2015 Douglas Nassif Roma Junior
 *
 * Permission is hereby granted, free of charge, to any person obtaining a copy
 * of this software and associated documentation files (the "Software"), to deal
 * in the Software without restriction, including without limitation the rights
 * to use, copy, modify, merge, publish, distribute, sublicense, and/or sell
 * copies of the Software, and to permit persons to whom the Software is
 * furnished to do so, subject to the following conditions:
 *
 * The above copyright notice and this permission notice shall be included in all
 * copies or substantial portions of the Software.
 *
 * THE SOFTWARE IS PROVIDED "AS IS", WITHOUT WARRANTY OF ANY KIND, EXPRESS OR
 * IMPLIED, INCLUDING BUT NOT LIMITED TO THE WARRANTIES OF MERCHANTABILITY,
 * FITNESS FOR A PARTICULAR PURPOSE AND NONINFRINGEMENT. IN NO EVENT SHALL THE
 * AUTHORS OR COPYRIGHT HOLDERS BE LIABLE FOR ANY CLAIM, DAMAGES OR OTHER
 * LIABILITY, WHETHER IN AN ACTION OF CONTRACT, TORT OR OTHERWISE, ARISING FROM,
 * OUT OF OR IN CONNECTION WITH THE SOFTWARE OR THE USE OR OTHER DEALINGS IN THE
 * SOFTWARE.
 */

package com.robocat.android.rc.services.bluetooth;

import android.app.Service;
import android.bluetooth.BluetoothDevice;
import android.os.Handler;
import android.util.Log;

/**
 * Created by douglas on 23/03/15.
 * Extended by jack on 04/02/23.
 */
public abstract class BluetoothService extends Service {
    private static final String TAG = BluetoothService.class.getSimpleName();
    protected static final boolean D = true;

    protected static BluetoothService defaultServiceInstance;
    protected BluetoothConfiguration mConfig;
    protected BluetoothStatus mStatus;

    protected Handler handler;

    protected OnBluetoothEventCallback onEventCallback;
    protected OnBluetoothScanCallback onScanCallback;

    public void setOnEventCallback(OnBluetoothEventCallback onEventCallback) {
        this.onEventCallback = onEventCallback;
    }

    public void setOnScanCallback(OnBluetoothScanCallback onScanCallback) {
        this.onScanCallback = onScanCallback;
    }

    public BluetoothConfiguration getConfiguration() {
        return mConfig;
    }

    protected synchronized void updateState(final BluetoothStatus status) {
        Log.v(TAG, "updateStatus() " + this.mStatus + " -> " + status);
        this.mStatus = status;

        // Give the new state to the Handler so the UI Activity can update
        if (onEventCallback != null) {
            runOnMainThread(() -> onEventCallback.onStatusChange(status));
        }
    }

    protected void runOnMainThread(final Runnable runnable, final long delayMillis) {
        if (mConfig.callListenersInMainThread) {
            if (delayMillis > 0) {
                handler.postDelayed(runnable, delayMillis);
            } else {
                handler.post(runnable);
            }
        } else {
            if (delayMillis > 0) {
                new Thread() {
                    @Override
                    public void run() {
                        try {
                            Thread.sleep(delayMillis);
                            runnable.run();
                        } catch (InterruptedException e) {
                            e.printStackTrace();
                        }
                    }
                }.start();
            } else {
                runnable.run();
            }
        }
    }

    protected void runOnMainThread(Runnable runnable) {
        runOnMainThread(runnable, 0);
    }

    protected void removeRunnableFromHandler(Runnable runnable) {
        handler.removeCallbacks(runnable);
    }

    /**
     * Current BluetoothService status.
     * @return BluetoothStatus
     */
    public synchronized BluetoothStatus getStatus() {
        return mStatus;
    }

    /**
     * Start scan process and call the {@link OnBluetoothScanCallback}
     */
    public abstract void startScan();

    /**
     * Stop scan process and call the {@link OnBluetoothScanCallback}
     */
    public abstract void stopScan();

    /**
     * Try to connect to the device and call the {@link OnBluetoothEventCallback}
     */
    public abstract void connect(BluetoothDevice device);

    /**
     * Try to disconnect to the device and call the {@link OnBluetoothEventCallback}
     */
    public abstract void disconnect();

    /**
     * Write a array of bytes to the connected device.
     */
    public abstract void write(byte[] bytes);

    /**
     * Stops the BluetoothService and turn it unusable.
     */
    public abstract void stopService();

    /**
     * Request the connection priority.
     */
    public abstract void requestConnectionPriority(int connectionPriority);

    /* ====================================
                STATICS METHODS
     ====================================== */


    /* ====================================
                    CALLBACKS
     ====================================== */

    public interface OnBluetoothEventCallback {
        void onDataRead(byte[] buffer, int length);

        void onStatusChange(BluetoothStatus status);

        void onDeviceName(String deviceName);

        void onToast(String message);

        void onDataWrite(byte[] buffer);
    }

    public interface OnBluetoothScanCallback {
        void onDeviceDiscovered(BluetoothDevice device, int rssi);

        void onStartScan();

        void onStopScan();
    }
}
