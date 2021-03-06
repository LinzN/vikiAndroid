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
        System.out.println("Connected to Viki framework");
        vikiAndroid.mainActivity.guiOptions.setInfoView(2, "Connected to Viki on LeegianOS", false);
    }

    @Override
    public void onDisconnectEvent(UUID clientUUID) {
        System.out.println("Disconnected from Viki framework");
        vikiAndroid.mainActivity.guiOptions.setInfoView(2, "Disconnected from Viki on LeegianOS", false);
    }
}
