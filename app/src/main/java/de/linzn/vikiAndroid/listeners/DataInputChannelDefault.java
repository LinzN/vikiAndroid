package de.linzn.vikiAndroid.listeners;


import java.io.ByteArrayInputStream;
import java.io.DataInputStream;
import java.io.IOException;
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
        DataInputStream in = new DataInputStream(new ByteArrayInputStream(dataInBytes));
        String text = null;
        boolean needResponse = false;
        byte[] bytes;
        try {
            needResponse = in.readBoolean();
            text = in.readUTF();
            bytes = new byte[in.available()];
            in.readFully(bytes);
        } catch (IOException e) {
            e.printStackTrace();
        }
        this.vikiAndroid.soundOutput.playSounds(dataInBytes, needResponse);
    }


}
