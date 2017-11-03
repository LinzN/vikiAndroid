package de.linzn.vikiAndroid;

import android.os.Handler;

import java.util.Timer;
import java.util.TimerTask;

import de.linzn.jSocket.client.JClientConnection;
import de.linzn.vikiAndroid.listeners.ConnectionChanges;
import de.linzn.vikiAndroid.listeners.DataInputChannelDefault;
import de.linzn.vikiAndroid.listeners.DataInputChannelLeegianOS;
import de.linzn.vikiAndroid.speech.activeRec.ActiveSpeech;
import de.linzn.vikiAndroid.speech.passiveRec.PassiveSpeech;
import de.linzn.vikiAndroid.speech.voiceSyn.SoundOutput;
import viki_android.linzn.de.viki_android.MainActivity;

public class VikiAndroid {
    public MainActivity mainActivity;
    public JClientConnection jClientConnection;
    public PassiveSpeech passiveSpeech;
    public ActiveSpeech activeSpeech;
    public SoundOutput soundOutput;

    public VikiAndroid(MainActivity mainActivity) {
        this.mainActivity = mainActivity;
        jClientConnection = new JClientConnection("viki.lan", 11102);
        this.registerEvents();
        this.checkStatusInfo();
        this.registerSpeechInterfaces();
    }

    public void start_VikiAndroid() {
        jClientConnection.setEnable();
    }

    public void stop_VikiAndroid() {
        jClientConnection.setDisable();
    }

    private void registerEvents() {
        jClientConnection.registerIncomingDataListener("voiceChannel", new DataInputChannelDefault(this));
        jClientConnection.registerIncomingDataListener("leegianOSData", new DataInputChannelLeegianOS(this));
        jClientConnection.registerConnectionListener(new ConnectionChanges(this));
    }

    private void checkStatusInfo() {
        Handler handler = new Handler();
        new Timer().schedule(new TimerTask() {
            @Override
            public void run() {
                handler.post(() -> {
                    mainActivity.guiOptions.setGuiOnline(jClientConnection.isValidConnection());
                });
            }
        }, 0, 200);
    }

    private void registerSpeechInterfaces() {
        this.activeSpeech = new ActiveSpeech(this.mainActivity);
        this.passiveSpeech = new PassiveSpeech(this.mainActivity);
        this.soundOutput = new SoundOutput(this.mainActivity);
    }

    public void setSpeechMode(int mode) {
        switch (mode) {
            case 0:
                System.out.println("SpeechMode disable");
                this.mainActivity.guiOptions.setGuiState(3);
                this.activeSpeech.stopListening();
                this.passiveSpeech.stopListening();
                break;
            case 1:
                System.out.println("SpeechMode active");
                this.mainActivity.guiOptions.setGuiState(0);
                this.activeSpeech.stopListening();
                this.passiveSpeech.stopListening();
                this.activeSpeech.startListening();
                break;
            case 2:
                System.out.println("SpeechMode passive");
                this.mainActivity.guiOptions.setGuiState(2);
                this.passiveSpeech.stopListening();
                this.activeSpeech.stopListening();
                this.passiveSpeech.startListening();
                break;
            default:
                System.out.println("SpeechMode unknown");
                this.setSpeechMode(0);
                break;
        }
    }
}
