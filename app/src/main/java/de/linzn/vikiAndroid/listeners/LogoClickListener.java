package de.linzn.vikiAndroid.listeners;

import android.view.View;

import viki_android.linzn.de.viki_android.MainActivity;


public class LogoClickListener implements View.OnClickListener {

    private MainActivity mainActivity;

    public LogoClickListener(MainActivity mainActivity) {
        this.mainActivity = mainActivity;
    }

    @Override
    public void onClick(View view) {
        this.mainActivity.guiOptions.setInfoView(5, "You can speak now...", true);
        this.mainActivity.vikiAndroid.setSpeechMode(2);
    }
}
