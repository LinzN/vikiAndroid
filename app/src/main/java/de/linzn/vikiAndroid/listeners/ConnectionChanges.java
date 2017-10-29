package de.linzn.vikiAndroid.listeners;

import java.util.UUID;

import de.linzn.jSocket.core.ConnectionListener;
import de.linzn.vikiAndroid.VikiAndroid;


public class ConnectionChanges implements ConnectionListener {
    private VikiAndroid vikiAndroid;

    public ConnectionChanges(VikiAndroid vikiAndroid) {
        this.vikiAndroid = vikiAndroid;
    }

    @Override
    public void onConnectEvent(UUID clientUUID) {
        System.out.println("Connected!!!");
        vikiAndroid.setOutputText("Connected to Viki Framework");

    }

    @Override
    public void onDisconnectEvent(UUID clientUUID) {
        System.out.println("Disconnected!!!");
        vikiAndroid.setOutputText("Disconnected from Viki Framework");

    }
}
