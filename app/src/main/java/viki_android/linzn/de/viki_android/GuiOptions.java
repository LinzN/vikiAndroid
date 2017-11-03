package viki_android.linzn.de.viki_android;


import android.graphics.Color;
import android.os.Handler;
import android.os.Looper;

import java.util.Timer;
import java.util.TimerTask;
import java.util.UUID;

public class GuiOptions {
    private MainActivity mainActivity;
    private UUID uuidForInfoView;

    public GuiOptions(MainActivity mainActivity) {
        this.mainActivity = mainActivity;
    }

    public synchronized void setGuiData(String osVersion) {
        new Handler(Looper.getMainLooper()).post(() -> {
            mainActivity.osVersion.setText(osVersion);
        });
    }

    /* Change Gui state things */
    public synchronized void setInfoView(int time, String text, boolean setWorking) {
        Handler handler = new Handler(Looper.getMainLooper());
        UUID refInfoViewUUID = UUID.randomUUID();
        this.uuidForInfoView = refInfoViewUUID;
        handler.post(() -> {
            mainActivity.infoView.setText(text);
        });
        new Timer().schedule(new TimerTask() {
            @Override
            public void run() {
                handler.post(() -> {
                    if (uuidForInfoView == refInfoViewUUID) {
                        mainActivity.infoView.setText("");
                    }
                });
            }
        }, time * 1000);
    }

    public synchronized void setGuiOnline(boolean value) {
        if (this.mainActivity.isDestroyed == value) {
            if (value) {
                mainActivity.isDestroyed = false;
                this.setGuiState(0);
            } else {
                mainActivity.isDestroyed = true;
                this.setGuiState(1);
            }
        }
    }


    public synchronized void setGuiState(int mode) {
        switch (mode) {
            case 0: //default state (active listening)
                System.out.println("guiState 0");
                mainActivity.imageSwitcher.setBackgroundColor(Color.GREEN);
                mainActivity.circularRing1.setColor(mainActivity.getResources().getColor(R.color.ringOrange));
                mainActivity.circularRing1.setBackgroundColor(mainActivity.getResources().getColor(R.color.ringOrange));
                mainActivity.circularRing2.setColor(mainActivity.getResources().getColor(R.color.ringOrange));
                mainActivity.circularRing3.setColor(mainActivity.getResources().getColor(R.color.ringOrange));
                mainActivity.currentRingSpeed1 = mainActivity.fixedRingSpeed1;
                mainActivity.currentRingSpeed2 = mainActivity.fixedRingSpeed2;
                break;
            case 1: //offline state (disconnected)
                System.out.println("guiState 1");
                mainActivity.imageSwitcher.setBackgroundColor(Color.RED);
                mainActivity.circularRing1.setColor(Color.GRAY);
                mainActivity.circularRing1.setBackgroundColor(Color.GRAY);
                mainActivity.circularRing2.setColor(Color.GRAY);
                mainActivity.circularRing3.setColor(Color.GRAY);
                mainActivity.currentRingSpeed1 = mainActivity.fixedRingSpeed1 * -0.1F;
                mainActivity.currentRingSpeed2 = mainActivity.fixedRingSpeed2 * -0.1F;
                break;
            case 2: //passive state (listening input)
                System.out.println("guiState 2");
                mainActivity.imageSwitcher.setBackgroundColor(Color.GREEN);
                mainActivity.circularRing1.setColor(mainActivity.getResources().getColor(R.color.ringOrange));
                mainActivity.circularRing1.setBackgroundColor(mainActivity.getResources().getColor(R.color.ringOrange));
                mainActivity.circularRing2.setColor(mainActivity.getResources().getColor(R.color.ringOrange));
                mainActivity.circularRing3.setColor(mainActivity.getResources().getColor(R.color.ringOrange));
                mainActivity.currentRingSpeed1 = mainActivity.fixedRingSpeed1;
                mainActivity.currentRingSpeed2 = mainActivity.fixedRingSpeed2 * 5.5F;
                break;
            case 3: //sound state (playback speech output)
                System.out.println("guiState 3");
                mainActivity.imageSwitcher.setBackgroundColor(Color.GREEN);
                mainActivity.circularRing1.setColor(mainActivity.getResources().getColor(R.color.ringOrange));
                mainActivity.circularRing1.setBackgroundColor(mainActivity.getResources().getColor(R.color.ringOrange));
                mainActivity.circularRing2.setColor(mainActivity.getResources().getColor(R.color.ringOrange));
                mainActivity.circularRing3.setColor(mainActivity.getResources().getColor(R.color.ringOrange));
                mainActivity.currentRingSpeed1 = mainActivity.fixedRingSpeed1 * -0.7F;
                mainActivity.currentRingSpeed2 = mainActivity.fixedRingSpeed2 * -0.7F;
                break;
            default:
                this.setGuiState(0);
                break;
        }
    }


}
