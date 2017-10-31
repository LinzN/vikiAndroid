package de.linzn.vikiAndroid;

import android.os.Handler;

import java.util.Timer;
import java.util.TimerTask;

import de.linzn.jSocket.client.JClientConnection;
import de.linzn.vikiAndroid.listeners.ConnectionChanges;
import de.linzn.vikiAndroid.listeners.DataInputChannelDefault;
import viki_android.linzn.de.viki_android.MainActivity;

public class VikiAndroid {
    public MainActivity mainActivity;
    private JClientConnection jClientConnection;
    private boolean old_online_status = false;

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
        Handler handler = new Handler();
        new Timer().schedule(new TimerTask() {
            @Override
            public void run() {
                handler.post(() -> {
                    if (old_online_status != jClientConnection.isValidConnection()) {
                        mainActivity.setGuiOnline(jClientConnection.isValidConnection());
                        old_online_status = jClientConnection.isValidConnection();
                    }
                });
            }
        }, 0, 200);
    }
}
