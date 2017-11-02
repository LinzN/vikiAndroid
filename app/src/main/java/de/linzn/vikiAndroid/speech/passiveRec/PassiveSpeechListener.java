package de.linzn.vikiAndroid.speech.passiveRec;


import android.os.Bundle;
import android.speech.RecognitionListener;
import android.speech.SpeechRecognizer;

import java.io.ByteArrayOutputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.util.ArrayList;

public class PassiveSpeechListener implements RecognitionListener {

    public boolean isActive;
    private PassiveSpeech passiveSpeech;

    public PassiveSpeechListener(PassiveSpeech passiveSpeech) {
        this.passiveSpeech = passiveSpeech;
    }

    @Override
    public void onReadyForSpeech(Bundle bundle) {
        System.out.println("onReadyForSpeech");
    }

    @Override
    public void onBeginningOfSpeech() {
        this.isActive = true;
        System.out.println("onBeginningOfSpeech");
    }

    @Override
    public void onRmsChanged(float v) {
    }

    @Override
    public void onBufferReceived(byte[] bytes) {
        System.out.println("onBufferReceived");
    }

    @Override
    public void onEndOfSpeech() {
        this.isActive = false;
        System.out.println("onEndOfSpeech");
    }

    @Override
    public void onError(int error) {
        System.out.println("onError");
        if (error == android.speech.SpeechRecognizer.ERROR_SPEECH_TIMEOUT
                || error == android.speech.SpeechRecognizer.ERROR_NO_MATCH) {
            this.passiveSpeech.mainActivity.vikiAndroid.setSpeechMode(1);
            this.isActive = false;
        }
    }

    @Override
    public void onResults(Bundle bundle) {
        this.isActive = false;
        System.out.println("onResults");
        ArrayList<String> result = bundle.getStringArrayList(SpeechRecognizer.RESULTS_RECOGNITION);
        System.out.println("onResults: " + result.get(0));
        ByteArrayOutputStream byteOut = new ByteArrayOutputStream();
        DataOutputStream out = new DataOutputStream(byteOut);
        try {
            out.writeUTF(result.get(0));
        } catch (IOException e) {
            e.printStackTrace();
        }
        this.passiveSpeech.mainActivity.vikiAndroid.jClientConnection.writeOutput("terminalChannel", byteOut.toByteArray());
        this.passiveSpeech.mainActivity.vikiAndroid.setSpeechMode(1);
    }

    @Override
    public void onPartialResults(Bundle bundle) {
        System.out.println("onPartialResults");

    }

    @Override
    public void onEvent(int i, Bundle bundle) {
        System.out.println("onEvent");
    }
}
