package de.linzn.vikiAndroid;


import android.media.AudioManager;
import android.media.SoundPool;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;

public class VikiSound {
    private VikiAndroid vikiAndroid;

    public VikiSound(VikiAndroid vikiAndroid) {
        this.vikiAndroid = vikiAndroid;

    }

    public void playSoundFromBytes(byte[] dataInBytes) {

        try {
            File tempFile = File.createTempFile("VIS", "mp3", this.vikiAndroid.mainActivity.getCacheDir());
            tempFile.deleteOnExit();
            FileOutputStream fos = new FileOutputStream(tempFile);
            fos.write(dataInBytes);
            fos.close();
            SoundPool sp = new SoundPool(5, AudioManager.STREAM_MUSIC, 0);
            int soundId = sp.load(new FileInputStream(tempFile).getFD(), 0, dataInBytes.length, 2);
            sp.setOnLoadCompleteListener((soundPool, i, i1) -> sp.play(soundId, 1, 1, 0, 0, 1.03F));
        } catch (IOException ex) {
            ex.printStackTrace();
        }

    }

}
