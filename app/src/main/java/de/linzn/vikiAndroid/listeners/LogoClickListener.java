package de.linzn.vikiAndroid.listeners;

import android.view.View;

import java.io.ByteArrayOutputStream;
import java.io.DataOutputStream;
import java.io.IOException;

import viki_android.linzn.de.viki_android.MainActivity;


public class LogoClickListener implements View.OnClickListener {

    private MainActivity mainActivity;

    public LogoClickListener(MainActivity mainActivity) {
        this.mainActivity = mainActivity;
    }

    @Override
    public void onClick(View view) {
        this.mainActivity.guiOptions.setInfoView(5, "You can speak now...", true);
        ByteArrayOutputStream byteOut = new ByteArrayOutputStream();
        DataOutputStream out = new DataOutputStream(byteOut);
        try {
            out.writeUTF("wetter heute");
        } catch (IOException e) {
            e.printStackTrace();
        }

        mainActivity.vikiAndroid.jClientConnection.writeOutput("terminalChannel", byteOut.toByteArray());
    }
}
