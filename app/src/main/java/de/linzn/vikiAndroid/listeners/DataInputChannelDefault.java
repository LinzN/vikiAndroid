package de.linzn.vikiAndroid.listeners;


import android.media.MediaPlayer;
import android.os.Handler;
import android.os.Looper;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
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
        System.out.print("Receive Voice");
        new Handler(Looper.getMainLooper()).post(() -> {
            playByteArray(dataInBytes);
        });

    }

    public void playByteArray(byte[] mp3SoundByteArray) {
        try {
            File mytemp = File.createTempFile("TCL", "mp3", this.vikiAndroid.mainActivity.getCacheDir());
            mytemp.deleteOnExit();
            FileOutputStream fos = new FileOutputStream(mytemp);
            fos.write(mp3SoundByteArray);
            fos.close();

            MediaPlayer mediaPlayer = new MediaPlayer();

            FileInputStream myFile = new FileInputStream(mytemp);
            mediaPlayer.setDataSource(myFile.getFD());

            mediaPlayer.prepare();
            mediaPlayer.start();
        } catch (IOException ex) {
            String s = ex.toString();
            ex.printStackTrace();
        }
    }

}
