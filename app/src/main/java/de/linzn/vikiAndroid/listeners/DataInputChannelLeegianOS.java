package de.linzn.vikiAndroid.listeners;


import java.io.ByteArrayInputStream;
import java.io.DataInputStream;
import java.io.IOException;
import java.util.UUID;

import de.linzn.jSocket.core.IncomingDataListener;
import de.linzn.vikiAndroid.VikiAndroid;

public class DataInputChannelLeegianOS implements IncomingDataListener {

    private VikiAndroid vikiAndroid;

    public DataInputChannelLeegianOS(VikiAndroid vikiAndroid) {
        this.vikiAndroid = vikiAndroid;
    }

    @Override
    public void onEvent(String channel, UUID clientUUID, byte[] dataInBytes) {
        try {
            DataInputStream in = new DataInputStream(new ByteArrayInputStream(dataInBytes));
            String leegianOSVersion = in.readUTF();
            vikiAndroid.mainActivity.guiOptions.setGuiData(leegianOSVersion);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }


}
