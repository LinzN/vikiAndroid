package de.linzn.vikiAndroid.listeners;


import java.util.UUID;

import de.linzn.jSocket.core.IncomingDataListener;
import de.linzn.vikiAndroid.VikiAndroid;

public class DataInputChannelDefault implements IncomingDataListener {

    private VikiAndroid vikiAndroid;

    public DataInputChannelDefault(VikiAndroid vikiAndroid) {
        this.vikiAndroid = vikiAndroid;
    }

    @Override
    public void onEvent(String channel, UUID clientUUID, byte[] dataInBytes) {
        System.out.println("Receive Voice");
        this.vikiAndroid.soundOutput.playSounds(dataInBytes);
    }


}
