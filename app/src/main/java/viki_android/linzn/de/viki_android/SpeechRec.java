package viki_android.linzn.de.viki_android;

import android.content.Intent;
import android.os.Bundle;
import android.speech.RecognitionListener;
import android.speech.RecognizerIntent;
import android.speech.SpeechRecognizer;

import java.io.ByteArrayOutputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.util.ArrayList;


public class SpeechRec {


    private SpeechRecognizer mSpeechRecognizer;
    private Intent mSpeechRecognizerIntent;
    private MainActivity mainActivity;


    public SpeechRec(MainActivity mainActivity) {
        this.mainActivity = mainActivity;
        mSpeechRecognizer = SpeechRecognizer.createSpeechRecognizer(mainActivity);
        mSpeechRecognizerIntent = new Intent(RecognizerIntent.ACTION_RECOGNIZE_SPEECH);
        mSpeechRecognizerIntent.putExtra(RecognizerIntent.EXTRA_LANGUAGE_MODEL,
                RecognizerIntent.LANGUAGE_MODEL_FREE_FORM);
        mSpeechRecognizerIntent.putExtra(RecognizerIntent.EXTRA_CALLING_PACKAGE,
                mainActivity.getPackageName());
        mSpeechRecognizer.setRecognitionListener(new SpeechRecognitionListener());
        mSpeechRecognizer.startListening(mSpeechRecognizerIntent);

    }


    protected class SpeechRecognitionListener implements RecognitionListener {

        @Override
        public void onBeginningOfSpeech() {
            System.out.println("Start speech");
        }

        @Override
        public void onBufferReceived(byte[] buffer) {

        }

        @Override
        public void onEndOfSpeech() {
            System.out.println("End speech");
        }

        @Override
        public void onError(int error) {
            //mSpeechRecognizer.startListening(mSpeechRecognizerIntent);
            System.out.println("Error");
            //Log.d(TAG, "error = " + error);
        }

        @Override
        public void onEvent(int eventType, Bundle params) {

        }

        @Override
        public void onPartialResults(Bundle partialResults) {
        }

        @Override
        public void onReadyForSpeech(Bundle params) {
        }

        @Override
        public void onResults(Bundle results) {
            ArrayList<String> suggestedWords = results.getStringArrayList(SpeechRecognizer.RESULTS_RECOGNITION);
            System.out.println(suggestedWords.get(1));

            ByteArrayOutputStream byteOut = new ByteArrayOutputStream();
            DataOutputStream out = new DataOutputStream(byteOut);
            try {
                out.writeUTF(suggestedWords.get(1));
            } catch (IOException e) {
                e.printStackTrace();
            }
            mainActivity.vikiAndroid.jClientConnection.writeOutput("terminalChannel", byteOut.toByteArray());
        }

        @Override
        public void onRmsChanged(float rmsdB) {

        }

    }
}