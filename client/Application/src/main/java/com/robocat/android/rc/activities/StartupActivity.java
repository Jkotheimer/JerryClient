package com.robocat.android.rc.activities;

import android.app.Activity;
import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.os.Parcelable;
import android.os.PersistableBundle;
import android.view.View;
import android.widget.ProgressBar;

import com.robocat.android.rc.R;
import com.robocat.android.rc.persistence.ApplicationDatabase;
import com.robocat.android.rc.persistence.entities.RemoteDevice;
import com.robocat.android.rc.persistence.entities.RemoteDeviceDao;

import java.util.ArrayList;
import java.util.List;

import io.reactivex.disposables.Disposable;
import io.reactivex.observers.DisposableSingleObserver;

public class StartupActivity extends Activity {

    private final long TIMEOUT_MILLIS = 10000;

    private ProgressBar spinner;
    private RemoteDeviceDao dao;
    private Disposable databaseDisposable;

    @Override
    public void onCreate(Bundle savedInstanceState, PersistableBundle persistentState) {
        super.onCreate(savedInstanceState, persistentState);
        this.setContentView(R.layout.spinner);

        this.spinner = (ProgressBar) findViewById(R.id.overlaySpinner);
        this.spinner.setVisibility(View.VISIBLE);
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            this.spinner.setMin(0);
        }
        this.spinner.setMax(1);

        this.dao = ApplicationDatabase.getInstance(this.getApplicationContext()).remoteDeviceDao();

        this.databaseDisposable = this.dao.getAllRemoteDevices().subscribeWith(new DisposableSingleObserver<List<RemoteDevice>>() {
            private final StartupActivity self = StartupActivity.this;
            @Override
            public void onSuccess(List<RemoteDevice> remoteDevices) {
                self.spinner.incrementProgressBy(1);
                Intent intent = new Intent();
                intent.putParcelableArrayListExtra(
                        HomepageActivity.EXTRA_REMOTE_DEVICES,
                        (ArrayList<RemoteDevice>) remoteDevices
                );
                if (remoteDevices.isEmpty()) {
                    intent.setAction(HomepageActivity.ACTION_GETTING_STARTED);
                } else {
                    intent.setAction(HomepageActivity.ACTION_PLAYGROUND);
                }
                self.startActivity(intent);
            }
            @Override
            public void onError(Throwable e) {
                self.startActivity(new Intent(HomepageActivity.ACTION_GETTING_STARTED));
            }
        });

        try {
            Thread.sleep(this.TIMEOUT_MILLIS);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        this.databaseDisposable.dispose();
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        this.databaseDisposable.dispose();
    }

    @Override
    protected void onStop() {
        super.onStop();
    }
}
