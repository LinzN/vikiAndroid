package de.linzn.vikiAndroid.speech.activeRec;


import android.annotation.SuppressLint;
import android.os.AsyncTask;

import java.io.File;
import java.io.IOException;

import edu.cmu.pocketsphinx.Assets;
import edu.cmu.pocketsphinx.SpeechRecognizerSetup;
import viki_android.linzn.de.viki_android.MainActivity;

public class ActiveSpeech {
    static final String KEYPHRASE = "hey viki";
    private static final String KWS_SEARCH = "wakeup";
    MainActivity mainActivity;
    private boolean isActive;
    private edu.cmu.pocketsphinx.SpeechRecognizer sphinxSpeechRecognizer;


    public ActiveSpeech(MainActivity mainActivity) {
        this.mainActivity = mainActivity;
        runRecognizerSetup();
    }

    public void startListening() {
        if (!this.isActive) {
            this.isActive = true;
            this.sphinxSpeechRecognizer.startListening(KWS_SEARCH);
        } else {
            System.out.println("Already active");
        }
    }

    public void stopListening() {
        this.isActive = false;
        this.sphinxSpeechRecognizer.cancel();
    }


    @SuppressLint("StaticFieldLeak")
    private void runRecognizerSetup() {

        new AsyncTask<Void, Void, Exception>() {
            @Override
            protected Exception doInBackground(Void... params) {
                try {
                    Assets assets = new Assets(mainActivity);

                    //Performs the synchronization of assets in the application and external storage
                    File assetDir = assets.syncAssets();

                    //Creates a new speech recognizer builder with default configuration
                    SpeechRecognizerSetup speechRecognizerSetup = SpeechRecognizerSetup.defaultSetup();

                    speechRecognizerSetup.setAcousticModel(new File(assetDir, "en-us-ptm"));
                    speechRecognizerSetup.setDictionary(new File(assetDir, "cmudict-en-us.dict"));

                    // Threshold to tune for keyphrase to balance between false alarms and misses
                    speechRecognizerSetup.setKeywordThreshold(1e-30F);

                    //Creates a new SpeechRecognizer object based on previous set up.
                    sphinxSpeechRecognizer = speechRecognizerSetup.getRecognizer();

                    // Create keyword-activation search.
                    sphinxSpeechRecognizer.addListener(new ActiveSpeechListener(mainActivity.vikiAndroid.activeSpeech));
                    sphinxSpeechRecognizer.addKeyphraseSearch(KWS_SEARCH, KEYPHRASE);
                } catch (IOException e) {
                    return e;
                }
                return null;
            }

            @Override
            protected void onPostExecute(Exception result) {
                if (result != null) {
                    System.out.println("Failed to init recognizer " + result);
                } else {
                    mainActivity.vikiAndroid.setSpeechMode(1);

                }
            }
        }.execute();
    }


}
