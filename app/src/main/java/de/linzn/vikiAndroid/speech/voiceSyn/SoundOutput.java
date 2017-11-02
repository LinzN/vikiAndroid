package de.linzn.vikiAndroid.speech.voiceSyn;


import android.media.MediaPlayer;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;

import viki_android.linzn.de.viki_android.MainActivity;

public class SoundOutput {
    private MainActivity mainActivity;
    private MediaPlayer mediaPlayer;

    public SoundOutput(MainActivity mainActivity) {
        this.mainActivity = mainActivity;
        this.mediaPlayer = new MediaPlayer();

    }

    public void playSounds(byte[] dataInBytes) {

        try {
            mediaPlayer.reset();
            File tempFile = File.createTempFile("VIS", "mp3", this.mainActivity.vikiAndroid.mainActivity.getCacheDir());
            tempFile.deleteOnExit();
            FileOutputStream fos = new FileOutputStream(tempFile);
            fos.write(dataInBytes);
            fos.close();
            mediaPlayer.setDataSource(new FileInputStream(tempFile).getFD());
            mediaPlayer.setOnCompletionListener(mediaPlayer -> {
                System.out.println("Sound complete");
                this.mainActivity.vikiAndroid.setSpeechMode(1);
            });
            mediaPlayer.setOnPreparedListener(mediaPlayer -> {
                System.out.println("Sound prepare");
                this.mainActivity.vikiAndroid.setSpeechMode(0);
            });
            mediaPlayer.prepare();
            mediaPlayer.start();



        } catch (IOException ex) {
            ex.printStackTrace();
        }

    }

}
