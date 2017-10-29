package de.linzn.vikiAndroid;

import android.graphics.Color;
import android.os.Handler;
import android.os.Looper;

import de.linzn.jSocket.client.JClientConnection;
import de.linzn.jSocket.core.TaskRunnable;
import de.linzn.vikiAndroid.listeners.ConnectionChanges;
import de.linzn.vikiAndroid.listeners.DataInputChannelDefault;
import viki_android.linzn.de.viki_android.MainActivity;

public class VikiAndroid {
    public MainActivity mainActivity;
    private JClientConnection jClientConnection;

    public VikiAndroid(MainActivity mainActivity) {
        this.mainActivity = mainActivity;
        jClientConnection = new JClientConnection("viki.lan", 11102);
        this.registerEvents();
        this.checkStatusInfo();
    }

    public void start_VikiAndroid() {
        jClientConnection.setEnable();
    }

    public void stop_VikiAndroid() {
        jClientConnection.setDisable();
    }

    private void registerEvents() {
        jClientConnection.registerIncomingDataListener("voiceChannel", new DataInputChannelDefault(this));
        jClientConnection.registerConnectionListener(new ConnectionChanges(this));
    }

    private void checkStatusInfo() {
        Runnable runnable = () -> {
            while (true) {
                if (jClientConnection.isValidConnection()) {
                    new Handler(Looper.getMainLooper()).post(() -> mainActivity.imageSwitcher.setBackgroundColor(Color.GREEN));
                } else {
                    new Handler(Looper.getMainLooper()).post(() -> mainActivity.imageSwitcher.setBackgroundColor(Color.RED));
                }
                try {
                    Thread.sleep(200);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
        };
        new TaskRunnable().runSingleThreadExecutor(runnable);
    }
}
