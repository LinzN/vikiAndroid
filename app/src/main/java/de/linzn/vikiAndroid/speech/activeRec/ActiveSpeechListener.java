package de.linzn.vikiAndroid.speech.activeRec;


import edu.cmu.pocketsphinx.Hypothesis;

import static de.linzn.vikiAndroid.speech.activeRec.ActiveSpeech.KEYPHRASE;

public class ActiveSpeechListener implements edu.cmu.pocketsphinx.RecognitionListener {

    private ActiveSpeech activeSpeech;

    public ActiveSpeechListener(ActiveSpeech activeSpeech) {
        this.activeSpeech = activeSpeech;
    }


    @Override
    public void onBeginningOfSpeech() {
    }


    @Override
    public void onPartialResult(Hypothesis hypothesis) {
        if (hypothesis == null) {
            return;
        }
        String text = hypothesis.getHypstr();
        if (text.equals(KEYPHRASE)) {
            System.out.println("You said: " + text);
            this.activeSpeech.mainActivity.vikiAndroid.setSpeechMode(2);
            return;
        }
    }

    @Override
    public void onResult(Hypothesis hypothesis) {
    }

    @Override
    public void onEndOfSpeech() {
    }

    public void onError(Exception error) {
        this.activeSpeech.mainActivity.vikiAndroid.setSpeechMode(1);
    }

    @Override
    public void onTimeout() {
        System.out.println("onTimeout");
        this.activeSpeech.mainActivity.vikiAndroid.setSpeechMode(1);
    }


}
