package de.linzn.vikiAndroid;

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
                this.mainActivity.setGuiOnline(jClientConnection.isValidConnection());
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
