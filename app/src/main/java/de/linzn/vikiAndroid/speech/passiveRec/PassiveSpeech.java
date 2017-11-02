package de.linzn.vikiAndroid.speech.passiveRec;


import android.content.Intent;
import android.speech.RecognizerIntent;
import android.speech.SpeechRecognizer;

import viki_android.linzn.de.viki_android.MainActivity;


public class PassiveSpeech {
    public MainActivity mainActivity;
    private SpeechRecognizer googleSpeechRecognizer;
    private Intent recognizerIntent;
    private PassiveSpeechListener passiveSpeechListener;


    public PassiveSpeech(MainActivity mainActivity) {
        this.mainActivity = mainActivity;
        this.passiveSpeechListener = new PassiveSpeechListener(this);
        this.googleSpeechRecognizer = SpeechRecognizer.createSpeechRecognizer(mainActivity);
        this.recognizerIntent = new Intent(RecognizerIntent.ACTION_RECOGNIZE_SPEECH);

        this.recognizerIntent.putExtra(RecognizerIntent.EXTRA_LANGUAGE_MODEL,
                RecognizerIntent.LANGUAGE_MODEL_FREE_FORM);
        this.googleSpeechRecognizer.setRecognitionListener(this.passiveSpeechListener);
    }

    public boolean startListening() {
        if (!this.passiveSpeechListener.isActive) {
            this.googleSpeechRecognizer.startListening(recognizerIntent);
            return true;
        }
        return false;
    }

    public boolean stopListening() {
        if (this.passiveSpeechListener.isActive) {
            this.googleSpeechRecognizer.stopListening();
            return true;
        }
        return false;
    }

    public boolean isListening() {
        return this.passiveSpeechListener.isActive;
    }
}
